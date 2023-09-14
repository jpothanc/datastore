package com.jpothanc.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonObject;
import com.jpothanc.helpers.CatalogueHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static com.jpothanc.helpers.CatalogueHelper.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class QueryResponse {
    private String catalogueItem;
    private String error;
    private String timeStamp;
    private int records;
    private HttpStatusCode statusCode;
    private List<JsonObject> result;
    public boolean hasValue(){
        return result != null;
    }
    public static ResponseEntity<QueryResponse> notFound(String catalogueKey, String errorMessage, HttpStatusCode statusCode){

        return new ResponseEntity<>(generateResponse(catalogueKey,errorMessage,HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<QueryResponse> badRequest(String catalogueKey, String errorMessage,HttpStatusCode statusCode){

        return new ResponseEntity<>(generateResponse(catalogueKey,errorMessage,HttpStatus.BAD_REQUEST), HttpStatus.NOT_FOUND);
    }
    private static QueryResponse generateResponse(String catalogueKey, String errorMessage, HttpStatusCode statusCode) {
        var response = new QueryResponse();
        response.setCatalogueItem(catalogueKey);
        response.setError(errorMessage);
        response.setStatusCode(HttpStatus.NOT_FOUND);
        response.setTimeStamp(formattedTimeStamp());
        return response;
    }
}
