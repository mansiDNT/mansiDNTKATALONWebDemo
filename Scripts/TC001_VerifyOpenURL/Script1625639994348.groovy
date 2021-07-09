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
import java.lang.String as String

try {
    CustomKeywords.'com.example.DateTime.startTime'()

    // Open Browser & URL
    WebUI.openBrowser(GlobalVariable.TestData['OpenUrl'])

    WebUI.maximizeWindow()

	if(WebUI.verifyElementPresent(findTestObject('saucedemo_loginpage/swag_labs'),15)) {
		
	//	WebUI.click(findTestObject('avasam/Page_Login/a_Accept'))
		url = WebUI.getUrl()
}

    if (url == (GlobalVariable.TestData['OpenUrl'])) {

        GlobalVariable.vartestresult = 'PASS'

        GlobalVariable.varpassremarks = 'Home page for saucedemo open successfully and url is: '+url
    } else {

        GlobalVariable.vartestresult = 'FAIL'

        GlobalVariable.varfailremarks = 'Home page for saucedemo not loaded...'

        WebUI.back()
    }
    
    CustomKeywords.'com.example.WebCustomKeyword.settestresult'(GlobalVariable.vartestresult, GlobalVariable.varpassremarks, 
        GlobalVariable.varfailremarks)

    CustomKeywords.'com.example.DateTime.endTime'()

    CustomKeywords.'com.example.DateTime.totalTime'()
}
catch (Exception e) {
    println(e)

    GlobalVariable.vartestresult = 'FAIL'

    GlobalVariable.varfailremarks = ('Exception: ' + e.getStackTrace().toString())

    CustomKeywords.'com.example.WebCustomKeyword.settestresult'(GlobalVariable.vartestresult, GlobalVariable.varpassremarks, 
        GlobalVariable.varfailremarks)

    CustomKeywords.'com.example.DateTime.endTime'()

    CustomKeywords.'com.example.DateTime.totalTime'()
}