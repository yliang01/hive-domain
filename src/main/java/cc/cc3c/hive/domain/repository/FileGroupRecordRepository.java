package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.FileGroupRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FileGroupRecordRepository extends JpaRepository<FileGroupRecord, Long> {
    List<FileGroupRecord> findByHiveRecord_IdIn(Collection<Integer> hiveRecordIds);

    Optional<FileGroupRecord> findByHiveRecord_IdAndGroup_Category_Code(Integer hiveRecordId, String categoryCode);

    long countByGroup_Id(Long groupId);

    @Query("select gr.hiveRecord.id from FileGroupRecord gr where gr.group.category.code = :categoryCode")
    List<Integer> findHiveRecordIdsByCategoryCode(@Param("categoryCode") String categoryCode);

    @Query("select gr.hiveRecord.id from FileGroupRecord gr where gr.group.id = :groupId and gr.group.category.code = :categoryCode")
    List<Integer> findHiveRecordIdsByGroupIdAndCategoryCode(@Param("groupId") Long groupId, @Param("categoryCode") String categoryCode);
}
