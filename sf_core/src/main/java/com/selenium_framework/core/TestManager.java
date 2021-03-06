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
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.util.concurrent.ConcurrentHashMap;

public class TestManager {
    private static TestManager instance = new TestManager();
    private static String browser;
    private static ExtentReports extentReport;
    /**
     * ThreadLocal que guarda en hilos el driver y el extentest que se utilizara en cada prueba
     */
    protected ThreadLocal<ConcurrentHashMap<String, Object>> threadLocal = ThreadLocal.withInitial(() -> {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        switch (browser.toUpperCase()) {
            case "CHROME":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.setCapability(ChromeOptions.CAPABILITY, options);
                WebDriver webDriver = new ChromeDriver(options);
                map.put("DRIVER", webDriver);
                ExtentTest extentTest = extentReport.createTest("testName");
                map.put("TEST", extentTest);
                return map;
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                map.put("DRIVER", new FirefoxDriver());
                map.put("TEST", extentReport.createTest("testName"));
                return map;
            case "OPERA":
                WebDriverManager.operadriver().setup();
                map.put("DRIVER", new OperaDriver());
                map.put("TEST", extentReport.createTest("testName"));
                return map;
            case "EDGE":
                WebDriverManager.edgedriver().setup();
                map.put("DRIVER", new EdgeDriver());
                map.put("TEST", extentReport.createTest("testName"));
                return map;
            case "IEXPLORER":
                WebDriverManager.iedriver().setup();
                map.put("DRIVER", new InternetExplorerDriver());
                map.put("TEST", extentReport.createTest("testName"));
                return map;

            default:
                throw new WebDriverManagerException("Explorador no implementado");
        }

    });

    /**
     * Retorna la instancia del TestManager
     */
    protected static TestManager getInstance() {
        return instance;
    }

    /**
     * llamar a este método para obtener el driver del hilo en que se ecnuentra la prueba.
     *
     * @return Devuelve el driver
     */
    protected WebDriver getDriver() {
        return (WebDriver) threadLocal.get().get("DRIVER");
    }

    /**
     * Método para establecer el driver a utilizar en la prueba
     *
     * @param browser "CHROME", "FIREFOX", "OPERA", "EDGE", "IEXPLORER"
     */
    protected void setDriver(String browser) {
        TestManager.browser = browser;
    }

    /**
     * Método que cierra el driver y elimina el hilo de la prueba, este método se debe llamar cuando la prueba finalice
     */
    protected void removeDriver() {
        WebDriver webDriver = ((WebDriver) threadLocal.get().get("DRIVER"));
        webDriver.quit();
        threadLocal.remove();
    }

    /**
     * Establece el ExtentReport que se utilizara en todas las pruebas
     */
    protected void setExtentReport(ExtentReports extentReport) {
        TestManager.extentReport = extentReport;
    }

    /**
     * Retorna el ExtentTest que se creó en el hilo de la prueba, este ExtentTest se utilizará para guardar el estado de los pasos del @Test
     *
     * @return extentest
     */
    protected ExtentTest getExtentTest() {
        return ((ExtentTest) threadLocal.get().get("TEST"));
    }

    /**
     * Devuelve los objetos que se han agregado al ThreadLocal durante la ejecución del hilo
     */
    protected Object getObjectFromTestManager(String objectName) {
        return threadLocal.get().get(objectName);
    }

    /**
     * Agrega al ThreadLocal los objetos que se crean durante la ejecución del hilo.
     */
    protected void setObjectFromTestManager(Object object, String objectName) {
        threadLocal.get().put(objectName, object);
    }

}
