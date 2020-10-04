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

import java.io.IOException;
import java.util.ArrayList;

public class BaseParallelTests {

    private static ExtentReports extentReports;
    private static ExtentTest extentTest;
    public String propertyFile;
    public String testFile_path;
    public String testFile_sheet;
    public String[] headers;
    public static Object testResultData;
    private WebDriver driver;
    private Object[][] testData;
    private Logger logger = LogManager.getLogger(BaseParallelTests.class);
    private String user_dir = System.getProperty("user.dir");
    private Report report = new Report();
    private static ArrayList<String[]> listTestResultData = new ArrayList<String[]>();

    protected Object getTestResultData() {
        return TestManager.getInstance().getTestResultData();
    }

    protected void addTestResultData(String[] testResultData) {
        listTestResultData.add(testResultData);
    }

    /**
     * Método que devuelve el ExtentReport al listener
     *
     * @return
     */
    protected ExtentReports getExtentReports() {
        return extentReports;
    }


    /**
     * Método que devuelve el Extentest del hilo actual al método @Test
     *
     * @return
     */
    protected ExtentTest getExtentTest() {
        ExtentTest extentTest = TestManager.getInstance().getExtentTest();
        return extentTest;
    }

    /**
     * Método que devuelve el driver
     *
     * @return driver
     */
    protected WebDriver getDriver() {
        WebDriver webDriver = TestManager.getInstance().getDriver();
        return webDriver;
    }

    protected Media takeScreenshot(String id, String name, Boolean isFullSize) throws Exception {
        return Report.takeScreenshot(getDriver(), id, name, false);
    }

    /**
     * Método que devuelve los datos del excel en una matriz de objetos.
     *
     * @return testData
     */
    protected Object[][] getData() {
        return testData;
    }

    /**
     * Proveedor con la matriz de objetos.
     * Devuelve un Objeto[][], donde a cada Objeto[] se le asigna la lista de parámetros del método de prueba.
     * El método @Test que quiere recibir datos de este DataProvider debe colocar el nombre DataProvider igual
     * al nombre de esta anotación
     *
     * @return
     * @throws IOException
     */
    @DataProvider(name = "data-provider", parallel = true)
    public Object[][] data() {
        return getData();
    }

    /**
     * Se ejecutará antes de que se ejecute cualquier método de prueba que pertenezca a las clases dentro de la etiqueta <test>
     *
     * @throws IOException
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
    public void beforeClass() throws Throwable {

    }

    /**
     * Se ejecutará antes de cada método de prueba
     */
    @BeforeMethod
    public void beforeMethod() throws Throwable {
        TestManager testManager = new TestManager();
        testManager.setDriver(GetPropertyValues.getPropertyValue("config.properties", "driver"));
        testManager.setExtentReport(extentReports);
        testManager.setTestResultData(testResultData);
        driver = TestManager.getInstance().getDriver();
        extentTest = TestManager.getInstance().getExtentTest();
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