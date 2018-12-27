package org.zia.training.selenium.keyword;

import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.Sheet;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import org.zia.training.selenium.keyword.Driver;
import org.zia.training.selenium.keyword.ExcelSheetDriver;
import org.zia.training.selenium.keyword.CommonFunctionsLib;
import org.zia.training.selenium.keyword.Utilities;


public class TestAutomation {

	final static Logger logger = Logger.getLogger(TestAutomation.class);

	//public static void main(String[] args) throws FileNotFoundException, IOException, BiffException, NumberFormatException, InterruptedException {
		@Test
		public void testLogin() throws Exception {
		Driver d = new Driver();
		ExcelSheetDriver excelSheetDriver = new ExcelSheetDriver();
		ExcelSheetDriver excelSheetDriver1 = new ExcelSheetDriver();
		ExcelSheetDriver excelSheetDriver2 = new ExcelSheetDriver();
		Sheet testSuitesheet = excelSheetDriver.getWorksheet("TestCases/TestSuite.xls", "Sheet1");
		//Sheet testSuitesheet = excelSheetDriver.getWorksheet("C:\\Users\\Tariq Ahsan\\Desktop\\ZIA Java Training\\Eclipse Projects\\SeleniumAutomation-master\\TestCases\\TestSuite.xls", "Sheet1");
		// WebDriver driver = d.InitateDriver();

		//driver.get("https://login.live.com/login.srf?wa = wsignin1.0&rpsnv = 13&ct = 1510644152&rver = 6.7.6640.0&wp = MBI_SSL&wreply = https%3a%2f%2foutlook.live.com%2fowa%2f%3fnlp%3d1%26RpsCsrfState%3d02d9a83a-4a66-dba6-f710-5248efde97fc&id = 292841&CBCXT = out&lw = 1&fl = dob%2cflname%2cwld&cobrandid = 90015");
		Utilities.loadlog4jfile();
		int c = excelSheetDriver.columnCount();
		int r = excelSheetDriver.rowCount();
		// Read the TestSuite.xls file to find which Test Suite to run
		for (int i = 1; i < r; i++)
		{
			String serialNumber = excelSheetDriver.readCell(testSuitesheet,0, i);

			String description = excelSheetDriver.readCell(testSuitesheet,1, i);
			String executionFlag = excelSheetDriver.readCell(testSuitesheet,2, i);
			logger.info("TestSuite:" + serialNumber);
			logger.info("TestSuite:" + description);
			logger.info("TestSuite:" + executionFlag);
			// Only execute the Test Suite which has a execution flag set to 'Y'
			if (executionFlag.equalsIgnoreCase("Y")){
				// Read the TestCase.xls file to find which Test Case(s) to run
				Sheet TestCasesheet =  excelSheetDriver1.getWorksheet("TestCases/TestCase.xls", description);
				//Sheet TestCasesheet =  excelSheetDriver1.getWorksheet("C:\\Users\\Tariq Ahsan\\Desktop\\ZIA Java Training\\Eclipse Projects\\SeleniumAutomation-master\\TestCases\\TestCase.xls", description);
				
				int testCaseRow = excelSheetDriver1.rowCount();
				int testCaseColumn = excelSheetDriver1.columnCount();

				for (int k = 1; k < testCaseRow; k++)
				{
					String testCaseSerialNumber = excelSheetDriver1.readCell(TestCasesheet,0, k);
					String testCaseNumber = excelSheetDriver1.readCell(TestCasesheet,1, k);
					String testcaseDescription = excelSheetDriver1.readCell(TestCasesheet,2, k);
					String testcaseExecutionFlag = excelSheetDriver1.readCell(TestCasesheet,3, k);
					logger.info("TestCases:" + testCaseSerialNumber);
					logger.info("TestCases:" + testCaseNumber);
					logger.info("TestCases:" + testcaseDescription);
					logger.info("TestCases:" + testcaseExecutionFlag);

					// Only execute the Test Case which has a execution flag set to 'Y' or 'y'
					if (testcaseExecutionFlag.equalsIgnoreCase("y"))
					{
						// Read the TestSteps.xls file to find which steps to run
						Sheet testStepSheet = excelSheetDriver2.getWorksheet("TestCases/BankTestSteps.xls", description);
						//Sheet testStepSheet = excelSheetDriver2.getWorksheet("C:\\Users\\Tariq Ahsan\\Desktop\\ZIA Java Training\\Eclipse Projects\\SeleniumAutomation-master\\TestCases\\TestStep.xls", description);
						int testStepsRow = excelSheetDriver2.rowCount();
						int testStepsColumn = excelSheetDriver2.columnCount();
						WebDriver driver = d.InitateDriver();
						CommonFunctionsLib commonFunctionsLib = new CommonFunctionsLib(driver);
						for (int w = 1; w < testStepsRow; w++)
						{
							String testStepsSerialNumber = excelSheetDriver.readCell(testStepSheet,0, w);
							String testStepsCaseNumber = excelSheetDriver.readCell(testStepSheet,1, w);
							String testStepsDescription = excelSheetDriver.readCell(testStepSheet,2, w);
							String testStepsXpath = excelSheetDriver.readCell(testStepSheet,3, w);
							String testStepsValue = excelSheetDriver.readCell(testStepSheet,4, w);
							String testStepsKeyword = excelSheetDriver.readCell(testStepSheet,5, w);
							
							// Only run the Steps which has a matching Test Case Number (e.g. TC1, TC2 ...)
							if (testCaseNumber.equalsIgnoreCase(testStepsCaseNumber)){
								logger.info("testStepsSerialNumber:" + testStepsSerialNumber);
								logger.info("testStepsDescription:" + testStepsDescription);
								logger.info("testStepsXpath:" + testStepsXpath);
								logger.info("testStepsValue:" + testStepsValue);
								logger.info("testStepsKeyword:" + testStepsKeyword);

								commonFunctionsLib.performActions(testStepsKeyword, testStepsValue, testStepsXpath);
							}

						}

					}
				}

			}
		}

		excelSheetDriver.closeworkbook();
		excelSheetDriver2.closeworkbook();
		excelSheetDriver1.closeworkbook();

	}

}
