package com.ibit.datastore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;

import static com.ibit.datastore.helpers.Constants.KEY_SEP;

@Configuration
public class ConfigLoader {
    @Value("${spring.profiles.active}")
    private String activeProfile;
    @Autowired
    AppConfig appConfig;

    private final Environment environment;

    public ConfigLoader(Environment environment) {
        this.environment = environment;
    }

    public void loadConfig() throws IOException {
        System.out.println("Active profile : " + activeProfile);
        String configFile = "appsettings-" + activeProfile + ".json";
        var url = AppConfig.class.getClassLoader().getResource(configFile);

        if (url == null)
            url = AppConfig.class.getClassLoader().getResource("appsettings.json");
        System.out.println("Loading configuration : " + url);
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        var localConfig = objectMapper.readValue(new File(url.getPath()), AppConfig.class);
        override(localConfig);
    }

    private void override(AppConfig localConfig) {
        appConfig.setCatalogues(localConfig.getCatalogues());
        appConfig.setDataSources(localConfig.getDataSources());
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
