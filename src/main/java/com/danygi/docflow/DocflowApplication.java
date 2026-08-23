package com.danygi.docflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class DocflowApplication {

	private DocflowApplication() {
	}

	public static void main(final String[] args) {
		SpringApplication.run(DocflowApplication.class, args);
	}

}