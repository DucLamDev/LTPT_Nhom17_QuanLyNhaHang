package model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Proxy;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "categories")
@NamedQueries({
        @NamedQuery(name = "Category.findAll", query = "SELECT c FROM CategoryEntity c")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Proxy(lazy = false)
public class CategoryEntity implements Serializable {

    @Id
    @Column(name = "category_id", nullable = false)
    private String categoryId;

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private boolean active;

    // Custom constructor for initialization with generated ID
    public CategoryEntity(String name, String description) {
        this.categoryId = "C" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        this.name = name;
        this.description = description;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
        this.active = true;
    }
}
