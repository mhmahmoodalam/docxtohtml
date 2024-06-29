package com.fev.docxtohtml.docxtohtmlservice.service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();

    String store(String jobName,MultipartFile file);

    Path getLocation(String jobName);
    List<File> loadAll(String path);

    void deleteAll();
}
