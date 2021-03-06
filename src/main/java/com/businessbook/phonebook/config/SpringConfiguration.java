
package com.businessbook.phonebook.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan(basePackages = "com.businessbook.phonebook")
public class SpringConfiguration {

    @Autowired
    @Qualifier("configuration")
    private PhonebookConfiguration configuration;

    @Bean
    public DataSource dataSource() {
        System.out.println(configuration.getDataSource().getPassword());
        System.out.println(configuration.getDataSource().getUrl());
        return configuration.getDataSource();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }
}
