package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.HiveRecord;
import cc.cc3c.hive.domain.model.HiveDownloadStatus;
import cc.cc3c.hive.domain.model.HiveRecordStatus;
import cc.cc3c.hive.domain.model.HiveRestoreStatusState;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface HiveRecordRepository extends JpaRepository<HiveRecord, Integer>, JpaSpecificationExecutor<HiveRecord> {

    List<HiveRecord> findByCategory_IdAndDeletedIsFalse(Long categoryId);

    @EntityGraph(attributePaths = "group")
    Optional<HiveRecord> findByCategory_IdAndFileKeyAndDeletedIsFalse(Long categoryId, String fileKey);

    List<HiveRecord> findByCategory_IdAndFileKeyInAndDeletedIsFalse(Long categoryId, Collection<String> fileKeys);

    @Query("select r from HiveRecord r left join fetch r.group where r.category.id = :categoryId and r.fileKey in :fileKeys and r.deleted = false")
    List<HiveRecord> findWithGroupByCategory_IdAndFileKeyInAndDeletedIsFalse(
            @Param("categoryId") Long categoryId, @Param("fileKeys") Collection<String> fileKeys);

    @EntityGraph(attributePaths = "group")
    List<HiveRecord> findByGroup_Id(Long groupId);

    @Override
    @EntityGraph(attributePaths = "group")
    Page<HiveRecord> findAll(Specification<HiveRecord> spec, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update HiveRecord r set r.category = null where r.category.id = :categoryId")
    void detachFromCategory(@Param("categoryId") Long categoryId);

    @Transactional
    @Modifying
    @Query("UPDATE HiveRecord r SET r.downloadStatus = :downloadStatus WHERE r.category.id = :categoryId AND r.fileKey = :fileKey")
    int updateDownloadStatus(@Param("categoryId") Long categoryId, @Param("fileKey") String fileKey, @Param("downloadStatus") HiveDownloadStatus downloadStatus);

    @Transactional
    @Modifying
    @Query("UPDATE HiveRecord r SET r.restoreStatus = :restoreStatus, r.restoreRequestedAt = CASE WHEN :requested = true THEN CURRENT_TIMESTAMP ELSE r.restoreRequestedAt END, r.restoreStatusUpdatedAt = CURRENT_TIMESTAMP, r.restoreTime = CASE WHEN :restoreTime IS NULL THEN r.restoreTime ELSE :restoreTime END WHERE r.id = :recordId")
    int updateRestoreStatus(@Param("recordId") Integer recordId,
                            @Param("restoreStatus") HiveRestoreStatusState restoreStatus,
                            @Param("requested") boolean requested,
                             @Param("restoreTime") java.time.LocalDateTime restoreTime);

}
