package com.ibit.common.database;

import com.ibit.common.database.models.DataRow;
import com.ibit.common.database.models.DbRequest;
import com.ibit.common.database.models.DbResponse;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Service

public class DatabaseServiceImpl implements DatabaseService {

    public DbResponse Query(DbRequest request) {

        Map<Long, DataRow> result = new HashMap<>(100);

        try (Connection connection = DriverManager.getConnection(
                request.getDatabaseSetting().getConnectionString(),
                request.getDatabaseSetting().getUsername(),
                request.getDatabaseSetting().getPassword())) {
            if (connection != null) {
                System.out.println("Connected to the database");
                Statement statement = connection.createStatement();

                String sqlQuery = request.getQuery();
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                Long rowId = 0L;
                // Process the query results
                while (resultSet.next()) {

                    DataRow dataRow = new DataRow();

                    for (int i = 1; i <= columnCount; i++) {
                        String name = metaData.getColumnName(i);
                        var value = resultSet.getObject(name);
                        dataRow.put(name, value);
                    }
                    result.put(++rowId, dataRow);
                }

                resultSet.close();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        var response = new DbResponse();
        response.setResultSet(result);
        return response;
    }
}

