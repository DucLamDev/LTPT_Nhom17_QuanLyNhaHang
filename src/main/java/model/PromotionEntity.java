package model;

import common.LevelCustomer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Proxy;
import utillity.CustomerLevelConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "promotions")
@Proxy(lazy = true)  // Đảm bảo sử dụng proxy lazy loading
@Data
@AllArgsConstructor
@Builder
public class PromotionEntity implements Serializable {

    @Id
    @Column(name = "promotion_id")
    private String promotionId;

    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description;

    @Column(name = "discount_rate", nullable = false)
    private double discountPercentage;

    @Column(name = "min_price", nullable = false)
    private double minPrice;

    @Column(name = "started_date", nullable = false)
    private LocalDateTime startedDate;

    @Column(name = "ended_date", nullable = false)
    private LocalDateTime endedDate;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime creatededDate;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    @OneToMany(mappedBy = "promotion")
    private List<PromotionDetailEntity> promotionDetails;

    @Convert(converter = CustomerLevelConverter.class)
    @Column(name = "applicable_customer_levels", nullable = false, columnDefinition = "nvarchar(50)")
    private List<LevelCustomer> applicableCustomerLevels;

    // Constructor tự động tạo ID khi đối tượng được khởi tạo
    public PromotionEntity() {
        this.promotionId = UUID.randomUUID().toString();
    }

    // Constructor với các tham số được truyền vào
    public PromotionEntity(String description, double discountPercentage, LocalDateTime startedDate, LocalDateTime endedDate, boolean active, List<PromotionDetailEntity> promotionDetails, List<LevelCustomer> applicableCustomerLevels) {
        this.promotionId = UUID.randomUUID().toString(); // Tạo UUID cho promotionId
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.active = active;
        this.promotionDetails = promotionDetails;
        this.creatededDate = LocalDateTime.now();
        this.applicableCustomerLevels = applicableCustomerLevels;
    }

    // Constructor đầy đủ với tất cả các trường
    public PromotionEntity(String promotionId, String description, double discountPercentage, double minPrice, LocalDateTime startedDate, LocalDateTime endedDate, boolean active, LocalDateTime creatededDate, List<PromotionDetailEntity> promotionDetails, List<LevelCustomer> applicableCustomerLevels) {
        this.promotionId = promotionId;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.minPrice = minPrice;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.active = active;
        this.creatededDate = creatededDate;
        this.promotionDetails = promotionDetails;
        this.applicableCustomerLevels = applicableCustomerLevels;
    }
}
