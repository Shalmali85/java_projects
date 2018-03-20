package com.teoco.rnto.util.xcel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guptaam on 12/17/2014.
 */
public class XcelParser {

    public static final int INVALID_NUMERIC_VALUE = -999;

    //XLS,XLSX
    public FileInputStream fis = null;
    public Workbook workbook = null;
    Row currentRow;
    Iterator<Row> rowIterator;

    //CSV
    CSVParser csvFileParser = null;
    InputStreamReader fileReader = null;
    List csvRecords;
    CSVRecord record; //current CSV record
    int recordNo = 0;


    boolean isXcel = false;
    boolean isCsv = false;

    public XcelParser(String fileName) throws Exception {

       //Create Workbook instance for xlsx/xls file input stream
        if (fileName.toLowerCase().endsWith("xlsx")) {
            //Create the input stream from the xlsx/xls file
            fis = new FileInputStream(fileName);
            workbook = new XSSFWorkbook(fis);
            isXcel = true;
        } else if (fileName.toLowerCase().endsWith("xls")) {
            //Create the input stream from the xlsx/xls file
            fis = new FileInputStream(fileName);
            workbook = new HSSFWorkbook(fis);
            isXcel = true;
        } else if (fileName.toLowerCase().endsWith("csv")) {
            isCsv = true;
            fileReader = new InputStreamReader(new FileInputStream(fileName), "GB2312");
            //System.out.println("Encoding:"+fileReader.getEncoding());
            final String[] FILE_HEADER_MAPPING = {"Region", "Cell Name CN", "Cell Name En", "CGI", "eNB ID", "Sector ID", "CI", "Cell ID", "PCI", "TAC", "Tpye", "覆盖场景", "归属网格", "Lat", "Lon", "Band	", "eARFCN", "E Tile", "M Tile", "Total Tile", "Azimuth", "Antenna Height", "RS Power", "Antenna Type"};
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
        }
    }

    public XcelParser(String fileName, String[] fileHeaderMapping) throws Exception {
        if (fileName.toLowerCase().endsWith("txt")) {
            isCsv = true;
            fileReader = new InputStreamReader(new FileInputStream(fileName), "UTF8");
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(fileHeaderMapping);
            csvFileParser = new CSVParser(fileReader, CSVFormat.TDF);
        }
    }

    public boolean moveSheet(String sheetName) {
        if(isXcel) {

            return true;
        }

        return false;
    }

    public boolean moveNext() {
        if(isXcel) {
            while (rowIterator.hasNext()) {
                currentRow = rowIterator.next();
                return true;
            }
        }
        else if(isCsv) {
            if(recordNo < csvRecords.size()) {
                record = (CSVRecord) csvRecords.get(recordNo++);
                return true;
            }
        }

        return false;
    }

    public int getIntColumnValue(int columnIndex) throws Exception {
        if(isXcel) {
            Cell currentCell = currentRow.getCell(columnIndex);
            if(currentCell == null) return INVALID_NUMERIC_VALUE;
            if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
                return Integer.parseInt(new String(currentCell.getStringCellValue().getBytes("UTF-8")));
            } else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return  (int) currentCell.getNumericCellValue();
            }
        }
        else if(isCsv) {
            return Integer.parseInt(record.get(columnIndex));
        }

        return INVALID_NUMERIC_VALUE;
    }

    public long getLongColumnValue(int columnIndex) throws Exception {
        if(isXcel) {
            Cell currentCell = currentRow.getCell(columnIndex);
            if(currentCell == null) return INVALID_NUMERIC_VALUE;
            if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
                return Long.parseLong(new String(currentCell.getStringCellValue().getBytes("UTF-8")));
            } else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return  (long) currentCell.getNumericCellValue();
            }
        }
        else if(isCsv) {
            return  Long.parseLong(record.get(columnIndex));
        }

        return INVALID_NUMERIC_VALUE;
    }

    public double getDoubleColumnValue(int columnIndex)  {
        try {
            if (isXcel) {
                Cell currentCell = currentRow.getCell(columnIndex);
                if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
                    return Double.parseDouble(new String(currentCell.getStringCellValue().getBytes("UTF-8")));
                } else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    return (double) currentCell.getNumericCellValue();
                }
            } else if (isCsv) {
                return Double.parseDouble(record.get(columnIndex));
            }
        }
        catch(NumberFormatException nex) {
        }
        catch(UnsupportedEncodingException unSupportedEncEx) {
        }
        return INVALID_NUMERIC_VALUE;
    }

    public String getStringColumnValue(int columnIndex) throws Exception {
        if (isXcel) {
            Cell currentCell = currentRow.getCell(columnIndex);
            if(currentCell == null) {
                return null;
            }
            if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
                return new String(currentCell.getStringCellValue().getBytes("UTF-8"));
            } else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                return String.valueOf(currentCell.getNumericCellValue());
            }
        } else if (isCsv) {
            return record.get(columnIndex);
        }

        return "";
    }

    public void startReading() throws Exception {
        if(isXcel) {
            Sheet currentSheet = workbook.getSheet("Sheet1");
            rowIterator = currentSheet.iterator();
        } else if (isCsv) {
            csvRecords = csvFileParser.getRecords();
        }
    }

    /***
     * Go to specific worksheet by name
     * @param sheetName
     * @throws Exception
     */
    public void gotoSheet(String sheetName) throws Exception {
        Sheet currentSheet = workbook.getSheet(sheetName);
        rowIterator = currentSheet.iterator();
    }

    /***
     * Go the first sheet int the workbook
     * @throws Exception
     */
    public void gotoSheet(int index) throws Exception {
        Sheet currentSheet = workbook.getSheetAt(index);
        rowIterator = currentSheet.iterator();
    }

    public boolean isCSV() {
        return isCsv;
    }
}
