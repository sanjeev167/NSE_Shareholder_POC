
package com.nse.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * sanjeevkumar 
 * 22-Jul-2024 
 * 4:04:34 pm
 * Objective: This approach should be used when we have a large no of key-set in the properties file. 
 * Here, we have a privilege to load keys using its prefix. This will work for object level field value initialization.
 * 
 *  ################################################
 *  Following keys will be loaded here automatically
 *  ################################################
 *  
 *  infobip.API_KEY will be loaded into API_KEY
 *  infobip.BASE_URL will be loaded into BASE_URL
 *  infobip.mailSubject will be loaded into mailSubject
 *  infobip.host-projectName will be loaded into projectName
 *  
 **/
@ConfigurationProperties(prefix="infobip")
@Configuration
@Data
public class PropertyViaConfiguration {
	private  String API_KEY;
	private  String BASE_URL;
	private  String mailSubject;
	private  String projectName;	
}//End of PropertyViaConfiguration
