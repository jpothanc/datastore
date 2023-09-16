package com.ibit.datastore.datastore;

import com.ibit.datastore.config.AppConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestAppSettings {

    @Bean
    public AppConfig appSettings() {
        AppConfig appSettings = new AppConfig();
        return appSettings;
    }

}
