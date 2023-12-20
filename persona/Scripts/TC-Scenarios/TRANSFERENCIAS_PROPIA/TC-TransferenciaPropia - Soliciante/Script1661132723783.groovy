import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import com.kms.katalon.core.mobile.keyword.internal.MobileAbstractKeyword as MobileAbstractKeyword
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory as MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper as MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException as WebElementNotFoundException
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.ui.SystemOutputInterceptor as SystemOutputInterceptor
import io.appium.java_client.AppiumDriver as AppiumDriver
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.StringUtils as StringUtils
import java.lang.Integer as Integer
import io.appium.java_client.MultiTouchAction as MultiTouchAction
import io.appium.java_client.TouchAction as TouchAction
import io.appium.java_client.touch.WaitOptions as WaitOptions
import io.appium.java_client.touch.offset.PointOption as PointOption
import io.appium.java_client.MobileElement as MobileElement
import io.appium.java_client.remote.AndroidMobileCapabilityType as AndroidMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType as MobileCapabilityType
import io.appium.java_client.remote.MobilePlatform as MobilePlatform
import org.openqa.selenium.remote.CapabilityType as CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities

String cmd = 'taskkill /im node.exe /f'
Runtime.getRuntime().exec(cmd)
Mobile.delay(2)

'TRANSFERENCIA PROPIA : '
WebUI.comment((ID + ' - ') + Description)

'CHECK DATABASE BALANCE : '
// PENDING
// GET FROM ACCOUNT UPDATED BALANCE FROM DATABASE AND SAVE IT GLOBAL VARIABLE'

'APPLICATION LAUNCH / USER LOGGED IN VERIFICATION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)

'USERLOGGED IN VERIFICATION : '
CustomKeywords.'helper.customfunction.QuickLogin'(RNC,Username,Password,Email,Role,Type)

'TAP ON HAMBURGER MENU : '
Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)

'SELECT TRANSACCIONES / AUTHORIZATION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_TransactionType'), [('Type') : Type], FailureHandling.CONTINUE_ON_FAILURE)

if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Origen-Title'), 10, FailureHandling.OPTIONAL)) {
	Mobile.takeScreenshot(FailureHandling.CONTINUE_ON_FAILURE)
	'ATTEMPT TRANSACTION PAYMENTS : '

	'SELECT ORIGEN ACCOUNT :'
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_OrigenAccount'), [('CtaOrigen') : CtaOrigen], FailureHandling.STOP_ON_FAILURE)

	'SELECT DESTINO ACCOUNT :'
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccount'), [('CtaDestino') : CtaDestino], FailureHandling.STOP_ON_FAILURE)

	'ENTER MONTO FOR PAYMENT :'
	WebUI.callTestCase(findTestCase('CommonTestCases/TC-Monto'), [('Monto') : Monto], FailureHandling.STOP_ON_FAILURE)

	Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 3, FailureHandling.OPTIONAL)
	
	'VERIFICATION SCENARIO TEST CASE:'
	CustomKeywords.'helper.paymenthelper.PaymentResponses'()
	
	'VERIFICATION CODIGO : '
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification'), [('Monto') : Type, ('Monto') : Password,('Description') : Description], FailureHandling.OPTIONAL)
} 

if (Verification == 'Authorize') {
	
	'AUTHORIZE USER LOG IN : '
	//WebUI.callTestCase(findTestCase('TC-Scenarios/TRANSFERENCIAS_PROPIA/TC-TransferenciaPropia - Authorize'), [('RNC') : RNC,('Username') : Username,('Password') : Password,('Email') : Email,('Role') : Role,('Verification') : Verification], FailureHandling.CONTINUE_ON_FAILURE)
	
	
	'SOLICIANTE USER LOG IN : '
	//CustomKeywords.'helper.customfunction.QuickLogin'(RNC,Username,Password,Email,Role,Type)
}


'CLOSING THE APPLICATION'
Mobile.closeApplication()