package com.danygi.docflow.conversion.controller;

import com.danygi.docflow.conversion.domain.ConversionResult;
import com.danygi.docflow.conversion.domain.DocumentFormat;
import com.danygi.docflow.conversion.service.DocumentConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/conversions")
public class ConversionController {

    private final DocumentConversionService conversionService;

    public ConversionController(DocumentConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping
    public ResponseEntity<StreamingResponseBody> convert(
            @RequestParam("file") MultipartFile file,
            @RequestParam("from") DocumentFormat source,
            @RequestParam("to") DocumentFormat target
    ) throws IOException {

        Path inputFile = Files.createTempFile(
                "docflow-upload-",
                "-" + file.getOriginalFilename()
        );

        file.transferTo(inputFile);

        ConversionResult result = conversionService.convert(
                inputFile,
                source,
                target
        );

        StreamingResponseBody stream = outputStream -> {
            try {
                Files.copy(result.file(), outputStream);
            } finally {
                Files.deleteIfExists(inputFile);
                result.cleanup();
            }
        };

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + result.file().getFileName() + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(stream);
    }
}