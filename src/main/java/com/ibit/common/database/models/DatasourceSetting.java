package com.ibit.common.database.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatasourceSetting {
    private String type;
    private String connectionString;
    private String username;
    private String password;
    private String healthQuery;
    public String getConnectionString() {
        return String.format(connectionString, username,password);
    }
}
