package com.ibit.datastore;

import com.ibit.datastore.services.AppService;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


@SpringBootApplication
public class AppJava implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	public static void main(String[] args) {

		SpringApplication.run(AppJava.class, args);

		AppService cf = applicationContext.getBean(AppService.class);
		cf.start();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		AppJava.applicationContext = applicationContext;
	}
}
