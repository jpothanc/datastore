package com.ibit.common.database;

import com.ibit.common.database.models.DbRequest;
import com.ibit.common.database.models.DbResponse;
import org.springframework.stereotype.Service;

public interface DatabaseService {
    DbResponse Query(DbRequest request);
}
