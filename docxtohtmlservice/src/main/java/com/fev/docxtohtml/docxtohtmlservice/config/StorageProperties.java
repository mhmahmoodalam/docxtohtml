package com.fev.docxtohtml.docxtohtmlservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("com.fev.docxtohtml.config.storage")
public class StorageProperties {
    private String location;
}
