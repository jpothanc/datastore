package com.ibit.datastore.services;

import com.ibit.datastore.config.ConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class AppServiceImpl implements AppService {
    @Autowired
    ConfigLoader configLoader;

    @Override
    public void start() {
        try {
            configLoader.loadConfig();
        } catch (IOException e) {
            throw new RuntimeException("Application Initialization failed." + e);
        }
    }

    @Override
    public void stop() {
    }
}

