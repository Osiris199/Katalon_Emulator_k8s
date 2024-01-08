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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.annotation.Keyword as Keyword

if (Verification == 'NA') {
    KeywordUtil.markPassed('Database Connectivity Not Neccessary For TestCase ' + Description)
	
} else if ((((((Verification == 'Authorize') || (Verification == 'D-Authorize')) || (Verification == 'Reject')) || (Verification == 
'SelfAuthorize')) || (Verification == 'NoProcess')) || (Verification == 'ExceedOrigenAmount')|| (Verification == 'D-Reject')) {

    'CONNECTING DATABASE : 172.20.30.34 '
    CustomKeywords.'helper.functionsupdate.connectDB1'(host, db, port, user, pass)

	Mobile.delay(3)
	
    'CONNECTING DATABASE : 172.22.50.212 '
    CustomKeywords.'helper.functionsupdate.connectDB3'(user2, pass2)

	Mobile.delay(3)
	
	'CONNECTING DATABASE : 172.20.50.188 '
	CustomKeywords.'helper.functionsupdate.connectDB2'(host2, db1, port1, user1, pass1)
	
    'CLEAR PENDING PAYMENTS FROM AUTHORIZER :'
    CustomKeywords.'helper.functionsupdate.ClearAuthorization'()
}