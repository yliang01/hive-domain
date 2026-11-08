package cc.cc3c.hive.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@EntityScan
@EnableJpaRepositories
public class HiveDomainAutoConfiguration {
}
