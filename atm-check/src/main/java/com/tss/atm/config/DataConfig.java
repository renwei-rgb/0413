package com.tss.atm.config;

import com.tss.config.config.DatasourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DatasourceAutoConfiguration.class)

public class DataConfig {

}
