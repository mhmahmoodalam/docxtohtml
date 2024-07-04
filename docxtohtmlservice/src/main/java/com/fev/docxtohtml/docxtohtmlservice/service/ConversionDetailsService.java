package com.fev.docxtohtml.docxtohtmlservice.service;

import com.fev.docxtohtml.docxtohtmlservice.entity.ConversionDetails;
import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionListOutResource;
import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionOutResource;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ConversionDetailsService {

    ConversionOutResource createConversionJob(String jobName, MultipartFile file, String language );

    ConversionDetails getConversionJob(String jobId);
    List<ConversionListOutResource> getConversionJobs();

    void delete(String jobId);
}
