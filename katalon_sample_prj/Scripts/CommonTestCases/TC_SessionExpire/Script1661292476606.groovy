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

SessionExpirePrerequisite()

def SessionExpirePrerequisite() {
	CustomKeywords.'alerthandling.alerthandling.RelaunchApp'()
	
	if (Mobile.waitForElementPresent(findTestObject('Dashboard/Resumen de productos'),10, FailureHandling.CONTINUE_ON_FAILURE)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markPassed('User Is On Account Dashboard Screen...!!!')
		Mobile.delay(320)
		SessionExpireVerification()
		
	} else if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Vista rpida'),10, FailureHandling.CONTINUE_ON_FAILURE)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markPassed('User Can View Quick Access View...!!!')
		
		Mobile.tap(findTestObject('Object Repository/Dashboard/ACCEDER'), 5, FailureHandling.OPTIONAL)
		Mobile.setText(findTestObject('Object Repository/Dashboard/CONTRASEA'), GlobalVariable.Correct_Contrasena, 5)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
		CustomKeywords.'alerthandling.alerthandling.Continuar'()
		Mobile.delay(5)
		Mobile.delay(320)
		SessionExpireVerification()
		
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.delay(3)
		KeywordUtil.markFailedAndStop('User Is Not On Account Dashboard Screen. Please Check...!!!')
	}
}

def SessionExpireVerification() {
	Mobile.startApplication(GlobalVariable.AppPath, false)
	Mobile.delay(5)
	
	if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Vista rpida'),10, FailureHandling.CONTINUE_ON_FAILURE)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markPassed('User Can View Quick Access View - Session Expire Verification Success...!!!')
				
	} else if (Mobile.waitForElementPresent(findTestObject('Dashboard/Resumen de productos'),10, FailureHandling.CONTINUE_ON_FAILURE)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markPassed('User Can View Dashboard Access View - Session Expire Verification Success...!!!')

	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.delay(3)
		KeywordUtil.markFailedAndStop('User Cannot View Quick Access View - Session Expire Verification Failed...!!!')
	}
}
