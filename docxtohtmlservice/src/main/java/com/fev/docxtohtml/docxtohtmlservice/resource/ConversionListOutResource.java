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

package com.fev.docxtohtml.docxtohtmlservice.resource;

import com.fev.docxtohtml.docxtohtmlservice.entity.ConversionDetails;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ConversionListOutResource {
    private String id;
    private String jobName;
    private String originalFile;
    private String convertedFile;
    private ConversionState conversionState;

    private LocalDateTime startDate;
    private LocalDateTime completionDate;

    public static ConversionListOutResource convertFrom(ConversionDetails entity){

        var originalFile =Optional.ofNullable(entity.getOriginalFile());
        var convertedFile =Optional.ofNullable(entity.getConvertedFile());
        var outResource =  ConversionListOutResource.builder()
                .id(entity.getId())
                .jobName(entity.getJobName())
                .conversionState(entity.getConversionState())
                .startDate(entity.getStartDate())
                .completionDate(entity.getCompletionDate())
                .build();
        originalFile.ifPresent(x -> outResource.setOriginalFile(x.getId()));
        convertedFile.ifPresent(x -> outResource.setConvertedFile(x.getId()));
        return outResource;
  }
}
