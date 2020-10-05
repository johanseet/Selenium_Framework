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
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Report {
    private static final String currentTime = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date()).replace(":", ".").replace("/", "-");
    private static String fs = File.separator;
    private static String OUTPUT_FOLDER;

    static {
        try {
            OUTPUT_FOLDER = System.getProperty("user.dir") + fs + "extent-reports" + fs + GetPropertyValues.getPropertyValue("config.properties", "report_name") + "_" + currentTime;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Método que toma captura de pantalla
     *
     * @param webDriver  driver donde se toma la captura de pantalla
     * @param testId     Id de la prueba
     * @param name       Nombre de la captura
     * @param isFullSize Indica true si se toma captura a la página completa o false si no.
     */
    protected static Media takeScreenshot(WebDriver webDriver, String testId, String name, boolean isFullSize) throws Exception {
        name += ".png";
        String imgPath = "test_" + testId;
        String imgFullPath = OUTPUT_FOLDER + fs + imgPath;

        new File(imgFullPath).mkdirs();
        File file = new File(imgFullPath + fs + name);
        file.createNewFile();

        if (isFullSize) {
            ImageIO.write(new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(webDriver).getImage(), "png", file);
        } else {
            FileUtils.copyFile(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE), file);
        }

        return MediaEntityBuilder.createScreenCaptureFromPath(imgFullPath + fs + name).build();
    }

    /**
     * Método que genera el ExtentReport a utilizar en las pruebas
     *
     * @return Instancia del ExtentReport
     */
    protected static ExtentReports generateReport() throws Throwable {
        String path = OUTPUT_FOLDER + fs + "index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName(GetPropertyValues.getPropertyValue("config.properties", "report_name"));
        reporter.config().setDocumentTitle(GetPropertyValues.getPropertyValue("config.properties", "report_tabname"));
        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo(GetPropertyValues.getPropertyValue("config.properties", "tester_position"), GetPropertyValues.getPropertyValue("config.properties", "tester_name"));
        return extentReports;
    }

    /**
     * Método que genera el excel del resultado de las pruebas
     *
     * @param headers Encabezados de las columnas
     */
    protected void createExcelFileResult(String[] headers) throws Throwable {
        ExcelFunctions.createExcel(OUTPUT_FOLDER + fs + GetPropertyValues.getPropertyValue("config.properties", "resultFile_name"), headers);
    }

    /**
     * Método que escribe todos los resultados de las pruebas en el excel de resultados
     *
     * @param headers        Encabezados de los columnas
     * @param testResultData Lista de todos los datos de las pruebas
     * @throws Throwable
     */
    protected void writeExcelFileResult(String[] headers, ArrayList<String[]> testResultData) throws Throwable {
        ExcelFunctions.writeTestResultData(OUTPUT_FOLDER + fs + GetPropertyValues.getPropertyValue("config.properties", "resultFile_name"), headers, testResultData);
    }
}