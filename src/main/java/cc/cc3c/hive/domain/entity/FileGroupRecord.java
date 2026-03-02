package cc.cc3c.hive.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "file_group_record")
public class FileGroupRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private FileGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hive_record_id", nullable = false)
    private HiveRecord hiveRecord;

    @Column(name = "assigned_by")
    private String assignedBy;
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;
}
