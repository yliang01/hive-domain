package cc.cc3c.hive.domain.entity;

import cc.cc3c.hive.domain.model.HiveRecordSource;
import cc.cc3c.hive.domain.model.HiveRecordStatus;
import lombok.*;

import javax.persistence.*;
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
    private HiveRecordSource source;
    private Long size;
    private LocalDateTime updateTime;
    @Enumerated(EnumType.STRING)
    private HiveRecordStatus status;
    private LocalDateTime lastSyncTime;
    private Boolean deletable;
}
