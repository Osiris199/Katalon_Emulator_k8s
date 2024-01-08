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

if (Monto == '') {
    KeywordUtil.markPassed('Skipped NonPayment Flows ' + Description)
	CustomKeywords.'helper.customfunction.continuar'()
} else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
	GlobalVariable.origen_before_app = "SelectionFailed"
    println('Origen Account Not Available - Skipping Codigo Verification Flow...!!!')
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
	GlobalVariable.destino_before_app = "SelectionFailed Account or Beneficiary not visible"
    println('Destino Account Not Available - Skipping Codigo Verification Flow...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('TokenImport/Su Token Digital se estar validando de manera automtica al confirmar la transaccin'), 10, FailureHandling.OPTIONAL)) {
    println('Token Imported - Skipping Codigo Verification Flow...!!!')
	CustomKeywords.'helper.customfunction.Screenshot'()
	Mobile.delay(0.2)
	CustomKeywords.'helper.customfunction.continuar'()
	Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
	Mobile.delay(5)
	PaymentVerification()
	
} else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Ingresa el cdigo de verificacin'), 40, FailureHandling.OPTIONAL)) {
		
    Mobile.setText(findTestObject('PagosYTransferencia/Ingresa el cdigo de verificacin'), GlobalVariable.Token, 2, FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.HideKeyboard'()
	CustomKeywords.'helper.customfunction.Screenshot'()
    'CLICK ON CONTINUAR BUTTON:'
	CustomKeywords.'helper.customfunction.continuar'()
	Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
	PaymentVerification()
	
} else if(Mobile.waitForElementPresent(findTestObject('Buttons/Confirmar- 2'), 5, FailureHandling.OPTIONAL)) {
	 
	 CustomKeywords.'helper.customfunction.swipeUP'()
	 
	 if (Mobile.waitForElementPresent(findTestObject('TokenImport/Su Token Digital se estar validando de manera automtica al confirmar la transaccin'), 10, FailureHandling.OPTIONAL)) {
		 println('Token Imported - Skipping Codigo Verification Flow...!!!')
		 CustomKeywords.'helper.customfunction.Screenshot'()
		 Mobile.delay(0.2)
		 CustomKeywords.'helper.customfunction.continuar'()
		 Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
		 Mobile.delay(5)
		 PaymentVerification()
	 } else {
		  KeywordUtil.markPassed('Codigo Is Not Necessary For Test Case...!!!')	  
		  Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
		  Mobile.delay(2)
		  Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
		  PaymentVerification()
	  }
}

def PaymentVerification() {
	if(Mobile.verifyElementVisible(findTestObject('MultipleTransactions/Completado screen button'), 500, FailureHandling.OPTIONAL)) {
		'PAYMENT TRANSACTION VERIFICATON : '
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		try {
			Mobile.scrollToText("Sometida. Pendiente de autorización", FailureHandling.OPTIONAL)
			MobileElement ElementOne = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'Sometida. Pendiente de autorización' or . = 'Sometida. Pendiente de autorización')]"))
			if(ElementOne.displayed.TRUE) {
				GlobalVariable.PaymentStatus = "Passed"
				GlobalVariable.PaymentMessageMultiple = "Sometida. Pendiente de autorización"
			}
		} catch(Exception e) {
			try {
				Mobile.scrollToText("Sometida. Pendiente de autorizacion", FailureHandling.OPTIONAL)
				MobileElement ElementTwo = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'Sometida. Pendiente de autorizacion' or . = 'Sometida. Pendiente de autorizacion')]"))
				if(ElementTwo.displayed.TRUE) {
					GlobalVariable.PaymentStatus = "Passed"
					GlobalVariable.PaymentMessageMultiple += "Sometida. Pendiente de autorizacion"
				}
			} catch(Exception er) {
				GlobalVariable.PaymentStatus = "Passed"
				KeywordUtil.logInfo("Sometida. Pendiente de autorización or Sometida. Pendiente de autorizacion !!!")
			}
		}
		
		try {
			Mobile.scrollToText("TRANSACCION NO PROCESADA", FailureHandling.OPTIONAL)
			MobileElement ElementThree = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'TRANSACCION NO PROCESADA' or . = 'TRANSACCION NO PROCESADA')]"))
			if(ElementThree.displayed.TRUE) {
				GlobalVariable.PaymentStatus = "Passed"
				GlobalVariable.PaymentMessageMultiple += " / TRANSACCION NO PROCESADA "
			}
		} catch(Exception e) {
			try {
				Mobile.scrollToText("TRANSACCIÓN NO PROCESADA", FailureHandling.OPTIONAL)
				MobileElement ElementFour = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = 'TRANSACCIÓN NO PROCESADA' or . = 'TRANSACCIÓN NO PROCESADA')]"))
				if(ElementFour.displayed.TRUE) {
					GlobalVariable.PaymentStatus = "Passed"
					GlobalVariable.PaymentMessageMultiple += "/ TRANSACCIÓN NO PROCESADA "
				}
			} catch(Exception er) {
				GlobalVariable.PaymentStatus = "Passed"
				KeywordUtil.logInfo("TRANSACCIÓN NO PROCESADA or TRANSACCION NO PROCESADA not found !!!")
			}
		}
		
		if(Mobile.verifyElementVisible(findTestObject('ErrorMessage/esta transaccin no es vlida, por favor intntalo de nuevo'), 2, FailureHandling.OPTIONAL) || Mobile.verifyElementVisible(findTestObject('ErrorMessage/esta transferencia no es vlida, por favor intntalo de nuevo'), 2, FailureHandling.OPTIONAL)) {
			GlobalVariable.PaymentStatus = "Failed"
			GlobalVariable.PaymentMessageMultiple += " / esta transacción or transferencia no es válida, por favor inténtalo de nuevo."
		}
		
		if(Mobile.verifyElementVisible(findTestObject('ErrorMessage/producto destino no vlido'), 2, FailureHandling.OPTIONAL)) {
			GlobalVariable.PaymentStatus = "Failed"
			GlobalVariable.PaymentMessageMultiple += " / producto destino no válido"
		}
		
		if(Mobile.verifyElementVisible(findTestObject('ErrorMessage/operador no autorizado'), 2, FailureHandling.OPTIONAL)) {
			GlobalVariable.PaymentStatus = "Failed"
			GlobalVariable.PaymentMessageMultiple += " / operador no autorizado"
		}
		
		GlobalVariable.AppPaymentMessage = GlobalVariable.PaymentMessageMultiple
		
		CustomKeywords.'helper.customfunction.Screenshot'()
	} else {
		Mobile.delay(1)
		CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.PaymentStatus = "Failed"
		println('Non Payment Transaction Scenario - So Verification Skipped...!!!')
	}
}