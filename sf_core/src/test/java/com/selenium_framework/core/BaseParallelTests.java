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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Media;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.ArrayList;

public class BaseParallelTests {

    private static ExtentReports extentReports;
    private static ArrayList<String[]> listTestResultData = new ArrayList<>();
    public String propertyFile;
    public String testFile_path;
    public String testFile_sheet;
    public String[] headers;
    private Object[][] testData;
    private Logger logger = LogManager.getLogger(BaseParallelTests.class);
    private String user_dir = System.getProperty("user.dir");
    private Report report = new Report();

    /**
     * Método que devuelve el objeto deseado del ThreadLocal
     */
    protected Object getObjectFromTestManager(String objectName) {
        return TestManager.getInstance().getObjectFromTestManager(objectName);
    }

    /**
     * Método que agrega al ThreadLocal un objeto deseado.
     */
    protected void setObjectFromTestManager(Object object, String objectName) {
        TestManager.getInstance().setObjectFromTestManager(object, objectName);
    }

    /**
     * Método que agrega los datos finales de cada prueba ejecutada a la lista de resultados
     */
    protected void addTestResultData(String[] testResultData) {
        listTestResultData.add(testResultData);
    }

    /**
     * Método que devuelve el ExtentReport al listener, el ExtentReport se utilizara durante toda la prueba
     */
    protected ExtentReports getExtentReports() {
        return extentReports;
    }


    /**
     * Método que devuelve el Extentest del hilo actual al método @Test, el ExtenTest es donde se agregan
     * los registros de los pasos (fail, pass), capturas de pantalla, etc.
     */
    protected ExtentTest getExtentTest() {
        return TestManager.getInstance().getExtentTest();
    }

    /**
     * Método que devuelve el driver
     */
    protected WebDriver getDriver() {
        return TestManager.getInstance().getDriver();
    }

    /**
     * Método que toma captura de pantalla
     */
    protected Media takeScreenshot(String id, String name, Boolean isFullSize) throws Exception {
        return Report.takeScreenshot(getDriver(), id, name, isFullSize);
    }

    /**
     * Método que devuelve los datos del excel en una matriz de objetos.
     */
    protected Object[][] getData() {
        return testData;
    }

    /**
     * El proveedor devuelve una mstriz de Objeto[x][y], donde a cada Objeto[y] se le asigna la lista de parámetros del método de prueba.
     * Se debe colocar el nombre del DataProvider en el método @Test para poder recibir los datos
     */
    @DataProvider(name = "dataProvider", parallel = true)
    public Object[][] dataProvider() {
        return getData();
    }

    /**
     * Se ejecutará antes de que se ejecute cualquier método de prueba que pertenezca a las clases dentro de la etiqueta <test>
     */
    @BeforeTest
    public void beforeTest() throws Throwable {
        String file_path = user_dir + GetPropertyValues.getPropertyValue(propertyFile, testFile_path);
        String file_sheet = GetPropertyValues.getPropertyValue(propertyFile, testFile_sheet);
        testData = ExcelFunctions.getDataExcel(file_path, file_sheet);
        extentReports = Report.generateReport();
        report.createExcelFileResult(headers);
    }

    /**
     * Se ejecutará solo una vez después de que se hayan ejecutado todos los métodos de prueba de la clase actual
     */
    @BeforeClass
    public void beforeClass() {

    }

    /**
     * Se ejecutará antes de cada método de prueba
     */
    @BeforeMethod
    public void beforeMethod() throws Throwable {
        TestManager testManager = new TestManager();
        testManager.setDriver(GetPropertyValues.getPropertyValue("config.properties", "driver"));
        testManager.setExtentReport(extentReports);
        TestManager.getInstance().getDriver();
        TestManager.getInstance().getExtentTest();
    }

    /**
     * Se ejecutará después de cada método de prueba
     */
    @AfterMethod
    public void afterMethod() {
        TestManager.getInstance().removeDriver();
    }

    /**
     * Se ejecutará solo una vez después de que se hayan ejecutado todos los métodos de prueba de la clase actual
     */
    @AfterClass
    public void afterClass() {

    }

    /**
     * Se ejecutará antes de que se ejecute cualquier método de prueba que pertenezca a las clases dentro de la etiqueta <test>
     */
    @AfterTest
    public void afterTest() throws Throwable {
        report.writeExcelFileResult(headers, listTestResultData);
    }
}