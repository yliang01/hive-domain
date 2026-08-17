package cc.cc3c.hive.domain.entity;

import cc.cc3c.hive.domain.model.HiveDownloadStatus;
import cc.cc3c.hive.domain.model.HiveStorageProvider;
import cc.cc3c.hive.domain.model.HiveRecordStatus;
import cc.cc3c.hive.domain.model.HiveRestoreStatusState;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private FileCategoryEntity category;
    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "file_group_record",
            joinColumns = @JoinColumn(name = "hive_record_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private FileGroup group;
    private Long size;
    private Long originalSize;
    private LocalDateTime updateTime;
    @Enumerated(EnumType.STRING)
    private HiveRecordStatus status;
    private LocalDateTime lastSyncTime;
    private Boolean deletable;
    private Boolean deleted = false;
    private LocalDateTime restoreTime;
    @Enumerated(EnumType.STRING)
    private HiveRestoreStatusState restoreStatus;
    private LocalDateTime restoreRequestedAt;
    private LocalDateTime restoreStatusUpdatedAt;
    @Enumerated(EnumType.STRING)
    private HiveDownloadStatus downloadStatus;

    public void assignTo(FileGroup group) {
        this.group = group;
    }

    public void clearGroup() {
        this.group = null;
    }
}
