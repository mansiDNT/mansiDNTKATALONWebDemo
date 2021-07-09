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

import internal.GlobalVariable

public class DateTime {

	@Keyword
	def startTime(){
		GlobalVariable.startTime = getCurrentTime()
		println (GlobalVariable.startTime)
	}

	@Keyword
	def endTime(){
		GlobalVariable.endTime = getCurrentTime()
		println (GlobalVariable.endTime)
	}

	@Keyword
	def totalTime(){
		Date time1=Date.parse("HH:mm:ss", GlobalVariable.startTime)
		def epoch_milis_start = time1.getTime()

		Date time2=Date.parse("HH:mm:ss", GlobalVariable.endTime)
		def epoch_milis_end = time2.getTime()

		String total = (epoch_milis_end - epoch_milis_start)/1000
		GlobalVariable.totalTime = total;
		println(GlobalVariable.totalTime)
		return total
	}

	@Keyword
	def getCurrentTime(){
		Date today = new Date()
		String nowTime = today.format('HH:mm:ss')
		return (nowTime)
	}

	@Keyword
	def getExecStartTime(){
		Date today = new Date()
		String ExecTime = today.format('[dd.MM.yyyy] [HH:mm]')
		GlobalVariable.ExecDateTime = ExecTime;
		return (ExecTime)
	}

	@Keyword
	def takeScreenshot(){
		Date today = new Date()
		String todaysDate = today.format('dd/MMMM/yyyy')
		String nowTime = today.format('HH:mm:ss' )
		String newDate = todaysDate.replaceAll('/', '_')
		String newTime = nowTime.replaceAll(':', '_')

		WebUI.takeScreenshot("Reports/Test_Result_"+newDate+" "+newTime+".png")
	}
}
