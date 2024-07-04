package com.fev.docxtohtml.docxtohtmlservice.service;

import com.fev.docxtohtml.docxtohtmlservice.entity.ConversionDetails;
import com.fev.docxtohtml.docxtohtmlservice.entity.FileDetails;
import com.fev.docxtohtml.docxtohtmlservice.exceptions.JobNotFoundException;
import com.fev.docxtohtml.docxtohtmlservice.repository.ConversionDetailsRepository;
import com.fev.docxtohtml.docxtohtmlservice.repository.FileRepository;
import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionListOutResource;
import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionOutResource;
import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionState;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileConversionDetailsService implements ConversionDetailsService {

    private final ConversionDetailsRepository conversionDetailsRepository;
    private final StorageService storageService;
    private final FileRepository fileRepository;
    private final ConversionExecutorService conversionExecutorService;

    static Logger logger = Logger.getLogger(FileConversionDetailsService.class.getName());

    @Autowired
    public FileConversionDetailsService(ConversionDetailsRepository conversionDetailsRepository,
                                        StorageService storageService,
                                        FileRepository fileRepository, ConversionExecutorService conversionExecutorService) {
        this.conversionDetailsRepository = conversionDetailsRepository;
        this.storageService = storageService;
        this.fileRepository = fileRepository;
        this.conversionExecutorService = conversionExecutorService;
    }

    @Override
    public ConversionOutResource createConversionJob(String jobName, MultipartFile file, String language) {
        logger.info("uploading files");
        var originalFileLocation = this.storageService.store(jobName,file);
        logger.info("creating file entry");
        var fileDetails = FileDetails
                .builder()
                .location(originalFileLocation)
                .uploadTime(LocalDateTime.now()).build();
        fileRepository.saveAndFlush(fileDetails);
        logger.info("creating conversion job entry");
        var conversionDetails = ConversionDetails.builder()
                        .jobName(jobName)
                        .location(storageService.getLocation(jobName).toString())
                        .originalFile(fileDetails)
                        .startDate(LocalDateTime.now())
                        .conversionState(ConversionState.UPLOADED)
                        .build();
        conversionDetails = conversionDetailsRepository.saveAndFlush(conversionDetails);
        logger.info("invoking conversion as fire and forget");
        conversionExecutorService.executeConversion(conversionDetails.getId(),language);
        return toConversionDetailsOutResource(conversionDetails);
    }


    @Override
    public ConversionDetails getConversionJob(String jobId) {
        return conversionDetailsRepository.findById(jobId)
                .orElseThrow(()-> new JobNotFoundException(jobId));
    }


    @Override
    public List<ConversionListOutResource> getConversionJobs() {
        var list =  conversionDetailsRepository.findAll();
        logger.info("Getting list of jobs ");
        return list.stream().map(ConversionListOutResource::convertFrom)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String jobId) {
        var jobDetails = conversionDetailsRepository.findById(jobId)
                .orElseThrow(()-> new JobNotFoundException(jobId));
        conversionDetailsRepository.deleteById(jobId);
        storageService.deleteJobFiles(jobDetails.getLocation());


    }


    private static ConversionOutResource toConversionDetailsOutResource(ConversionDetails conversionDetails) {
        return ConversionOutResource.builder()
                .jobId(conversionDetails.getId())
                .jobName(conversionDetails.getJobName())
                .conversionState(conversionDetails.getConversionState())
                .updateTime(LocalDateTime.now())
                .build();
    }

}
