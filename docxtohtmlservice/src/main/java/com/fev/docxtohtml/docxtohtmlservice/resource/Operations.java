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

public enum Operations {

    FILE_OPEN("OpeningFile", "open an ms word 2007 onwards file"),
    FILE_READ("ReadingFile", "Reading the file");

    private final String type;

    private final String description;

    Operations(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Operations{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }
}
