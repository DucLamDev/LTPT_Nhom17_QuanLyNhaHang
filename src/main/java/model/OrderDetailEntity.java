package model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Proxy;
import java.io.Serializable;

@Entity
@Table(name = "order_details")
@NamedQueries({
        @NamedQuery(name = "OrderDetail.findAll", query = "SELECT o FROM OrderDetailEntity o"),
        @NamedQuery(name = "OrderDetail.findByOrderId", query = "SELECT o FROM OrderDetailEntity o WHERE o.id.orderId = :orderId"),
        @NamedQuery(name = "OrderDetail.findByItemId", query = "SELECT o FROM OrderDetailEntity o WHERE o.id.itemId = :itemId"),
        @NamedQuery(name = "OrderDetail.findByOrderIdAndItemId", query = "SELECT o FROM OrderDetailEntity o WHERE o.id.orderId = :orderId AND o.id.itemId = :itemId")
})
@Proxy(lazy = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailEntity implements Serializable {

    @EmbeddedId
    private OrderDetailId orderDetailId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double linetotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId") // Trường itemId trong khóa chính hợp thành tương ứng với khóa chính của ItemEntity
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId") // Trường orderId trong khóa chính hợp thành tương ứng với khóa chính của OrderEntity
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    public double getLinetotal() {
        return item.getSellingPrice() * quantity * (1 + item.getTopDiscountPercentage());
    }

    @Override
    public String toString() {
        return "OrderDetailEntity{" + "id=" + orderDetailId + ", quantity=" + quantity + ", linetotal=" + linetotal + '}';
    }
}
