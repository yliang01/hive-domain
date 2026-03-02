package cc.cc3c.hive.domain.entity;

import cc.cc3c.hive.domain.model.HiveDownloadStatus;
import cc.cc3c.hive.domain.model.CategoryStorageClass;
import cc.cc3c.hive.domain.model.HiveStorageProvider;
import cc.cc3c.hive.domain.model.HiveRecordStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HiveRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fileName;
    private String fileKey;
    private Boolean zipped;
    @Enumerated(EnumType.STRING)
    private HiveStorageProvider provider;
    @Column(name = "bucket_name")
    private String bucketName;
    @Column(name = "storage_class_cache")
    @Enumerated(EnumType.STRING)
    private CategoryStorageClass storageClassCache;
    private Long size;
    private LocalDateTime updateTime;
    @Enumerated(EnumType.STRING)
    private HiveRecordStatus status;
    private LocalDateTime lastSyncTime;
    private Boolean deletable;
    private Boolean deleted = false;
    private LocalDateTime restoreTime;
    @Enumerated(EnumType.STRING)
    private HiveDownloadStatus downloadStatus;
}
