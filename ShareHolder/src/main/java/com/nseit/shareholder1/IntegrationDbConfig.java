//package com.nseit.shareholder1;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(entityManagerFactoryRef = "integrationManagerFactory",
//    transactionManagerRef = "integrationTransactionManager", basePackages = {"com.nseit.shareholder1.regulatorydao"})
//public class IntegrationDbConfig {
//	 @Bean(name = "integrationDataSource")
//	  @ConfigurationProperties(prefix = "integration.datasource")
//	  public DataSource dataSource() {
//	    return DataSourceBuilder.create().build();
//	  }
//
//	  @Bean(name = "integrationEntityManagerFactory")
//	  public LocalContainerEntityManagerFactoryBean barEntityManagerFactory(
//	      EntityManagerFactoryBuilder builder, @Qualifier("integrationDataSource") DataSource dataSource) {
//	    return builder.dataSource(dataSource).packages("com.nseit.shareholder1.regulatorymodel").persistenceUnit("integration")
//	        .build();
//	  }
//
//	  @Bean(name = "inntegrationTransactionManager")
//	  public PlatformTransactionManager barTransactionManager(
//	      @Qualifier("barEntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
//	    return new JpaTransactionManager(barEntityManagerFactory);
//	  }
//}
