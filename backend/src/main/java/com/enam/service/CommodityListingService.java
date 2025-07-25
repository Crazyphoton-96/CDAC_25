package com.enam.service;

import com.enam.model.CommodityListing;
import com.enam.model.ListingStatus;

import java.util.List;

public interface CommodityListingService {
    
    /**
     * Get all active auctions
     */
    List<CommodityListing> getActiveAuctions();
    
    /**
     * Get listings by status
     */
    List<CommodityListing> getListingsByStatus(ListingStatus status);
    
    /**
     * Get listing by ID
     */
    CommodityListing getListingById(Long id);
    
    /**
     * Create new listing
     */
    CommodityListing createListing(CommodityListing listing);
    
    /**
     * Update listing
     */
    CommodityListing updateListing(CommodityListing listing);
    
    /**
     * Get farmer's listings
     */
    List<CommodityListing> getFarmerListings(Long farmerId);
    
    /**
     * Get mandi listings
     */
    List<CommodityListing> getMandiListings(Long mandiId);
}