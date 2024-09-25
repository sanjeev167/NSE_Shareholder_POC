package com.nseit.shareholder1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@ComponentScan(basePackages = {
		"com.nseit.shareholder1","com.nseit.regulatory"
})
public class ShareHolderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareHolderApplication.class, args);
	}

}
