package com.jpothanc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AppServiceImpl implements AppService {



    @Override
    public void start() {
//        var url = AppSettings.class.getClassLoader().getResource("appsettings.json");
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            // File file = new File("config.json");
//            AppSettings app = objectMapper.readValue(new File(url.getPath()), AppSettings.class);
//            System.out.println(app);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }







    }

    @Override
    public void stop() {
    }

}

