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

WebUI.callTestCase(findTestCase('CommonTestCases/TC_KillPreviousSession'), [:], FailureHandling.OPTIONAL)

'APPLICATION LAUNCH : '
Mobile.startApplication(AppPath, true)

WebUI.comment((ID + ' - ') + Description)

if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TuBnco Empresas'), 60, FailureHandling.OPTIONAL)) {
    //CustomKeywords.'helper.customfunction.Screenshot'()

    //'SIB VERIFICATION : '
    //CustomKeywords.'helper.customfunction.SIB'(SIB)
} else {
    CustomKeywords.'helper.customfunction.LoginResponses'()

    CustomKeywords.'helper.customfunction.Screenshot'()

    KeywordUtil.markFailedAndStop('Application Launch Failed After Waiting 60 Seconds...Please Check Screenshot...!!!')
}