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
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

try {
CustomKeywords.'com.example.DateTime.startTime'()

println('product name' + GlobalVariable.TestData['ProductName'])
//search product
if (WebUI.verifyElementPresent(findTestObject('saucedemo_product/product'),15)) {
	
			GlobalVariable.vartestresult = 'PASS'
	
			GlobalVariable.varpassremarks = 'Product is available in the product list; Product Name is : ' + GlobalVariable.TestData['ProductName']
		
		}
		else {
			
			GlobalVariable.vartestresult = 'FAIL'
	
			GlobalVariable.varfailremarks = 'Product is not available in the list'
			
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