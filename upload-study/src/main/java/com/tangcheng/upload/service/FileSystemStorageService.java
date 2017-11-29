package com.tangcheng.upload.service;

import com.tangcheng.upload.config.StorageProperties;
import com.tangcheng.upload.exception.StorageException;
import com.tangcheng.upload.exception.StorageFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author tangcheng
 * 2017/11/29
 */
@Slf4j
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            String msg = "Could not initialize storage";
            log.error("{} {}", msg, e.getMessage(), e);
            throw new StorageException(msg, e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                String msg = "Failed to store empty file ";
                log.warn("{} {}", msg, file.getOriginalFilename());
                throw new StorageException(msg + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            String msg = "Failed to store file ";
            log.error("{} {}", msg, e.getMessage(), e);
            throw new StorageException(msg + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            String msg = "Failed to read stored files";
            log.error("{} {}", msg, e.getMessage(), e);
            throw new StorageException(msg, e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        String msg = "Could not read file: ";
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            log.error("{} {}", msg, filename);
            throw new StorageFileNotFoundException(msg + filename);
        } catch (MalformedURLException e) {
            log.error("{} {} {}", msg, filename, e.getCause(), e);
            throw new StorageFileNotFoundException(msg + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
