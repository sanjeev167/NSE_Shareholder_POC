package com.nse.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.nse.common.db.config.DatabaseRepositoryControl;
import com.nse.common.sec.config.mfactor.InfobipService;
import com.nse.common.util.PropertyViaConfiguration;
import com.nse.common.util.PropertyViaEnvironment;
import com.nse.common.util.StaticPropertyHolder;

@SpringBootApplication
public class ShCommonApplication implements CommandLineRunner {

	@Autowired
	DatabaseRepositoryControl databaseRepositoryControl;
	//@Autowired
	//PropertyViaEnvironment propertyViaEnvironment;
	//@Autowired
	//PropertyViaConfiguration propertyViaConfiguration;
	
	public static void main(String[] args) {
		SpringApplication.run(ShCommonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//databaseRepositoryControl.printHikariConnectionDetails();		
		//System.out.println("propertyViaEnvironment API-KEY = "+propertyViaEnvironment.getAPI_KEY());
		//System.out.println("propertyViaEnvironment BASE_URL = "+propertyViaEnvironment.getBASE_URL());
		//System.out.println("StaticPropertyHolder API-KEY = "+StaticPropertyHolder.API_KEY);
		//System.out.println("StaticPropertyHolder BASE_URL = "+StaticPropertyHolder.BASE_URL);
		//System.out.println("propertyViaConfiguration API-KEY = "+propertyViaConfiguration.getAPI_KEY());
		//System.out.println("propertyViaConfiguration BASE_URL = "+propertyViaConfiguration.getBASE_URL());		
	}
}
