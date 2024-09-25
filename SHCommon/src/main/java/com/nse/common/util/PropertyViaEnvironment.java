
package com.nse.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * sanjeevkumar 
 * 22-Jul-2024 
 * 4:04:34 pm 
 * Objective: This can load all properties keys in a spring-environment-variable. Here, you have a privilege
 * of using a properties file of your choice. Only the mandatory thing about it is that it must be in the classpath.
 * Keep it in mind that this approach will be used at object level field initialization. It can't be used for static field initialization.
 */
@PropertySource({ "classpath:application.properties" })
@Component
public class PropertyViaEnvironment {

	@Autowired
	private Environment env;

	public  String getAPI_KEY() {
		return    env.getProperty("infobip.API_KEY");
	}

	public  String getBASE_URL() {		
		return    env.getProperty("infobip.BASE_URL");
	}

	public  String getMailSubject() {		
		return    env.getProperty("infobip.mailSubject");
	}

	public  String getProjectName() {	
		return    env.getProperty("infobip.projectName");
	}

}// End of PropertyViaEnvironment
