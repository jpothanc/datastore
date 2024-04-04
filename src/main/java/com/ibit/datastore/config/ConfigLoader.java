package com.ibit.datastore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibit.datastore.helpers.CatalogueHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static com.ibit.datastore.helpers.CatalogueHelper.decryptPassword;
import static com.ibit.datastore.helpers.Constants.KEY_SEP;

@Configuration
public class ConfigLoader {
    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Autowired
    AppConfig appConfig;

    private final Environment environment;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public ConfigLoader(Environment environment,ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.environment = environment;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public void loadConfig() throws IOException {
        System.out.println("Active profile : " + activeProfile);
        String configFile = "classpath:appsettings-" + activeProfile + ".json";
        Resource resource = resourceLoader.getResource(configFile);
        System.out.println("resource loaded");
        var localConfig = objectMapper.readValue(resource.getInputStream(), AppConfig.class);
        System.out.println("resource parsed" + localConfig.getCatalogues());
        override(localConfig);
    }

    private void override(AppConfig localConfig) {
        appConfig.setCatalogues(localConfig.getCatalogues());

        var dataSources = localConfig.getDataSources();
        for ( var value : dataSources.values()) {
            if(value.getPassword() == null || value.getPassword() == "")
                continue;
            var password = decryptPassword(value.getPassword(), CatalogueHelper.getPasswordEncryptionKey());
            value.setPassword(password);
        }
        appConfig.setDataSources(dataSources);
        setCatalogues();
    }

    private void setCatalogues() {
        var catalogues = appConfig.getCatalogues();
        for (var key : catalogues.keySet()) {
            var c = catalogues.get(key);

            for (var itemKey : c.getItems().keySet()) {
                var cItem = c.getItems().get(itemKey);
                cItem.setKey(key + KEY_SEP + itemKey);
            }
        }
    }
}
