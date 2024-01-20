package com.ibit.datastore.services;

import com.ibit.datastore.cache.BlockingCache;
import com.ibit.datastore.cache.BlockingCacheImpl;
import com.ibit.datastore.config.AppConfig;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.swagger.annotations.Scope;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

import static com.ibit.datastore.helpers.Constants.CATALOGUE_NOT_FOUND;

@Service
@Scope(name = "singleton", description = "AsyncCatalogueServiceImpl")
public class CatalogueServiceImplAsync implements CatalogueServiceAsync, ApplicationContextAware {
    BlockingCache<QueryRequest> queryRequests;
    private final AppConfig appConfig;
    private static ApplicationContext applicationContext;
    PublishSubject<QueryResponse> subject = PublishSubject.create();


    @Autowired
    public CatalogueServiceImplAsync(AppConfig appConfig) {
        this.appConfig = appConfig;
        queryRequests = new BlockingCacheImpl<QueryRequest>(100);
        queryRequests.subscribe((request) -> {
            System.out.println("Processing Request: " + request);
            var response = applicationContext.getBean(CatalogueService.class).queryCatalogueItem(request).join();
            subject.onNext(response);
        });
    }

    @Override
    public CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request) {

        var cItem = appConfig.getCatalogueItem(request.getCatalogue(), request.getCatalogueItem());
        if (cItem == null) throw new NoSuchElementException(CATALOGUE_NOT_FOUND);

        if (request.getQueryArgs() != null && request.getQueryArgs().length > 0) {
            var query = String.format(cItem.getQuery(), request.getQueryArgs());
            cItem.setQuery(query);
        }
        request.setCatalogueItemInstance(cItem);
        queryRequests.add(request);

        QueryResponse response = QueryResponse.createOkResponse(request);
        response.setSource("Async");
        return CompletableFuture.completedFuture(response);
    }

    @Override
    public Observable<QueryResponse> subscribe() {
       return subject;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CatalogueServiceImplAsync.applicationContext = applicationContext;
    }
}
