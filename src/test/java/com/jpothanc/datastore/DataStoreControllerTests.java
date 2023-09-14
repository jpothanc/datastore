package com.jpothanc.datastore;

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



import com.jpothanc.controllers.DataController;
import com.jpothanc.models.QueryRequest;
import com.jpothanc.services.CatalogueProvider;
import com.jpothanc.services.CatalogueService;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
//@WebFluxTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
//@AutoConfigureWebTestClient
//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@SpringBootTest
class DataStoreControllerTests {


    @Autowired
    private WebTestClient webTestClient;

    public DataStoreControllerTests() {

    }

    @Test
    public void testGetData1() {
        webTestClient.get()
                .uri("/api/v1/data/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("DataStore");
    }

//    @Test
//    public void test3GetSingleGithubRepository() {
//        webTestClient.get().uri("/api/repos/{repo}", "test-webclient-repository")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull());
//    }

}
