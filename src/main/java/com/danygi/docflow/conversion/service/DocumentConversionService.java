package com.danygi.docflow.conversion.service;

import com.danygi.docflow.conversion.domain.DocumentConverter;
import com.danygi.docflow.conversion.domain.DocumentFormat;
import org.springframework.stereotype.Service;
import java.nio.file.Path;

/**
 * DocumentConversionService.
 */

@Service
public class DocumentConversionService {

    private final ConverterRegistry converterRegistry;

    public DocumentConversionService(ConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    public Path convert(
            Path input,
            DocumentFormat source,
            DocumentFormat target
    ) {
        DocumentConverter converter =
                converterRegistry.getConverter(source, target);

        return converter.convert(input, target);
    }
}