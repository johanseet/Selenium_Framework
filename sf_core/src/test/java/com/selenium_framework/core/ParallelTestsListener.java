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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ParallelTestsListener extends BaseParallelTests implements ITestListener {
    private static Logger logger = LogManager.getLogger(ParallelTestsListener.class);
    //private static ThreadLocal<ExtentTest> threadTest = new ThreadLocal<ExtentTest>();
    public ExtentReports extentReports;

    @Override
    public void onStart(ITestContext context) {
        logger.debug("THREAD: " + Thread.currentThread().getId());
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentReports = getExtentReports();
        logger.debug("THREAD: " + Thread.currentThread().getId() + "utilizando el extentReport = " + extentReports);
        logger.debug("THREAD: " + Thread.currentThread().getId() + "utilizando el extenttest = " + getExtentTest());
        //threadTest.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        getExtentTest().log(Status.PASS, "marcando pass el extentTest " + getExtentTest());
        logger.debug("THREAD: " + Thread.currentThread().getId() + "marcando pass el extentTest " + getExtentTest());
        //threadTest.get().log(Status.PASS,"prueba pass");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        getExtentTest().fail("marcando fail el extentTest " + getExtentTest());
        logger.debug("THREAD: " + Thread.currentThread().getId() + " marcando fail el extentTest " + getExtentTest());
        //threadTest.get().fail(result.getThrowable());
        //threadTest.get().addScreenCaptureFromPath(result.)
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.debug("THREAD: " + Thread.currentThread().getId());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.debug("THREAD: " + Thread.currentThread().getId());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        logger.debug("THREAD: " + Thread.currentThread().getId());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.debug("THREAD: " + Thread.currentThread().getId() + " finalizando el extentreport = " + extentReports);
        extentReports.flush();
    }
}