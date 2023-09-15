package com.ibit.common.database.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DbResponse {
    private Map<Long, DataRow> resultSet;
}
