package com.danygi.docflow.conversion.controller;

import com.danygi.docflow.conversion.domain.DocumentFormat;
import com.danygi.docflow.conversion.service.DocumentConversionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Resource> convert(
            @RequestParam("file") MultipartFile file,
            @RequestParam("from") DocumentFormat source,
            @RequestParam("to") DocumentFormat target
    ) throws IOException {

        Path inputFile = Files.createTempFile(
                "docflow-upload-",
                "-" + file.getOriginalFilename()
        );

        file.transferTo(inputFile);

        Path outputFile = conversionService.convert(
                inputFile,
                source,
                target
        );

        Resource resource = new FileSystemResource(outputFile);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + outputFile.getFileName() + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}