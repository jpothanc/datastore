package com.ibit.datastore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonObject;
import com.ibit.common.database.models.DataRow;
import com.ibit.datastore.helpers.Constants;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ibit.datastore.helpers.CatalogueHelper.formattedTimeStamp;
import static com.ibit.datastore.helpers.CatalogueHelper.getCatalogueKey;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@Jacksonized

public class QueryResponse {
    private String catalogueItem;
    private String error;
    private String timeStamp;
    private int records;
    private String statusCode;
    private String source;
    private String cacheKey;
    @JsonIgnore
    private List<JsonObject> result;
    @JsonIgnore
    private Map<Long, DataRow> data;

    public boolean hasValue() {
        return result != null;
    }

    public void setData(Map<Long, DataRow> result) {
        this.data = result;
        if (result != null)
            this.records = result.size();
    }

    public QueryResponse toResult() {

        // Convert the values in the Map to a List of JsonObject
        var result = data.values().stream()
                .map(DataRow::getJsonObject)
                .collect(Collectors.toList());

        return  QueryResponse.builder()
                .cacheKey(this.cacheKey)
                .records(this.records)
                .statusCode(this.statusCode)
                .catalogueItem(this.catalogueItem)
                .error(this.error)
                .timeStamp(this.timeStamp)
                .source(this.source)
                .data(null)
                .result(result)
                .build();
    }

    public static QueryResponse createOkResponse(QueryRequest request) {

        var catalogueKey =  getCatalogueKey(request.getCatalogue(), request.getCatalogueItem());
        return generateResponse(catalogueKey, "", HttpStatus.OK);
    }

    public static Mono<ResponseEntity<QueryResponse>> notFound(QueryRequest request, String errorMessage, HttpStatusCode statusCode) {

        var catalogueKey =  getCatalogueKey(request.getCatalogue(), request.getCatalogueItem());
        return notFound(catalogueKey, errorMessage, HttpStatus.NOT_FOUND);
    }

    public static Mono<ResponseEntity<QueryResponse>> badRequest(QueryRequest request, String errorMessage, HttpStatusCode statusCode) {

        var catalogueKey =  getCatalogueKey(request.getCatalogue(), request.getCatalogueItem());
        return notFound(catalogueKey, errorMessage, HttpStatus.BAD_REQUEST);
    }

    public static Mono<ResponseEntity<QueryResponse>> notFound(String catalogueKey, String errorMessage, HttpStatusCode statusCode) {

        return Mono.just(new ResponseEntity<>(generateResponse(catalogueKey, errorMessage, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND));
    }

    public static Mono<ResponseEntity<QueryResponse>> badRequest(String catalogueKey, String errorMessage, HttpStatusCode statusCode) {

        return Mono.just(new ResponseEntity<>(generateResponse(catalogueKey, errorMessage, HttpStatus.BAD_REQUEST), HttpStatus.NOT_FOUND));
    }

    private static QueryResponse generateResponse(String catalogueKey, String errorMessage, HttpStatusCode statusCode) {
        return QueryResponse.builder().
                catalogueItem(catalogueKey).
                error(errorMessage).
                statusCode(statusCode.toString()).
                timeStamp(formattedTimeStamp())
                .source(Constants.CATALOGUE_SOURCE_QUERY).
                build();
    }
}
