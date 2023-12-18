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

if (((((Verification == 'Authorize') || (Verification == 'D-Authorize')) || (Verification == 'Reject')) || (Verification == 
'SelfAuthorize')) || (Verification == 'NoProcess') || (Verification == 'D-Reject')) {
   // CustomKeywords.'helper.customfunction.Screenshot'()

    if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizaciones'), 10, FailureHandling.OPTIONAL)) {
        Mobile.tap(findTestObject('Authorize/Autorizaciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)

        Mobile.delay(5)

        if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizaciones-Title'), 60, FailureHandling.CONTINUE_ON_FAILURE)) {
            KeywordUtil.markPassed('Authorization Payment Screen Is Displayed...!!!')
        } else {
            Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 15, FailureHandling.CONTINUE_ON_FAILURE)

            Mobile.tap(findTestObject('Authorize/Autorizaciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)

            Mobile.delay(5)
        }
    } else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/HamburgerMenu'), 10, FailureHandling.OPTIONAL)) {
        Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.CONTINUE_ON_FAILURE)

        Mobile.tap(findTestObject('Authorize/Autorizaciones'), 5, FailureHandling.CONTINUE_ON_FAILURE)

        if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizaciones-Title'), 60, FailureHandling.CONTINUE_ON_FAILURE)) {
            KeywordUtil.markPassed('Authorization Payment Screen Is Displayed...!!!')
        } else {
            Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 15, FailureHandling.CONTINUE_ON_FAILURE)

            Mobile.tap(findTestObject('Authorize/Autorizaciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)

            Mobile.delay(5)
        }
    } else {
        CustomKeywords.'helper.customfunction.Screenshot'()

        KeywordUtil.markFailed('Authorization Menu Not Displayed...!!!')
    }
} else {
    println('No Verification Method Matched For Authorization...!!!')
}