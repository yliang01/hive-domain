package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.HiveRecordImageMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface HiveRecordImageMetaRepository extends JpaRepository<HiveRecordImageMeta, Long> {

    Optional<HiveRecordImageMeta> findByHiveRecordId(Integer hiveRecordId);

    List<HiveRecordImageMeta> findByHiveRecordIdIn(Collection<Integer> hiveRecordIds);
}
