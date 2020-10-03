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

package com.selenium_framework.page_objects;

import com.selenium_framework.core.BasePageObjects;
import com.selenium_framework.core.GetPropertyValues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class Ebay_Page_Home extends BasePageObjects {
    private static Logger logger = LogManager.getLogger(Ebay_Page_Home.class);

    @FindBy(xpath = "//*[@id=\"gh-ac\"]")
    WebElement txt_Search;

    @FindBy(xpath = "//*[@id=\"gh-cat\"]\n")
    WebElement dpbox_Categories;

    @FindBy(xpath = "//*[@id=\"gh-btn\"]\n")
    WebElement btn_search;

    public Ebay_Page_Home(WebDriver driver) {
        super(driver);
    }

    public void goToEbayHomePage() throws Exception {
        String url_ebayhome = null;
        try {
            url_ebayhome = GetPropertyValues.getPropertyValue("config.properties", "url_ebayhome");
            driver.navigate().to(url_ebayhome);
        } catch (Exception e) {
            logger.error("No se pudo redireccionar a la pagina " + url_ebayhome, e);
            throw e;
        }
    }

    public void writeInTxtSearch(String search) throws Exception {
        txt_Search.clear();
        txt_Search.sendKeys(search);
    }

    public void selectCategory(String category) throws Exception {
        try {
            Select slc_Category = new Select(dpbox_Categories);
            slc_Category.selectByVisibleText(category);
        } catch (Exception e) {
            throw e;
        }

    }

    public void clickButtonSearch() throws Exception {
        btn_search.click();
    }
}