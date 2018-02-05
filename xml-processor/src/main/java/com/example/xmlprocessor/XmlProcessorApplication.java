package com.example.xmlprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableJpaAuditing
public class XmlProcessorApplication {
	private static final Logger logger = LoggerFactory.getLogger(XmlProcessorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(XmlProcessorApplication.class, args);
		logger.debug("--Application started--");
	}
}
