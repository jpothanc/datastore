package com.ibit.datastore.services.command;

import com.ibit.datastore.repositories.WebCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.ibit.datastore.helpers.CatalogueHelper.formattedTimeStamp;

@Service
public class WebCounterCmdServiceImpl implements WebCounterCmdService {
    private final WebCounterRepository webCounterRepository;

    @Autowired
    public WebCounterCmdServiceImpl(WebCounterRepository webCounterRepository) {
        this.webCounterRepository = webCounterRepository;
    }

    @Override
    public int getCounter(String siteName) {
        return webCounterRepository.findById(siteName).get().getCounter();
    }

    @Override
    public void incrementCounter(String siteName) {
        var webCounter = webCounterRepository.findById(siteName).get();
        webCounter.setCounter(webCounter.getCounter() + 1);
        webCounter.setModified_by("datastore");
        webCounter.setModified_date(formattedTimeStamp());
        webCounterRepository.save(webCounter);
    }
}
