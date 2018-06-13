package com.companyname.at.support;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;

public class PdfReaderHelper {

    public static String[] getTextFromPages(File file) throws IOException {
        PdfDocument pdfDocument = getDocument(file);
        int numberOfPages = pdfDocument.getNumberOfPages();
        String[] pages = new String[numberOfPages];
        for (int i = 0; i < numberOfPages; i++) {
            pages[i] = normalizeString(PdfTextExtractor.getTextFromPage(pdfDocument.getPage(i + 1)));
        }
        return pages;
    }

    private static PdfDocument getDocument(File file) throws IOException {
        return new PdfDocument(new PdfReader(file));
    }

    private static String normalizeString(String textToTrim) {
        String result = textToTrim.trim();
        result = result.replaceAll("\\s+", " ");
        result = result.replaceAll("\\n", "");
        result = result.replaceAll("\\r", "");
        return result;
    }
}
