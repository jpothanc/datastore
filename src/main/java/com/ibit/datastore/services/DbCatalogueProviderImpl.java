package com.ibit.datastore.services;

import com.ibit.common.database.DatabaseService;
import com.ibit.common.database.DatabaseServiceImpl;
import com.ibit.common.database.models.DatabaseSetting;
import com.ibit.common.database.models.DbRequest;
import com.ibit.datastore.models.Enums.CatalogueProviders;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import io.swagger.annotations.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Scope(name = "prototype", description = "")
public class DbCatalogueProviderImpl implements CatalogueProvider {

    //    @Autowired
//    DatabaseService databaseService;
    @Override
    public CatalogueProviders getName() {
        return CatalogueProviders.Database;
    }

    @Override
    public CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request) {

        System.out.println("Querying Catalogue " + request.getCatalogueItem() + " using :" + getName().toString());

        DatabaseSetting dbSetting = new DatabaseSetting(){{
            setConnectionString("jdbc:postgresql://localhost:5432/test");
            setUsername("postgres");
            setPassword("admin");
        }};
        var cItem = request.getCatalogueItemInstance();
        DbRequest  dbRequest = new DbRequest(){{
            setQuery(cItem.getQuery());
            setDatabaseSetting(dbSetting);
        }};
        DatabaseService databaseService = new DatabaseServiceImpl();
        var dbResponse = databaseService.Query(dbRequest);
        var qResponse =  QueryResponse.createOkResponse(request);
        qResponse.setData(dbResponse.getResultSet());
        return CompletableFuture.completedFuture(qResponse);
    }
}
