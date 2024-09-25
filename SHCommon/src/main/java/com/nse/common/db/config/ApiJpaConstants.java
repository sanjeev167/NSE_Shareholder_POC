/**
 * 
 */
package com.nse.common.db.config;

/**
 * @author sanjeevkumar
 * 20-July-2024
 * 11:50:26 am 
 * Objective: 
 */
public class ApiJpaConstants {
	
	//Persistence unit 
    public static final String API_SERVICE_JPA_UNIT ="ApiService";
   
	
	//Define all the entities package names here. This will be an entityManager specific
    public static final String[] API_SERVICE_PKG_ENTITIES_ARRAY=new String[] {  "com.nse.common.sec.entities",
    		                                                                    "com.nse.common.pub.entities" };    
    
  //Define all the repositories package names here. This will be an entityManager specific   
    public static final String PKG_SEC_REPO = "com.nse.common.sec.repo";
    public static final String PKG_PUB_REPO = "com.nse.common.pub.repo";
  
    
    
}//End of ApiJpaConstants
