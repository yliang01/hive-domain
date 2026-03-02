package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.FileGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileGroupRepository extends JpaRepository<FileGroup, Long> {
    List<FileGroup> findByCategory_CodeAndEnabledTrueOrderBySortOrderAscIdAsc(String categoryCode);

    Optional<FileGroup> findByIdAndCategory_CodeAndEnabledTrue(Long id, String categoryCode);

    Optional<FileGroup> findByIdAndCategory_Code(Long id, String categoryCode);

    boolean existsByCategory_CodeAndGroupCodeIgnoreCase(String categoryCode, String groupCode);

    List<FileGroup> findByCategory_CodeOrderBySortOrderAscIdAsc(String categoryCode);
}
