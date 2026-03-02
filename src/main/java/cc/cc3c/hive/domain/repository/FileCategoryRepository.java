package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.FileCategoryEntity;
import cc.cc3c.hive.domain.model.CategoryStorageClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileCategoryRepository extends JpaRepository<FileCategoryEntity, Long> {
    Optional<FileCategoryEntity> findByCodeAndEnabledTrue(String code);

    List<FileCategoryEntity> findByEnabledTrueOrderBySortOrderAscIdAsc();

    Optional<FileCategoryEntity> findFirstByStorageClassAndEnabledTrueOrderBySortOrderAscIdAsc(CategoryStorageClass storageClass);

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByBucketNameIgnoreCase(String bucketName);

    boolean existsByBucketNameIgnoreCaseAndIdNot(String bucketName, Long id);

    Optional<FileCategoryEntity> findByBucketNameAndEnabledTrue(String bucketName);
}
