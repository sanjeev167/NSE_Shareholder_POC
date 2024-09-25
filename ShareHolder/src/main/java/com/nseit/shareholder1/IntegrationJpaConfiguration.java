package com.nseit.shareholder1;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "integrationManagerFactory",
    transactionManagerRef = "integrationTransactionManager", basePackages = {"com.nseit.regulatory"})
public class IntegrationJpaConfiguration {
	@Bean(name="integrationManagerFactory")
	  public LocalContainerEntityManagerFactoryBean integrationEntityManagerFactory(
	      EntityManagerFactoryBuilder builder, @Qualifier("integrationDataSource") DataSource dataSource) {
	    return builder.dataSource(dataSource).packages("com.nseit.regulatory").build();
	        
	  }

	@Bean(name="integrationTransactionManager")
	  public PlatformTransactionManager integrationTransactionManager(
	      @Qualifier("integrationManagerFactory") LocalContainerEntityManagerFactoryBean integrationManagerFactory) {
		  
	    return new JpaTransactionManager(Objects.requireNonNull(integrationManagerFactory.getObject()));
	  }
}
