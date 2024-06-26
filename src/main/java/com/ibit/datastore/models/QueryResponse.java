package com.ibit.datastore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonObject;
import com.ibit.common.database.models.DataRow;
import com.ibit.datastore.helpers.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ibit.datastore.helpers.CatalogueHelper.formattedTimeStamp;
import static com.ibit.datastore.helpers.CatalogueHelper.getCatalogueKey;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class QueryResponse {
    private String catalogueItem;
    private String error;
    private String timeStamp;
    private int records;
    private String statusCode;
    @JsonIgnore
    private Map<Long, DataRow> data;
    private String source;
    private String cacheKey;
    @JsonIgnore
    private List<JsonObject> result;

    public boolean hasValue() {
        return result != null;
    }

    public void setData(Map<Long, DataRow> result) {
        this.data = result;
        if (result != null)
            this.records = result.size();
    }

    public QueryResponse clone() {
        var q = new QueryResponse();
        q.cacheKey = this.cacheKey;
        q.records = this.records;
        q.statusCode = this.statusCode;
        q.catalogueItem = this.catalogueItem;
        q.error = this.error;
        q.timeStamp = this.timeStamp;
        q.source = this.source;
        q.data = null;
        q.result = new ArrayList<>();

        for (var key : data.keySet()) {
            var row = data.get(key);
            q.result.add(row.getJsonObject());
        }

        return q;
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
        var response = new QueryResponse();
        response.setCatalogueItem(catalogueKey);
        response.setError(errorMessage);
        response.setStatusCode(statusCode.toString());
        response.setTimeStamp(formattedTimeStamp());
        response.setSource(Constants.CATALOGUE_SOURCE_QUERY);
        return response;
    }
}
