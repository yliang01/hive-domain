package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.FileCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileCategoryRepository extends JpaRepository<FileCategoryEntity, Long> {

    Optional<FileCategoryEntity> findByCode(String code);

    Optional<FileCategoryEntity> findByCodeAndEnabledTrue(String code);

    List<FileCategoryEntity> findAllByOrderBySortOrderAscIdAsc();

    List<FileCategoryEntity> findByEnabledTrueOrderBySortOrderAscIdAsc();

    boolean existsByCode(String code);

    boolean existsByBucketName(String bucketName);
}
