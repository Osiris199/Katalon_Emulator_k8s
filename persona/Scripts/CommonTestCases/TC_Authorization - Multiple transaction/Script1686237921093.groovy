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
if(Mobile.waitForElementPresent(findTestObject('TokenImport/Su Token Digital se estar validando de manera automtica al confirmar la transaccin'), 15, FailureHandling.OPTIONAL)) {
} else {
	Mobile.setText(findTestObject('Authorize/Ingresa el cdigo de verificacin'), GlobalVariable.Token, 5, FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.HideKeyboard'()
}

if(Verification == 'Reject') {
	
} else {
	CustomKeywords.'helper.customfunction.Screenshot'()
	Mobile.delay(0.2)
}
Mobile.tap(findTestObject('Authorize/Confirmar'), 2, FailureHandling.OPTIONAL)

Mobile.delay(120)

AppiumDriver<?> driver = MobileDriverFactory.getDriver()

/*
if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizada'), 120, FailureHandling.OPTIONAL)) {
    //CustomKeywords.'helper.customfunction.Screenshot'()

    GlobalVariable.AuthorizationStatus = 'Passed'
	
    KeywordUtil.markPassed('Authorization Approved Is Successful...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Rechazado'), 15, FailureHandling.OPTIONAL)) {
   // CustomKeywords.'helper.customfunction.Screenshot'()

    GlobalVariable.AuthorizationStatus = 'Passed'

    KeywordUtil.markPassed('Authorization Reject Is Successful...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizada'), 5, FailureHandling.OPTIONAL)) {
    GlobalVariable.AuthorizationStatus = 'Passed'
	
    KeywordUtil.markPassed('Authorization Approved Is Successful...!!!')
	
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Rechazado'), 5, FailureHandling.OPTIONAL)) {
    GlobalVariable.AuthorizationStatus = 'Passed'
	
    KeywordUtil.markPassed('Authorization Reject Is Successful...!!!')
	
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/TRANSACCIN PROCESADA'), 15, FailureHandling.OPTIONAL)) {
    //CustomKeywords.'helper.customfunction.Screenshot'()

    GlobalVariable.AuthorizationStatus = 'Passed'

    KeywordUtil.markPassed('Authorization Process Is SelfAuthorize...!!!')

    Mobile.delay(3)
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/En proceso'), 3, FailureHandling.OPTIONAL)) {
   // CustomKeywords.'helper.customfunction.Screenshot'()

    KeywordUtil.markPassed('Authorization Process Is In Process...Waiting For 10 More Seconds...!!!')

    Mobile.delay(10)
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizada'), 3, FailureHandling.OPTIONAL)) {
    //CustomKeywords.'helper.customfunction.Screenshot'()

    GlobalVariable.AuthorizationStatus = 'Passed'

    KeywordUtil.markPassed('Authorization Approved Is Successful...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('Authorize/OCURRI UN ERROR AL REALIZAR LA AUTORIZACIN'), 3, FailureHandling.OPTIONAL)) {
    //CustomKeywords.'helper.customfunction.Screenshot'()

    GlobalVariable.AuthorizationStatus = 'Passed'

    KeywordUtil.markPassed('Authorization Process Failed - Error OCURRI UN ERROR AL REALIZAR LA AUTORIZACIN...!!!')
} else {
    GlobalVariable.AuthorizationStatus = 'Failed'

    //CustomKeywords.'helper.customfunction.Screenshot'()

    KeywordUtil.markFailed('Check ScreenShot - Error In Authorization ...!!!')
}
*/

try {
	Mobile.scrollToText("Autorizada")
	MobileElement ElementOne = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'Autorizada' or . = 'Autorizada')]"))
	if(ElementOne.displayed.TRUE) {
		 GlobalVariable.AuthorizationStatus = 'Passed'
		GlobalVariable.AuthorizePaymentMesg += "Autorizada"
	}
} catch(Exception e) {
	KeywordUtil.logInfo("Autorizada not present")
}

try {
	Mobile.scrollToText("Rechazado")
	MobileElement ElementTwo = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'Rechazado' or . = 'Rechazado')]"))
	if(ElementTwo.displayed.TRUE) {
		 GlobalVariable.AuthorizationStatus = 'Passed'
		GlobalVariable.AuthorizePaymentMesg += " / Rechazado "
	}
} catch(Exception er) {
	KeywordUtil.logInfo("Rechazado not present")
}

try {
	Mobile.scrollToText("OCURRIÓ UN ERROR AL REALIZAR LA AUTORIZACIÓN.")
	MobileElement ElementThree = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'OCURRIÓ UN ERROR AL REALIZAR LA AUTORIZACIÓN.' or . = 'OCURRIÓ UN ERROR AL REALIZAR LA AUTORIZACIÓN.')]"))
	if(ElementThree.displayed.TRUE) {
		GlobalVariable.AuthorizationStatus = 'Failed'
		GlobalVariable.AuthorizePaymentMesg += " / OCURRIÓ UN ERROR AL REALIZAR LA AUTORIZACIÓN. "
	}
} catch(Exception er) {
	KeywordUtil.logInfo("No OCURRIÓ UN ERROR AL REALIZAR LA AUTORIZACIÓN. present")
}

try {
	Mobile.scrollToText("En proceso")
	MobileElement ElementFour = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'En proceso' or . = 'En proceso')]"))
	if(ElementFour.displayed.TRUE) {
		GlobalVariable.AuthorizationStatus = 'Failed'
		GlobalVariable.AuthorizePaymentMesg += " / En proceso "
	}
} catch(Exception er) {
	KeywordUtil.logInfo("No En proceso present")
}

CustomKeywords.'helper.customfunction.Screenshot'()

Mobile.delay(2)