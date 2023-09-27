package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.HiveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HiveRecordRepository extends JpaRepository<HiveRecord, Integer> {

    Optional<HiveRecord> findByFileKey(String fileKey);

    @Modifying
    @Query(nativeQuery = true, value = "delete from hive_record")
    void deleteAll();
}
