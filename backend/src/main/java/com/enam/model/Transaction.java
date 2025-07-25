package com.enam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private CommodityListing listing;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
    
    @NotNull
    @Positive
    private BigDecimal finalPrice;
    
    @NotNull
    @Positive
    private BigDecimal quantity;
    
    @NotNull
    @Positive
    private BigDecimal totalAmount;
    
    private BigDecimal marketFee;
    
    private BigDecimal commissionFee;
    
    private BigDecimal netAmountToFarmer;
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    
    private String transactionId; // Unique transaction identifier
    
    private String paymentReference;
    
    private LocalDateTime transactionDate = LocalDateTime.now();
    
    private LocalDateTime paymentDate;
    
    private LocalDateTime deliveryDate;
    
    private String remarks;
    
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Constructors
    public Transaction() {}
    
    public Transaction(CommodityListing listing, User buyer, User seller, 
                      BigDecimal finalPrice, BigDecimal quantity) {
        this.listing = listing;
        this.buyer = buyer;
        this.seller = seller;
        this.finalPrice = finalPrice;
        this.quantity = quantity;
        this.totalAmount = finalPrice.multiply(quantity);
        this.status = TransactionStatus.PENDING;
        this.paymentStatus = PaymentStatus.PENDING;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public CommodityListing getListing() { return listing; }
    public void setListing(CommodityListing listing) { this.listing = listing; }
    
    public User getBuyer() { return buyer; }
    public void setBuyer(User buyer) { this.buyer = buyer; }
    
    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
    
    public BigDecimal getFinalPrice() { return finalPrice; }
    public void setFinalPrice(BigDecimal finalPrice) { this.finalPrice = finalPrice; }
    
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public BigDecimal getMarketFee() { return marketFee; }
    public void setMarketFee(BigDecimal marketFee) { this.marketFee = marketFee; }
    
    public BigDecimal getCommissionFee() { return commissionFee; }
    public void setCommissionFee(BigDecimal commissionFee) { this.commissionFee = commissionFee; }
    
    public BigDecimal getNetAmountToFarmer() { return netAmountToFarmer; }
    public void setNetAmountToFarmer(BigDecimal netAmountToFarmer) { this.netAmountToFarmer = netAmountToFarmer; }
    
    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }
    
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getPaymentReference() { return paymentReference; }
    public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }
    
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDateTime deliveryDate) { this.deliveryDate = deliveryDate; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

enum TransactionStatus {
    PENDING,        // Transaction created
    CONFIRMED,      // Transaction confirmed
    PAYMENT_PENDING, // Waiting for payment
    PAID,           // Payment completed
    DELIVERED,      // Goods delivered
    COMPLETED,      // Transaction completed
    CANCELLED,      // Transaction cancelled
    DISPUTED        // In dispute
}

enum PaymentStatus {
    PENDING,        // Payment pending
    PROCESSING,     // Payment processing
    COMPLETED,      // Payment completed
    FAILED,         // Payment failed
    REFUNDED        // Payment refunded
}