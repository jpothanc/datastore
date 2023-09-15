package com.ibit.common.database.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseSetting {
    private String connectionString;
    private String username;
    private String password;
    public String getConnectionString() {
        return String.format(connectionString, username,password);
    }
}
