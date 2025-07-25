package com.enam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bids")
public class Bid {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private CommodityListing listing;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id", nullable = false)
    private User bidder;
    
    @NotNull
    @Positive
    private BigDecimal bidAmount;
    
    @NotNull
    @Positive
    private BigDecimal quantity; // Quantity being bid for
    
    @Enumerated(EnumType.STRING)
    private BidStatus status;
    
    private LocalDateTime bidTime = LocalDateTime.now();
    
    private Boolean isWinning = false; // Current highest bid
    
    private String remarks;
    
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Constructors
    public Bid() {}
    
    public Bid(CommodityListing listing, User bidder, BigDecimal bidAmount, BigDecimal quantity) {
        this.listing = listing;
        this.bidder = bidder;
        this.bidAmount = bidAmount;
        this.quantity = quantity;
        this.status = BidStatus.ACTIVE;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public CommodityListing getListing() { return listing; }
    public void setListing(CommodityListing listing) { this.listing = listing; }
    
    public User getBidder() { return bidder; }
    public void setBidder(User bidder) { this.bidder = bidder; }
    
    public BigDecimal getBidAmount() { return bidAmount; }
    public void setBidAmount(BigDecimal bidAmount) { this.bidAmount = bidAmount; }
    
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    
    public BidStatus getStatus() { return status; }
    public void setStatus(BidStatus status) { this.status = status; }
    
    public LocalDateTime getBidTime() { return bidTime; }
    public void setBidTime(LocalDateTime bidTime) { this.bidTime = bidTime; }
    
    public Boolean getIsWinning() { return isWinning; }
    public void setIsWinning(Boolean isWinning) { this.isWinning = isWinning; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

enum BidStatus {
    ACTIVE,     // Active bid
    OUTBID,     // Outbid by higher amount
    WINNING,    // Currently winning
    WON,        // Won the auction
    CANCELLED,  // Cancelled by bidder
    EXPIRED     // Bid expired
}