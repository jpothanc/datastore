package com.ibit.common.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DbRequest {
    private String query;
    private DatabaseSetting databaseSetting;
}
