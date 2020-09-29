package com.customer.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * CLASSE DE CONEXÃO COM A APPLICAÇÃO EXTERNA
 */
@Configuration
public class Config implements WebMvcConfigurer {

	public void configureAsyncSupport(AsyncSupportConfigurer configure) {
		configure.setDefaultTimeout(-1);
		configure.setTaskExecutor(asyncTaskExecutor());
	}

	@Bean
	public AsyncTaskExecutor asyncTaskExecutor() {
		return new SimpleAsyncTaskExecutor("async");
	}

}
