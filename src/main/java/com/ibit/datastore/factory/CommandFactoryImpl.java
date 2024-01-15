package com.ibit.datastore.factory;

import com.ibit.datastore.services.command.WebCounterCmdService;
import com.ibit.datastore.services.command.WebCounterCmdServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CommandFactoryImpl implements CommandFactory, ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public WebCounterCmdService getWebCounterCommand() {
        return applicationContext.getBean(WebCounterCmdServiceImpl.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
}
