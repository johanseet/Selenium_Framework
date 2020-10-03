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

import java.io.IOException;

public class ExcelFunctions {
    static Xls_Reader reader;
    private static Logger logger = LogManager.getLogger(ExcelFunctions.class);

    /**
     * Metodo que extrae todos los datos del archivo excel y los devuelve en un arreglo de objetos
     *
     * @param filePath  Ruta del archivo excel
     * @param sheetName Nombre de la hoja de excel
     * @return
     * @throws IOException
     */
    public static Object[][] getDataExcel(String filePath, String sheetName) throws IOException {
        reader = new Xls_Reader(filePath);
        int totalRows = reader.getRowCount(sheetName);
        int totalColumns = reader.getColumnCount(sheetName);

        Object[][] excel_data = new Object[totalRows - 1][];

        int rowCounter = 0;
        //Se inicia en la fila 2 del excel, la primera fila son los titulos
        for (int rowNum = 1; rowNum < totalRows; rowNum++) {
            int columnCounter = 0;
            excel_data[rowCounter] = new Object[totalColumns];
            //Se recorre todas las columnas con datos
            for (int columnsNum = 0; columnsNum < totalColumns; columnsNum++) {
                excel_data[rowCounter][columnCounter] = reader.getCellData(sheetName, columnsNum, rowNum);
                columnCounter++;
            }
            rowCounter++;
        }
        return excel_data;
    }
}