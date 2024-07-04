package com.fev.docxtohtml.docxtohtmlservice.service;

import com.fev.docxtohtml.docxtohtmlservice.config.StorageProperties;
import com.fev.docxtohtml.docxtohtmlservice.exceptions.StorageException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final ArchiveFileService archiveFileService;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, ArchiveFileService archiveFileService) {
        rootLocation = Paths.get(properties.getLocation());
        this.archiveFileService = archiveFileService;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(String jobName,MultipartFile file) {
        var destinationFile = this.rootLocation.resolve(
                        Paths.get(jobName,Objects.requireNonNull(file.getOriginalFilename())))
                .normalize().toAbsolutePath();
        if (!destinationFile.getParent().getParent().equals(this.rootLocation.toAbsolutePath())) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file outside current directory.");
        }

        try(InputStream inputStream = file.getInputStream()){
            Files.createDirectories(destinationFile.getParent());
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            if (destinationFile.toFile().getName().endsWith(".zip")
                && (!archiveFileService.unzipFile(destinationFile))) {
                    throw new StorageException("Failed to unzip file");

            }
            return destinationFile.toString();
        }catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Path getLocation(String jobName) {
        return this.rootLocation.resolve(Paths.get(jobName))
                .normalize().toAbsolutePath();
    }

    @Override
    public List<File> loadAll(String path) {
        try(Stream<Path> list = Files.walk(Path.of(path), Integer.MAX_VALUE)) {
                   return  list
                    .map(file -> new File(file.toString()))
                    .toList();
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }


    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void deleteJobFiles(String jobFilesLocation) {
        FileSystemUtils.deleteRecursively(Path.of(jobFilesLocation).toFile());
    }
}
