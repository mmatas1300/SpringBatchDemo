package com.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//Configuracion a DB desde clase
@Configuration
public class ApplicationConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Drive");
        dataSource.setUrl("jdbc:mysql://localhost:3306/batch_demo");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        return dataSource;
    }
}
