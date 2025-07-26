package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.HiveRecord;
import cc.cc3c.hive.domain.model.HiveDownloadStatus;
import cc.cc3c.hive.domain.model.HiveRecordSource;
import cc.cc3c.hive.domain.model.HiveRecordStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HiveRecordRepository extends JpaRepository<HiveRecord, Integer> {

    Optional<HiveRecord> findBySourceAndFileKey(HiveRecordSource source, String fileKey);

    List<HiveRecord> findBySourceAndDeletedIsFalse(HiveRecordSource source);

    Page<HiveRecord> findBySourceAndDeletedIsFalse(Pageable pageable, HiveRecordSource source);

    List<HiveRecord> findByStatusAndDeletedIsFalse(HiveRecordStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE HiveRecord r SET r.downloadStatus = :downloadStatus WHERE r.fileKey = :fileKey")
    int updateDownloadStatus(@Param("fileKey") String fileKey, @Param("downloadStatus") HiveDownloadStatus downloadStatus);
}
