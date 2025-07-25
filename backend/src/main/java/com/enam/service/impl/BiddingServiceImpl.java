package com.enam.service.impl;

import com.enam.dto.BidRequest;
import com.enam.dto.BidResponse;
import com.enam.model.*;
import com.enam.repository.*;
import com.enam.service.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class BiddingServiceImpl implements BiddingService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private CommodityListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public BidResponse placeBid(BidRequest bidRequest, String username) throws Exception {
        // Validate user
        User bidder = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate listing
        CommodityListing listing = listingRepository.findById(bidRequest.getListingId())
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        // Validate auction is active
        if (!listing.getStatus().equals(ListingStatus.ACTIVE)) {
            throw new RuntimeException("Auction is not active");
        }

        // Check auction end time
        if (listing.getAuctionEndTime() != null && 
            LocalDateTime.now().isAfter(listing.getAuctionEndTime())) {
            throw new RuntimeException("Auction has ended");
        }

        // Validate bidder is not the farmer
        if (listing.getFarmer().getId().equals(bidder.getId())) {
            throw new RuntimeException("Farmers cannot bid on their own listings");
        }

        // Validate bid amount
        Bid currentHighestBid = getHighestBid(bidRequest.getListingId());
        BigDecimal minimumBidAmount = currentHighestBid != null ? 
                currentHighestBid.getBidAmount() : listing.getBasePrice();

        if (bidRequest.getBidAmount().compareTo(minimumBidAmount) <= 0) {
            throw new RuntimeException("Bid amount must be higher than current highest bid");
        }

        // Check reserve price if set
        if (listing.getReservePrice() != null && 
            bidRequest.getBidAmount().compareTo(listing.getReservePrice()) < 0) {
            throw new RuntimeException("Bid amount is below reserve price");
        }

        // Update previous winning bid
        if (currentHighestBid != null) {
            currentHighestBid.setIsWinning(false);
            currentHighestBid.setStatus(BidStatus.OUTBID);
            bidRepository.save(currentHighestBid);
        }

        // Create new bid
        Bid newBid = new Bid(listing, bidder, bidRequest.getBidAmount(), bidRequest.getQuantity());
        newBid.setIsWinning(true);
        newBid.setStatus(BidStatus.WINNING);
        newBid.setRemarks(bidRequest.getRemarks());
        
        Bid savedBid = bidRepository.save(newBid);

        // Create response
        BidResponse response = createBidResponse(savedBid, listing);
        response.setMessage("Bid placed successfully");
        response.setStatus("SUCCESS");

        // Broadcast to WebSocket subscribers
        broadcastBidUpdate(listing.getId(), response);

        return response;
    }

    @Override
    public List<Bid> getBidsForListing(Long listingId) {
        return bidRepository.findByListingIdOrderByBidAmountDesc(listingId);
    }

    @Override
    public Bid getHighestBid(Long listingId) {
        return bidRepository.findTopByListingIdOrderByBidAmountDesc(listingId);
    }

    @Override
    public List<Bid> getUserBids(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bidRepository.findByBidderIdOrderByBidTimeDesc(user.getId());
    }

    @Override
    public CommodityListing startAuction(Long listingId, String username) throws Exception {
        CommodityListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        // Validate user can start auction (admin or farmer)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getUserType().equals(UserType.ADMIN) && 
            !listing.getFarmer().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to start auction");
        }

        if (!listing.getStatus().equals(ListingStatus.APPROVED)) {
            throw new RuntimeException("Listing must be approved before starting auction");
        }

        // Set auction times
        listing.setAuctionStartTime(LocalDateTime.now());
        listing.setAuctionEndTime(LocalDateTime.now().plusHours(2)); // 2-hour auction
        listing.setStatus(ListingStatus.ACTIVE);

        CommodityListing savedListing = listingRepository.save(listing);

        // Broadcast auction start
        Map<String, Object> auctionStart = new HashMap<>();
        auctionStart.put("listingId", listingId);
        auctionStart.put("status", "STARTED");
        auctionStart.put("endTime", listing.getAuctionEndTime());
        
        messagingTemplate.convertAndSend("/topic/auction-status", auctionStart);

        return savedListing;
    }

    @Override
    public CommodityListing endAuction(Long listingId, String username) throws Exception {
        CommodityListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        // Validate user can end auction
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getUserType().equals(UserType.ADMIN) && 
            !listing.getFarmer().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to end auction");
        }

        Bid winningBid = getHighestBid(listingId);
        
        if (winningBid != null) {
            winningBid.setStatus(BidStatus.WON);
            bidRepository.save(winningBid);
            listing.setStatus(ListingStatus.SOLD);
        } else {
            listing.setStatus(ListingStatus.UNSOLD);
        }

        CommodityListing savedListing = listingRepository.save(listing);

        // Broadcast auction end
        Map<String, Object> auctionEnd = new HashMap<>();
        auctionEnd.put("listingId", listingId);
        auctionEnd.put("status", "ENDED");
        auctionEnd.put("winningBid", winningBid);
        
        messagingTemplate.convertAndSend("/topic/auction-status", auctionEnd);

        return savedListing;
    }

    @Override
    public Map<String, Object> getAuctionStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalActiveAuctions", listingRepository.countByStatus(ListingStatus.ACTIVE));
        stats.put("totalBidsToday", bidRepository.countBidsToday());
        stats.put("totalListings", listingRepository.count());
        stats.put("totalUsers", userRepository.count());
        
        return stats;
    }

    @Override
    public boolean canUserBid(Long listingId, String username) {
        try {
            User user = userRepository.findByUsername(username).orElse(null);
            CommodityListing listing = listingRepository.findById(listingId).orElse(null);
            
            if (user == null || listing == null) return false;
            if (!listing.getStatus().equals(ListingStatus.ACTIVE)) return false;
            if (listing.getFarmer().getId().equals(user.getId())) return false;
            if (listing.getAuctionEndTime() != null && 
                LocalDateTime.now().isAfter(listing.getAuctionEndTime())) return false;
                
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Bid getWinningBid(Long listingId) {
        return bidRepository.findByListingIdAndIsWinning(listingId, true);
    }

    @Override
    public void cancelBid(Long bidId, String username) throws Exception {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new RuntimeException("Bid not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!bid.getBidder().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to cancel this bid");
        }

        if (bid.getIsWinning()) {
            throw new RuntimeException("Cannot cancel winning bid");
        }

        bid.setStatus(BidStatus.CANCELLED);
        bidRepository.save(bid);
    }

    @Override
    public Map<String, Object> getAuctionData(Long listingId) {
        CommodityListing listing = listingRepository.findById(listingId).orElse(null);
        if (listing == null) return new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        data.put("listing", listing);
        data.put("currentHighestBid", getHighestBid(listingId));
        data.put("totalBids", bidRepository.countByListingId(listingId));
        data.put("recentBids", bidRepository.findTop5ByListingIdOrderByBidTimeDesc(listingId));
        data.put("timeRemaining", getTimeRemaining(listing));
        
        return data;
    }

    private BidResponse createBidResponse(Bid bid, CommodityListing listing) {
        BidResponse response = new BidResponse();
        response.setBidId(bid.getId());
        response.setListingId(listing.getId());
        response.setBidderName(bid.getBidder().getFullName());
        response.setBidAmount(bid.getBidAmount());
        response.setQuantity(bid.getQuantity());
        response.setBidTime(bid.getBidTime());
        response.setIsWinning(bid.getIsWinning());
        response.setCurrentHighestBid(bid.getBidAmount());
        response.setCommodityName(listing.getCommodity().getName());
        response.setFarmerName(listing.getFarmer().getFullName());
        response.setMandiName(listing.getMandi().getName());
        response.setTotalBids(bidRepository.countByListingId(listing.getId()).intValue());
        response.setAuctionEndTime(listing.getAuctionEndTime());
        
        return response;
    }

    private void broadcastBidUpdate(Long listingId, BidResponse response) {
        messagingTemplate.convertAndSend("/topic/auction/" + listingId, response);
        messagingTemplate.convertAndSend("/topic/auction-updates", response);
    }

    private long getTimeRemaining(CommodityListing listing) {
        if (listing.getAuctionEndTime() == null) return -1;
        return java.time.Duration.between(LocalDateTime.now(), listing.getAuctionEndTime()).toSeconds();
    }
}