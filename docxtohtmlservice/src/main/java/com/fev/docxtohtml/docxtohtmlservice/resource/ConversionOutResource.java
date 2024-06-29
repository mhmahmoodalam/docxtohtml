package com.fev.docxtohtml.docxtohtmlservice.resource;

import java.time.LocalDateTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ConversionOutResource {

    private String jobId;
    private String jobName;
    private ConversionState conversionState;
    private LocalDateTime updateTime;
}
