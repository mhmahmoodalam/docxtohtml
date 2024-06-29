package com.fev.docxtohtml.docxtohtmlservice.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(String jobName) {
        super("Job was not found " + jobName);
    }

    public JobNotFoundException(String jobName, Throwable cause) {

        super("File was not found " + jobName, cause);
    }

}
