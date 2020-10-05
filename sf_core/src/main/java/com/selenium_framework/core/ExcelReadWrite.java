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

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class ExcelReadWrite {
    private String path;
    private FileInputStream fileInputStream = null;
    private FileOutputStream fileOutputStream = null;
    private XSSFWorkbook xssfWorkbook = null;
    private XSSFSheet xssfSheet = null;
    private XSSFRow xssfRow = null;
    private XSSFCell xssfCell = null;

    public ExcelReadWrite(String path) {
        this.path = path;
        try {
            fileInputStream = new FileInputStream(path);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
            xssfSheet = xssfWorkbook.getSheetAt(0);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ExcelReadWrite(String path, String sheetName) {
        this.path = path;
        try {
            fileOutputStream = new FileOutputStream(path);
            xssfWorkbook = new XSSFWorkbook();
            xssfWorkbook.createSheet(sheetName);
            xssfWorkbook.write(fileOutputStream);
            xssfWorkbook.close();
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
        int index = xssfWorkbook.getSheetIndex(sheetName);
        if (index == -1) {
            return 0;
        } else {
            xssfSheet = xssfWorkbook.getSheetAt(index);
            int number = xssfSheet.getLastRowNum() + 1;
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

        xssfSheet = xssfWorkbook.getSheet(sheetName);
        xssfRow = xssfSheet.getRow(0);

        if (xssfRow == null)
            return -1;

        return xssfRow.getLastCellNum();

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
            int index = xssfWorkbook.getSheetIndex(sheetName);

            if (index == -1)
                return "";

            xssfSheet = xssfWorkbook.getSheetAt(index);
            xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow == null)
                return "";
            xssfCell = xssfRow.getCell(colNum);
            if (xssfCell == null)
                return "";

            if (xssfCell.getCellType().name().equals("STRING"))
                return xssfCell.getStringCellValue();

            else if ((xssfCell.getCellType().name().equals("NUMERIC")) || (xssfCell.getCellType().name().equals("FORMULA"))) {

                String cellText = String.valueOf(xssfCell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(xssfCell)) {
                    double d = xssfCell.getNumericCellValue();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(DateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
                }

                return cellText;
            } else if (xssfCell.getCellType().BLANK != null)
                return "";
            else
                return String.valueOf(xssfCell.getBooleanCellValue());
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

            int index = xssfWorkbook.getSheetIndex(sheetName);
            int col_Num = -1;
            if (index == -1)
                return "";

            xssfSheet = xssfWorkbook.getSheetAt(index);
            xssfRow = xssfSheet.getRow(0);
            for (int i = 0; i < xssfRow.getLastCellNum(); i++) {
                if (xssfRow.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
            if (col_Num == -1)
                return "";

            xssfSheet = xssfWorkbook.getSheetAt(index);
            xssfRow = xssfSheet.getRow(rowNum - 1);
            if (xssfRow == null)
                return "";
            xssfCell = xssfRow.getCell(col_Num);

            if (xssfCell == null)
                return "";

            if (xssfCell.getCellType().name().equals("STRING")) {
                cellValue = xssfCell.getStringCellValue();
                return cellValue;

            } else if ((xssfCell.getCellType().name().equals("NUMERIC")) || (xssfCell.getCellType().name().equals("FORMULA"))) {

                String cellText = String.valueOf(xssfCell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(xssfCell)) {
                    double d = xssfCell.getNumericCellValue();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(DateUtil.getJavaDate(d));
                    cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
                }
                return cellText;
            } else if (xssfCell.getCellType().BLANK != null)
                return "";
            else
                boolCellValue = String.valueOf(xssfCell.getBooleanCellValue());
            return boolCellValue;

        } catch (Exception e) {

            e.printStackTrace();
            return "fila " + rowNum + " o columna " + colName + " no existe en el archivo";
        }
    }

    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {
            fileInputStream = new FileInputStream(path);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);

            if (rowNum <= 0)
                rowNum = 1;

            int index = xssfWorkbook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;

            xssfSheet = xssfWorkbook.getSheetAt(index);

            xssfRow = xssfSheet.getRow(0);
            for (int i = 0; i < xssfRow.getLastCellNum(); i++) {
                if (xssfRow.getCell(i).getStringCellValue().equals(colName)) {
                    colNum = i;
                }
            }
            if (colNum == -1)
                return false;

            xssfSheet.autoSizeColumn(colNum);
            xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow == null)
                xssfRow = xssfSheet.createRow(rowNum);

            xssfCell = xssfRow.getCell(colNum);
            if (xssfCell == null)
                xssfCell = xssfRow.createCell(colNum);

            xssfCell.setCellValue(data);

            fileOutputStream = new FileOutputStream(path);

            xssfWorkbook.write(fileOutputStream);

            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addSheet(String sheetname) {

        FileOutputStream fileOut;
        try {
            xssfWorkbook.createSheet(sheetname);
            fileOut = new FileOutputStream(path);
            xssfWorkbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeSheet(String sheetName) {
        int index = xssfWorkbook.getSheetIndex(sheetName);
        if (index == -1)
            return false;

        FileOutputStream fileOut;
        try {
            xssfWorkbook.removeSheetAt(index);
            fileOut = new FileOutputStream(path);
            xssfWorkbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addColumn(String sheetName, String colName) {
        try {
            fileInputStream = new FileInputStream(path);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
            int index = xssfWorkbook.getSheetIndex(sheetName);
            if (index == -1)
                return false;

            XSSFCellStyle style = xssfWorkbook.createCellStyle();
            xssfSheet = xssfWorkbook.getSheetAt(index);
            xssfRow = xssfSheet.getRow(0);
            if (xssfRow == null)
                xssfRow = xssfSheet.createRow(0);

            if (xssfRow.getLastCellNum() == -1)
                xssfCell = xssfRow.createCell(0);
            else
                xssfCell = xssfRow.createCell(xssfRow.getLastCellNum());

            xssfCell.setCellValue(colName);
            xssfCell.setCellStyle(style);

            fileOutputStream = new FileOutputStream(path);
            xssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();

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
            fileInputStream = new FileInputStream(path);
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
            xssfSheet = xssfWorkbook.getSheet(sheetName);
            XSSFCellStyle style = xssfWorkbook.createCellStyle();
            XSSFCreationHelper createHelper = xssfWorkbook.getCreationHelper();
            for (int i = 0; i < getRowCount(sheetName); i++) {
                xssfRow = xssfSheet.getRow(i);
                if (xssfRow != null) {
                    xssfCell = xssfRow.getCell(colNum);
                    if (xssfCell != null) {
                        xssfCell.setCellStyle(style);
                        xssfRow.removeCell(xssfCell);
                    }
                }
            }
            fileOutputStream = new FileOutputStream(path);
            xssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean isSheetExist(String sheetName) {
        int index = xssfWorkbook.getSheetIndex(sheetName);
        if (index == -1) {
            index = xssfWorkbook.getSheetIndex(sheetName.toUpperCase());
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