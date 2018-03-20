package com.teoco.rnto.util.xcel;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Utility class that generates a Excel file
 */
public class XcelWriter {
    private XSSFWorkbook workBook = null;
    private XSSFSheet sheet = null;

    /**
     * Constructor that uses an instance of workbook
     */
    public XcelWriter() {
        workBook = new XSSFWorkbook();
    }

    /**
     * Creates a sheet in the workbook
     */
    public void createSheet() {
        if (workBook == null) return;
        sheet = workBook.createSheet();
    }

    /**
     * Writes value at the specified row,column of the sheet created last.
     *
     * @param rowNum  int
     * @param colNum  int
     * @param value   String
     * @param bHeader boolean
     */
    public void writeToCell(int rowNum, int colNum, String value, boolean bHeader) {
        if (sheet == null) return;
        XSSFRow row = sheet.getRow(rowNum);

        if (row == null)
            row = sheet.createRow(rowNum);

        XSSFCell cell = row.createCell(colNum);
        cell.setCellValue(value);

        if (bHeader) {
            CellStyle style = workBook.createCellStyle();
            //style.setFillBackgroundColor(IndexedColors.GOLD.getIndex());
            //style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            Font font = workBook.createFont();
            font.setColor(HSSFColor.BLUE.index);
            font.setBoldweight((short) 700);
            style.setFont(font);
            cell.setCellStyle(style);
        }
    }

    /**
     * Inserts an image in the specified row,column of the last created sheet.
     *
     * @param rowNum
     * @param colNum
     * @param imgFile
     * @return boolean
     */
    public boolean addImageToCell(int rowNum, int colNum, String imgFile) {
        if (sheet == null) return false;
        try {
            InputStream inputStream = new FileInputStream(imgFile);
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            int pictureureIdx = workBook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();

            CreationHelper helper = workBook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(colNum);
            anchor.setRow1(rowNum);

            Picture pict = drawing.createPicture(anchor, pictureureIdx);
            pict.resize();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * Saves the workbook to a file.
     *
     * @param fileName
     * @return boolean
     */
    public boolean saveToFile(String fileName) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            workBook.write(out);
            out.close();
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
}
