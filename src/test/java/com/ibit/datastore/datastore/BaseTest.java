package com.ibit.datastore.datastore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibit.datastore.config.AppSettings;
import com.ibit.datastore.models.CatalogueItem;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.services.AppService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static com.ibit.datastore.helpers.Constants.KEY_SEP;

public abstract class BaseTest {
    @BeforeEach
    public void beforeEachTest(){
        appService.start();
    }
    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    AppSettings appSettings;
    @Autowired
    AppService appService;

    protected static final String DATASTORE_API = "/api/v1/data/query?catalogue=%s&catalogueItem=%s";
    protected static final String DATASTORE_ADMIN_API = "/api/v1/admin/clear/%s/%s";
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

    protected void clearCache(QueryRequest request){
        var api = String.format(DATASTORE_ADMIN_API, request.getCatalogue(), request.getCatalogueItem());

        webTestClient.get()
                .uri(api)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Success");
    }
    protected CatalogueItem getCatalogueItem(QueryRequest request){
        return appSettings.getCatalogueItem(request.getCatalogue(), request.getCatalogueItem());
    }
}
