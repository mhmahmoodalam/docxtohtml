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
