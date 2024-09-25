package com.nseit.shareholder1;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "shareholderManagerFactory",
    transactionManagerRef = "shareholderTransactionManager", basePackages = {"com.nseit.shareholder1"})
public class ShareholderJpaConfiguration {
	 @Bean(name="shareholderManagerFactory")
	 @Primary
	  public LocalContainerEntityManagerFactoryBean shareholderEntityManagerFactory(
	      EntityManagerFactoryBuilder builder, @Qualifier("shareholderDataSource") DataSource dataSource) {
	    return builder.dataSource(dataSource).packages("com.nseit.shareholder1") .build();
	        
	  }

	  @Bean(name="shareholderTransactionManager")
	  public PlatformTransactionManager shareholderTransactionManager(
	      @Qualifier("shareholderManagerFactory") LocalContainerEntityManagerFactoryBean shareholderManagerFactory) {
		  
	    return new JpaTransactionManager(Objects.requireNonNull(shareholderManagerFactory.getObject()));
	  }

}
