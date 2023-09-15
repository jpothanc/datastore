package com.ibit.datastore.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Catalogue {
    private Map<String,CatalogueItem> items = new HashMap<>();

}
