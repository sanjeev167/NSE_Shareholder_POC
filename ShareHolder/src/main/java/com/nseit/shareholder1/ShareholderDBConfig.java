package com.nseit.shareholder1;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//  entityManagerFactoryRef = "entityManagerFactory",
//  basePackages = { "com.nseit.shareholder1.dao" }
//)
public class ShareholderDBConfig {
	
	
	
	  
//	  @Primary
	  @Bean
	  @ConfigurationProperties(prefix = "spring.datasource")
	  public DataSourceProperties shareholderDataSourceProperties() {
	    return new DataSourceProperties();
	  }
	  
	  @Bean
	  @ConfigurationProperties(prefix = "integration")
	  public DataSourceProperties integrationDataSourceProperties() {
	    return new DataSourceProperties();
	  }
	  
	  @Primary
	  @Bean 
	  public DataSource shareholderDataSource() {
		  return shareholderDataSourceProperties().initializeDataSourceBuilder().build();
	  }
	  
	  @Bean(name="integrationDataSource")
	  public DataSource integrationDataSource() {
		  return integrationDataSourceProperties().initializeDataSourceBuilder().build();
	  }
	  

	

}
