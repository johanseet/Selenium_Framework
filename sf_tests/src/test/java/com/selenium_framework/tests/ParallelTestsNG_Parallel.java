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

public class ParallelTestsNG_Parallel extends BaseParallelTests {

    private static Logger logger = LogManager.getLogger(ParallelTestsNG_Parallel.class);
    String propertyFile = "config.properties";
    String testFile_path = "testfile_path2";
    String testFile_sheet = "testfile_sheet";

    public ParallelTestsNG_Parallel() {
        logger.info("THREAD: " + Thread.currentThread().getId());
        super.propertyFile = propertyFile;
        super.testFile_path = testFile_path;
        super.testFile_sheet = testFile_sheet;
    }

    @Test(dataProvider = "data-provider")
    public void pageTest(String id, String browser, String search_data, String category) throws Throwable {
        logger.warn("THREAD: " + Thread.currentThread().getId() + " se inicia la prueba " + id + " utilizando el extentTest = " + getExtentTest());
        getExtentTest().getModel().setName("Test Case " + id + " extenttest " + getExtentTest());
        Ebay_Page_Home ebay_page_home = new Ebay_Page_Home(driver);
        ebay_page_home.goToEbayHomePage();
        ebay_page_home.writeInTxtSearch(search_data);
        ebay_page_home.selectCategory(category);
        ebay_page_home.clickButtonSearch();

        getExtentTest().fail("getExtentTest().fail" + " extenttest " + getExtentTest());
        getExtentTest().pass("getExtentTest().pass" + " extenttest " + getExtentTest());
        getExtentTest().log(Status.DEBUG, "getExtentTest().log(Status.DEBUG" + " extenttest " + getExtentTest());
        getExtentTest().log(Status.FAIL, "getExtentTest().log(Status.FAIL" + " extenttest " + getExtentTest());
        getExtentTest().log(Status.ERROR, "getExtentTest().log(Status.ERROR" + " extenttest " + getExtentTest());
        //extentReports.flush();
        logger.warn("THREAD: " + Thread.currentThread().getId() + " se finaliza la prueba " + id + " utilizando el extentTest = " + getExtentTest());
    }

}