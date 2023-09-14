package com.ibit.common.database;

import com.ibit.common.models.DbRequest;
import com.ibit.common.models.DbResponse;

public interface DatabaseService {
    DbResponse Query(DbRequest request);
}
