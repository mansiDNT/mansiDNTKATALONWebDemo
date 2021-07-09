package com.example

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonSlurper

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.openqa.selenium.By
import org.openqa.selenium.Capabilities
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import com.kms.katalon.core.webui.driver.DriverFactory

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
//import java.math.BigDecimal;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.lang.String
import com.kms.katalon.core.configuration.RunConfiguration
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.BorderStyle

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.remote.DesiredCapabilities


import com.kms.katalon.core.appium.driver.AppiumDriverManager
import com.kms.katalon.core.mobile.driver.MobileDriverType
import com.kms.katalon.core.util.internal.PathUtil as PathUtil
import io.appium.java_client.android.AndroidDriver
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import io.appium.java_client.AppiumDriver
//import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import internal.GlobalVariable
import org.openqa.selenium.Proxy;

public class WebCustomKeyword {

	@Keyword
	def settestresult(String testresult, String testpassremarks, testfailremarks) {
		GlobalVariable.TestResult = testresult

		if (GlobalVariable.TestResult == "PASS") {
			GlobalVariable.TestRemarks = testpassremarks
			if(GlobalVariable.TestSuitResult != "FAIL") {

				GlobalVariable.TestSuitResult = "PASS"
			}
			if(GlobalVariable.TestModuleResult != "FAIL") {

				GlobalVariable.TestModuleResult = "PASS"
			}

			println ("Module ID is: "+GlobalVariable.ModID)
			println ("Test Cases ID is: "+GlobalVariable.TestCaseID)

			WebUI.takeScreenshot(GlobalVariable.G_AppPath+"/Screenshot/Success/"+GlobalVariable.TestCaseID+".png")
		}

		else if (GlobalVariable.TestResult == "FAIL") {
			GlobalVariable.TestRemarks = testfailremarks
			GlobalVariable.TestSuitResult = "FAIL"
			GlobalVariable.TestModuleResult = "FAIL"

			WebUI.takeScreenshot(GlobalVariable.G_AppPath+"/Screenshot/Failure/"+GlobalVariable.TestCaseID+".png")
		}
	}

	@Keyword
	def gettestdata() {
		try{
			def key
			def value
			Map<String,String> gettestdata  = new HashMap<>();
			FileInputStream file = new FileInputStream (new File(GlobalVariable.TestDataFile))
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			int getSheetsCount = workbook.getNumberOfSheets();

			for(int m = 0 ; m < getSheetsCount; m++)
			{
				XSSFSheet sh = workbook.getSheetAt(m);
				int getColumnCount = sh.getRow(0).getLastCellNum()
				println("getColumnCount:="+getColumnCount)
				for(int i=0; i<getColumnCount; i++)
				{
					if(XSSFCell!=null)
					{
						key = sh.getRow(0).getCell(i).getStringCellValue().toString()
						if(sh.getRow(1).getCell(i).getCellType() == Cell.CELL_TYPE_STRING)
						{
							value = sh.getRow(1).getCell(i).getStringCellValue().toString()
						}
						else if(sh.getRow(1).getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC)
						{
							value = sh.getRow(1).getCell(i).getNumericCellValue().toString()
						}

						GlobalVariable.TestData.put(key, value);

					}
				}

			}


			//println(GlobalVariable.TestData)


			file.close();

		}catch(Exception e){

			println(e.getStackTrace().toString())
		}
	}

	/*
	 * This keyword returns the row number of passed parameter keyword from excel
	 */
	@Keyword
	def getKeywordRowNum() {
		try{

			FileInputStream file = new FileInputStream (new File(GlobalVariable.TestResultFile))
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			println "Test Result File is: "+GlobalVariable.TestResultFile

			XSSFSheet sheet1 = workbook.getSheet("Test_Results");
			Map<String,Integer> getkeywordval  = new HashMap<>();
			int lastRowNum = sheet1.getLastRowNum();
			println("lastRowNum:"+lastRowNum)
			for(int i=1; i<=lastRowNum; i++){
				println("i:"+i +"-"+ sheet1.getRow(i).getCell(6).getStringCellValue().toString())
				getkeywordval.put(sheet1.getRow(i).getCell(6).getStringCellValue().toString(), i);
			}
			println("map getkeywordval:"+getkeywordval)
			file.close();
			return getkeywordval;  //Used 0 if No rows found with specific keyword

		}catch(Exception e){
			println(e.getStackTrace().toString())
		}
	}


	//	----------------------------------------------------------------------------------------------------------
	//	----------------------------------------------------------------------------------------------------------


	@Keyword
	def writeCSV(String csvPath) {


		FileWriter  pw = null;
		try {
			pw = new FileWriter (csvPath, true)
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		File newFile = new File(csvPath)
		if (newFile.length() != 0){
			pw.append('\n');
		}
		pw.append(String.valueOf(GlobalVariable.TC));
		pw.append('AAA');
		pw.append(GlobalVariable.TestResult);
		pw.append('AAA');
		pw.append(GlobalVariable.TestRemarks);
		pw.append('AAA');
		pw.append(String.valueOf(GlobalVariable.startTime));
		pw.append('AAA');
		pw.append(String.valueOf(GlobalVariable.endTime));
		pw.append('AAA');
		pw.append(String.valueOf(GlobalVariable.totalTime));

		pw.flush();
		pw.close();

	}

	@Keyword
	def CleanExisitingFiles()
	{
		//	Delete existing csv file.
		File csvFile = new File(GlobalVariable.csvPath);
		if(csvFile.delete())
		{
			System.out.println("CSV File deleted successfully");
		}
		else
		{
			System.out.println("Failed to delete CSV file");
		}

		File dir1 = new File(GlobalVariable.G_AppPath+"/Screenshot/Success");
		for (File file :  dir1.listFiles()){
			// folder.listFiles()){
			if (file.getName().endsWith(".png")){
				file.delete();
			}
		}
		File dir2 = new File(GlobalVariable.G_AppPath+"/Screenshot/Failure");
		for (File file :  dir2.listFiles()){
			// folder.listFiles()){
			if (file.getName().endsWith(".png")){
				file.delete();
			}
		}

	}

	@Keyword
	def csvToExcel(String csvPath){

		FileInputStream file = new FileInputStream (new File(GlobalVariable.TestResultFile))
		FileOutputStream outFile = null;
		XSSFWorkbook workbook = new XSSFWorkbook(file)
		XSSFSheet sheet3 = workbook.getSheet("Test_Results")
		//		int tcrow = sheet3.getLastRowNum()

		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);

		BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
		String row;
		while ((row = csvReader.readLine()) != null) {
			try{

				String[] data = row.split("AAA");

				int TC_ID = Integer.valueOf(data[0])
				String Test_Result = data[1]
				String Test_Remarks = data[2]
				String Start_Time = data[3]
				String End_Time = data[4]
				String Total_Time = data[5]

				XSSFCellStyle headerStyle = workbook.createCellStyle();
				Font headerFont = workbook.createFont();
				headerStyle.setBorderTop(BorderStyle.THIN);
				headerStyle.setBorderBottom(BorderStyle.THIN);
				headerStyle.setBorderLeft(BorderStyle.THIN);
				headerStyle.setBorderRight(BorderStyle.THIN);

				if(TC_ID != null){

					if (Test_Result == 'PASS'){

						headerFont.setColor(IndexedColors.GREEN.getIndex());
						headerStyle.setFont(headerFont);

					}else{

						headerFont.setColor(IndexedColors.RED.getIndex());
						headerStyle.setFont(headerFont);
					}

					sheet3.getRow(TC_ID).createCell(8).setCellValue(Test_Result)
					sheet3.getRow(TC_ID).createCell(9).setCellValue(Test_Remarks)
					sheet3.getRow(TC_ID).createCell(10).setCellValue(Start_Time)
					sheet3.getRow(TC_ID).createCell(11).setCellValue(End_Time)
					sheet3.getRow(TC_ID).createCell(12).setCellValue(Total_Time)
					sheet3.getRow(TC_ID).getCell(8).setCellStyle(headerStyle);
					sheet3.getRow(TC_ID).getCell(9).setCellStyle(style)
					sheet3.getRow(TC_ID).getCell(10).setCellStyle(style)
					sheet3.getRow(TC_ID).getCell(11).setCellStyle(style)
					sheet3.getRow(TC_ID).getCell(12).setCellStyle(style)
				}

			}catch(Exception e){

				//			Do nothing

			}

		}

		file.close();

		outFile = new FileOutputStream(new File(GlobalVariable.TestResultFile));
		workbook.setForceFormulaRecalculation(true);
		workbook.write(outFile);
		outFile.close();

		csvReader.close();
	}

	@Keyword
	def gettestdatafix() {
		try{

			FileInputStream file = new FileInputStream (new File(GlobalVariable.G_AppPath+"/Data Files/Saucedemo_TestResult.xlsx"))
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet1 = workbook.getSheetAt(0);

			GlobalVariable.OpenUrl = sheet1.getRow(1).getCell(0).getStringCellValue();
			println ("Application URL is: "+GlobalVariable.OpenUrl)
			file.close();
		}catch(Exception e){

			println(e.getStackTrace().toString())
		}
	}
}