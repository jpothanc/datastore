package com.ibit.common.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;

@Getter
@Setter
public class DbResponse {
    private ResultSet resultSet;
}
