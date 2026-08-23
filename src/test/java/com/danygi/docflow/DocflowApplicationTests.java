package com.danygi.docflow;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DocflowApplicationTests {

	private final ApplicationContext context;

	/* package */ DocflowApplicationTests(final ApplicationContext context) {
		this.context = context;
	}

	@Test
	void contextLoads() {
		assertNotNull(context, "Application context should be initialized");
	}

	@Test
	void mainMethodStartsApplication() {
		try (MockedStatic<SpringApplication> springApplication =
					 Mockito.mockStatic(SpringApplication.class)) {

			DocflowApplication.main(new String[]{});

			springApplication.verify(
					() -> SpringApplication.run(DocflowApplication.class));
		}
	}
}