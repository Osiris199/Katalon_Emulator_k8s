import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import static io.appium.java_client.touch.TapOptions.tapOptions
import static io.appium.java_client.touch.WaitOptions.waitOptions
import static io.appium.java_client.touch.offset.ElementOption.element
import static io.appium.java_client.touch.offset.PointOption.point
import static java.time.Duration.ofMillis
import static java.time.Duration.ofSeconds
import io.appium.java_client.android.AndroidDriver as AndroidDriver
import io.appium.java_client.android.AndroidElement as AndroidElement
import org.openqa.selenium.Dimension as Dimension
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.util.internal.PathUtil as PathUtil

//'APPLICATION LAUNCH FOR AUTHORIZATION : '
//WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)

'USERLOGGED IN VERIFICATION : '
CustomKeywords.'helper.customfunction.QuickLoginSwitch'(RNC, Username1, Username2, Username3, Password, Email, Role, Verification, Type)
Mobile.delay(2)

'TAP ON HAMBURGER MENU : '
Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 2, FailureHandling.CONTINUE_ON_FAILURE)

'SELECT AUTHORIZATION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_AuthorizationType'), [('Verification') : Verification], FailureHandling.CONTINUE_ON_FAILURE)

//if (Mobile.waitForElementPresent(findTestObject('Authorize/Autorizaciones'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
//    Mobile.tap(findTestObject('Authorize/Autorizaciones'), 2, FailureHandling.CONTINUE_ON_FAILURE)
//
//    Mobile.delay(2)
//} else {
//    Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 2, FailureHandling.CONTINUE_ON_FAILURE)
//
//    Mobile.tap(findTestObject('Authorize/Autorizaciones'), 2, FailureHandling.CONTINUE_ON_FAILURE)
//
//    Mobile.delay(2)
//}

if (Mobile.waitForElementPresent(findTestObject('Authorize/No hay autorizaciones pendientes'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
    CustomKeywords.'helper.customfunction.Screenshot'()
    GlobalVariable.AuthorizePaymentMesg = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'),2, FailureHandling.OPTIONAL)
    println('No Payments Available For Authorization Or Rejection...!!!')
} else {
    println('Payments Available For Authorization Or Rejection...!!!')

    if(Verification == 'Reject') {
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.delay(0.2)
	}

    Mobile.tap(findTestObject('Authorize/android.view.ViewGroup'), 2, FailureHandling.OPTIONAL)
	
    if (Mobile.waitForElementPresent(findTestObject('Authorize/Aprobar'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
        //CustomKeywords.'helper.customfunction.Screenshot'()

        println('Pending Payment Selected...!!!')
    } else {
        //CustomKeywords.'helper.customfunction.Screenshot'()

        Mobile.tap(findTestObject('Authorize/PaymentOption1'), 2, FailureHandling.OPTIONAL)

        if (Mobile.waitForElementPresent(findTestObject('Authorize/Aprobar'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
           //CustomKeywords.'helper.customfunction.Screenshot'()

            println('Pending Payment Selected...!!!')
        } else {
           //CustomKeywords.'helper.customfunction.Screenshot'()

            Mobile.tap(findTestObject('Authorize/PaymentOption2'), 2, FailureHandling.OPTIONAL)

            if (Mobile.waitForElementPresent(findTestObject('Authorize/Aprobar'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
               //CustomKeywords.'helper.customfunction.Screenshot'()

                println('Pending Payment Selected...!!!')
            } else {
               //CustomKeywords.'helper.customfunction.Screenshot'()

                Mobile.tap(findTestObject('Authorize/RadioButton-1'), 2, FailureHandling.OPTIONAL)

                if (Mobile.waitForElementPresent(findTestObject('Authorize/Aprobar'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
                   //CustomKeywords.'helper.customfunction.Screenshot'()

                    println('Pending Payment Selected...!!!')
                } else {
                    //CustomKeywords.'helper.customfunction.Screenshot'()

                    AppiumDriver<?> driver = MobileDriverFactory.getDriver()

                    MobileElement Element = driver.findElement(By.xpath(((('//*[@class = \'android.widget.TextView\' and (@text = \'' + Concept) + '\' or . = \'') + Concept) + '\')]'))

                    Element.click()

                    if (Mobile.waitForElementPresent(findTestObject('Authorize/Aprobar'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
                        //CustomKeywords.'helper.customfunction.Screenshot'()

                        println('Pending Payment Selected...!!!')
                    } else {
                       //CustomKeywords.'helper.customfunction.Screenshot'()

                        Mobile.tap(findTestObject('Authorize/Origen-1'), 2, FailureHandling.CONTINUE_ON_FAILURE)
                    }
                }
            }
        }
    }
    
    'PAYMENT TO BE : ' + Verification

    if ((Verification == 'Authorize') || (Verification == 'D-Authorize')) {
        Mobile.tap(findTestObject('Authorize/Aprobar'), 2, FailureHandling.CONTINUE_ON_FAILURE)
        //CustomKeywords.'helper.customfunction.Screenshot'()
    } else if (Verification == 'D-Reject') {
		if(GlobalVariable.Counter == "Authorize-1") {
			Mobile.tap(findTestObject('Authorize/Aprobar'), 2, FailureHandling.CONTINUE_ON_FAILURE)
		} else if(GlobalVariable.Counter == "Authorize-2"){
			Mobile.tap(findTestObject('Authorize/Rechazar'), 2, FailureHandling.CONTINUE_ON_FAILURE)
		}       
        //CustomKeywords.'helper.customfunction.Screenshot'()
    } else if (Verification == 'Reject') {
        Mobile.tap(findTestObject('Authorize/Rechazar'), 2, FailureHandling.CONTINUE_ON_FAILURE)
        //CustomKeywords.'helper.customfunction.Screenshot'()
    }
    'VALIDATE AUTHORIZATION PROCESS: '
    WebUI.callTestCase(findTestCase('CommonTestCases/TC_Authorization'), [('Password') : Password, ('Verification') : Verification], FailureHandling.CONTINUE_ON_FAILURE)
}

//'CLOSING THE APPLICATION'
//Mobile.closeApplication()