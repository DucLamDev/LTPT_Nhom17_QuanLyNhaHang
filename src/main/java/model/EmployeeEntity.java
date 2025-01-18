package model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@NamedQueries({
        @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM EmployeeEntity e"),
        @NamedQuery(name = "Employee.findByPhone", query = "SELECT e FROM EmployeeEntity e WHERE e.email = :phone")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmployeeEntity {

    @Id
    @Column(name = "employee_id", nullable = false)
    private String employeeId;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String username;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String password;

    @Column(name = "fullname", nullable = false, columnDefinition = "nvarchar(255)")
    private String fullname;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String address;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "employee")
    private List<OrderEntity> orders;

    // Constructor with auto-generated ID
    public EmployeeEntity(String username, String password, String fullname, String phoneNumber, String email, String address, RoleEntity role) {
        this.employeeId = "E" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.role = role;
        this.createdDate = LocalDateTime.now();
        this.active = true;
    }
}
