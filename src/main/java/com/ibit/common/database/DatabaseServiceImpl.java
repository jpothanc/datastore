package com.ibit.common.database;

import com.ibit.common.models.DbRequest;
import com.ibit.common.models.DbResponse;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseServiceImpl implements  DatabaseService {

    public DbResponse Query(DbRequest request) {
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
        return  new DbResponse();
    }
}

