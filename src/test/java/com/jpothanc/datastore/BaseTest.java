package com.jpothanc.datastore;

import com.jpothanc.config.AppSettings;
import com.jpothanc.models.CatalogueItem;
import com.jpothanc.models.QueryRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

public abstract class BaseTest {
    @Autowired
    AppSettings appSettings;
    protected static final String DATASTORE_API = "/api/v1/data/query?catalogue=%s&catalogueItem=%s";
    static Stream<Object[]> getValidQueryRequest() {
        var req1 = new QueryRequest(){{
            setCatalogue("Trading");
            setCatalogueItem("Users");
        }};
        var req2 = new QueryRequest(){{
            setCatalogue("Trading");
            setCatalogueItem("Users");
        }};

        return Stream.of(
                new Object[] { req1 },
                new Object[] { req1 }
        );
    }
    static Stream<Object[]> getInValidQueryRequest() {
        var req1 = new QueryRequest(){{
            setCatalogue("XXXX");
            setCatalogueItem("XXXX");
        }};
        var req2 = new QueryRequest(){{
            setCatalogue("XXXXX");
            setCatalogueItem("XXXX");
        }};

        return Stream.of(
                new Object[] { req1 },
                new Object[] { req1 }
        );
    }

    protected QueryRequest getQueryRequest(){
        return new QueryRequest(){{
            setCatalogue("Trading");
            setCatalogueItem("Users");
        }};
    }
    protected CatalogueItem getCatalogueItem(QueryRequest request){
        return appSettings.getCatalogueItem(request.getCatalogue(), request.getCatalogueItem());
    }
}
