import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW

try {
    CustomKeywords.'com.example.DateTime.startTime'()
	  //Enter invalid UserName
    WebUI.setText(findTestObject('saucedemo_loginpage/username'), GlobalVariable.TestData['InValidUserName'])

    //Enter invalid Password	
    WebUI.setText(findTestObject('saucedemo_loginpage/password'), GlobalVariable.TestData['InvalidPassword'])
		
    //click on Log in button
    WebUI.click(findTestObject('saucedemo_loginpage/login_button'))
	
	
	String message=WebUI.getText(findTestObject('saucedemo_loginpage/error_message'))
	println('error message' + message )
	
	  if (message == 'Epic sadface: Username and password do not match any user in this service') {

        GlobalVariable.vartestresult = 'PASS'

        GlobalVariable.varpassremarks = 'Error Message displayed successfully and error message is: ' +message
    } 
	else {

        GlobalVariable.vartestresult = 'FAIL'

        GlobalVariable.varfailremarks = 'Error Message for invalid password is not displayed.'

        WebUI.back()
    }
    
    CustomKeywords.'com.example.WebCustomKeyword.settestresult'(GlobalVariable.vartestresult, GlobalVariable.varpassremarks, 
        GlobalVariable.varfailremarks)

    CustomKeywords.'com.example.DateTime.endTime'()

    CustomKeywords.'com.example.DateTime.totalTime'()
}
catch (Exception e) {
    CustomKeywords.'com.example.DateTime.endTime'()

    CustomKeywords.'com.example.DateTime.totalTime'()
}