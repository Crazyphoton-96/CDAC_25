package com.enam.repository;

import com.enam.model.CommodityListing;
import com.enam.model.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommodityListingRepository extends JpaRepository<CommodityListing, Long> {
    
    List<CommodityListing> findByStatus(ListingStatus status);
    
    List<CommodityListing> findByFarmerId(Long farmerId);
    
    List<CommodityListing> findByMandiId(Long mandiId);
    
    List<CommodityListing> findByCommodityId(Long commodityId);
    
    @Query("SELECT cl FROM CommodityListing cl WHERE cl.status = 'ACTIVE'")
    List<CommodityListing> findActiveAuctions();
    
    @Query("SELECT cl FROM CommodityListing cl WHERE cl.auctionEndTime < ?1 AND cl.status = 'ACTIVE'")
    List<CommodityListing> findExpiredAuctions(LocalDateTime currentTime);
    
    @Query("SELECT cl FROM CommodityListing cl WHERE cl.mandi.state = ?1")
    List<CommodityListing> findByState(String state);
    
    @Query("SELECT cl FROM CommodityListing cl WHERE cl.commodity.category = ?1")
    List<CommodityListing> findByCommodityCategory(String category);
    
    Long countByStatus(ListingStatus status);
    
    @Query("SELECT COUNT(cl) FROM CommodityListing cl WHERE DATE(cl.listingDate) = CURRENT_DATE")
    Long countTodayListings();
    
    @Query("SELECT cl FROM CommodityListing cl WHERE cl.auctionStartTime <= ?1 AND cl.auctionEndTime >= ?1")
    List<CommodityListing> findActiveAuctionsAtTime(LocalDateTime time);
}