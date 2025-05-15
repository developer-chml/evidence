package com.github.developerchml.evdbackend.storage;

import com.github.developerchml.evdbackend.config.FileStorageConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class IMGImpl extends FileUpload {

    public IMGImpl(FileStorageConfig fileStorageConfig) {
        super(fileStorageConfig);
    }

    @Override
    protected String defineFileName(MultipartFile file) {
        return System.currentTimeMillis() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
    }
}
