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
import javax.mail.*
import java.util.Properties
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.annotation.Keyword as Keyword

for (i =1 ; i<=2; i++) {
	Mobile.delay(5)
	try {
	@Grab(group='javax.mail', module='mail', version='1.4')
	def session = Session.getDefaultInstance(new Properties(["mail.store.protocol":"imaps", "mail.imaps.host":"imap.gmail.com", "mail.imaps.port":"993"]),null)
	def store = session.getStore("imaps")
	store.connect('imap.gmail.com', GlobalVariable.GmailUsername, GlobalVariable.GmailPassword)
	def folder = store.getFolder("INBOX")
	folder.open(Folder.READ_WRITE)
	String Mail
	folder.messages.each { msg -> println msg.content
	Mail = msg.content
	}
	
	println "MAIL IS : " + Mail
	String Otp = Mail.split('es: <strong>')[1].substring(0,6).trim()
	println "OTP For Token Digital Is :  " + Otp
	GlobalVariable.Otp = Otp
	break
	
}catch (Exception e) {
	
	KeywordUtil.markFailed('No Email Found - Try Again..!!!')
	println "No Email Found - Try Again"
}
}

