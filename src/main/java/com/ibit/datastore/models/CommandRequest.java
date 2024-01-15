package com.ibit.datastore.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommandRequest {
    private String catalogue;
    private String catalogueItem;
    private String command;
    private boolean cancel;
    private String clientIdentifier;
}
