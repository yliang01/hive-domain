package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.HiveRecord;
import cc.cc3c.hive.domain.model.HiveRecordSource;
import cc.cc3c.hive.domain.model.HiveRecordStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HiveRecordRepository extends JpaRepository<HiveRecord, Integer> {

    Optional<HiveRecord> findByFileKey(String fileKey);

    List<HiveRecord> findBySourceAndDeletedIsFalse(HiveRecordSource source);

    Page<HiveRecord> findBySourceAndDeletedIsFalse(Pageable pageable, HiveRecordSource source);

    List<HiveRecord> findByStatusAndDeletedIsFalse(HiveRecordStatus status);
}
