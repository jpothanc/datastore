package com.ibit.datastore.factory;

import com.ibit.datastore.models.CommandRequest;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.services.command.WebCounterCmdService;
import com.ibit.datastore.services.providers.CatalogueProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;
public interface CommandFactory {
    WebCounterCmdService getWebCounterCommand();

}
