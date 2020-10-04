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

public class Search_dto {
    private Logger logger = LogManager.getLogger(Search_dto.class);
    private String testcase;
    private String search_data;
    private String category;

    public Search_dto(final Object[] oRow) throws Exception {
        logger.info("Iniciando la encapsulacion de los datos");
        setTestCase(oRow[0]);
        setSearchdata(oRow[1]);
        setCategory(oRow[2]);
    }

    public String getTestCase() {
        return testcase;
    }

    /**
     * Set TestCase value
     */

    public void setTestCase(Object testcase) {
        this.testcase = testcase.toString().trim();
    }


    /**
     * Set search data
     */

    public void setSearchdata(Object search_data) {
        this.search_data = search_data.toString().trim();
    }

    public String getSearch_data() {
        return search_data;
    }

    public String getCategory() {
        return category;
    }

    /**
     * Set Category
     */

    public void setCategory(Object category) {
        this.category = category.toString().trim();

    }

}