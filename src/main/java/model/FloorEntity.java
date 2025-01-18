package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.Proxy;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "floors")
@NamedQueries({
        @NamedQuery(name = "Floor.findAll", query = "SELECT f FROM FloorEntity f")
})
@Proxy(lazy = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloorEntity implements Serializable {

    @Id
    @Column(name = "floor_id")
    private String floorId = UUID.randomUUID().toString(); // Automatically generated unique ID

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "floor", fetch = FetchType.LAZY)
    private List<TableEntity> tables;

    // Constructor using UUID for floorId
    public FloorEntity(String name, int capacity, List<TableEntity> tables) {
        this.floorId = UUID.randomUUID().toString(); // Generate unique ID
        this.name = name;
        this.capacity = capacity;
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "FloorEntity{" + "floorId=" + floorId + ", name=" + name + ", capacity=" + capacity + '}';
    }
}
