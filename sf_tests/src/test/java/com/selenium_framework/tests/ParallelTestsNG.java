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

    private String propertyFile = "config.properties";
    private String testFile_path = "testfile_path";
    private String testFile_sheet = "testfile_sheet";
    private String[] headers = new HeadersResultFile().getHeadersResultFile();
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
        try {
            search = (Search) getTestResultData();
            logger.debug(Thread.currentThread().getId() + " ***dataprovider testCase = " + id);
            //Actualiando el titulo del reporte
            getExtentTest().getModel().setName("Test Case " + id);
            Ebay_Page_Home ebay_page_home = new Ebay_Page_Home(getDriver(), id);
            ebay_page_home.goToEbayHomePage();
            if (id.equals("2")) {
                getExtentTest().fail("Falla a proposito");
            }
            ebay_page_home.writeInTxtSearch(search_data);
            ebay_page_home.selectCategory(category);
            ebay_page_home.clickButtonSearch();

            search.setTestcase(id);
            search.setSearch_data(search_data);
            search.setCategory(category);

        } catch (Throwable t) {
            //throw logger.throwing(t);
        }
    }

/*
public void pageTest(String[] data) throws Throwable {
        search = (Search) getTestResultData();
        loadData(data);

        getExtentTest().getModel().setName("Test Case " + search.getTestcase());
        Ebay_Page_Home ebay_page_home = new Ebay_Page_Home(getDriver(), search.getTestcase());
        ebay_page_home.goToEbayHomePage();
        if (search.getTestcase().equals("2")) {
            getExtentTest().fail("Falla a proposito");
        }
        ebay_page_home.writeInTxtSearch(search.getSearch_data());
        ebay_page_home.selectCategory(search.getCategory());
        ebay_page_home.clickButtonSearch();
    }

    public void loadData(String[] data){
        search.setTestcase(data[0]);
        search.setSearch_data(data[1]);
        search.setCategory(data[2]);
    }
*/
}