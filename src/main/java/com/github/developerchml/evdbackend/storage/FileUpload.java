package com.github.developerchml.evdbackend.storage;

import com.github.developerchml.evdbackend.config.FileStorageConfig;
import com.github.developerchml.evdbackend.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public abstract class FileUpload {
    protected final Path fileStorageConfig;

    protected Path partialPath;
    protected Path absolutePath;

    @Autowired
    public FileUpload(FileStorageConfig fileStorageConfig) {
        this.fileStorageConfig = Path.of(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageConfig);
        } catch (IOException e) {
            throw new FileStorageException("Não foi possível criar o diretório onde os arquivos serão armazenados.", e);
        }
    }

    public FileUpload store(MultipartFile file, String... subFolder){
        String  fileName = defineFileName(file);
        Path subFolderPath = Paths.get(String.join(File.separator, subFolder) ).normalize();
        Path newPath = this.fileStorageConfig.resolve(subFolderPath).normalize();
        partialPath = subFolderPath.resolve(fileName).normalize();
        absolutePath = newPath.resolve(fileName).normalize();

        try {
            Files.createDirectories(newPath);
            file.transferTo(absolutePath);
            return this;
        } catch (IOException e) {
            throw new FileStorageException(e.getMessage(), e.getCause());
        }
    }

    protected String defineFileName(MultipartFile file){
        return file.getOriginalFilename();
    }

    public Path getPartialPath(){
        return partialPath;
    }
}
