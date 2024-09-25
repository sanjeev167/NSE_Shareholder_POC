package com.nse.common.db.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.zaxxer.hikari.HikariDataSource;
/**
 * @author sanjeevkumar 
 * 20-July-2024
 * 11:43:00 am 
 * Objective:
 */

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application.properties" })
@EnableJpaRepositories(basePackages = {ApiJpaConstants.PKG_SEC_REPO,ApiJpaConstants.PKG_PUB_REPO }, 
                                       entityManagerFactoryRef = "apiServiceEntityManager", 
                                       transactionManagerRef = "apiServiceTransactionManager")

public class ApiServicePersistenceConfig {


	@Autowired
	private Environment env;// Contains Properties loaded by @PropertySources		
	
	/**
	 * Author: sanjeevkumar<p>
	 * Date 20-July-2024<p>
	 * Objective : This bean has been configure for creating a data-source for apiService.<p>
	 * Using a datasource, we could get a connection for doing database related operation.
	 *
	 */
	@Bean(name = "apiServiceDataSource")
	public HikariDataSource apiServiceDataSource() {
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));		
		hikariDataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
		hikariDataSource.setUsername(env.getProperty("spring.datasource.username"));
		hikariDataSource.setPassword(env.getProperty("spring.datasource.password"));
		hikariDataSource.setMaximumPoolSize(Integer.parseInt(env.getProperty("spring.datasource.hikari.maximumPoolSize")));
		hikariDataSource.setMinimumIdle(Integer.parseInt(env.getProperty("spring.datasource.hikari.minimumIdle")));			
		hikariDataSource.setIdleTimeout(Integer.parseInt(env.getProperty("spring.datasource.hikari.idleTimeout")));
		hikariDataSource.setConnectionTimeout(Integer.parseInt(env.getProperty("spring.datasource.hikari.connectionTimeout")));
		hikariDataSource.setLeakDetectionThreshold(1);//Connection leak detection is enabled
		hikariDataSource.setMaxLifetime(Integer.parseInt(env.getProperty("spring.datasource.hikari.maxLifetime")));
		hikariDataSource.setPoolName(env.getProperty("spring.datasource.hikari.poolName"));	
		return hikariDataSource;
	}//End of apiServiceDataSource

	/**
	 * Author: sanjeevkumar<p>
	 * Date 20-July-2024<p>
	 * Objective This bean has been configured for creating an apiServiceEntityManager for the
	 *            apiService. This will manage all those entities which have
	 *            been defined in API_SERVICE_PKG_ENTITIES_ARRAY under API_SERVICE_JPA_UNIT. 
	 *            An entity-manager is used for doing operation through entities.
	 *
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean apiServiceEntityManager() {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		
		em.setDataSource(apiServiceDataSource());
		// Scan Entities in Package:
		em.setPackagesToScan(ApiJpaConstants.API_SERVICE_PKG_ENTITIES_ARRAY); 
		em.setPersistenceUnitName(ApiJpaConstants.API_SERVICE_JPA_UNIT);// Important !!
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);		
		em.setJpaPropertyMap(createProps());
		em.afterPropertiesSet();
		return em;
	}//End of apiServiceEntityManager
	
	
	/**
	 * Author: sanjeevkumar<p>
	 * Date 20-July-2024<p>
	 * Objective : This method will be used for creating an apiServiceTransactionManager for apiServiceEntityManager. It is used for managing a transaction.<p>
	 *
	 */
	
	@Bean(name = "apiServiceTransactionManager")
	public PlatformTransactionManager apiServiceTransactionManager() {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(apiServiceEntityManager().getObject());
		return transactionManager;
	}//End of apiServiceTransactionManager
	
	
	
	private Map<String, Object> createProps(){ 
	      
			final HashMap<String, Object> jpaProperties = new LinkedHashMap<String, Object>();

			// Configures the used database dialect. This allows Hibernate to create SQL that is optimized for the used database.
			jpaProperties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

			// If the value of this property is true, Hibernate writes all SQL statements to the console.
			jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));

			// If the value of this property is true, Hibernate will format the SQL that is written to the console.
			jpaProperties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));

			// Solved Error: PostGres createClob() is not yet implemented. PostGres Only:
			jpaProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);
	        //If this value comes as update/create, table will be created using entities
			jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
			
			/* Will be used when required
			spring.jpa.hibernate.naming-strategy			
			spring.jpa.properties.hibernate.dialect			
			spring.datasource.testWhileIdl			
			spring.datasource.validationQuery
			*/	
			
	      return jpaProperties;
	   }

}// End of ApiServicePersistenceConfig
