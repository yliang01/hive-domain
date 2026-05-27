package cc.cc3c.hive.domain.model;

import java.time.LocalDateTime;

public interface HiveRecordFileView {
    Integer getId();

    String getFileName();

    String getFileKey();

    Boolean getZipped();

    HiveStorageProvider getProvider();

    Long getSize();

    HiveRecordStatus getStatus();

    LocalDateTime getRestoreTime();

    HiveRestoreStatusState getRestoreStatus();

    HiveDownloadStatus getDownloadStatus();
}
