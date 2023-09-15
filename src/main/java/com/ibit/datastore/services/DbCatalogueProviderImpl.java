package com.ibit.datastore.services;

import com.ibit.common.database.DatabaseService;
import com.ibit.common.database.DatabaseServiceImpl;
import com.ibit.common.database.models.DatasourceSetting;
import com.ibit.common.database.models.DbRequest;
import com.ibit.datastore.config.AppSettings;
import com.ibit.datastore.models.Enums.CatalogueProviders;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import io.swagger.annotations.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Scope(name = "prototype", description = "")
public class DbCatalogueProviderImpl implements CatalogueProvider {

    //    @Autowired
//    DatabaseService databaseService;
    @Autowired
    AppSettings appSettings;
    @Override
    public CatalogueProviders getName() {
        return CatalogueProviders.Database;
    }

    @Override
    public CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request) {

        System.out.println("Querying Catalogue " + request.getCatalogueItem() + " using :" + getName().toString());
        var cItem = request.getCatalogueItemInstance();

        var dbSetting = appSettings.getDataSourceSetting(cItem.getDatasource());
        if(dbSetting.isEmpty())
            throw new RuntimeException("DataSource not found : " + cItem.getDatasource());
        DbRequest  dbRequest = new DbRequest(){{
            setQuery(cItem.getQuery());
            setDatabaseSetting(dbSetting.get());
        }};
        DatabaseService databaseService = new DatabaseServiceImpl();
        var dbResponse = databaseService.Query(dbRequest);
        var qResponse =  QueryResponse.createOkResponse(request);
        qResponse.setData(dbResponse.getResultSet());
        return CompletableFuture.completedFuture(qResponse);
    }
}
