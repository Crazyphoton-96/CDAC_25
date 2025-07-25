package com.enam.repository;

import com.enam.model.Bid;
import com.enam.model.BidStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    
    List<Bid> findByListingIdOrderByBidAmountDesc(Long listingId);
    
    List<Bid> findByBidderIdOrderByBidTimeDesc(Long bidderId);
    
    Bid findTopByListingIdOrderByBidAmountDesc(Long listingId);
    
    Bid findByListingIdAndIsWinning(Long listingId, Boolean isWinning);
    
    List<Bid> findTop5ByListingIdOrderByBidTimeDesc(Long listingId);
    
    List<Bid> findByStatus(BidStatus status);
    
    Long countByListingId(Long listingId);
    
    @Query("SELECT COUNT(b) FROM Bid b WHERE DATE(b.bidTime) = CURRENT_DATE")
    Long countBidsToday();
    
    @Query("SELECT b FROM Bid b WHERE b.listing.id = ?1 AND b.bidTime >= ?2")
    List<Bid> findBidsAfterTime(Long listingId, LocalDateTime time);
    
    @Query("SELECT MAX(b.bidAmount) FROM Bid b WHERE b.listing.id = ?1")
    java.math.BigDecimal findMaxBidAmountForListing(Long listingId);
    
    @Query("SELECT COUNT(DISTINCT b.bidder.id) FROM Bid b WHERE b.listing.id = ?1")
    Long countUniqueBiddersForListing(Long listingId);
    
    @Query("SELECT b FROM Bid b WHERE b.bidder.id = ?1 AND b.status = ?2")
    List<Bid> findByBidderIdAndStatus(Long bidderId, BidStatus status);
}