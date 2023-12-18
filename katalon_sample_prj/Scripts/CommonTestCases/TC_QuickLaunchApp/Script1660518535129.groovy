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

'APPLICATION LAUNCH : '
try {
	Mobile.startApplication(GlobalVariable.AppPath, false)
} catch (Exception e) {
	GlobalVariable.CaseStatusMessage += "startApplication failed to start App something went wrong...!!!"
	try {
		CustomKeywords.'helper.customfunction.Screenshot'()
	} catch (Exception er) {
		Mobile.takeScreenshot();
	}
}

'VERIFY USER IS LOGGED IN / LOGGED OUT : '
//if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Vista rpida'), 15, FailureHandling.OPTIONAL)) {
//    //CustomKeywords.'helper.customfunction.Screenshot'()
//	
//	Mobile.tap(findTestObject('Login/Acceder'), 1, FailureHandling.OPTIONAL)
//	
//    Mobile.tap(findTestObject('Buttons/ACCEDER'), 1, FailureHandling.OPTIONAL)
//	
//    KeywordUtil.markPassed('User Is Logged In Application Empressa...!!!')
//	
//	if (Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 15, FailureHandling.OPTIONAL)||
//		Mobile.waitForElementPresent(findTestObject('AppLaunch/TuBnco Empresas'), 5, FailureHandling.OPTIONAL)) {
//		// CustomKeywords.'helper.customfunction.Screenshot'()
//		 Mobile.delay(1)
//		 KeywordUtil.markPassed('User Is Not Logged In Application Empressa...!!!')
//	 }
//	
//}
if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TuBnco Empresas'), 500, FailureHandling.OPTIONAL)||
	Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 5, FailureHandling.OPTIONAL)) {
    KeywordUtil.markPassed('User Is Not Logged In Application Empressa...!!!')
	
} else if (Mobile.waitForElementPresent(findTestObject('ErrorMessage/UnhandledMFcode'), 5, FailureHandling.OPTIONAL)) {
	CustomKeywords.'helper.customfunction.Screenshot'()
	KeywordUtil.markFailedAndStop('ERROR CANNOT PROCEED FURTHER!')
	
} else if (Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 5, FailureHandling.OPTIONAL)||
	Mobile.waitForElementPresent(findTestObject('AppLaunch/TuBnco Empresas'), 20, FailureHandling.OPTIONAL)) {
    KeywordUtil.markPassed('User Is Not Logged In Application Empressa...!!!')
	
} else {
     try {
    	CustomKeywords.'helper.customfunction.Screenshot'()
	} catch (Exception e) {
		Mobile.takeScreenshot();
		GlobalVariable.CaseStatusMessage += "Login Screen Not Found - Waited For 60 Seconds - Please Check Once...!!!"
	}

	GlobalVariable.CaseStatusMessage += "Login Screen Not Found - Waited For 60 Seconds - Please Check Once...!!!" 
    KeywordUtil.markFailedAndStop('Login Screen Not Found - Waited For 60 Seconds - Please Check Once...!!!')
}