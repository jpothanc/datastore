package com.ibit.datastore.services;

import com.google.gson.JsonObject;
import com.ibit.datastore.models.Enums.CatalogueProviders;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import io.swagger.annotations.Scope;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Scope(name = "prototype", description = "")
public class DbCatalogueProviderImpl implements CatalogueProvider {
    @Override
    public CatalogueProviders getName() {
        return CatalogueProviders.Database;
    }

    @Override
    public CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request) {

        System.out.println("Querying Catalogue " + request.getCatalogueItem() + " using :" + getName().toString());

        List<JsonObject> res =  new ArrayList<JsonObject>();

        JsonObject jsonObject = new JsonObject();
        // Add key-value pairs to the JSON object
        jsonObject.addProperty("name", "John");
        jsonObject.addProperty("age", 30);
        jsonObject.addProperty("city", "New York");
        res.add(jsonObject);
        var response =  QueryResponse.createOkResponse(request);
        response.setResult(res);
        response.setRecords(res.size());
        Query();
        return CompletableFuture.completedFuture(response);
    }
    public  void Query() {

        String jdbcUrl = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "admin";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            if (connection != null) {
                System.out.println("Connected to the database");

                // Create a statement
                Statement statement = connection.createStatement();

                // Execute an SQL query
                String sqlQuery = "SELECT * FROM Student"; // Replace with your SQL query
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                // Process the query results
                while (resultSet.next()) {
                    // Retrieve data from the result set
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");

                    // Process the data
                    System.out.println("ID: " + id + ", Name: " + name);
                }

                // Close the result set and statement
                resultSet.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
