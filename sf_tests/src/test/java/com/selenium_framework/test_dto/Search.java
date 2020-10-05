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

public class Search {
    private String idTestCase;
    private String searchData;
    private String category;
    private String state;
    private String[] testResultData;

    public Search(String[] data) {
        setIdTestCase(data[0]);
        setSearchData(data[1]);
        setCategory(data[2]);
    }

    public String getIdTestCase() {
        return idTestCase;
    }

    public void setIdTestCase(String idTestCase) {
        this.idTestCase = idTestCase;
    }

    public String getSearchData() {
        return searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
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
        testResultData[0] = getIdTestCase();
        testResultData[1] = getSearchData();
        testResultData[2] = getCategory();
        testResultData[3] = getState();
    }
}