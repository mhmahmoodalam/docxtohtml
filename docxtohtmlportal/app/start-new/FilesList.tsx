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

"use server";

import * as fsWalk from "@nodelib/fs.walk";

type FileInfo = {
    name: string,
    path: string,
    fullPath: string,
}

export default async function listFiles(directoryPath: string) {
  var enteries = fsWalk.walkSync(directoryPath);  
  var results: FileInfo[] = [];
  enteries.forEach(x => {
    if(x.name.endsWith(".docx")){
        results.push({ name: x.name, path: x.dirent.path, fullPath :x.path});
    }
  })
  return results;
}
