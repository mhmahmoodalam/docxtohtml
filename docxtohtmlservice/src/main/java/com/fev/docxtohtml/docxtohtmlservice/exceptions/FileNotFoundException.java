package com.fev.docxtohtml.docxtohtmlservice.exceptions;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String fileName) {
        super("File was not found " + fileName);
    }

    public FileNotFoundException(String fileName, Throwable cause) {

        super("File was not found " + fileName, cause);
    }

}
