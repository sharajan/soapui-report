package com.deloitte.soapuiutils.listener;

import java.io.File;
import java.util.List;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.testsuite.ProjectRunContext;
import com.eviware.soapui.model.testsuite.ProjectRunListener;
import com.eviware.soapui.model.testsuite.ProjectRunner;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.model.testsuite.TestSuiteRunner;

import com.deloitte.soapuiutils.service.SoapUIService;
import com.deloitte.soapuiutils.service.SoapUIServiceImplementation;


	public class ExtentProjectRunListener implements ProjectRunListener {

		public static SoapUIService Projservice = null;
		public String reportPath;
		private static int timeout = 1000;

		public void beforeRun(ProjectRunner runner, ProjectRunContext context) {
			// SoapUI.log("Inside BeforeRun in ProjectRunListener - 1");
			try {
				List<TestProperty> properties = context.getProject().getPropertyList();
				//HashMap<String, String> klovConfig = getKlovConfiguration(properties);
				Projservice = new SoapUIServiceImplementation();
				String projectXmlPath = context.getProject().getPath();
				int index = projectXmlPath.lastIndexOf("\\");
				reportPath = projectXmlPath.substring(0, index);
				reportPath = reportPath + File.separator + "Reports";
				SoapUI.log("SOAPUI REport PAth. " + reportPath);

				String reportName = context.getProject().getName();
				Projservice.startReporting(reportPath, reportName);
				Projservice.addEnvDetails(properties);
			} catch (Exception t) {
				SoapUI.log("SOAPUI Extentter plugin cannot be initialized. " + t.getMessage());
			}
		}

	/*	private HashMap<String, String> getKlovConfiguration(List<TestProperty> properties) {
			HashMap<String, String> klovMap = new HashMap<String, String>();
			try {
				int propSize = properties.size();
				if (propSize != 0) {
					for (int propInterator = 0; propInterator < propSize; propInterator++) {
						if (properties.get(propInterator).getName().equalsIgnoreCase("MongoDBIP")) {
							if (properties.get(propInterator).getValue() != null && InetAddress
									.getByName(properties.get(propInterator).getValue()).isReachable(timeout)) {
								klovMap.put("MongoDBIP", properties.get(propInterator).getValue());
							} else {
								SoapUI.log("MongoDBIP is not accessible");
								klovMap.put("MongoDBIP", "");
							}
						}
						if (properties.get(propInterator).getName().equalsIgnoreCase("MongoDBPort")) {
							if (properties.get(propInterator).getValue() != null) {
								klovMap.put("MongoDBPort", properties.get(propInterator).getValue());
							}
						}
						if (properties.get(propInterator).getName().equalsIgnoreCase("KlovServerUrl")) {
							if (properties.get(propInterator).getValue() != null) {
								klovMap.put("KlovServerUrl", properties.get(propInterator).getValue());
							}
						}
					}
				}
			} catch (Exception e) {
				String exceptionMessage = " Exception occurred for Report method - getKlovConfiguration as ";
				SoapUI.log(exceptionMessage + e.toString());
			}
			return klovMap;
		}*/

		public void afterRun(ProjectRunner paramProjectRunner, ProjectRunContext paramProjectRunContext) {
			// SoapUI.log("Inside AfterRun in ProjectRunListener - 12");
			try {
				Projservice.finishReporting();
				Projservice = null;
				SoapUI.log("Reports are published at " + reportPath);
			} catch (Exception t) {
				SoapUI.log("SOAPUI Extentter plugin cannot be initialized. " + t.getMessage());
			}
		}

		public void beforeTestSuite(ProjectRunner paramProjectRunner, ProjectRunContext paramProjectRunContext,
				TestSuite paramTestSuite) {
			// SoapUI.log("Inside BeforeTestSuite in ProjectRunListener - 2");
			try {
				String testSuiteName = paramTestSuite.getName();
				String testSuiteDesc = paramTestSuite.getDescription();
				String testSuiteId = paramTestSuite.getId();
				Projservice.startTestSuiteLogging(testSuiteName, testSuiteDesc, testSuiteId);
			} catch (Throwable t) {
				SoapUI.log("SOAPUI Extentter Error in  beforeTestSuite of ProjectRunListener " + t.getMessage());
			}
		}

		public void afterTestSuite(ProjectRunner paramProjectRunner, ProjectRunContext paramProjectRunContext,
				TestSuiteRunner paramTestSuiteRunner) {
			// SoapUI.log("Inside AfterTestSuite in ProjectRunListener - 11");
		}
}
	

