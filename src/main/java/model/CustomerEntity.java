package model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import common.LevelCustomer;

@Entity
@Table(name = "customers")
@NamedQueries({
        @NamedQuery(name = "CustomerEntity.findAll", query = "SELECT c FROM CustomerEntity c"),
        @NamedQuery(name = "CustomerEntity.findByPhone", query = "SELECT c FROM CustomerEntity c WHERE c.phoneNumber = :phoneNumber")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerEntity {

    @Id
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "first_name", nullable = false, columnDefinition = "nvarchar(255)")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "nvarchar(255)")
    private String lastName;

    @Column(name = "email", nullable = false, columnDefinition = "nvarchar(255)")
    private String email;

    @Column(name = "phone_number", unique = true, columnDefinition = "nvarchar(255)")
    private String phoneNumber;

    @Column(name = "address", columnDefinition = "nvarchar(255)")
    private String address;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDateTime dayOfBirth;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    @Column(name = "rewarded_point")
    private int rewardedPoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_customer", nullable = false, columnDefinition = "nvarchar(255)")
    private LevelCustomer levelCustomer;

    // Custom constructor for initialization with generated ID
    public CustomerEntity(String firstName, String lastName, String email, String phoneNumber, String address, LocalDateTime dayOfBirth) {
        this.customerId = "Cust" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dayOfBirth = dayOfBirth;
        this.createdDate = LocalDateTime.now();
    }

    // Tính tổng số điểm thưởng từ tất cả các đơn hàng
    public int getRewardedPoint() {
        if (orders == null || orders.isEmpty()) {
            return 0;
        }
        return orders.stream().mapToInt(order -> (int) (order.getTotalPrice() / 100000)).sum();
    }

    // Tính toán levelCustomer dựa trên rewardPoint
    public LevelCustomer getLevelCustomer() {
        int rewardPoint = getRewardedPoint();
        if (rewardPoint <= 500) {
            return LevelCustomer.NEW;
        } else if (rewardPoint <= 2000) {
            return LevelCustomer.POTENTIAL;
        } else {
            return LevelCustomer.VIP;
        }
    }
}
