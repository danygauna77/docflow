package com.danygi.docflow.conversion.infrastructure.libreoffice;

import com.danygi.docflow.conversion.configuration.LibreOfficeProperties;
import com.danygi.docflow.conversion.domain.DocumentConverter;
import com.danygi.docflow.conversion.domain.DocumentFormat;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Component
public class LibreOfficeDocumentConverter implements DocumentConverter {

    private static final Map<DocumentFormat, Set<DocumentFormat>> SUPPORTED_CONVERSIONS =
            Map.of(
                    DocumentFormat.ODT,
                    Set.of(DocumentFormat.PDF)
            );

    private final LibreOfficeProperties properties;

    public LibreOfficeDocumentConverter(LibreOfficeProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean supports(DocumentFormat source, DocumentFormat target) {
        return SUPPORTED_CONVERSIONS
                .getOrDefault(source, Set.of())
                .contains(target);
    }

    @Override
    public Path convert(Path input, DocumentFormat target) {
        try {
            Path outputDirectory = Files.createTempDirectory("docflow-conversion-");

            String targetExtension = target.name().toLowerCase(Locale.ROOT);

            ProcessBuilder processBuilder = new ProcessBuilder(
                    properties.executable(),
                    "--headless",
                    "--convert-to",
                    targetExtension,
                    "--outdir",
                    outputDirectory.toString(),
                    input.toString()
            );

            processBuilder.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            processBuilder.redirectError(ProcessBuilder.Redirect.DISCARD);

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new IllegalStateException(
                        "LibreOffice conversion failed with exit code: " + exitCode
                );
            }

            Path fileName = input.getFileName();

            if (fileName == null) {
                throw new IllegalArgumentException("Input path must contain a file name");
            }

            String inputFileName = fileName.toString();

            int extensionIndex = inputFileName.lastIndexOf('.');

            String baseName = extensionIndex > 0
                    ? inputFileName.substring(0, extensionIndex)
                    : inputFileName;

            Path outputFile = outputDirectory.resolve(
                    baseName + "." + targetExtension
            );

            if (!Files.exists(outputFile)) {
                throw new IllegalStateException(
                        "LibreOffice did not generate the expected output file: " + outputFile
                );
            }

            return outputFile;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error executing LibreOffice conversion",
                    e
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new IllegalStateException(
                    "LibreOffice conversion was interrupted",
                    e
            );
        }
    }
}
