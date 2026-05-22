package cc.cc3c.hive.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 1:1 image/thumbnail metadata for records that have thumbnails (e.g. IMAGE_PREVIEW).
 * Split from hive_record to keep record table generic.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hive_record_image_meta")
public class HiveRecordImageMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hive_record_id", nullable = false, unique = true)
    private Integer hiveRecordId;

    @Column(name = "thumb_key", length = 512)
    private String thumbKey;

    @Column(name = "thumb_status", length = 32)
    private String thumbStatus; // PENDING, READY, FAILED

    @Column(name = "image_width")
    private Integer imageWidth;

    @Column(name = "image_height")
    private Integer imageHeight;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
