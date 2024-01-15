package com.ibit.datastore.services.command;

public interface WebCounterCmdService {
    int getCounter(String siteName);
    void incrementCounter(String siteName);
}
