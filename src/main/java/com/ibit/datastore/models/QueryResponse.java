package com.ibit.datastore.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonObject;
import com.ibit.datastore.helpers.Constants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.ibit.datastore.helpers.CatalogueHelper.*;

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
    private List<JsonObject> result;
    private String source;
    private String cacheKey;

    public boolean hasValue() {
        return result != null;
    }

    public static QueryResponse createOkResponse(QueryRequest request) {
        return generateResponse(request, "", HttpStatus.OK);
    }

    public static ResponseEntity<QueryResponse> notFound(QueryRequest request, String errorMessage, HttpStatusCode statusCode) {

        return new ResponseEntity<>(generateResponse(request, errorMessage, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<QueryResponse> badRequest(QueryRequest request, String errorMessage, HttpStatusCode statusCode) {

        return new ResponseEntity<>(generateResponse(request, errorMessage, HttpStatus.BAD_REQUEST), HttpStatus.NOT_FOUND);
    }

    private static QueryResponse generateResponse(QueryRequest request, String errorMessage, HttpStatusCode statusCode) {
        var response = new QueryResponse();
        response.setCatalogueItem(getCatalogueKey(request));
        response.setError(errorMessage);
        response.setStatusCode(statusCode.toString());
        response.setTimeStamp(formattedTimeStamp());
        response.setSource(Constants.CATALOGUE_SOURCE_QUERY);
        return response;
    }
}
