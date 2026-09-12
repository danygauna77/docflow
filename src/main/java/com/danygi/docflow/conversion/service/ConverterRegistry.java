package com.danygi.docflow.conversion.service;

import com.danygi.docflow.conversion.domain.DocumentConverter;
import com.danygi.docflow.conversion.domain.DocumentFormat;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * ConverterRegistry.
 */

@Component
public class ConverterRegistry {

    private final List<DocumentConverter> converters;

    public ConverterRegistry(List<DocumentConverter> converters) {
        this.converters = List.copyOf(converters);
    }

    public DocumentConverter getConverter(
            DocumentFormat source,
            DocumentFormat target) {

        return converters.stream()
                .filter(converter -> converter.supports(source, target))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unsupported conversion: "
                                        + source + " -> " + target
                        )
                );
    }
}