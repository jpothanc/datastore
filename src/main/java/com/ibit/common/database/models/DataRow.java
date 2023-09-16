package com.ibit.common.database.models;

import com.google.gson.JsonObject;
import lombok.Getter;

@Getter
public class DataRow {
    private JsonObject jsonObject;

    public DataRow() {
        jsonObject = new JsonObject();
    }

    public void put(String key, Object value) {
        jsonObject.addProperty(key, value.toString());
    }

    public void remove(String key, Object value) {
        jsonObject.remove(key);
    }
}
