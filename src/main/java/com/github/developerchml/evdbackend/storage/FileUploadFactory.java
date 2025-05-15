package com.github.developerchml.evdbackend.storage;

import com.github.developerchml.evdbackend.exceptions.FileNotAcceptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class FileUploadFactory {
    @Autowired
    private ApplicationContext context;

    public FileUpload of(MultipartFile file) throws FileNotAcceptedException {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        String extension = Objects.requireNonNull(StringUtils.getFilenameExtension(fileName));

        return switch (extension){
            case String strExtension when Objects.nonNull(FileAccepted.of(strExtension)) -> context.getBean(FileImpl.class);
            case String strExtension when Objects.nonNull(IMGAccepted.of(strExtension)) -> context.getBean(IMGImpl.class);
            default -> throw new FileNotAcceptedException("Arquivo " + fileName + " não aceito");
        };
    };
}
