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
	CustomKeywords.'helper.customfunction.Screenshot'()
	//CustomKeywords.'helper.customfunction.continuar'()
	PrintResults()
	
} else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
    GlobalVariable.origen_before_app = "SelectionFailed"
    println('Origen Account Not Available - Skipping Codigo Verification Flow...!!!')
	//CustomKeywords.'helper.customfunction.Screenshot'()
	//PrintResults()
	ErrorCheck()
	
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
    GlobalVariable.destino_before_app = "SelectionFailed Account or Beneficiary not visible"
    println('Destino Account Not Available - Skipping Codigo Verification Flow...!!!')
	//CustomKeywords.'helper.customfunction.continuar'()
	//CustomKeywords.'helper.customfunction.Screenshot'()
	//PrintResults()
	ErrorCheck()
	
} else if (Mobile.waitForElementPresent(findTestObject('TokenImport/Su Token Digital se estar validando de manera automtica al confirmar la transaccin'), 30, FailureHandling.OPTIONAL)) {
    println('Token Imported - Skipping Codigo Verification Flow...!!!')
	CustomKeywords.'helper.customfunction.Screenshot'()
	Mobile.delay(0.2)
	CustomKeywords.'helper.customfunction.continuar'()
	Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
	PaymentVerification()
	
} else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Ingresa el cdigo de verificacin'), 40, FailureHandling.OPTIONAL)) {
		
    Mobile.setText(findTestObject('PagosYTransferencia/Ingresa el cdigo de verificacin'), GlobalVariable.Token, 2, FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.HideKeyboard'()
	CustomKeywords.'helper.customfunction.Screenshot'()
    'CLICK ON CONTINUAR BUTTON:'
	CustomKeywords.'helper.customfunction.continuar'()
	Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
	PaymentVerification()
	
 } else if(Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Continuar'), 5, FailureHandling.OPTIONAL)) {
	 
	 CustomKeywords.'helper.customfunction.swipeUP'()
	 
	 if (Mobile.waitForElementPresent(findTestObject('TokenImport/Su Token Digital se estar validando de manera automtica al confirmar la transaccin'), 10, FailureHandling.OPTIONAL)) {
		 println('Token Imported - Skipping Codigo Verification Flow...!!!')
		 CustomKeywords.'helper.customfunction.Screenshot'()
		 Mobile.delay(0.2)
		 CustomKeywords.'helper.customfunction.continuar'()
		 Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
		 PaymentVerification()
		 
	 } else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Ingresa el cdigo de verificacin'), 20, FailureHandling.OPTIONAL)) {
			 
		 Mobile.setText(findTestObject('PagosYTransferencia/Ingresa el cdigo de verificacin'), GlobalVariable.Token, 2, FailureHandling.OPTIONAL)
		 CustomKeywords.'helper.customfunction.HideKeyboard'()
		 CustomKeywords.'helper.customfunction.Screenshot'()
		 'CLICK ON CONTINUAR BUTTON:'
		 CustomKeywords.'helper.customfunction.continuar'()
		 Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
		 PaymentVerification()
		 
	  } else {
		 CustomKeywords.'helper.customfunction.Screenshot'()
		 KeywordUtil.markPassed('Codigo Is Not Necessary For Test Case...!!!')
		 CustomKeywords.'helper.customfunction.continuar'()
		 Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
		 PaymentVerification()
	  }
 } else {
	 
	 CustomKeywords.'helper.customfunction.swipeUP'()
	 
	 if (Mobile.waitForElementPresent(findTestObject('TokenImport/Su Token Digital se estar validando de manera automtica al confirmar la transaccin'), 3, FailureHandling.OPTIONAL)) {
		 println('Token Imported - Skipping Codigo Verification Flow...!!!')
		 CustomKeywords.'helper.customfunction.Screenshot'()
		 Mobile.delay(0.2)
		 CustomKeywords.'helper.customfunction.continuar'()
		 Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
		 PaymentVerification()
		 
	 } else {
		// if(Description != "Transfer Saving Account Which Has Only Consulatation Permissions" || Description != "ACH - Restricted Account - NoProcess"){
		// 	CustomKeywords.'helper.customfunction.Screenshot'()
		// }
		 
		 KeywordUtil.markPassed('Codigo Is Not Necessary For Test Case...!!!')
		 CustomKeywords.'helper.customfunction.continuar'()
		 Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
		 PaymentVerification()
	 }
  }

def PrintResults() {
	'TRANSACTION DETAILS :'
	GlobalVariable.PaymentMessage = Mobile.getText(findTestObject('PaymentSuccess/TransactionMessage'), 1, FailureHandling.OPTIONAL)
	WebUI.comment('### - TRANSACTION PAYMENT MESSAGE ### : ' + GlobalVariable.PaymentMessage )

	GlobalVariable.PaymentTime = Mobile.getText(findTestObject('PaymentSuccess/TransactionTime'), 1, FailureHandling.OPTIONAL)
	WebUI.comment('### - PAYMENT TIME ### : ' + GlobalVariable.PaymentTime )
	
	GlobalVariable.PaymentReciept= Mobile.getText(findTestObject('PaymentSuccess/PaymentReciept'), 1, FailureHandling.OPTIONAL)
	WebUI.comment('### - PAYMENT RECIEPT ### : ' + GlobalVariable.PaymentReciept )
	
	GlobalVariable.PaymentAmount = Mobile.getText(findTestObject('PaymentSuccess/Amount'), 1, FailureHandling.OPTIONAL)
	WebUI.comment('### - MONTO AMOUNT ### : ' + GlobalVariable.PaymentAmount)

	GlobalVariable.PaymentDestino = Mobile.getText(findTestObject('PaymentSuccess/DestinoAccount'), 1, FailureHandling.OPTIONAL)
	WebUI.comment('### - DESTINO ACCOUNT ### : ' + GlobalVariable.PaymentDestino)
}

def PaymentVerification() {
	'PAYMENT TRANSACTION VERIFICATON : '
	if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/Creada Pendiente Autorizacion'), 60, FailureHandling.OPTIONAL)
		|| Mobile.waitForElementPresent(findTestObject('PaymentMessages/Sometida. Pendiente de autorizacin'), 10, FailureHandling.OPTIONAL)
		|| Mobile.waitForElementPresent(findTestObject('PaymentMessages/TRANSACCIN PROCESADA'), 10, FailureHandling.OPTIONAL)
		|| Mobile.waitForElementPresent(findTestObject('PaymentMessages/La transaccin fue realizada'), 10, FailureHandling.OPTIONAL)) {
		GlobalVariable.PaymentStatus = "Passed"
		KeywordUtil.markPassed('Payment Transaction Success...!!!')
		CustomKeywords.'helper.customfunction.Screenshot'()
		PrintResults()
		
	} else if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/TRANSACCIN NO PROCESADA'), 3, FailureHandling.OPTIONAL)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.PaymentStatus = "Failed"
		KeywordUtil.markPassed('Payment Failed..' + Description)
		PrintResults()
		
	} else {
		//PrintResults()
		ErrorCheck()
	}
}


def ErrorCheck() {
	
	CustomKeywords.'helper.customfunction.Screenshot'()
	
	if (Mobile.waitForElementPresent(findTestObject('ErrorMessage/Actualmente no posees productos activos en Banreservas'), 3, FailureHandling.OPTIONAL)) {
		GlobalVariable.PaymentStatus = "Failed"
		KeywordUtil.markFailed('Payment Failed..' + Description)
		PrintResults()
   
	} else if(Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Process screen'), 3, FailureHandling.OPTIONAL)) {
		GlobalVariable.PaymentStatus = "Failed"
		GlobalVariable.CaseStatusMessage += "\n *** Stuck on Processing screen *** \n"
		KeywordUtil.markFailedAndStop('Payment Failed..' + Description)
	} else {
		GlobalVariable.PaymentStatus = "Failed"
		println('Non Payment Transaction Scenario - So Verification Skipped...!!!')
		PrintResults()
	}
}