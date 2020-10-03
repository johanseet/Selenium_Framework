/*
 * Copyright 2020 Johanseet Ramírez (https://github.com/johanseet)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.selenium_framework.core;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class Xls_Reader {
    public String path;
    public FileInputStream fis = null;
    public FileOutputStream fileOut = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    public Xls_Reader(String path) {
        this.path = path;
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el recuento de filas en una hoja de excel
     *
     * @param sheetName Nombre de la hoja de excel
     * @return
     */
    public int getRowCount(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            return 0;
        } else {
            sheet = workbook.getSheetAt(index);
            int number = sheet.getLastRowNum() + 1;
            return number;
        }
    }

    /**
     * Devuelve el recuento de columnas en una hoja de excel
     *
     * @param sheetName Nombre de la hoja de excel
     * @return
     */
    public int getColumnCount(String sheetName) {
        if (!isSheetExist(sheetName))
            return -1;

        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);

        if (row == null)
            return -1;

        return row.getLastCellNum();

    }

    /**
     * Devuelve el dato que contiene una celda de excel
     *
     * @param sheetName Nombre de la hoja de excel
     * @param colNum    Número de columna
     * @param rowNum    Número de fila
     * @return
     */
    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            int index = workbook.getSheetIndex(sheetName);

            if (index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum);
            if (row == null)
                return "";
            cell = row.getCell(colNum);
            if (cell == null)
                return "";

            if (cell.getCellType().name().equals("STRING"))
                return cell.getStringCellValue();

            else if ((cell.getCellType().name().equals("NUMERIC")) || (cell.getCellType().name().equals("FORMULA"))) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

                }

                return cellText;
            } else if (cell.getCellType().BLANK != null)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        } catch (Exception e) {

            e.printStackTrace();
            return "fila " + rowNum + " o columna " + colNum + " no existe en el archivo de excel";
        }
    }

    /**
     * @param sheetName
     * @param colName
     * @param rowNum
     * @return
     */
    public String getCellData(String sheetName, String colName, int rowNum) {
        String cellValue, boolCellValue;
        try {
            if (rowNum <= 0)
                return "";

            int index = workbook.getSheetIndex(sheetName);
            int col_Num = -1;
            if (index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
            if (col_Num == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(col_Num);

            if (cell == null)
                return "";

            if (cell.getCellType().name().equals("STRING")) {
                cellValue = cell.getStringCellValue();
                return cellValue;

            } else if ((cell.getCellType().name().equals("NUMERIC")) || (cell.getCellType().name().equals("FORMULA"))) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

                }
                return cellText;
            } else if (cell.getCellType().BLANK != null)
                return "";
            else
                boolCellValue = String.valueOf(cell.getBooleanCellValue());
            return boolCellValue;

        } catch (Exception e) {

            e.printStackTrace();
            return "fila " + rowNum + " o columna " + colName + " no existe en el archivo";
        }
    }

    // returns true if data is set successfully else false
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;

            sheet = workbook.getSheetAt(index);

            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                // System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName))
                    colNum = i;
            }
            if (colNum == -1)
                return false;

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            // cell style
            // CellStyle cs = workbook.createCellStyle();
            // cs.setWrapText(true);
            // cell.setCellStyle(cs);
            cell.setCellValue(data);

            fileOut = new FileOutputStream(path);

            workbook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addSheet(String sheetname) {

        FileOutputStream fileOut;
        try {
            workbook.createSheet(sheetname);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeSheet(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1)
            return false;

        FileOutputStream fileOut;
        try {
            workbook.removeSheetAt(index);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addColumn(String sheetName, String colName) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            int index = workbook.getSheetIndex(sheetName);
            if (index == -1)
                return false;

            XSSFCellStyle style = workbook.createCellStyle();
            // style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            // style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            sheet = workbook.getSheetAt(index);

            row = sheet.getRow(0);
            if (row == null)
                row = sheet.createRow(0);

            // cell = row.getCell();
            // if (cell == null)
            // System.out.println(row.getLastCellNum());
            if (row.getLastCellNum() == -1)
                cell = row.createCell(0);
            else
                cell = row.createCell(row.getLastCellNum());

            cell.setCellValue(colName);
            cell.setCellStyle(style);

            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean removeColumn(String sheetName, int colNum) {
        try {
            if (!isSheetExist(sheetName))
                return false;
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            XSSFCellStyle style = workbook.createCellStyle();
            // style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            XSSFCreationHelper createHelper = workbook.getCreationHelper();
            // style.setFillPattern(XSSFCellStyle.NO_FILL);
            for (int i = 0; i < getRowCount(sheetName); i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    cell = row.getCell(colNum);
                    if (cell != null) {
                        cell.setCellStyle(style);
                        row.removeCell(cell);
                    }
                }
            }
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean isSheetExist(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workbook.getSheetIndex(sheetName.toUpperCase());
            return index != -1;
        } else
            return true;
    }

    public int getCellRowNum(String sheetName, String colName, String cellValue) {

        for (int i = 2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
                return i;
            }
        }
        return -1;

    }

}