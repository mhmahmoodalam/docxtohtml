package com.fev.docxtohtml.docxtohtmlservice.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipOutputStream;

public interface ArchiveFileService {
    boolean unzipFile(Path path);
    void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException;

}
