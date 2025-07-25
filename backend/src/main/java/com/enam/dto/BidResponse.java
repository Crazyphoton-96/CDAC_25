package com.enam.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BidResponse {
    
    private Long bidId;
    private Long listingId;
    private String bidderName;
    private BigDecimal bidAmount;
    private BigDecimal quantity;
    private LocalDateTime bidTime;
    private Boolean isWinning;
    private String status;
    private String message;
    
    // Additional auction information
    private BigDecimal currentHighestBid;
    private String commodityName;
    private String farmerName;
    private String mandiName;
    private Integer totalBids;
    private LocalDateTime auctionEndTime;
    
    // Constructors
    public BidResponse() {}
    
    public BidResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getBidId() { return bidId; }
    public void setBidId(Long bidId) { this.bidId = bidId; }
    
    public Long getListingId() { return listingId; }
    public void setListingId(Long listingId) { this.listingId = listingId; }
    
    public String getBidderName() { return bidderName; }
    public void setBidderName(String bidderName) { this.bidderName = bidderName; }
    
    public BigDecimal getBidAmount() { return bidAmount; }
    public void setBidAmount(BigDecimal bidAmount) { this.bidAmount = bidAmount; }
    
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    
    public LocalDateTime getBidTime() { return bidTime; }
    public void setBidTime(LocalDateTime bidTime) { this.bidTime = bidTime; }
    
    public Boolean getIsWinning() { return isWinning; }
    public void setIsWinning(Boolean isWinning) { this.isWinning = isWinning; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public BigDecimal getCurrentHighestBid() { return currentHighestBid; }
    public void setCurrentHighestBid(BigDecimal currentHighestBid) { this.currentHighestBid = currentHighestBid; }
    
    public String getCommodityName() { return commodityName; }
    public void setCommodityName(String commodityName) { this.commodityName = commodityName; }
    
    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }
    
    public String getMandiName() { return mandiName; }
    public void setMandiName(String mandiName) { this.mandiName = mandiName; }
    
    public Integer getTotalBids() { return totalBids; }
    public void setTotalBids(Integer totalBids) { this.totalBids = totalBids; }
    
    public LocalDateTime getAuctionEndTime() { return auctionEndTime; }
    public void setAuctionEndTime(LocalDateTime auctionEndTime) { this.auctionEndTime = auctionEndTime; }
}