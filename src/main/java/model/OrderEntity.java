package model;

import common.OrderStatus;
import common.OrderType;
import common.PaymentMethod;
import common.PaymentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import java.util.Optional;
import utillity.IDGeneratorUtility;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "Order.findAll", query = "select o from OrderEntity o")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class OrderEntity {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "reservation_time", nullable = false)
    private LocalDateTime reservationTime;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "total_discount", nullable = false)
    private double totalDiscount;

    @Column(name = "total_paid", nullable = false)
    private double totalPaid;

    @Column(name = "number_of_customer", nullable = false)
    private int numberOfCustomer;

    @Column(name = "deposit", nullable = false)
    private double deposit;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableEntity table;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "nvarchar(50)")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "nvarchar(50)")
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "nvarchar(50)")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "nvarchar(50)")
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderDetailEntity> orderDetails;

    public OrderEntity(String orderId) {
        setOrderId(orderId);
    }

    public OrderEntity(LocalDateTime reservationTime, int numberOfCustomer, double deposit, CustomerEntity customer, EmployeeEntity employee, TableEntity table, List<OrderDetailEntity> orderDetails) {
        this.orderId = IDGeneratorUtility.generateIDWithCreatedDate("O", "orders", "order_id", "order_date");
        this.reservationTime = reservationTime;
        this.numberOfCustomer = numberOfCustomer;
        this.deposit = deposit;
        this.customer = customer;
        this.employee = employee;
        this.table = table;
        this.orderDetails = orderDetails;
        this.orderDate = LocalDateTime.now();
    }

    public OrderEntity(String orderId, LocalDateTime orderDate, LocalDateTime reservationTime, double totalPrice, double totalDiscount, double totalPaid, int numberOfCustomer, double deposit, CustomerEntity customer, EmployeeEntity employee, TableEntity table, PromotionEntity promotion, OrderStatus orderStatus, OrderType orderType, PaymentMethod paymentMethod, PaymentStatus paymentStatus, List<OrderDetailEntity> orderDetails) {
        setOrderId(orderId);
        this.orderDate = orderDate;
        this.reservationTime = reservationTime;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.totalPaid = totalPaid;
        this.numberOfCustomer = numberOfCustomer;
        this.deposit = deposit;
        this.customer = customer;
        this.employee = employee;
        this.table = table;
        this.promotion = promotion;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.orderDetails = orderDetails;
    }

    public double getTotalPrice() {
        return this.orderDetails.stream()
                .mapToDouble(OrderDetailEntity::getLinetotal)
                .sum();
    }

    public double getTotalDiscount() {
        return getTopDiscountRate();
    }

    public double getTotalPaid() {
        return getTotalPrice() - getTotalDiscount();
    }

    public double getTopDiscountRate() {
        return 0;
    }

    public boolean insertOrderDetail(OrderDetailEntity orderDetail) {
        List<OrderDetailEntity> orderDetails = this.getOrderDetails();

        Optional<OrderDetailEntity> existingOrderDetail = orderDetails.stream()
                .filter(od -> od.equals(orderDetail))
                .findFirst();

        if(existingOrderDetail.isPresent()){
            existingOrderDetail.get().setQuantity(
                    existingOrderDetail.get().getQuantity() + orderDetail.getQuantity()
            );
        }
        else {
            orderDetails.add(orderDetail);
        }

        this.setOrderDetails(orderDetails);

        return true;
    }
}
