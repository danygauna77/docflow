package com.danygi.docflow.conversion.domain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record ConversionResult(
        Path file,
        Path workingDirectory
) {

    public void cleanup() {
        try {
            if (workingDirectory != null && Files.exists(workingDirectory)) {
                try (var paths = Files.list(workingDirectory)) {
                    paths.forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ignored) {
                        }
                    });
                }

                Files.deleteIfExists(workingDirectory);
            }
        } catch (IOException ignored) {
            // Best effort cleanup
        }
    }
}
