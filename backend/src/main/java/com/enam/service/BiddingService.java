package com.enam.service;

import com.enam.dto.BidRequest;
import com.enam.dto.BidResponse;
import com.enam.model.Bid;
import com.enam.model.CommodityListing;

import java.util.List;
import java.util.Map;

public interface BiddingService {
    
    /**
     * Place a bid on a commodity listing
     */
    BidResponse placeBid(BidRequest bidRequest, String username) throws Exception;
    
    /**
     * Get all bids for a specific listing
     */
    List<Bid> getBidsForListing(Long listingId);
    
    /**
     * Get the highest bid for a listing
     */
    Bid getHighestBid(Long listingId);
    
    /**
     * Get all bids placed by a user
     */
    List<Bid> getUserBids(String username);
    
    /**
     * Start auction for a listing
     */
    CommodityListing startAuction(Long listingId, String username) throws Exception;
    
    /**
     * End auction for a listing
     */
    CommodityListing endAuction(Long listingId, String username) throws Exception;
    
    /**
     * Get auction statistics
     */
    Map<String, Object> getAuctionStatistics();
    
    /**
     * Check if user can bid on a listing
     */
    boolean canUserBid(Long listingId, String username);
    
    /**
     * Get winning bid for a listing
     */
    Bid getWinningBid(Long listingId);
    
    /**
     * Cancel a bid
     */
    void cancelBid(Long bidId, String username) throws Exception;
    
    /**
     * Get real-time auction data
     */
    Map<String, Object> getAuctionData(Long listingId);
}