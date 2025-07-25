package com.enam.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "commodities")
public class Commodity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String name;
    
    @Size(max = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private CommodityCategory category;
    
    @NotBlank
    @Size(max = 20)
    private String unit; // kg, quintal, ton, etc.
    
    @Positive
    private BigDecimal minimumSupportPrice; // MSP
    
    @Size(max = 200)
    private String qualityParameters;
    
    private String imageUrl;
    
    private Boolean isActive = true;
    
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    // Relationships
    @OneToMany(mappedBy = "commodity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommodityListing> listings;
    
    // Constructors
    public Commodity() {}
    
    public Commodity(String name, CommodityCategory category, String unit, BigDecimal minimumSupportPrice) {
        this.name = name;
        this.category = category;
        this.unit = unit;
        this.minimumSupportPrice = minimumSupportPrice;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public CommodityCategory getCategory() { return category; }
    public void setCategory(CommodityCategory category) { this.category = category; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public BigDecimal getMinimumSupportPrice() { return minimumSupportPrice; }
    public void setMinimumSupportPrice(BigDecimal minimumSupportPrice) { this.minimumSupportPrice = minimumSupportPrice; }
    
    public String getQualityParameters() { return qualityParameters; }
    public void setQualityParameters(String qualityParameters) { this.qualityParameters = qualityParameters; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<CommodityListing> getListings() { return listings; }
    public void setListings(List<CommodityListing> listings) { this.listings = listings; }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

enum CommodityCategory {
    CEREALS,           // Rice, Wheat, Maize, etc.
    PULSES,            // Lentils, Chickpea, etc.
    OILSEEDS,          // Groundnut, Mustard, etc.
    VEGETABLES,        // Tomato, Onion, Potato, etc.
    FRUITS,            // Apple, Mango, Banana, etc.
    SPICES,            // Turmeric, Coriander, etc.
    CASH_CROPS,        // Cotton, Sugarcane, etc.
    MILLETS,           // Little Millet, Kodo Millet, etc.
    OTHERS             // Miscellaneous items
}