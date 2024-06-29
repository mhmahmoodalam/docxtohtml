package com.fev.docxtohtml.docxtohtmlservice.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
@Setter
public class ConversionInResource {
    String jobName;
    MultipartFile file;
}
