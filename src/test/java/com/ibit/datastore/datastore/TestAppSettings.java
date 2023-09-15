package com.ibit.datastore.datastore;

import com.ibit.datastore.config.AppSettings;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestAppSettings {

    @Bean
    public AppSettings appSettings() {
        AppSettings appSettings = new AppSettings();
        return appSettings;
    }

}
