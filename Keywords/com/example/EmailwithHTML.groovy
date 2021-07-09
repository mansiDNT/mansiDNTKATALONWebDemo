package com.example
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.google.api.client.util.IOUtils
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
import java.util.Properties
import javax.activation.DataSource
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.Session
import javax.mail.internet.MimeBodyPart

import java.io.FileInputStream
import java.io.FileOutputStream
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.lang.String;

public class EmailwithHTML {
	@Keyword
	def sendemail(String [] args) {
		String to = GlobalVariable.TestData["EmailTo"] ;
		//"hasti.buch@dntinfotech.com,shrikant.khairnar@dntinfotech.com,tapan.shah@dntinfotech.com,bhavesh@ebusinessguru.co.uk,jay.pathak@ebusinessguru.co.uk,nilesh@avasam.com"
		String subject = "[Avasam Automation]"+ GlobalVariable.ExecDateTime
		final String from =GlobalVariable.TestData["EmailFrom"] ;
		final  String password =GlobalVariable.TestData["EmailPassword"] ;

		String body = testingHTML();
		Properties props = new Properties();

		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from,password); } });
		try {
			// Create a default MimeMessage object.
			//session.setDebug(true);
			Transport transport = session.getTransport();
			InternetAddress addressFrom = new InternetAddress(from);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(addressFrom);
			message.setSubject(subject);
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Now set the actual message
			//messageBodyPart.setText(body);
			messageBodyPart.setContent(body, "text/html;charset=UTF-8");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			// String fileAttachment = GlobalVariable.G_AppPath+"/Data Files/DemoMobile_TestResults.xlsx";
			String fileAttachment = GlobalVariable.G_AppPath+"/Data Files/Avasam_TestResult.xlsx";
			println (fileAttachment)
			DataSource source = new FileDataSource(fileAttachment);
			String filename = new File(fileAttachment).getName();
			println filename
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			InternetAddress[] iAdressArray = InternetAddress.parse(to);
			//InternetAddress[] iAdressArray1 = InternetAddress.parse(cc);
			message.setRecipients(Message.RecipientType.TO, iAdressArray);
			//message.setRecipients(Message.RecipientType.CC, iAdressArray1);
			transport.connect();
			Transport.send(message);
			transport.close();
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace(); } }

	public static FileOutputStream outputFile;
	static FileInputStream file;
	public static final String TEST_CASES = GlobalVariable.G_AppPath+"/Data Files/Avasam_TestResult.xlsx";
	int rowCount;
	String xl = TEST_CASES;
	String sheet = "Summary";

	@Keyword
	def testingHTML() {


		String th="border: 1px solid #ddd;padding: 8px;padding-top: 12px;padding-bottom: 12px;text-align: left;background-color: #5b9bd5;color: white;";
		StringBuffer pw = new StringBuffer();
		//PrintWriter pw = new PrintWriter(new FileWriter("E:\\Katalon_project\\Katalon_mobile\\Demo\\Data Files\\TestReport.html"));
		pw.append("<html><body>Dear Team,")
		pw.append(
				"<h4>Please find the attached test result for the last web testing on QA environment.</h4>");

		pw.append(
				"<h4>Avasam test execution report summary:</h4>");


		pw.append("<head><TABLE style='font-family:Georgia, serif;border-collapse: collapse;width:80%;border:1px solid #ddd;padding:8px;'><TR style='background-color: #ddd';><TH style='"+th+"'>Module Name<TH style='"+th+"'>No Of Test Cases<TH style='"+th+"'>Pass Count<TH style='"+th+"'>Fail Count</TR>");


		rowCount = getRowCount(xl, sheet)
		System.out.println(rowCount);

		String css = "background-color: #f2f2f2;";
		String td="border: 1px solid #ddd;padding: 8px;";

		for (int i = 1; i <= rowCount; i++) {
			String Module_Name = getCellValue(xl, sheet, i, 0);
			System.out.println(Module_Name);
			String NoOfTestCases = getCellValue(xl, sheet, i, 1);
			String PassCount = getCellValue(xl, sheet, i, 2);
			println(PassCount);
			String FailCount = getCellValue(xl, sheet, i, 3);

			pw.append("<TR style='"+css+"'><TD style='"+td+"'>" + Module_Name + "<TD style='"+td+"'>" + NoOfTestCases + "<TD style='"+td+"'>" + PassCount + "<TD style='"+td+"'>" + FailCount);

		}

		pw.append("</TABLE></head><BR/>Regards,<BR/>Automation Test CoE</body></html>");
		//pw.close();
		return pw;

	}

	@Keyword
	def getCellValue(String xl, String sheet, int row, int cell) {

		def val = "";
		try {
			FileInputStream file = new FileInputStream (xl);
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFCell cell1 = workbook.getSheet(sheet).getRow(row).getCell(cell);

			switch(cell1.getCellType()){
				case XSSFCell.CELL_TYPE_NUMERIC:
					val = cell1.getNumericCellValue() + "";
					break;
				case XSSFCell.CELL_TYPE_STRING:
					val = cell1.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_FORMULA:
					XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
					val = cell1.getNumericCellValue() + "";
					return val.substring(0, val.indexOf('.'));
					file.close();

					break;

			}
		}catch(Exception e){

			println(e.getStackTrace().toString())
		}
		return val;

	}

	@Keyword
	def getRowCount(String xl, String sheet) {

		try {
			FileInputStream files = new FileInputStream(xl);
			XSSFWorkbook workbook = new XSSFWorkbook(files);
			return workbook.getSheet(sheet).getLastRowNum();
		} catch (Exception e) {
			return 0;
		}
	}

	@Keyword
	def getRowCount(String sheet, int row, int cell, String result) {
		FileInputStream file = new FileInputStream(GlobalVariable.G_AppPath+"/Data Files/Avasam_TestResult.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheets = workbook.getSheet(sheet);

		//Uncomment this code
		FileOutputStream output = new FileOutputStream(GlobalVariable.G_AppPath+"/Data Files/Avasam_TestResult.xlsx");
		sheets.getRow(row).createCell(cell).setCellValue(result);
		workbook.setForceFormulaRecalculation(true);

		workbook.write(output);
		output.close();
	}
}