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

package com.selenium_framework.test_dto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Search {
    public Logger logger = LogManager.getLogger(Search.class);
    private String testcase;
    private String search_data;
    private String category;
    private String state;
    private String[] testResultData;

    public String getTestcase() {
        return testcase;
    }

    public void setTestcase(String testcase) {
        logger.error("seteando el id " + testcase + " al id actual " + this.testcase);
        this.testcase = testcase;
    }

    public String getSearch_data() {
        return search_data;
    }

    public void setSearch_data(String search_data) {
        this.search_data = search_data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String[] getTestResultData() {
        return testResultData;
    }

    public void setTestResultData() {
        testResultData = new String[4];
        testResultData[0] = getTestcase();
        testResultData[1] = getSearch_data();
        testResultData[2] = getCategory();
        testResultData[3] = getState();
    }
}