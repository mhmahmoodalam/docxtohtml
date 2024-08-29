/*
# Copyright 2024 Muhammed Mahmood Alam
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
 */

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
