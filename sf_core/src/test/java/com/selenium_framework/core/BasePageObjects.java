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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BasePageObjects extends BaseParallelTests {

    public BasePageObjects(WebDriver driver) {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
    }

/*    public Media takeScreenshot(String id, String name, Boolean isFullSize) throws Exception {
        System.out.println(id);
        return Report.takeScreenshot(driver,id,name,false);
    }*/

}