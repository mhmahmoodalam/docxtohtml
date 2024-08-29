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

package com.fev.docxtohtml.docxtohtmlservice.service;

import com.fev.docxtohtml.docxtohtmlservice.entity.ConversionDetails;
import com.fev.docxtohtml.docxtohtmlservice.entity.FileDetails;
import com.fev.docxtohtml.docxtohtmlservice.exceptions.OperationaFailedException;
import com.fev.docxtohtml.docxtohtmlservice.repository.ConversionDetailsRepository;
import com.fev.docxtohtml.docxtohtmlservice.repository.FileRepository;
import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionState;
import com.fev.docxtohtml.docxtohtmlservice.utils.WordToHtmlConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class DocxFileConversionExecutorService implements ConversionExecutorService {

    private final ConversionDetailsRepository conversionDetailsRepository;
    private final FileRepository fileRepository;
    private final ExecutorService executorService;
    private final StorageService storageService;

    static Logger logger = Logger.getLogger(DocxFileConversionExecutorService.class.getName());


    public DocxFileConversionExecutorService(ConversionDetailsRepository conversionDetailsRepository,
                                             FileRepository fileRepository,
                                             ExecutorService executorService,
                                             StorageService storageService) {
        this.conversionDetailsRepository = conversionDetailsRepository;
        this.fileRepository = fileRepository;
        this.executorService = executorService;
        this.storageService = storageService;
    }

    @Override
    public void executeConversion(String jobId, String language) {

        executorService.submit(() ->{
            try {
                executefileConversion(jobId, language);
            } catch (InterruptedException e) {
                handleFailedJob(jobId);
                Thread.currentThread().interrupt();
                throw new OperationaFailedException(e.getMessage());
            }
        });
    }

    private void executefileConversion(String jobId, String language) throws InterruptedException {

        var jobDetail = conversionDetailsRepository.findById(jobId)
                .orElseThrow(() ->new OperationaFailedException("failed to run job"));
        jobDetail.setConversionState(ConversionState.PROCESSING);
        conversionDetailsRepository.saveAndFlush(jobDetail);
        convertFiles(jobDetail,language);
        jobDetail.setConversionState(ConversionState.COMPLETED);
        jobDetail.setCompletionDate(LocalDateTime.now());
        var fileDetails = FileDetails
                .builder()
                .location(jobDetail.getOriginalFile().getLocation())
                .uploadTime(LocalDateTime.now()).build();
        fileRepository.saveAndFlush(fileDetails);
        jobDetail.setConvertedFile(fileDetails);
        conversionDetailsRepository.saveAndFlush(jobDetail);

    }

    private void convertFiles(ConversionDetails jobDetails, String lan) {
        var location = jobDetails.getLocation();
        logger.log(Level.INFO, "Trying to convert the files at --  {0}",location);
        logger.log(Level.INFO, "Processing Folder files at --  {0}",location);
        var filesLIst = storageService.loadAll(location);
        HashMap<String, HashMap<String, List<String>>> conversionResultMap
                = new HashMap<>();
        filesLIst.forEach( file ->
         {
            logger.log(Level.INFO, "Trying to convert the files at --  {0}}",file.getName());
            String language = StringUtils.isBlank(lan) ?  getLanguage(location, file.getPath()) : lan;
             var wordTohtmlConverter = new WordToHtmlConverter();
             try (var msDocument = wordTohtmlConverter.openDocument(file.getPath())) {

                //set if numbers should be considered as list for this file
                wordTohtmlConverter.setConvertNumberTolist(shouldConvertNumberingtoList(file));
                //convert the file
                var finalHTml = wordTohtmlConverter.convertDocBodyToHtml(msDocument.getBodyElements(), language);
                //format the text to pretty print
                var prettyHtml = Jsoup.parse(finalHTml).body().unwrap().toString();

                // create output file and write the output
                var fileInitial = file.getName().split("\\.")[0];
                var filename = file.getParentFile().getAbsolutePath() + "\\" + fileInitial + ".html";
                try (var outputStream = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8)) {
                    outputStream.write(prettyHtml);
                }
                updateFileChanges(conversionResultMap, fileInitial, language, wordTohtmlConverter);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Status %s -> Failed | %s".formatted(file.getName(), e.getMessage()));
            }

            logger.log(Level.INFO, "Status {0} -> Success",file.getName());
        });
        var conversionResult = printFileConversionResult(conversionResultMap, filesLIst.size());
        var prettyConversionResult = Jsoup.parse(conversionResult).toString();
        var filename = location + "\\conversionResult.html";
        try (var outputStream = new OutputStreamWriter(Files.newOutputStream(Path.of(filename)), StandardCharsets.UTF_8)) {
            outputStream.write(prettyConversionResult);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "FAILED To write conversion log", e.getMessage());
        }
        logger.log(Level.INFO, "Status ----> DONE");
    }

    private void handleFailedJob(String jobId) {
        var jobDetail = conversionDetailsRepository.findById(jobId)
                .orElseThrow(() ->new OperationaFailedException("failed to run job"));
        jobDetail.setConversionState(ConversionState.FAILED);
        jobDetail.setCompletionDate(LocalDateTime.now());
        conversionDetailsRepository.saveAndFlush(jobDetail);
    }


    private boolean shouldConvertNumberingtoList(File file) {
        // certain files like common privacy require the numbering to be maintained
        return file.getName().contains("Global Terms")
                || file.getName().contains("Privacy Notice");
    }

    private void updateFileChanges(HashMap<String, HashMap<String, List<String>>> conversionResultMap,
                                          String fileName, String language, WordToHtmlConverter wordTohtmlConverter) {
        if (!conversionResultMap.containsKey(language)) {
            var fileChangesMap = new HashMap<String, List<String>>();
            fileChangesMap.put(fileName, wordTohtmlConverter.getFileChangesList());
            conversionResultMap.put(language, fileChangesMap);
        } else {
            var fileChangesMap = conversionResultMap.get(language);
            fileChangesMap.put(fileName, wordTohtmlConverter.getFileChangesList());
        }
    }

    private String printFileConversionResult(HashMap<String, HashMap<String, List<String>>> conversionResultMap, int numberofFiles) {
        // print the whole conversion process in html
        // conversionResult.html

        var stringBuilder = new StringBuilder();
        stringBuilder.append(
                """
                <html><head>
                <style>
                table, th, td {
                  border: 1px solid black;
                }
                </style>
                </head><body>
                """);
        stringBuilder.append("<div><h3>Total Number of Files: %d</h3></div>".formatted(numberofFiles));
        stringBuilder.append("<table>");
        conversionResultMap.forEach((language, filesMap) -> {
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>%s</td>".formatted(language));
            stringBuilder.append("<td>");
            stringBuilder.append("<table>");
            filesMap.forEach((filename, changeList) -> {
                stringBuilder.append("<tr>");
                stringBuilder.append("<td>%s</td>".formatted(filename));
                stringBuilder.append("<td><ul>");
                changeList.forEach(x -> stringBuilder.append("<li>%s</li>".formatted(x)));
                stringBuilder.append("</ul></td>");
                stringBuilder.append("</tr>");
            });
            stringBuilder.append("</table>");
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");
        });
        stringBuilder.append("</table></body></html>");
        return stringBuilder.toString();
    }


    private String getLanguage(String rootPath, String filePath) {

        // language of content is based on the folder name
        // no AI involved in detecting the language

        var stringPath = StringUtils.replace(filePath, rootPath + "\\", "").split("\\\\")[0];
        if (stringPath.contains("ENGLISH")) {
            return "English";
        }
        if (stringPath.contains("Important_Instructions_v4") || stringPath.contains("Z_Outdated_Files")) {
            return null;
        }
        return stringPath;
    }
}
