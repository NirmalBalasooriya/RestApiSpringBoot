package com.nirmal.springbootrest;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Boot initialization class of the ResrApiSpringBoot project
 * 
 * @author Nirmal Balasooriya
 *
 */

@ComponentScan({ "com.nirmal.springbootrest", "com.nirmal.springbootres.controller" })
@EnableJpaRepositories("com.nirmal.springbootrest")
@SpringBootApplication(scanBasePackages = { "com.nirmal.springbootres.controller" })
public class AppInitializer extends SpringBootServletInitializer {

	@Autowired
	DataSource dataSource;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppInitializer.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AppInitializer.class, args);
	}
}
