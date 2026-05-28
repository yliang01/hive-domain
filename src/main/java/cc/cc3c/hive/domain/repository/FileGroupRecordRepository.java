package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.FileGroupRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface FileGroupRecordRepository extends JpaRepository<FileGroupRecord, Long> {
    List<FileGroupRecord> findByHiveRecord_IdIn(Collection<Integer> hiveRecordIds);

    @Transactional
    @Modifying
    @Query("delete from FileGroupRecord gr where gr.hiveRecord.id = :hiveRecordId")
    void deleteByHiveRecord_Id(@Param("hiveRecordId") Integer hiveRecordId);

    @Transactional
    @Modifying
    @Query("delete from FileGroupRecord gr where gr.hiveRecord.id in :hiveRecordIds")
    void deleteByHiveRecord_IdIn(@Param("hiveRecordIds") Collection<Integer> hiveRecordIds);

    @Transactional
    @Modifying
    @Query("delete from FileGroupRecord gr where gr.group.id = :groupId")
    void deleteByGroup_Id(@Param("groupId") Long groupId);

}
