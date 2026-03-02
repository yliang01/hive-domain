package cc.cc3c.hive.domain.entity;

import cc.cc3c.hive.domain.model.CategoryStorageClass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "file_category")
public class FileCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String description;
    @Column(name = "storage_class")
    @Enumerated(EnumType.STRING)
    private CategoryStorageClass storageClass;
    @Column(name = "bucket_name")
    private String bucketName;
    @Column(name = "preview_policy")
    private String previewPolicy;
    @Column(name = "ui_variant")
    private String uiVariant;
    @Column(name = "is_system")
    private Boolean system;
    @Column(name = "sort_order")
    private Integer sortOrder;
    private Boolean enabled;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
