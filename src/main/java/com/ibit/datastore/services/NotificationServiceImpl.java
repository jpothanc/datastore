package com.ibit.datastore.services;

import com.ibit.datastore.models.QueryResponse;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.swagger.annotations.Scope;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.function.Consumer;

@Service
@Scope(name = "singleton", description = "NotificationServiceImpl")
public class NotificationServiceImpl implements NotificationService, ApplicationContextAware {

    ApplicationContext applicationContext;
    Disposable disposable;

    @Override
    public void start() {
        System.out.println("NotificationServiceImpl- Subscribe");
        disposable= applicationContext.getBean(CatalogueServiceAsync.class).subscribe().
                subscribeOn(Schedulers.newThread()).
                subscribe(data -> System.out.println("Observer 2: " + data));
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
}
