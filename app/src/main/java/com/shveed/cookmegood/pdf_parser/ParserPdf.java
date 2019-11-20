package com.shveed.cookmegood.pdf_parser;

import android.graphics.pdf.PdfDocument;

import java.util.ArrayList;
import java.util.List;

public class ParserPdf {

    List<Step> steps = new ArrayList<>();

    List<Step> parse(String file) {
        List<PdfDocument.PageInfo> pagesInfo;
        PdfDocument pdf = new PdfDocument();
        pagesInfo = pdf.getPages();
        for (PdfDocument.PageInfo page : pagesInfo) {
            page.getContentRect();
        }


        return null;
    }
}
