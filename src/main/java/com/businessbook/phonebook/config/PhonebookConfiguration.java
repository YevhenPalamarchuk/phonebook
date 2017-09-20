
package com.businessbook.phonebook.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class PhonebookConfiguration extends Configuration {

    private DriverManagerDataSource dataSource;

    @Valid
    @NotNull
    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    @JsonProperty("dataSource")
    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

}
