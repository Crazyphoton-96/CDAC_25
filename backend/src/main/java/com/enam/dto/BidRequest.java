package com.enam.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class BidRequest {
    
    @NotNull(message = "Listing ID is required")
    private Long listingId;
    
    @NotNull(message = "Bid amount is required")
    @Positive(message = "Bid amount must be positive")
    private BigDecimal bidAmount;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private BigDecimal quantity;
    
    private String remarks;
    
    // Constructors
    public BidRequest() {}
    
    public BidRequest(Long listingId, BigDecimal bidAmount, BigDecimal quantity) {
        this.listingId = listingId;
        this.bidAmount = bidAmount;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Long getListingId() { return listingId; }
    public void setListingId(Long listingId) { this.listingId = listingId; }
    
    public BigDecimal getBidAmount() { return bidAmount; }
    public void setBidAmount(BigDecimal bidAmount) { this.bidAmount = bidAmount; }
    
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}