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

import com.fev.docxtohtml.docxtohtmlservice.exceptions.StorageException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.springframework.stereotype.Service;

@Service
public class ZipFileService implements ArchiveFileService {
    static Logger logger = Logger.getLogger(ZipFileService.class.getName());
    @Override
    public boolean unzipFile(Path path) {
        byte[] buffer = new byte[1024];
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(path.toFile()))){
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(path.getParent().toFile(), zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new StorageException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new StorageException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
        }catch(Exception e){
            throw new StorageException("could not unzip file");
        }
        return true;
    }



    @Override
    public void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()|| fileName.endsWith(".zip")) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }
    private File newFile(File destinationDir, ZipEntry zipEntry) {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = null;
        try {
            destDirPath = destinationDir.getCanonicalPath();
            String destFilePath = destFile.getCanonicalPath();

            if (!destFilePath.startsWith(destDirPath + File.separator)) {
                throw new StorageException("Entry is outside of the target dir: " + zipEntry.getName());
            }

            return destFile;
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }

    }
}
