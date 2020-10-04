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

package com.selenium_framework.listener;

import com.aventstack.extentreports.ExtentReports;
import com.selenium_framework.core.BaseParallelTests;
import com.selenium_framework.test_dto.Search;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ParallelTestsListener extends BaseParallelTests implements ITestListener {
    private Logger logger = LogManager.getLogger(ParallelTestsListener.class);
    private ExtentReports extentReports;
    private Search search;

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentReports = getExtentReports();
        search = (Search) getTestResultData();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        search.setState("PASS");
        search.setTestResultData();
        addTestResultData(search.getTestResultData());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        getExtentTest().fail("La prueba ha fallado, información sobre el error: " + result.getThrowable());
        search.setState("PASS");
        search.setTestResultData();
        addTestResultData(search.getTestResultData());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.trace("Omitiendo la prueba");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.debug(Thread.currentThread().getId() + "***********************************");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}