
package com.businessbook.phonebook;

import java.util.Arrays;
import java.util.Map;

import javax.ws.rs.Path;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.businessbook.phonebook.config.PhonebookConfiguration;
import com.businessbook.phonebook.config.SpringConfiguration;
import com.businessbook.phonebook.config.SpringContextLoaderListener;
import com.businessbook.phonebook.resources.ContactResource;
import com.codahale.metrics.health.HealthCheck;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class App extends Application<PhonebookConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<PhonebookConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(PhonebookConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(PhonebookConfiguration configuration, Environment environment) throws Exception {
        AnnotationConfigWebApplicationContext parent = new AnnotationConfigWebApplicationContext();
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        parent.refresh();
        parent.getBeanFactory().registerSingleton("configuration", configuration);
        parent.registerShutdownHook();
        parent.start();

        context.setParent(parent);
        context.register(SpringConfiguration.class);
        context.refresh();
        context.registerShutdownHook();
        context.start();

        Arrays.asList(context.getBeanDefinitionNames()).forEach(System.out::println);

        registerHealthChecks(environment, context);
        registerResources(environment, context);

        // linking Spring to the embedded Jetty in Dropwizard
        environment.servlets().addServletListeners(new SpringContextLoaderListener(context));
        final ContactResource contactResource = new ContactResource();
        environment.jersey().register(contactResource);
    }

    private void registerResources(Environment environment, AnnotationConfigWebApplicationContext context) {
        Map<String, Object> resources = context.getBeansWithAnnotation(Path.class);
        for (Map.Entry<String, Object> entry : resources.entrySet()) {
            environment.jersey().register(entry.getValue());
        }
    }

    private void registerHealthChecks(Environment environment, AnnotationConfigWebApplicationContext context) {
        Map<String, HealthCheck> healthChecks = context.getBeansOfType(HealthCheck.class);
        for (Map.Entry<String, HealthCheck> entry : healthChecks.entrySet()) {
            environment.healthChecks().register("template", entry.getValue());
        }
    }
}
