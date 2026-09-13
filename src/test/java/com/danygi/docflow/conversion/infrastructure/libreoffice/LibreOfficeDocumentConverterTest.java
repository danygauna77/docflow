package com.danygi.docflow.conversion.infrastructure.libreoffice;

import com.danygi.docflow.conversion.configuration.LibreOfficeProperties;
import com.danygi.docflow.conversion.domain.ConversionResult;
import com.danygi.docflow.conversion.domain.DocumentFormat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibreOfficeDocumentConverterTest {

	@Test
	void shouldConvertOdtToPdf() throws Exception {
		String executable = System.getenv().getOrDefault(
				"LIBREOFFICE_EXECUTABLE",
				"C:/Program Files/LibreOffice/program/soffice.exe"
		);

		LibreOfficeProperties properties =
				new LibreOfficeProperties(executable);

		LibreOfficeDocumentConverter converter =
				new LibreOfficeDocumentConverter(properties);

		Path input = Path.of("src/test/resources/sample.odt");

		ConversionResult result = converter.convert(
				input,
				DocumentFormat.PDF
		);

		assertTrue(Files.exists(result.file()));
		assertTrue(result.file().toString().endsWith(".pdf"));
	}

	@Test
	void shouldConvertOdtToPdfAndCleanupTemporaryFiles() throws Exception {
		String executable = System.getenv().getOrDefault(
				"LIBREOFFICE_EXECUTABLE",
				"C:/Program Files/LibreOffice/program/soffice.exe"
		);

		LibreOfficeProperties properties =
				new LibreOfficeProperties(executable);

		LibreOfficeDocumentConverter converter =
				new LibreOfficeDocumentConverter(properties);

		Path input = Path.of("src/test/resources/sample.odt");

		ConversionResult result = converter.convert(
				input,
				DocumentFormat.PDF
		);

		assertTrue(Files.exists(result.file()));
		assertTrue(Files.exists(result.workingDirectory()));

		result.cleanup();

		assertFalse(Files.exists(result.file()));
		assertFalse(Files.exists(result.workingDirectory()));
	}
}