package cc.cc3c.hive.other.repository;

import cc.cc3c.hive.domain.entity.FileCategoryEntity;
import cc.cc3c.hive.domain.entity.FileGroup;
import cc.cc3c.hive.domain.entity.HiveRecord;
import cc.cc3c.hive.domain.model.CategoryStorageClass;
import cc.cc3c.hive.domain.model.HiveStorageProvider;
import cc.cc3c.hive.domain.model.HiveRecordStatus;
import cc.cc3c.hive.domain.repository.FileCategoryRepository;
import cc.cc3c.hive.domain.repository.FileGroupRepository;
import cc.cc3c.hive.domain.repository.HiveRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class HiveRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    HiveRecordRepository hiveRecordRepository;
    @Autowired
    FileCategoryRepository fileCategoryRepository;
    @Autowired
    FileGroupRepository fileGroupRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void test() {
        FileCategoryEntity category = new FileCategoryEntity();
        category.setCode("TEST");
        category.setName("Test");
        category.setStorageClass(CategoryStorageClass.STANDARD);
        category.setBucketName("test-bucket");
        category.setUiVariant("file");
        category.setSortOrder(0);
        category.setEnabled(true);
        category = fileCategoryRepository.save(category);

        HiveRecord hiveRecord = HiveRecord.builder()
                .fileName("fileName")
                .fileKey("fileKey")
                .zipped(true)
                .provider(HiveStorageProvider.ALIBABA)
                .category(category)
                .size(1L)
                .updateTime(LocalDateTime.now())
                .status(HiveRecordStatus.UPLOADED)
                .lastSyncTime(LocalDateTime.now())
                .deletable(true)
                .deleted(false)
                .build();
        hiveRecordRepository.save(hiveRecord);
        Optional<HiveRecord> fileKey = hiveRecordRepository.findByCategory_IdAndFileKeyAndDeletedIsFalse(category.getId(), "fileKey");
        Assert.assertTrue(fileKey.isPresent());
    }

    @Test
    public void groupAssociationUsesExistingJoinTable() {
        FileCategoryEntity category = createCategory("GROUP_TEST");
        FileGroup firstGroup = createGroup(category, "FIRST");
        FileGroup secondGroup = createGroup(category, "SECOND");
        HiveRecord record = createRecord(category, "grouped.txt", "grouped-key");

        record.assignTo(firstGroup);
        hiveRecordRepository.saveAndFlush(record);
        entityManager.clear();

        HiveRecord assigned = hiveRecordRepository.findWithGroupByIdIn(List.of(record.getId())).getFirst();
        Assert.assertEquals(assigned.getGroup().getGroupCode(), "FIRST");
        Assert.assertEquals(fileGroupRepository.findById(firstGroup.getId()).orElseThrow().getRecords().size(), 1);

        assigned.assignTo(secondGroup);
        hiveRecordRepository.saveAndFlush(assigned);
        entityManager.clear();

        HiveRecord reassigned = hiveRecordRepository.findWithGroupByIdIn(List.of(record.getId())).getFirst();
        Assert.assertEquals(reassigned.getGroup().getGroupCode(), "SECOND");

        reassigned.clearGroup();
        hiveRecordRepository.saveAndFlush(reassigned);
        entityManager.clear();

        HiveRecord ungrouped = hiveRecordRepository.findWithGroupByIdIn(List.of(record.getId())).getFirst();
        Assert.assertNull(ungrouped.getGroup());
    }

    private FileCategoryEntity createCategory(String code) {
        FileCategoryEntity category = new FileCategoryEntity();
        category.setCode(code);
        category.setName(code);
        category.setStorageClass(CategoryStorageClass.STANDARD);
        category.setBucketName("test-bucket");
        category.setUiVariant("file");
        category.setSortOrder(0);
        category.setEnabled(true);
        return fileCategoryRepository.save(category);
    }

    private FileGroup createGroup(FileCategoryEntity category, String code) {
        FileGroup group = new FileGroup();
        group.setCategory(category);
        group.setGroupCode(code);
        group.setGroupName(code);
        group.setSortOrder(0);
        return fileGroupRepository.save(group);
    }

    private HiveRecord createRecord(FileCategoryEntity category, String fileName, String fileKey) {
        return hiveRecordRepository.save(HiveRecord.builder()
                .fileName(fileName)
                .fileKey(fileKey)
                .zipped(true)
                .provider(HiveStorageProvider.ALIBABA)
                .category(category)
                .size(1L)
                .updateTime(LocalDateTime.now())
                .status(HiveRecordStatus.UPLOADED)
                .lastSyncTime(LocalDateTime.now())
                .deletable(true)
                .deleted(false)
                .build());
    }
}
