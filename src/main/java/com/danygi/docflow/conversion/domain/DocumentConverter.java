package com.danygi.docflow.conversion.domain;

import java.nio.file.Path;

public interface DocumentConverter {

    boolean supports(DocumentFormat source, DocumentFormat target);

    ConversionResult convert(Path input, DocumentFormat target);
}
