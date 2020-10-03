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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.IOException;

public class BaseParallelTests {

    public static WebDriver driver = null;
    public static Object[][] testData;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    private static Logger logger = LogManager.getLogger(BaseParallelTests.class);
    public String propertyFile;
    public String testFile_path;
    public String testFile_sheet;

    public static ExtentReports getExtentReports() {
        logger.trace("THREAD: " + Thread.currentThread().getId() + " extentreports = " + extentReports);
        return extentReports;
    }

    public static ExtentTest getExtentTest() {
        //return ExtentTestManager.getInstance().getExtentTest();
        ExtentTest extentTest = TestManager.getInstance().getExtentTest();
        logger.trace("THREAD: " + Thread.currentThread().getId() + " testmanager getestenttest = " + extentTest);
        return extentTest;
    }

    /**
     * Método que devuelve el driver
     *
     * @return driver
     */
    public WebDriver getDriver() {
        //return DriverManager.getInstance().getDriver();
        WebDriver webDriver = TestManager.getInstance().getDriver();
        logger.trace("THREAD: " + Thread.currentThread().getId() + " testmanager getdriver = " + webDriver);
        return webDriver;
    }

    /**
     * Método que devuelve los datos del excel en una matriz de objetos.
     *
     * @return testData
     */
    public Object[][] getData() {
        logger.trace("THREAD: " + Thread.currentThread().getId());
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
    public Object[][] data() throws IOException {
        logger.trace("THREAD: " + Thread.currentThread().getId());
        return getData();
    }

    /**
     * Se ejecutará antes de que se ejecute cualquier método de prueba que pertenezca a las clases dentro de la etiqueta <test>
     *
     * @throws IOException
     */
    @BeforeTest
    public void beforeTest() throws IOException {
        String file_path = GetPropertyValues.getPropertyValue(propertyFile, testFile_path);
        String file_sheet = GetPropertyValues.getPropertyValue(propertyFile, testFile_sheet);
        testData = ExcelFunctions.getDataExcel(file_path, file_sheet);
        extentReports = Report.generateReport();
        logger.trace("THREAD: " + Thread.currentThread().getId() + " extentReports = " + extentReports);
    }

    /**
     * Se ejecutará solo una vez después de que se hayan ejecutado todos los métodos de prueba de la clase actual
     */
    @BeforeClass
    public void beforeClass() {
        logger.trace("THREAD: " + Thread.currentThread().getId());
    }

    /**
     * Se ejecutará antes de cada método de prueba
     */
    @BeforeMethod
    public void beforeMethod() {
/*        DriverManager driverManager = new DriverManager();
        driverManager.setDriver("CHROME");
        driver = driverManager.getInstance().getDriver();
        ExtentTestManager extentTestManager = new ExtentTestManager();
        extentTestManager.setExtentReport(extentReports);
        extentTest = extentTestManager.getInstance().getExtentTest();
        logger.warn("se creo el extentTest "+ extentTest);
        */
        TestManager driverManager = new TestManager();
        driverManager.setDriver("CHROME");
        driverManager.setExtentReport(extentReports);
        driver = TestManager.getInstance().getDriver();
        logger.trace("THREAD: " + Thread.currentThread().getId() + " driver = " + driver);
        extentTest = TestManager.getInstance().getExtentTest();
        logger.trace("THREAD: " + Thread.currentThread().getId() + " extenTest = " + extentTest);
    }

    /**
     * Se ejecutará después de cada método de prueba
     */
    @AfterMethod
    public void afterMethod() {
        logger.trace("THREAD: " + Thread.currentThread().getId());
        //DriverManager.getInstance().removeDriver();
        TestManager.getInstance().removeDriver();
    }

    /**
     * Se ejecutará solo una vez después de que se hayan ejecutado todos los métodos de prueba de la clase actual
     */
    @AfterClass
    public void afterClass() {
        logger.trace("THREAD: " + Thread.currentThread().getId());
    }

    /**
     * Se ejecutará antes de que se ejecute cualquier método de prueba que pertenezca a las clases dentro de la etiqueta <test>
     */
    @AfterTest
    public void afterTest() {
        logger.trace("THREAD: " + Thread.currentThread().getId());
    }
}