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

package com.selenium_framework.tests;

import com.selenium_framework.core.BaseParallelTests;
import com.selenium_framework.page_objects.Ebay_Page_Home;
import com.selenium_framework.test_dto.HeadersResultFile;
import com.selenium_framework.test_dto.Search;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class ParallelTestsNG extends BaseParallelTests {

    private Logger logger = LogManager.getLogger(ParallelTestsNG.class);

    public ParallelTestsNG() {
        super.propertyFile = "config.properties";
        super.testFile_path = "testfile_path";
        super.testFile_sheet = "testfile_sheet";
        super.headers = new HeadersResultFile().getHeadersResultFile();
    }

    @Test(dataProvider = "dataProvider")
    public void pageTest(String[] data) throws Throwable {
        String testResultData = "TEST_RESULT_DATA";
        //Creando la instancia del DTO y cargando los datos del dataprovider
        Search search = new Search(data);
        //Agregando la instancia del DTO al ThreadLocal para el manejo dentro del hilo
        setObjectFromTestManager(search, testResultData);
        //Obteniendo la instancia del DTO que agregamos al hilo
        search = ((Search) getObjectFromTestManager(testResultData));

        logger.debug("Hilo actual = " + Thread.currentThread().getId() + " --> DTO del hilo = " + search);

        //Actualizando el nombre de la prueba
        getExtentTest().getModel().setName("Test Case " + search.getIdTestCase());

        //Ejecutar la prueba
        Ebay_Page_Home ebay_page_home = new Ebay_Page_Home(getDriver(), search.getIdTestCase());
        ebay_page_home.goToEbayHomePage();
        if (search.getIdTestCase().equals("2")) {
            getExtentTest().fail("Falla a proposito");
        }
        ebay_page_home.writeInTxtSearch(search.getSearchData());
        ebay_page_home.selectCategory(search.getCategory());
        ebay_page_home.clickButtonSearch();
    }

}