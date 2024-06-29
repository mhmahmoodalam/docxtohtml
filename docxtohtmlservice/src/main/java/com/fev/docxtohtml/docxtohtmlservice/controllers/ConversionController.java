package com.fev.docxtohtml.docxtohtmlservice.controllers;

import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionListOutResource;
import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionOutResource;
import com.fev.docxtohtml.docxtohtmlservice.resource.SupportedLanguages;
import com.fev.docxtohtml.docxtohtmlservice.service.ConversionDetailsService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
public class ConversionController {

    static Logger logger = Logger.getLogger(ConversionController.class.getName());

    private final ConversionDetailsService conversionDetailsService;


    @Autowired
    public ConversionController(ConversionDetailsService conversionDetailsService) {
        this.conversionDetailsService = conversionDetailsService;
    }

    @PostMapping(value = "/api/v1/conversion/create-job",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ConversionOutResource> createConversionJob(
            @RequestParam("jobName") String jobName,
            @RequestParam("isArchive") boolean isArchive,
            @RequestParam("isMultiLanguage") boolean isMultiLanguage,
            @RequestParam("language") @Nullable String language,
            @RequestParam("file") MultipartFile file) throws BadRequestException {
        logger.info( "creating conversion job %s".formatted(jobName));
        if(!isMultiLanguage){
            if(StringUtils.isBlank(language)){
                throw new BadRequestException("Language cannot not empty if file is not archive");
            }
            var languageSupported =Arrays.stream(SupportedLanguages.supportedLanguages).filter( x -> x.equals(language)).findFirst();
            languageSupported.orElseThrow(() ->new BadRequestException("Language not supported"));
        }
        if(isMultiLanguage && !isArchive){
            throw new BadRequestException("Multi language files must be put in language specific folders and uploaded as zip");
        }
        var conversionOutResource = this.conversionDetailsService.createConversionJob(jobName, file, language.trim());
        return ResponseEntity.status(HttpStatus.CREATED).body(conversionOutResource);
    }

    @GetMapping("/api/v1/conversion/list")
    public ResponseEntity<List<ConversionListOutResource>> getConversionHistory(){
        logger.info( "get list of  conversion jobs");
        var list = this.conversionDetailsService.getConversionJobs();
        return ResponseEntity.of(Optional.ofNullable(list));
    }

    @GetMapping("/api/v1/conversion/languages")
    public ResponseEntity<String[]> getConversionSupportedLanguages(){
        return ResponseEntity.ok(SupportedLanguages.supportedLanguages);
    }

}
