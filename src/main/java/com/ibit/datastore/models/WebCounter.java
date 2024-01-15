package com.ibit.datastore.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity

public class WebCounter {
    @Id
    private String siteName;
    private int counter;
    private String modified_by;
    private String modified_date;
}
