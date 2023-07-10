package com.deloitte.soapuiutils.service;

import java.util.HashMap;
import java.util.List;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestStepResult.TestStepStatus;
import com.eviware.soapui.model.testsuite.TestSuiteRunner;
import com.deloitte.soapuiutils.*;

public interface SoapUIService {

	void startReporting(String reportPath, String reportName);

	void finishReporting();

	void startTestSuiteLogging(String testSuiteName, String testDesc, String testSuiteId);

	void finishTestSuiteLogging(TestSuiteRunner testSuiteContext);

	void startTestCaseLogging(String testCaseName, String testSuiteId, String testCaseId);

	void finishTestCaseLogging(TestCaseRunner testCaseContext, PropertyExpansionContext propertyContext);

	void startTestStepLogging(TestStep testStep, String testCaseId);

	void finishTestStepLogging(TestStepResult testStepContext, String testSuiteId, String testCaseId);

	public void addEnvDetails(List<TestProperty> properties);
}
