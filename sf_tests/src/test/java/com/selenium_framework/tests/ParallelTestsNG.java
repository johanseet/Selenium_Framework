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
import com.selenium_framework.core.Report;
import com.selenium_framework.page_objects.Ebay_Page_Home;
import com.selenium_framework.test_dto.HeadersResultFile;
import com.selenium_framework.test_dto.Search;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParallelTestsNG extends BaseParallelTests {

    private String propertyFile = "config.properties";
    private String testFile_path = "testfile_path";
    private String testFile_sheet = "testfile_sheet";
    private String[] headers = new HeadersResultFile().getHeadersResultFile();
    private Report report = new Report();
    private static Search search = new Search();
    private Logger logger = LogManager.getLogger(ParallelTestsNG.class);

    public ParallelTestsNG() {
        super.propertyFile = propertyFile;
        super.testFile_path = testFile_path;
        super.testFile_sheet = testFile_sheet;
        super.headers = headers;
        testResultData = search;
    }

    @Test(dataProvider = "data-provider")
    public void pageTest(String id, String search_data, String category) throws Throwable {
        //Actualiando el titulo del reporte
        try {
            getExtentTest().getModel().setName("Test Case " + id);
            Ebay_Page_Home ebay_page_home = new Ebay_Page_Home(getDriver(), id);
            ebay_page_home.goToEbayHomePage();
            ebay_page_home.writeInTxtSearch(search_data);
            ebay_page_home.selectCategory(category);
            ebay_page_home.clickButtonSearch();
            if (id.equals("2")) {
                Assert.assertTrue(false);
                getExtentTest().fail("Falla a proposito");
            }
            ((Search) getTestResultData()).setTestcase(id);
            logger.debug(Thread.currentThread().getId() + " testCase = " + ((Search) getTestResultData()).getTestcase());
            ((Search) getTestResultData()).setSearch_data(search_data);
            logger.debug(Thread.currentThread().getId() + " search = " + ((Search) getTestResultData()).getSearch_data());
            ((Search) getTestResultData()).setCategory(category);
            logger.debug(Thread.currentThread().getId() + " category = " + ((Search) getTestResultData()).getCategory());
            ((Search) getTestResultData()).setTestResultData();
        } catch (Throwable t) {
            //throw logger.throwing(t);
        }
    }


/*    public void pageTest(Object[] data) throws Throwable {
        //Actualiando el titulo del reporte
        Search_dto search_dto = new Search_dto(data);
        logger.debug("THREAD " + Thread.currentThread().getId() + " "+search_dto);
        logger.debug("THREAD " + Thread.currentThread().getId() + " "+search_dto.getTestCase());
        logger.debug("THREAD " + Thread.currentThread().getId() + " "+search_dto.getSearch_data());
        logger.debug("THREAD " + Thread.currentThread().getId() + " "+search_dto.getCategory());
        getExtentTest().getModel().setName("Test Case " + search_dto.getTestCase());

        Ebay_Page_Home ebay_page_home = new Ebay_Page_Home(getDriver());
        ebay_page_home.goToEbayHomePage();
       // ebay_page_home.writeInTxtSearch(dto.getSearch_data());
       // ebay_page_home.selectCategory(dto.getCategory());
       // ebay_page_home.clickButtonSearch();
    }*/

}