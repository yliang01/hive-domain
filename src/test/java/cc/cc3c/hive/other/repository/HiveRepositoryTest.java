package cc.cc3c.hive.other.repository;

import cc.cc3c.hive.domain.entity.FileCategoryEntity;
import cc.cc3c.hive.domain.entity.HiveRecord;
import cc.cc3c.hive.domain.model.CategoryStorageClass;
import cc.cc3c.hive.domain.model.HiveStorageProvider;
import cc.cc3c.hive.domain.model.HiveRecordStatus;
import cc.cc3c.hive.domain.repository.FileCategoryRepository;
import cc.cc3c.hive.domain.repository.HiveRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class HiveRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    HiveRecordRepository hiveRecordRepository;
    @Autowired
    FileCategoryRepository fileCategoryRepository;

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
}
