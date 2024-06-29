package com.fev.docxtohtml.docxtohtmlservice.controllers;

import com.fev.docxtohtml.docxtohtmlservice.service.ArchiveFileService;
import com.fev.docxtohtml.docxtohtmlservice.service.ConversionDetailsService;
import com.fev.docxtohtml.docxtohtmlservice.service.StorageService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class FileController {

    static Logger logger = Logger.getLogger(ConversionController.class.getName());
    private final StorageService storageService;
    private final ArchiveFileService archiveFileService;
    private final ConversionDetailsService conversionDetailsService;

    @Autowired
    public FileController(StorageService storageService, ArchiveFileService archiveFileService, ConversionDetailsService conversionDetailsService) {
        this.storageService = storageService;
        this.archiveFileService = archiveFileService;
        this.conversionDetailsService = conversionDetailsService;
    }

    @GetMapping(value = "/api/v1/download/files")
    public void  serveAllFileAsZip(
            @RequestParam("jobId") String jobId,
            HttpServletResponse response
    ) throws IOException {
        var conversionDetails = conversionDetailsService.getConversionJob(jobId);
        var file = storageService.getLocation(conversionDetails.getJobName());

        response.setContentType("application/zip"); // zip archive format
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("download.zip", StandardCharsets.UTF_8)
                .build()
                .toString());
        // Archiving multiple files and responding to the client
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())){
            archiveFileService.zipFile(file.toFile(),file.toFile().getName(),zipOutputStream);
            zipOutputStream.closeEntry();
            zipOutputStream.flush();

        }
        response.flushBuffer();
    }

}
