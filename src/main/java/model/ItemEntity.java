package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.Proxy;
import java.io.Serializable;

@Entity
@Table(name = "items")
@NamedQueries({
        @NamedQuery(name = "Item.findAll", query = "SELECT i FROM ItemEntity i"),
        @NamedQuery(name = "Item.findByCategory", query = "SELECT i FROM ItemEntity i WHERE i.category.categoryId = :categoryId"),
        @NamedQuery(name = "Item.findByName", query = "SELECT i FROM ItemEntity i WHERE i.name = :name")
})
@Proxy(lazy = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEntity implements Serializable {
    @Id
    @Column(name = "item_id")
    private String itemId = UUID.randomUUID().toString(); // Automatically generated unique ID

    @Column(name = "VAT")
    private final double VAT = 0.0;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(name = "cost_price")
    private double costPrice;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "unit", columnDefinition = "nvarchar(255)")
    private String unit;

    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now(); // Automatically set the current date and time

    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate = LocalDateTime.now(); // Automatically set the current date and time

    @Column(name = "active")
    private boolean active;

    @Column(name = "selling_price", nullable = false)
    private double sellingPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "item")
    private List<PromotionDetailEntity> promotionDetails;

    // Constructor using UUID for itemId and createdDate
    public ItemEntity(String name, double costPrice, int stockQuantity, String unit, String description, boolean active, CategoryEntity category) {
        this.itemId = UUID.randomUUID().toString(); // Generate unique ID
        this.name = name;
        this.costPrice = costPrice;
        this.stockQuantity = stockQuantity;
        this.unit = unit;
        this.description = description;
        this.active = active;
        this.category = category;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ItemEntity{" + "itemId=" + itemId + ", VAT=" + VAT + ", name=" + name + ", costPrice=" + costPrice + ", stockQuantity=" + stockQuantity + ", unit=" + unit + ", description=" + description + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", active=" + active + ", sellingPrice=" + sellingPrice + '}';
    }

    // VAT calculation and selling price
    public double getSellingPrice() {
        return this.costPrice * (1 + getVAT());
    }

    // Get top discount percentage
    public double getTopDiscountPercentage() {
        return promotionDetails.stream()
                .mapToDouble(PromotionDetailEntity::getDiscountPercentage)
                .max()
                .orElse(0);
    }
}
