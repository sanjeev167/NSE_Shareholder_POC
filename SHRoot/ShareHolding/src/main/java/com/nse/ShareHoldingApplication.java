package com.nse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nse.common.TestController;

@SpringBootApplication
public class ShareHoldingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareHoldingApplication.class, args);
		TestController testController=new TestController();
		testController.welcome();
	}

}
