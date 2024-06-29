package com.fev.docxtohtml.docxtohtmlservice.repository;

import com.fev.docxtohtml.docxtohtmlservice.entity.ConversionDetails;
import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionListOutResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionDetailsRepository extends JpaRepository<ConversionDetails, String> {
    ConversionListOutResource findByJobName(String jobName);
}
