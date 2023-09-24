package com.ibit.datastore.services;

import com.ibit.datastore.config.ConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class AppServiceImpl implements AppService {
    @Autowired
    ConfigLoader configLoader;

    public void start() {
        System.out.println("AppServiceImpl- Start");
        try {
            configLoader.loadConfig();
        } catch (Exception e) {
            System.out.println("Application Initialization failed." + e.getMessage());
        }
    }

    public void stop() {
    }
}

