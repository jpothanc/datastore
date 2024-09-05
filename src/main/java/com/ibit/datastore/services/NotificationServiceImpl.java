package com.ibit.datastore.services;

import com.ibit.datastore.models.QueryResponse;
import io.reactivex.rxjava3.schedulers.Schedulers;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import io.reactivex.rxjava3.disposables.Disposable;

@Service
public class NotificationServiceImpl implements NotificationService, ApplicationContextAware {

    ApplicationContext applicationContext;
    Disposable disposable;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void start() {
        System.out.println("NotificationServiceImpl- Subscribe");
        disposable = applicationContext.getBean(CatalogueServiceAsync.class).onQueryCompleted()
                .subscribeOn(Schedulers.newThread()).
                subscribe((data) -> {
                    System.out.println("Observer 2: " + data);
                    sendQueryResponse(data);
                });
    }
    @Override
    public void stop() {
        if(disposable!=null)
            disposable.dispose();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public void sendQueryResponse(QueryResponse queryResponse) {

        System.out.println("Sending Notification: " + queryResponse.toString());
        messagingTemplate.convertAndSend("/topic/queryAsync", queryResponse);
    }
}
