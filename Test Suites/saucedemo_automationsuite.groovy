import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.annotation.SetUp as SetUp
import com.kms.katalon.core.annotation.SetupTestCase as SetupTestCase
import com.kms.katalon.core.annotation.TearDown as TearDown
import com.kms.katalon.core.annotation.TearDownTestCase as TearDownTestCase
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.context.TestCaseContext as TestCaseContext
import java.beans.Customizer as Customizer
import java.io.FileInputStream as FileInputStream
import java.io.FileNotFoundException as FileNotFoundException
import java.io.IOException as IOException
import java.util.Date as Date
import org.apache.poi.xssf.usermodel.XSSFCell as XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow as XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet as XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook as XSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFCellStyle as XSSFCellStyle
import org.apache.poi.ss.usermodel.FillPatternType as FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors as IndexedColors
import org.apache.poi.ss.usermodel.Font as Font
import org.apache.poi.ss.usermodel.CellStyle as CellStyle
import org.apache.poi.ss.usermodel.BorderStyle as BorderStyle
//import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import com.kms.katalon.core.util.internal.PathUtil as PathUtil
import org.openqa.selenium.WebElement as WebElement

// Please change skipped to be false to activate this method.
// Put your code here.
/**
* Clean test suites environment.
*/
// Please change skipped to be false to activate this method.
//CustomKeywords.'com.example.WebUICustomKeywords.csvToExcel'(GlobalVariable.csvPath)
//CustomKeywords.'com.application.EmailwithHTML.sendemail'()
/**
* Run before each test case starts.
*/
// Please change skipped to be false to activate this method.
/**
 * Run after each test case ends.
 */
// Please change skipped to be false to activate this method.
//Used 0 if No rows found with specific keyword
//	Write into CSV file.

@SetUp(skipped = false)
def setUp() {
    'Get execution date and time'
    CustomKeywords.'com.example.DateTime.getExecStartTime'()

    GlobalVariable.Phase = 'Phase1'

    GlobalVariable.G_AppPath = RunConfiguration.getProjectDir()

    GlobalVariable.TestResultFile = (GlobalVariable.G_AppPath + '/Data Files/Saucedemo_TestResult.xlsx')

    GlobalVariable.TestDataFile = (GlobalVariable.G_AppPath + '/Data Files/Saucedemo_TestData.xlsx')

    GlobalVariable.csvPath = (GlobalVariable.G_AppPath + '/Data Files/phase1_dump.csv')

    'Call Fix Test Data'
   //CustomKeywords.'com.example.DPOSCustomKeyword.gettestdata'()

    'Clean exisitng files'
    CustomKeywords.'com.example.WebCustomKeyword.CleanExisitingFiles'()

    'load testdata'
    CustomKeywords.'com.example.WebCustomKeyword.gettestdata'()

    GlobalVariable.TC = 0

    GlobalVariable.GetKeyword = CustomKeywords.'com.example.WebCustomKeyword.getKeywordRowNum'();

    println('GlobalVariable.GetKeyword map: ' + GlobalVariable.GetKeyword)
}

@TearDown(skipped = false)
def tearDown() {
    CustomKeywords.'com.example.WebCustomKeyword.csvToExcel'(GlobalVariable.csvPath)

    'Used below commented for sending a email to client'
	//CustomKeywords.'com.example.EmailwithHTML.sendemail'()
	
}

@SetupTestCase(skipped = false)
def setupTestCase() {
    GlobalVariable.TC = (GlobalVariable.GetKeyword[GlobalVariable.CurrentTestCaseName])
	
	println('tc name:' + GlobalVariable.CurrentTestCaseName)
    println('GlobalVariable.TC:' + GlobalVariable.TC)

    GlobalVariable.TestCaseID = ('TC_' + GlobalVariable.TC)
}

@TearDownTestCase(skipped = false)
def tearDownTestCase() {
    println(GlobalVariable.CurrentTestCaseName)

    if (GlobalVariable.TC != 0) {
        CustomKeywords.'com.example.WebCustomKeyword.writeCSV'(GlobalVariable.csvPath)
    } else {
        println('*****************<<<<<<<<<<<<<<<<<<<<< NO ROW FOUND WITH THAT SPECIFIC KEYWORD >>>>>>>>>>>>>>>*******************')
    }
}

