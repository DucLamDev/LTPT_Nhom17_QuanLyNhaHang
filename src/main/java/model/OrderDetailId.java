package model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderDetailId implements Serializable {

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "order_id")
    private String orderId;

    public OrderDetailId(String itemId, String orderId) {
        this.itemId = itemId;
        this.orderId = orderId;
    }
}
