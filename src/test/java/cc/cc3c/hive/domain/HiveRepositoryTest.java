package cc.cc3c.hive.domain;

import cc.cc3c.hive.domain.entity.HiveRecord;
import cc.cc3c.hive.domain.model.HiveRecordSource;
import cc.cc3c.hive.domain.model.HiveRecordStatus;
import cc.cc3c.hive.domain.repository.HiveRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
public class HiveRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    HiveRecordRepository hiveRecordRepository;

    @Test
    public void a() {
        HiveRecord hiveRecord = HiveRecord.builder()
                .fileName("fileName")
                .fileKey("fileKey")
                .zipped(true)
                .source(HiveRecordSource.ALIBABA_STANDARD)
                .size(1L)
                .updateTime(LocalDateTime.now())
                .status(HiveRecordStatus.UPLOADED)
                .lastSyncTime(LocalDateTime.now())
                .deletable(true)
                .build();
        hiveRecordRepository.save(hiveRecord);
        Optional<HiveRecord> fileKey = hiveRecordRepository.findByFileKey("fileKey");
        Assert.assertTrue(fileKey.isPresent());
    }
}
