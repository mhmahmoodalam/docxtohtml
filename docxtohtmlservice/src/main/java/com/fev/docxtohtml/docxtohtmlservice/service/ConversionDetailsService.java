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
