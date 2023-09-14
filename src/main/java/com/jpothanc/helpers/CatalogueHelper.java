package com.jpothanc.helpers;

import com.jpothanc.models.QueryRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CatalogueHelper {

    public static final String KEY_SEP = ":";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String getCatalogueKey(QueryRequest request){
        return getCatalogueKey(request.getCatalogue(),request.getCatalogueItem());
    }
    public static String getCatalogueKey(String catalogue, String catalogueItem){
        return new StringBuilder().append(catalogue).append(KEY_SEP).append(catalogueItem).toString();
    }

    public static String formattedTimeStamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT)).toString();
    }
}
