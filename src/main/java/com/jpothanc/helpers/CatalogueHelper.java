package com.jpothanc.helpers;

import com.jpothanc.config.AppSettings;
import com.jpothanc.models.QueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.jpothanc.helpers.Constants.*;

public class CatalogueHelper {


     @Autowired
        private Environment environment;
    public static String getCatalogueKey(QueryRequest request){
        return getCatalogueKey(request.getCatalogue(),request.getCatalogueItem());
    }
    public static String getCatalogueKey(String catalogue, String catalogueItem){
        return new StringBuilder().append(catalogue).append(KEY_SEP).append(catalogueItem).toString();
    }
    public static String formattedTimeStamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT)).toString();
    }

     public static String formattedDayStamp(){
         return LocalDate.now().format(DateTimeFormatter.ofPattern(DAY_FORMAT)).toString();
     }
    public static String generateCacheKey(Object... params){
        var keyBuilder = new StringBuilder("ds:").append(formattedDayStamp()).append(KEY_SEP);
        for (var arg: params) {
            keyBuilder.append(arg.toString().toLowerCase());
            keyBuilder.append(KEY_SEP);
        }
        return keyBuilder.toString();
    }
}
