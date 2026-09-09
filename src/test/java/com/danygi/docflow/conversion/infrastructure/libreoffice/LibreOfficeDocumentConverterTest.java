package com.danygi.docflow.conversion.infrastructure.libreoffice;

import com.danygi.docflow.conversion.configuration.LibreOfficeProperties;
import com.danygi.docflow.conversion.domain.DocumentFormat;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LibreOfficeDocumentConverterTest {

	@Test
	void shouldConvertOdtToPdf() throws Exception {
		LibreOfficeProperties properties = new LibreOfficeProperties(
				"C:/Program Files/LibreOffice/program/soffice.exe"
		);

		LibreOfficeDocumentConverter converter =
				new LibreOfficeDocumentConverter(properties);

		Path input = Path.of("src/test/resources/sample.odt");

		Path result = converter.convert(
				input,
				DocumentFormat.PDF
		);

		assertTrue(Files.exists(result));
		assertTrue(result.toString().endsWith(".pdf"));
	}
}