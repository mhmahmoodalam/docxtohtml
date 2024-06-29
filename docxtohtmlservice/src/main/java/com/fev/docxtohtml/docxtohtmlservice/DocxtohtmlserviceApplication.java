package com.fev.docxtohtml.docxtohtmlservice;

import com.fev.docxtohtml.docxtohtmlservice.config.StorageProperties;
import com.fev.docxtohtml.docxtohtmlservice.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableAsync
public class DocxtohtmlserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocxtohtmlserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}
