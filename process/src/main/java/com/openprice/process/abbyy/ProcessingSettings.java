package com.openprice.process.abbyy;

import lombok.Setter;

public class ProcessingSettings {

    public enum OutputFormat {
        txt, rtf, docx, xlsx, pptx, pdfSearchable, pdfTextAndImages, xml
    }

    /*
     * Recognition language. You can set any language listed at
     * http://ocrsdk.com/documentation/specifications/recognition-languages/ or
     * set comma-separated combination of them.
     *
     * Examples: English English,ChinesePRC English,French,German
     */
    @Setter
    private String language = "English";

    @Setter
    private OutputFormat outputFormat = OutputFormat.pdfSearchable;

    public String asUrlParams() {
        return String.format("language=%s&exportFormat=%s", language, outputFormat);
    }

    public String getOutputFileExt() {
        switch (outputFormat) {
        case txt:
            return ".txt";
        case rtf:
            return ".rtf";
        case docx:
            return ".docx";
        case xlsx:
            return ".xlsx";
        case pptx:
            return ".pptx";
        case pdfSearchable:
        case pdfTextAndImages:
            return ".pdf";
        case xml:
            return ".xml";
        }
        return ".ocr";
    }

}
