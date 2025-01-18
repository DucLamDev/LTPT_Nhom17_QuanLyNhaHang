package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "roles")
@NamedQueries({
        @NamedQuery(name = "RoleEntity.findAll", query = "SELECT r FROM RoleEntity r"),
        @NamedQuery(name = "RoleEntity.findByName", query = "SELECT r FROM RoleEntity r WHERE r.roleName = :roleName")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {

    @Id
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "role_name", nullable = false, columnDefinition = "nvarchar(50)")
    private String roleName;

    // Constructor with roleName, generates roleId automatically using UUID
    public RoleEntity(String roleName) {
        this.roleId = UUID.randomUUID().toString(); // Tạo UUID cho roleId
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleEntity{" + "roleId=" + roleId + ", roleName=" + roleName + '}';
    }
}
