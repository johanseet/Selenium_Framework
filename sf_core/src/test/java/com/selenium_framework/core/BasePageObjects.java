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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BasePageObjects {
    private static final int TIMEOUT = 5;
    private static final int POLLING = 100;
    private static Logger logger = LogManager.getLogger(BasePageObjects.class);
    protected WebDriver driver;

    public BasePageObjects(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

    protected void waitForElementToAppear(By locator) {
        logger.info("************** Metodo ejecutado **************");
    }

    protected void waitForElementToDisappear(By locator) {
        logger.info("************** Metodo ejecutado **************");
    }

    protected void waitForTextToDisappear(By locator, String text) {
        logger.debug("************** Metodo ejecutado **************");
    }
}