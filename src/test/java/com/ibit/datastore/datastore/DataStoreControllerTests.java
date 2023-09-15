package com.ibit.datastore.datastore;

//Annotation	Description
//@BeforeAll	Method annotated with this runs once before all test methods in the test class.
//@AfterAll	Method annotated with this runs once after all test methods in the test class.
//@BeforeEach	Method annotated with this runs before each individual test method in the test class.
//@AfterEach	Method annotated with this runs after each individual test method in the test class.
//@Test	Method annotated with this is a test method that contains the actual test logic.
//@TestFactory	Method annotated with this is a factory for dynamic tests, allowing you to create tests at runtime.
//@ParameterizedTest	Method annotated with this is used for parameterized testing, allowing multiple test runs with different inputs.
//@Disabled	Method or class annotated with this is disabled and will not be executed as part of the test suite.
//        These annotations provide control over the execution order and behavior of your JUnit 5 test methods and allow you to set up and tear down resources, perform setup tasks, and organize your tests effectively.

import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import com.ibit.datastore.services.AppService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.ibit.datastore.helpers.CatalogueHelper.getCatalogueKey;
import static com.ibit.datastore.helpers.Constants.CATALOGUE_SOURCE_CACHED;
import static com.ibit.datastore.helpers.Constants.CATALOGUE_SOURCE_QUERY;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:appsettings.test.json")
@FixMethodOrder(MethodSorters.JVM) //Disable parallel execution
class DataStoreControllerTests extends BaseTest{
    @BeforeEach
    public void beforeEachTest(){
        appService.start();
    }
    @Autowired
    private WebTestClient webTestClient;
    @Test
    public void when_DataControllerIsQueries_ShouldReturnValidResponse() {
        webTestClient.get()
                .uri("/api/v1/data/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("DataStore");
    }


    @ParameterizedTest
    @MethodSource("getValidQueryRequest")
    public void when_CatalogueItemIsQueried_ShouldReturnValidQueryResponse(QueryRequest request) {
        var api = String.format(DATASTORE_API, request.getCatalogue(), request.getCatalogueItem());
        var cacheKey = getCatalogueItem(request).getCacheKey();
        webTestClient.get()
                .uri(api)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(QueryResponse.class)
                .consumeWith(response -> {
                    QueryResponse res = response.getResponseBody();
                    // Assert individual attributes of response
                    assertEquals(res.getStatusCode(), HttpStatus.OK.toString());
                    assertEquals(res.getCatalogueItem(), getCatalogueKey(request));
                    assertNotNull(res);
                    assertNull(res.getData());
                    assertNotNull(res.getResult());
                    assertEquals(res.getCacheKey(), cacheKey);
                });
    }
    @ParameterizedTest
    @MethodSource("getInValidQueryRequest")
    public void when_InValidCatalogueItemIsQueried_ShouldReturnValidQueryResponse(QueryRequest request) {
        var api = String.format(DATASTORE_API, request.getCatalogue(), request.getCatalogueItem());
        webTestClient.get()
                .uri(api)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(QueryResponse.class)
                .consumeWith(response -> {
                    QueryResponse res = response.getResponseBody();
                    // Assert individual attributes of response
                    assertNotNull(res);
                    assertEquals(res.getCatalogueItem(), getCatalogueKey(request));
                    assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND.toString());
                    assertNull(res.getData());
                    assertNull(res.getResult());
                });
    }

    @Test
      public void when_CatalogueItemIsQueriedTwice_ShouldReturnSourceAsCachedOnSecondCall() {
        var request = getQueryRequest();
        var api = String.format(DATASTORE_API, request.getCatalogue(), request.getCatalogueItem());
        webTestClient.get()
                .uri(api)
                .exchange()
                .expectStatus().isOk()
                .expectBody(QueryResponse.class)
                .consumeWith(response -> {
                    QueryResponse res = response.getResponseBody();
                    assertEquals(res.getSource(), CATALOGUE_SOURCE_QUERY);
                });

        webTestClient.get()
                .uri(api)
                .exchange()
                .expectStatus().isOk()
                .expectBody(QueryResponse.class)
                .consumeWith(response -> {
                    QueryResponse res = response.getResponseBody();
                    assertEquals(res.getSource(), CATALOGUE_SOURCE_CACHED);
                });
    }

    @Test
    public void when_CatalogueItemIsQueriedTwice_ShouldReturnSourceAsCachedOnSecondCall1() {
        var request = getQueryRequest();
        var api = String.format(DATASTORE_API, request.getCatalogue(), request.getCatalogueItem());
        webTestClient.get()
                .uri(api)
                .exchange()
                .expectStatus().isOk()
                .expectBody(QueryResponse.class)
                .consumeWith(response -> {
                    QueryResponse res = response.getResponseBody();
                    assertEquals(res.getSource(), CATALOGUE_SOURCE_QUERY);
                });

        webTestClient.get()
                .uri(api)
                .exchange()
                .expectStatus().isOk()
                .expectBody(QueryResponse.class)
                .consumeWith(response -> {
                    QueryResponse res = response.getResponseBody();
                    assertEquals(res.getSource(), CATALOGUE_SOURCE_CACHED);
                });
    }
}
