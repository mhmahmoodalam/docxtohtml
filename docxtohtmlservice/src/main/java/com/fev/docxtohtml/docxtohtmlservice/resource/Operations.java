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
