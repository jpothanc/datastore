package com.ibit.datastore.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibit.datastore.config.AppSettings;
import com.ibit.datastore.models.Catalogue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.ibit.datastore.helpers.Constants.KEY_SEP;


@Service
public class AppServiceImpl implements AppService {

   @Autowired
   AppSettings appSettings;

    @Override
    public void start() {
        bindAppSettings();
    }

    private void bindAppSettings() {
        var url = AppSettings.class.getClassLoader().getResource("appsettings.json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var localSettings = objectMapper.readValue(new File(url.getPath()), AppSettings.class);
            appSettings.setCatalogue(localSettings.getCatalogue());
            System.out.println(appSettings);
            setCatalogues();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCatalogues(){
        var catalogues = appSettings.getCatalogue();
        for (var key:catalogues.keySet()) {
            var c = catalogues.get(key);

            for (var itemKey:c.getItems().keySet()) {
                var cItem  = c.getItems().get(itemKey);
                cItem.setKey(key + KEY_SEP + itemKey );
            }
        }
    }

    @Override
    public void stop() {
    }

}

