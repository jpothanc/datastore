package com.ibit.datastore.helpers;

import com.ibit.datastore.models.QueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CatalogueHelper {


     @Autowired
        private Environment environment;
    public static String getCatalogueKey(QueryRequest request){
        return getCatalogueKey(request.getCatalogue(),request.getCatalogueItem());
    }
    public static String getCatalogueKey(String catalogue, String catalogueItem){
        return new StringBuilder().append(catalogue).append(Constants.KEY_SEP).append(catalogueItem).toString();
    }
    public static String formattedTimeStamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.TIME_FORMAT)).toString();
    }

     public static String formattedDayStamp(){
         return LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DAY_FORMAT)).toString();
     }
    public static String generateCacheKey(Object... params){
        var keyBuilder = new StringBuilder("ds:").append(formattedDayStamp()).append(Constants.KEY_SEP);
        for (var arg: params) {
            keyBuilder.append(arg.toString().toLowerCase());
            keyBuilder.append(Constants.KEY_SEP);
        }
        return keyBuilder.toString();
    }
}
