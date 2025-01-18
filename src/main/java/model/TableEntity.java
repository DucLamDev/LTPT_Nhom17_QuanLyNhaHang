package model;

import jakarta.persistence.*;
import lombok.*;
import common.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tables")
@NamedQueries({
        @NamedQuery(name = "Table.findAll", query = "SELECT t FROM TableEntity t"),
        @NamedQuery(name = "Table.findByStatus", query = "SELECT t FROM TableEntity t WHERE t.tableStatus = :status"),
        @NamedQuery(name = "Table.findByFloor", query = "SELECT t FROM TableEntity t WHERE t.floor.floorId = :floorId"),
        @NamedQuery(name = "Table.findByCombinedTable", query = "SELECT t FROM TableEntity t WHERE t.combinedTableId = :combinedTableId")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TableEntity {

    @Id
    @Column(name = "table_id")
    private String tableId;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "combined_table_id", nullable = true, columnDefinition = "nvarchar(255)")
    private String combinedTableId;

    @Enumerated(EnumType.STRING)
    @Column(name = "table_status", nullable = false)
    private TableStatusEnum tableStatus;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private FloorEntity floor;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    @Builder
    public TableEntity(FloorEntity floor) {
        this.tableId = UUID.randomUUID().toString(); // Tạo UUID cho tableId
        this.floor = floor;
    }

    @Builder
    public TableEntity(String tableId) {
        this.tableId = tableId;
    }

    @Builder
    public TableEntity(Integer capacity, String combinedTableId, TableStatusEnum tableStatus, FloorEntity floor) {
        this.tableId = UUID.randomUUID().toString(); // Tạo UUID cho tableId
        this.capacity = capacity;
        this.combinedTableId = combinedTableId;
        this.tableStatus = tableStatus;
        this.floor = floor;
    }

    @Builder
    public TableEntity(String tableId, Integer capacity, String combinedTableId, TableStatusEnum tableStatus, FloorEntity floor) {
        this.tableId = tableId;
        this.capacity = capacity;
        this.combinedTableId = combinedTableId;
        this.tableStatus = tableStatus;
        this.floor = floor;
    }
}
