package com.ibit.common.database.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DbRequest {
    private String query;
    private DatasourceSetting databaseSetting;
}
