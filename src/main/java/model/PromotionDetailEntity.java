package model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotion_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class PromotionDetailEntity {

    @EmbeddedId
    private PromotionDetailId promotionDetailId;

    @Column(nullable = false)
    private String description;

    @Column(name = "discount_rate", nullable = false)
    private double discountPercentage;

    @ManyToOne
    @MapsId("promotionId")
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotion;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private ItemEntity item;

    public PromotionDetailEntity(PromotionDetailId promotionDetailId) {
        this.promotionDetailId = promotionDetailId;
    }

    public PromotionDetailEntity(PromotionDetailId promotionDetailId, String description, double discountPercentage, PromotionEntity promotion, ItemEntity item) {
        this.promotionDetailId = promotionDetailId;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.promotion = promotion;
        this.item = item;
    }
}
