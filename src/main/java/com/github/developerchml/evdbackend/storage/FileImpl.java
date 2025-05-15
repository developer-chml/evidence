package com.github.developerchml.evdbackend.storage;

import com.github.developerchml.evdbackend.config.FileStorageConfig;
import org.springframework.stereotype.Component;

@Component
public class FileImpl extends FileUpload {
    public FileImpl(FileStorageConfig fileStorageConfig) {
        super(fileStorageConfig);
    }
}
