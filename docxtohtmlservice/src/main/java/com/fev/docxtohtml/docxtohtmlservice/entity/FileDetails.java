package com.fev.docxtohtml.docxtohtmlservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime uploadTime;

    private LocalDateTime updateTime;
}
