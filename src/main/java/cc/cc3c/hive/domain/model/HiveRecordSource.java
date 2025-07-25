package cc.cc3c.hive.domain.model;

import lombok.Getter;

public enum HiveRecordSource {
    ALIBABA_STANDARD(false), ALIBABA_ACHIEVE(true);

    @Getter
    private final boolean restoreRequired;

    HiveRecordSource(boolean restoreRequired) {
        this.restoreRequired = restoreRequired;
    }
}
