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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ExcelFunctions {
    private static ExcelReadWrite excelReadWrite;
    private static Logger logger = LogManager.getLogger(ExcelFunctions.class);

    /**
     * Metodo que extrae todos los datos del archivo excel y los devuelve en un arreglo de objetos
     *
     * @param filePath  Ruta del archivo excel
     * @param sheetName Nombre de la hoja de excel
     * @return todos los datos del excel
     */
    protected static Object[][] getDataExcel(String filePath, String sheetName) {
        excelReadWrite = new ExcelReadWrite(filePath);
        int totalRows = excelReadWrite.getRowCount(sheetName);
        int totalColumns = excelReadWrite.getColumnCount(sheetName);

        Object[][] excel_data = new Object[totalRows - 1][];

        int rowCounter = 0;
        //Se inicia en la fila 2 del excel, la primera fila son los titulos
        for (int rowNum = 1; rowNum < totalRows; rowNum++) {
            int columnCounter = 0;
            excel_data[rowCounter] = new Object[totalColumns];
            //Se recorre todas las columnas con datos
            for (int columnsNum = 0; columnsNum < totalColumns; columnsNum++) {
                excel_data[rowCounter][columnCounter] = excelReadWrite.getCellData(sheetName, columnsNum, rowNum);
                columnCounter++;
            }
            rowCounter++;
        }
        return excel_data;
    }

    /**
     * Método utilizado para crear un archivo excel
     *
     * @param filePath Ruta del archivo a crear
     * @param headers  Nombres de las columnas del excel
     */
    protected static void createExcel(String filePath, String[] headers) throws Throwable {
        String sheetName = GetPropertyValues.getPropertyValue("config.properties", "resultFile_sheetName");
        excelReadWrite = new ExcelReadWrite(filePath, sheetName);

        for (String header : headers) {
            excelReadWrite.addColumn(sheetName, header);
        }
    }

    /**
     * Métddo que escribe los resultados de las pruebas en el el excel de resultados
     *
     * @param filePath       ruta al excel donde se escriben los resultados
     * @param headers        Encabezados de las columnas
     * @param testResultData Datos de los resultados de las pruebas
     */
    public static void writeTestResultData(String filePath, String[] headers, ArrayList<String[]> testResultData) throws Throwable {
        String sheetName = GetPropertyValues.getPropertyValue("config.properties", "resultFile_sheetName");
        excelReadWrite = new ExcelReadWrite(filePath);
        int x = 0;
        for (String[] test : testResultData) {
            for (int i = 0; i < test.length; i++) {
                excelReadWrite.setCellData(sheetName, headers[i], x + 1, test[i]);
            }
            x++;
        }

    }
}