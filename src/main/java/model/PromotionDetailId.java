package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
@Embeddable
@Getter
@Setter
@Proxy(lazy = false)
public class PromotionDetailId implements Serializable {

    @Column(name = "promotion_id")
    private UUID promotionId;

    @Column(name = "item_id")
    private UUID itemId;

    public PromotionDetailId() {
        this.promotionId = UUID.randomUUID();
        this.itemId = UUID.randomUUID();
    }

    public PromotionDetailId(UUID promotionId, UUID itemId) {
        setPromotionId(promotionId);
        setItemId(itemId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.promotionId);
        hash = 13 * hash + Objects.hashCode(this.itemId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PromotionDetailId other = (PromotionDetailId) obj;
        if (!Objects.equals(this.promotionId, other.promotionId)) {
            return false;
        }
        return Objects.equals(this.itemId, other.itemId);
    }

    @Override
    public String toString() {
        return "PromotionDetailId{" + "promotionId=" + promotionId + ", itemId=" + itemId + '}';
    }
}
