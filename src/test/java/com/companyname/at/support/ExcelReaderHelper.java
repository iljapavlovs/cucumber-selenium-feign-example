package com.companyname.at.support;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReaderHelper {

    private Workbook content;

    public ExcelReaderHelper(String filePath) {
        this.content = readFile(filePath);
    }

    private static Workbook readFile(String filePath) {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(filePath));
        } catch (IOException e) {
            throw new AssertionError("Input/Output exception: ", e);
        }
        return workbook;
    }

    private Workbook getWorkbook() {
        return content;
    }

    public Sheet getSheet(String sheetName) {
        return getWorkbook().getSheet(sheetName);
    }

    private String getCell(String sheetName, int rowNum, int cellNum) {
        return getWorkbook().getSheet(sheetName).getRow(rowNum).getCell(cellNum).toString();
    }


    public String getCell(String sheetName, String cellReference) {
        CellReference cr = new CellReference(cellReference);
        return getCell(sheetName, cr.getRow(), cr.getCol());
    }
}