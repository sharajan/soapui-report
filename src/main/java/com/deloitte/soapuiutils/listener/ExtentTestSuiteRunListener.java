package com.deloitte.soapuiutils.listener;

import java.io.File;
import java.util.HashMap;

import com.deloitte.soapuiutils.service.SoapUIService;
import com.deloitte.soapuiutils.service.SoapUIServiceImplementation;
import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestSuiteRunContext;
import com.eviware.soapui.model.testsuite.TestSuiteRunListener;
import com.eviware.soapui.model.testsuite.TestSuiteRunner;


public class ExtentTestSuiteRunListener implements TestSuiteRunListener {

	public static SoapUIService TSservice = null;
	public String reportPath;

	public void afterRun(TestSuiteRunner runner, TestSuiteRunContext context) {
		// SoapUI.log("Inside AfterRun in TestSuiteRunListener - 10");
		try {
			if (ExtentProjectRunListener.Projservice == null) {
				TSservice.finishReporting();
				TSservice = null;
				SoapUI.log("Reports are published at " + reportPath);
			}
		} catch (Throwable t) {
			SoapUI.log("TSYS Extentter Error in  beforeTestCase of TestSuiteRunListener " + t.getMessage());
		}
	}

	public void beforeRun(TestSuiteRunner runner, TestSuiteRunContext context) {
		// SoapUI.log("Inside BeforeRun in TestSuiteRunListener - 3");
		try {
			if (ExtentProjectRunListener.Projservice == null) {
				TSservice = new SoapUIServiceImplementation();

				String projectXmlPath = context.getTestSuite().getProject().getPath();
				int index = projectXmlPath.lastIndexOf("\\");
				reportPath = projectXmlPath.substring(0, index);
				reportPath = reportPath + File.separator + "Reports";

				String testSuiteName = context.getTestSuite().getName();
				String testSuiteDesc = context.getTestSuite().getDescription();
				String testSuiteId = context.getTestSuite().getId();

			/*	HashMap<String, String> klovConfig = new HashMap<String, String>();
				klovConfig.put("MongoDBIP", "");
				klovConfig.put("MongoDBPort", "");
				klovConfig.put("KlovServerUrl", "");*/

				SoapUI.log("ReportPath and File Name " + reportPath + testSuiteName);
				TSservice.startReporting(reportPath, testSuiteName);
				SoapUI.log("Suite  -- " + testSuiteName +"=="+ testSuiteDesc+ "--"+testSuiteId);
				TSservice.startTestSuiteLogging(testSuiteName, testSuiteDesc, testSuiteId);
			}
		} catch (Exception t) {
			SoapUI.log("TSYS Extentter plugin cannot be initialized. " + t.getMessage());
		}
	}

	public void beforeTestCase(TestSuiteRunner paramTestSuiteRunner, TestSuiteRunContext paramTestSuiteRunContext,
			TestCase paramTestCase) {
		// SoapUI.log("Inside BeforeTestCase in TestSuiteRunListener - 4");
		try {
			if (ExtentProjectRunListener.Projservice != null) {
				String testCaseName = paramTestCase.getName();
				String testSuiteId = paramTestSuiteRunContext.getTestSuite().getId();
				String testCaseId = paramTestCase.getId();
				ExtentProjectRunListener.Projservice.startTestCaseLogging(testCaseName, testSuiteId, testCaseId);
			} else {
				String testCaseName = paramTestCase.getName();
				String testSuiteId = paramTestSuiteRunContext.getTestSuite().getId();
				String testCaseId = paramTestCase.getId();
				TSservice.startTestCaseLogging(testCaseName, testSuiteId, testCaseId);
			}
		} catch (Throwable t) {
			SoapUI.log("TSYS Extentter Error in  beforeTestCase of TestSuiteRunListener " + t.getMessage());
		}
	}

	@Override
	public void afterTestCase(TestSuiteRunner arg0, TestSuiteRunContext arg1, TestCaseRunner arg2) {
		// TODO Auto-generated method stub
		
	}

	
}