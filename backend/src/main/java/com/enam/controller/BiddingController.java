package com.enam.controller;

import com.enam.dto.BidRequest;
import com.enam.dto.BidResponse;
import com.enam.model.Bid;
import com.enam.model.CommodityListing;
import com.enam.service.BiddingService;
import com.enam.service.CommodityListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bidding")
@CrossOrigin(origins = "http://localhost:3000")
public class BiddingController {

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private CommodityListingService listingService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // REST endpoint for placing bids
    @PostMapping("/place")
    public ResponseEntity<?> placeBid(@Valid @RequestBody BidRequest bidRequest, 
                                     Authentication authentication) {
        try {
            String username = authentication.getName();
            BidResponse bidResponse = biddingService.placeBid(bidRequest, username);
            
            // Broadcast bid update to all subscribers
            messagingTemplate.convertAndSend(
                "/topic/auction/" + bidRequest.getListingId(), 
                bidResponse
            );
            
            return ResponseEntity.ok(bidResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // WebSocket endpoint for real-time bidding
    @MessageMapping("/bid")
    @SendTo("/topic/auction")
    public BidResponse handleBid(BidRequest bidRequest) throws Exception {
        // This will be enhanced to include authentication context
        return biddingService.placeBid(bidRequest, "websocket-user");
    }

    // Get all bids for a listing
    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<Bid>> getBidsForListing(@PathVariable Long listingId) {
        List<Bid> bids = biddingService.getBidsForListing(listingId);
        return ResponseEntity.ok(bids);
    }

    // Get current highest bid for a listing
    @GetMapping("/listing/{listingId}/highest")
    public ResponseEntity<Bid> getHighestBid(@PathVariable Long listingId) {
        Bid highestBid = biddingService.getHighestBid(listingId);
        return ResponseEntity.ok(highestBid);
    }

    // Get user's bids
    @GetMapping("/user/my-bids")
    public ResponseEntity<List<Bid>> getUserBids(Authentication authentication) {
        String username = authentication.getName();
        List<Bid> userBids = biddingService.getUserBids(username);
        return ResponseEntity.ok(userBids);
    }

    // Get active auctions
    @GetMapping("/active-auctions")
    public ResponseEntity<List<CommodityListing>> getActiveAuctions() {
        List<CommodityListing> activeAuctions = listingService.getActiveAuctions();
        return ResponseEntity.ok(activeAuctions);
    }

    // Start auction for a listing
    @PostMapping("/start-auction/{listingId}")
    public ResponseEntity<?> startAuction(@PathVariable Long listingId, Authentication authentication) {
        try {
            String username = authentication.getName();
            CommodityListing listing = biddingService.startAuction(listingId, username);
            
            // Broadcast auction start
            messagingTemplate.convertAndSend(
                "/topic/auction-status", 
                "Auction started for listing: " + listingId
            );
            
            return ResponseEntity.ok(listing);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // End auction for a listing
    @PostMapping("/end-auction/{listingId}")
    public ResponseEntity<?> endAuction(@PathVariable Long listingId, Authentication authentication) {
        try {
            String username = authentication.getName();
            CommodityListing listing = biddingService.endAuction(listingId, username);
            
            // Broadcast auction end
            messagingTemplate.convertAndSend(
                "/topic/auction-status", 
                "Auction ended for listing: " + listingId
            );
            
            return ResponseEntity.ok(listing);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get auction statistics
    @GetMapping("/stats")
    public ResponseEntity<?> getAuctionStats() {
        return ResponseEntity.ok(biddingService.getAuctionStatistics());
    }
}