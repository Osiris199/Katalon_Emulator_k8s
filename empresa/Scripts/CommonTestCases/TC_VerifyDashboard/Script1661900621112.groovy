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

'User Login Verification : '
if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 5, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 2,FailureHandling.OPTIONAL)
	KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
	Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.GetUserDetails'()
	//Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
	
} else if(Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error communicating with provider'), 1, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error'), 2, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
	WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
} else if(Mobile.waitForElementPresent(findTestObject('TokenImport/Dispositivo pendiente de activacin'), 5, FailureHandling.OPTIONAL)
	||Mobile.waitForElementPresent(findTestObject('TokenImport/Salir'), 1, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('TokenImport/Salir'), 3, FailureHandling.OPTIONAL)
	Mobile.delay(1)
	Mobile.tap(findTestObject('Authorize/Button - CERRAR SESIN'), 3, FailureHandling.OPTIONAL)
	KeywordUtil.markPassed('Token Import Required..!!!')
	
	Mobile.callTestCase(findTestCase('Test Cases/TC-TokenDigitalMigration/TC-TokenDigitalMigration'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
	
} else if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 25, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 2,FailureHandling.OPTIONAL)
	KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
	Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.GetUserDetails'()
	//Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
	
} else if(Mobile.waitForElementPresent(findTestObject('TokenImport/Dispositivo pendiente de activacin'), 5, FailureHandling.OPTIONAL)
	||Mobile.waitForElementPresent(findTestObject('TokenImport/Salir'), 3, FailureHandling.OPTIONAL)) {
	KeywordUtil.markPassed('Token Import Required..!!!')
	
	Mobile.callTestCase(findTestCase('Test Cases/TC-TokenDigitalMigration/TC-TokenDigitalMigration'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
//	if(GlobalVariable.TokenProcessStatus == '') {
//		Mobile.callTestCase(findTestCase('Test Cases/TC-TokenDigitalMigration/TC-TokenDigitalMigration'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
//	} else {
//		CustomKeywords.'helper.customfunction.Screenshot'()
//		Mobile.comment('Unable To Login - Token Not Imported Properly...!!!')
//		KeywordUtil.markFailedAndStop('Unable To Login - Token Not Imported Properly...!!!')
//	}
	
} else if(Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Continuar'), 3, FailureHandling.OPTIONAL)||
	Mobile.waitForElementPresent(findTestObject('RegistroDeDispositivo/Confirmar'), 3, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 3, FailureHandling.OPTIONAL)
	Mobile.tap(findTestObject('RegistroDeDispositivo/Confirmar'), 1, FailureHandling.OPTIONAL)
	Mobile.tap(findTestObject('Buttons/Si'), 2,FailureHandling.OPTIONAL)
	Mobile.tap(findTestObject('Buttons/No'), 2,FailureHandling.OPTIONAL)
	Mobile.delay(5)
	
	if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 60, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 2,FailureHandling.OPTIONAL)
		KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
		Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.GetUserDetails'()
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailedAndStop('User Logged In - Dashboard Accounts Not Available...!!!')
	}
	
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 10, FailureHandling.STOP_ON_FAILURE)) {

	CustomKeywords.'helper.customfunction.Screenshot'()
	
	KeywordUtil.markFailedAndStop('User Logged In - Dashboard Accounts Not Available...!!!')
	
} else if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 120, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 2,FailureHandling.OPTIONAL)
	KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
	Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.GetUserDetails'()
	//Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
	
} else {
	CustomKeywords.'helper.customfunction.Screenshot'()
	
	KeywordUtil.markFailedAndStop('User Logged In - Dashboard Accounts Not Available...!!!')
}
