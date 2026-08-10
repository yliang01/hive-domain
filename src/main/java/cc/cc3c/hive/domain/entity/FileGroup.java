package cc.cc3c.hive.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "file_group")
public class FileGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private FileCategoryEntity category;

    @Column(name = "group_code")
    private String groupCode;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "sort_order")
    private Integer sortOrder;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Setter(lombok.AccessLevel.NONE)
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<HiveRecord> records = new ArrayList<>();

    public List<HiveRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }
}
