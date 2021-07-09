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
def count;
	CustomKeywords.'com.example.DateTime.startTime'()
	
	//scroll to add to cart icon
	WebUI.scrollToElement(findTestObject('saucedemo_product/add_to_cart_icon'),15)
	
	//get count of items added to cart before anything added
	if (WebUI.verifyElementNotPresent(findTestObject('saucedemo_product/count_of_added_items'), 30))
	{
		 count =0;
		println('initial count : ' + count)
	}
	else {
	def countstring = WebUI.getText(findTestObject('saucedemo_product/count_of_added_items'))
	
	  count = countstring as Integer
	
	println('initial count : ' + count)
	}
	//scroll to the product
	WebUI.scrollToElement(findTestObject('saucedemo_product/product'),15)
	
	//click on add to cart button at product
	WebUI.click(findTestObject('saucedemo_product/add_to_cart_button'))
	
	//scroll to add to cart icon
	WebUI.scrollToElement(findTestObject('saucedemo_product/add_to_cart_icon'),15)
	
	//get count of items added to cart after item added to cart
	def countstring_1 = WebUI.getText(findTestObject('saucedemo_product/count_of_added_items'))
	
	Integer count_new = countstring_1 as Integer
	
	println('initial count : ' + count_new)
	
	if (count_new == (count+1)) {
				GlobalVariable.vartestresult = 'PASS'
		
				GlobalVariable.varpassremarks = 'Product is added to cart successfully'
			
			}
			else {
		
				GlobalVariable.vartestresult = 'FAIL'
				
				GlobalVariable.varfailremarks = 'Product is not added to cart successfully'
				
			
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