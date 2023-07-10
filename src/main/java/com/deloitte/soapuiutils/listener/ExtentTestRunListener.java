package com.deloitte.soapuiutils.listener;

import java.io.File;
import java.util.HashMap;

import com.deloitte.soapuiutils.service.SoapUIService;
import com.deloitte.soapuiutils.service.SoapUIServiceImplementation;
import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunListener;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;



public class ExtentTestRunListener implements TestRunListener {
	public static SoapUIService TCservice = null;
	public String reportPath;

	@Override
	public void beforeRun(TestCaseRunner runner, TestCaseRunContext context) {
		// SoapUI.log("Inside BeforeRun in TestRunListener - 1");
		try {
			if (ExtentProjectRunListener.Projservice == null && ExtentTestSuiteRunListener.TSservice == null) {
				TCservice = new SoapUIServiceImplementation();

				String projectXmlPath = context.getTestCase().getTestSuite().getProject().getPath();
				int index = projectXmlPath.lastIndexOf("\\");
				reportPath = projectXmlPath.substring(0, index);
				reportPath = reportPath + File.separator + "Reports";

				String testCaseName = context.getTestCase().getName();
				String testCaseDesc = context.getTestCase().getDescription();
				String testCaseId = context.getTestCase().getId();
				HashMap<String, String> klovConfig = new HashMap<String, String>();
				klovConfig.put("MongoDBIP", "");
				klovConfig.put("MongoDBPort", "");
				klovConfig.put("KlovServerUrl", "");
				TCservice.startReporting(reportPath, testCaseName);
				TCservice.startTestSuiteLogging(testCaseName, testCaseDesc, testCaseId);
			}
		} catch (Exception t) {
			SoapUI.log("SOAPUI Extentter plugin cannot be initialized. " + t.getMessage());
		}
	}

	@Override
	public void afterRun(TestCaseRunner runner, TestCaseRunContext context) {
		// SoapUI.log("Inside AfterRun in TestRunListener - 4");
		try {
			if (ExtentProjectRunListener.Projservice == null && ExtentTestSuiteRunListener.TSservice == null) {
				TCservice.finishReporting();
				TCservice = null;
				SoapUI.log("Reports are published at " + reportPath);
			}
		} catch (Throwable t) {
			SoapUI.log("SOAPUI Extentter Error in  beforeTestCase of TestSuiteRunListener " + t.getMessage());
		}
	}

	@Override
	public void beforeStep(TestCaseRunner arg0, TestCaseRunContext arg1) {
		// This one is not using in current soapui
	}

	@Override
	public void afterStep(TestCaseRunner paramTestCaseRunner, TestCaseRunContext paramTestCaseRunContext,
			TestStepResult paramTestStepResult) {
		// SoapUI.log("Inside AfterStep in TestRunListener - 3");
		try {
			if (ExtentProjectRunListener.Projservice != null) {
				String testCaseId = paramTestCaseRunContext.getTestCase().getId();
				String testSuiteId = paramTestCaseRunContext.getTestCase().getTestSuite().getId();
				ExtentProjectRunListener.Projservice.finishTestStepLogging(paramTestStepResult, testSuiteId,
						testCaseId);
			} else if (ExtentTestSuiteRunListener.TSservice != null) {
				String testCaseId = paramTestCaseRunContext.getTestCase().getId();
				String testSuiteId = paramTestCaseRunContext.getTestCase().getTestSuite().getId();
				ExtentTestSuiteRunListener.TSservice.finishTestStepLogging(paramTestStepResult, testSuiteId,
						testCaseId);
			} else {
				String testCaseId = paramTestCaseRunContext.getTestCase().getId();
				String testSuiteId = paramTestCaseRunContext.getTestCase().getId();
				TCservice.finishTestStepLogging(paramTestStepResult, testSuiteId, testCaseId);
			}
		} catch (Throwable t) {
			SoapUI.log("SOAPUI Extentter Error in  beforeStep of TestRunListener " + t.getMessage());
		}
	}

	@Override
	public void beforeStep(TestCaseRunner arg0, TestCaseRunContext arg1, TestStep arg2) {
		// TODO Auto-generated method stub
		
	}



}
