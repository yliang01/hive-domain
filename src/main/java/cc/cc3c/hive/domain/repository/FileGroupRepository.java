package cc.cc3c.hive.domain.repository;

import cc.cc3c.hive.domain.entity.FileGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileGroupRepository extends JpaRepository<FileGroup, Long> {
    List<FileGroup> findByCategory_CodeOrderBySortOrderAscIdAsc(String categoryCode);

    Optional<FileGroup> findByCategory_CodeAndGroupCodeIgnoreCase(String categoryCode, String groupCode);

    boolean existsByCategory_CodeAndGroupCodeIgnoreCase(String categoryCode, String groupCode);

}
