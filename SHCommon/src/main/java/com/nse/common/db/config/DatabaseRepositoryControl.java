/**
 * 
 */
package com.nse.common.db.config;



import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariDataSource;



/**
 * @author sanjeevkumar 
 * 20-July-2024
 * 12:43:00 am 
 * Objective:
 */
@Service
public class DatabaseRepositoryControl {
	
	@Autowired
	HikariDataSource dataSource;

    /***
     * If you are using DBCP connection pooling then use this method to get the connection details
     */
    /*public void printDBCPConnectionDetails()
    {
        BasicDataSource basicDataSource = (BasicDataSource) dataSource;
        System.out.println("Instace of DBCP basic data source: " + basicDataSource);
        System.out.println("Driver class name: " + basicDataSource.getDriverClassName());
        System.out.println("Max idle connection: " + basicDataSource.getMaxIdle());
        System.out.println("Total connection: " + basicDataSource.getMaxTotal());
    }*/

    /***
     * If using Hikari connection pooling
     * @throws SQLException 
     */
    public void printHikariConnectionDetails() throws SQLException
    {
        HikariDataSource ds = (HikariDataSource)dataSource;   
        System.out.println("Url: " + ds.getJdbcUrl());
        System.out.println("Driver class name: " + ds.getDriverClassName());
        System.out.println("Instace of DBCP basic data source: " + ds);       
        System.out.println("Maximum - Connection Pool size : " + ds.getMaximumPoolSize());
        System.out.println("Database Meta => "+ ds.getConnection().getMetaData());
        System.out.println("MinimumIdle connection count = "+ds.getMinimumIdle());

    }

    /***
     * If using C3P0 connection pooling
     */
    /*public void printC3P0ConnectionDetails()
    {
        ComboPooledDataSource ds = (ComboPooledDataSource) dataSource;
        System.out.println("Instace of DBCP basic data source: " + ds);
        System.out.println("Connection Pool size : " + ds.getMaxPoolSize());
        System.out.println("Min connection pool size: " + ds.getMinPoolSize());
        System.out.println("Max statements: " + ds.getMaxStatements());
        System.out.println("Url: " + ds.getJdbcUrl());
    }*/
}
