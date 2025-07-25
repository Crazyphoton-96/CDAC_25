package com.enam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "commodity_listings")
public class CommodityListing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    private User farmer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commodity_id", nullable = false)
    private Commodity commodity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mandi_id", nullable = false)
    private Mandi mandi;
    
    @NotNull
    @Positive
    private BigDecimal quantity;
    
    @NotNull
    @Positive
    private BigDecimal basePrice; // Starting price
    
    private BigDecimal reservePrice; // Minimum acceptable price
    
    @Enumerated(EnumType.STRING)
    private QualityGrade qualityGrade;
    
    private String qualityDescription;
    
    @Enumerated(EnumType.STRING)
    private ListingStatus status;
    
    private LocalDateTime listingDate = LocalDateTime.now();
    
    private LocalDateTime auctionStartTime;
    
    private LocalDateTime auctionEndTime;
    
    private String lotNumber; // Unique lot identifier
    
    private String imageUrls; // Comma-separated URLs
    
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Relationships
    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bid> bids;
    
    @OneToOne(mappedBy = "listing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Transaction transaction;
    
    // Constructors
    public CommodityListing() {}
    
    public CommodityListing(User farmer, Commodity commodity, Mandi mandi, 
                           BigDecimal quantity, BigDecimal basePrice) {
        this.farmer = farmer;
        this.commodity = commodity;
        this.mandi = mandi;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.status = ListingStatus.PENDING;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getFarmer() { return farmer; }
    public void setFarmer(User farmer) { this.farmer = farmer; }
    
    public Commodity getCommodity() { return commodity; }
    public void setCommodity(Commodity commodity) { this.commodity = commodity; }
    
    public Mandi getMandi() { return mandi; }
    public void setMandi(Mandi mandi) { this.mandi = mandi; }
    
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    
    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
    
    public BigDecimal getReservePrice() { return reservePrice; }
    public void setReservePrice(BigDecimal reservePrice) { this.reservePrice = reservePrice; }
    
    public QualityGrade getQualityGrade() { return qualityGrade; }
    public void setQualityGrade(QualityGrade qualityGrade) { this.qualityGrade = qualityGrade; }
    
    public String getQualityDescription() { return qualityDescription; }
    public void setQualityDescription(String qualityDescription) { this.qualityDescription = qualityDescription; }
    
    public ListingStatus getStatus() { return status; }
    public void setStatus(ListingStatus status) { this.status = status; }
    
    public LocalDateTime getListingDate() { return listingDate; }
    public void setListingDate(LocalDateTime listingDate) { this.listingDate = listingDate; }
    
    public LocalDateTime getAuctionStartTime() { return auctionStartTime; }
    public void setAuctionStartTime(LocalDateTime auctionStartTime) { this.auctionStartTime = auctionStartTime; }
    
    public LocalDateTime getAuctionEndTime() { return auctionEndTime; }
    public void setAuctionEndTime(LocalDateTime auctionEndTime) { this.auctionEndTime = auctionEndTime; }
    
    public String getLotNumber() { return lotNumber; }
    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }
    
    public String getImageUrls() { return imageUrls; }
    public void setImageUrls(String imageUrls) { this.imageUrls = imageUrls; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<Bid> getBids() { return bids; }
    public void setBids(List<Bid> bids) { this.bids = bids; }
    
    public Transaction getTransaction() { return transaction; }
    public void setTransaction(Transaction transaction) { this.transaction = transaction; }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

enum ListingStatus {
    PENDING,           // Waiting for approval
    APPROVED,          // Approved for auction
    ACTIVE,            // Currently in auction
    SOLD,              // Successfully sold
    UNSOLD,            // Auction ended without sale
    CANCELLED,         // Cancelled by farmer
    EXPIRED            // Auction time expired
}

enum QualityGrade {
    A_GRADE,           // Premium quality
    B_GRADE,           // Good quality
    C_GRADE,           // Average quality
    REJECT             // Below standard
}