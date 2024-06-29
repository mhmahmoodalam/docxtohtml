package com.fev.docxtohtml.docxtohtmlservice.entity;

import com.fev.docxtohtml.docxtohtmlservice.resource.ConversionState;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String jobName;

    private String location;

    @Enumerated(EnumType.STRING)
    private ConversionState conversionState;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "originalFile")
    private FileDetails originalFile;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "convertedFile")
    private FileDetails convertedFile;

    @Column(nullable = false)
    private LocalDateTime startDate;
    private LocalDateTime completionDate;



}
