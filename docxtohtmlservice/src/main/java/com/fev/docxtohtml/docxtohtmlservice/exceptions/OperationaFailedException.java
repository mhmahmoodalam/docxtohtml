package com.fev.docxtohtml.docxtohtmlservice.exceptions;

public class OperationaFailedException extends RuntimeException {
    public OperationaFailedException(String operation) {
        super("Failed to do operation :  %s" + operation);
    }

    public OperationaFailedException(String operation, String message) {

        super("Failed to do operation :  " + operation + "\n" + message);
    }

    public OperationaFailedException(String operation, Throwable cause) {

        super("Failed to do operation :  " + operation + " " + cause);
    }
}
