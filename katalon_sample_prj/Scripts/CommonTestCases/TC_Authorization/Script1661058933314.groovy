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

if(Mobile.waitForElementPresent(findTestObject('TokenImport/Su Token Digital se estar validando de manera automtica al confirmar la transaccin'), 15, FailureHandling.OPTIONAL)) {
} else {
	Mobile.setText(findTestObject('Authorize/Ingresa el cdigo de verificacin'), GlobalVariable.Token, 5, FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.HideKeyboard'()
}

if(Verification != 'Reject') {
	CustomKeywords.'helper.customfunction.Screenshot'()
	Mobile.delay(0.2)
} 

Mobile.tap(findTestObject('Authorize/Confirmar'), 2, FailureHandling.OPTIONAL)

if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizada'), 15, FailureHandling.OPTIONAL)) {
    GlobalVariable.AuthorizationStatus = 'Passed'
	
    KeywordUtil.markPassed('Authorization Approved Is Successful...!!!')
	
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/OCURRI UN ERROR AL REALIZAR LA AUTORIZACIN'), 2, FailureHandling.OPTIONAL)) {
	if (Verification == "NoProcess") {
		GlobalVariable.AuthorizationStatus = 'Passed'
	} else {
		GlobalVariable.AuthorizationStatus = 'Failed'
	}
    
    KeywordUtil.markPassed('Authorization Process Failed - Error OCURRI UN ERROR AL REALIZAR LA AUTORIZACIN...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Rechazado'), 15, FailureHandling.OPTIONAL)) {
    GlobalVariable.AuthorizationStatus = 'Passed'

    KeywordUtil.markPassed('Authorization Reject Is Successful...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/OCURRI UN ERROR AL REALIZAR LA AUTORIZACIN'), 2, FailureHandling.OPTIONAL)) {
	if (Verification == "NoProcess") {
		GlobalVariable.AuthorizationStatus = 'Passed'
	} else {
		GlobalVariable.AuthorizationStatus = 'Failed'
	}
    
    KeywordUtil.markPassed('Authorization Process Failed - Error OCURRI UN ERROR AL REALIZAR LA AUTORIZACIN...!!!')
	
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizada'), 10, FailureHandling.OPTIONAL)) {
    GlobalVariable.AuthorizationStatus = 'Passed'
	
    KeywordUtil.markPassed('Authorization Approved Is Successful...!!!')
	
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Rechazado'), 5, FailureHandling.OPTIONAL)) {
    GlobalVariable.AuthorizationStatus = 'Passed'
	
    KeywordUtil.markPassed('Authorization Reject Is Successful...!!!')
	
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizada'), 3, FailureHandling.OPTIONAL)) {
    GlobalVariable.AuthorizationStatus = 'Passed'

    KeywordUtil.markPassed('Authorization Approved Is Successful...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Rechazado'), 3, FailureHandling.OPTIONAL)) {
    GlobalVariable.AuthorizationStatus = 'Passed'
	
    KeywordUtil.markPassed('Authorization Reject Is Successful...!!!')
	
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/En proceso'), 2, FailureHandling.OPTIONAL)) {
   // CustomKeywords.'helper.customfunction.Screenshot'()

    KeywordUtil.markPassed('Authorization Process Is In Process...Waiting For 10 More Seconds...!!!')

    Mobile.delay(10)
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/OCURRI UN ERROR AL REALIZAR LA AUTORIZACIN'), 2, FailureHandling.OPTIONAL)) {
	if (Verification == "NoProcess") {
		GlobalVariable.AuthorizationStatus = 'Passed'
	} else {
		GlobalVariable.AuthorizationStatus = 'Failed'
	}
    
    KeywordUtil.markPassed('Authorization Process Failed - Error OCURRI UN ERROR AL REALIZAR LA AUTORIZACIN...!!!')
}

/* else if (Mobile.waitForElementPresent(findTestObject('Authorize/TRANSACCIN PROCESADA'), 15, FailureHandling.OPTIONAL)) {
    //CustomKeywords.'helper.customfunction.Screenshot'()

    GlobalVariable.AuthorizationStatus = 'Passed'

    KeywordUtil.markPassed('Authorization Process Is SelfAuthorize...!!!')

    Mobile.delay(3)
}*/

else {
    GlobalVariable.AuthorizationStatus = 'Failed'
    //KeywordUtil.markFailed('Check ScreenShot - Error In Authorization ...!!!')
}

CustomKeywords.'helper.customfunction.Screenshot'()

GlobalVariable.AuthorizePaymentMesg = Mobile.getText(findTestObject('Authorize/Authorize-PaymentMessage'),2, FailureHandling.OPTIONAL)