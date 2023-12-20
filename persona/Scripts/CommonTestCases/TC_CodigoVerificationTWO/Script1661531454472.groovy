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
    println('Origen Account Not Available - Skipping Codigo Verification Flow...!!!')
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
    println('Destino Account Not Available - Skipping Codigo Verification Flow...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Ingresa el cdigo de verificacin'), 10, FailureHandling.OPTIONAL)) {
		
		CustomKeywords.'helper.customfunction.swipeUP'()
		CustomKeywords.'helper.customfunction.swipeDown'()
	
		'VERIFICATION DATA IS NOT EDITABLE:'
		if(Mobile.verifyElementAttributeValue(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto View Group'), 'clickable', 'false', 1, FailureHandling.OPTIONAL)
			&&Mobile.verifyElementAttributeValue(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuestos View Group'), 'clickable', 'false', 1, FailureHandling.OPTIONAL)
			&&Mobile.verifyElementAttributeValue(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Destino View Group'), 'clickable', 'false', 1, FailureHandling.OPTIONAL)
			&&Mobile.verifyElementAttributeValue(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Monto View Group'), 'clickable', 'false', 1, FailureHandling.OPTIONAL)
			&&Mobile.verifyElementAttributeValue(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Origen View Group'), 'clickable', 'false', 1, FailureHandling.OPTIONAL)){
			
			KeywordUtil.markPassed('Condition passed Origen, Destino, Monto, Impuestos, Concepto fields are not editable!!!')
			
		} else if(Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto View Group'), 1, FailureHandling.OPTIONAL)
			&&Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuestos View Group'), 1, FailureHandling.OPTIONAL)
			&&Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Destino View Group'), 1, FailureHandling.OPTIONAL)
			&&Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Monto View Group'), 1, FailureHandling.OPTIONAL)
			&&Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Origen View Group'), 1, FailureHandling.OPTIONAL)) {
			
				Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto View Group'), 1, FailureHandling.OPTIONAL)
	
				if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto View Group'), 1, FailureHandling.STOP_ON_FAILURE)) {
					KeywordUtil.markPassed('Condition passed Concepto field is not editable!!!')
				} else {
					KeywordUtil.markFailed('Condition Failed Concepto field is editable!!!')
				}
				
				Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuestos View Group'), 1, FailureHandling.OPTIONAL)
				
				if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuestos View Group'), 1, FailureHandling.STOP_ON_FAILURE)) {
					KeywordUtil.markPassed('Condition passed Impuestos field is not editable!!!')
				} else {
					KeywordUtil.markFailed('Condition Failed Impuestos field is editable!!!')
				}
				
				Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Destino View Group'), 1, FailureHandling.OPTIONAL)
				
				if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Destino View Group'), 1, FailureHandling.STOP_ON_FAILURE)) {
					KeywordUtil.markPassed('Condition passed Destino field is not editable!!!')
				} else {
					KeywordUtil.markFailed('Condition Failed Destino field is editable!!!')
				}
				
				Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Monto View Group'), 1, FailureHandling.OPTIONAL)
				
				if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Monto View Group'), 1, FailureHandling.STOP_ON_FAILURE)) {
					KeywordUtil.markPassed('Condition passed Monto field is not editable!!!')
				} else {
					KeywordUtil.markFailed('Condition Failed Monto field is editable!!!')
				}
				
				Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Origen View Group'), 1, FailureHandling.OPTIONAL)
				
				if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Origen View Group'), 1, FailureHandling.STOP_ON_FAILURE)) {
					KeywordUtil.markPassed('Condition passed Origen field is not editable!!!')
				} else {
					KeywordUtil.markFailed('Condition Failed Origen field is editable!!!')
				}
				
		} else {
			KeywordUtil.markFailed('Condition Failed Origen, Destino, Monto, Impuestos, Concepto fields are editable!!!')
		}
		
		'VERIFICATION DATA IS NOT CHANGED WHEN GOING BACK TO DATOS SCREEN'
		CustomKeywords.'helper.customfunction.ConfirmacionToDatosVerify'()
		
		CustomKeywords.'helper.customfunction.swipeUP'()

		Mobile.setText(findTestObject('PagosYTransferencia/Ingresa el cdigo de verificacin'), Password, 2, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
		
		'CLICK ON CONTINUAR BUTTON:'
		CustomKeywords.'helper.customfunction.continuar'()

		'VERIFY THE PAYMENT TRANSACTION:'
		if (Mobile.waitForElementPresent(findTestObject('PaymentMessages/Sometida. Pendiente de autorizacin'), 25, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('PaymentMessages/TRANSACCIN PROCESADA'), 15, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('PaymentMessages/Creada Pendiente Autorizacion'), 15, FailureHandling.OPTIONAL)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			GlobalVariable.PaymentStatus = "Passed"
			KeywordUtil.markPassed('Condition Passed Transaction is pending authorization is Displayed - Payment Transaction Success...!!!')
			CustomKeywords.'helper.customfunction.PrintResultsStatic'()
			
			/*
			'VERIFY THE PAYMENT IS REFLECTED IN TRANSACTION HISTORY:'
			if (Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/HamburgerMenu'), 25, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/HamburgerMenu'), 1, FailureHandling.CONTINUE_ON_FAILURE)
				Mobile.tap(findTestObject('UserDetails/Histrico de transacciones'), 1, FailureHandling.CONTINUE_ON_FAILURE)
				
				if (Mobile.waitForElementPresent(findTestObject('Object Repository/Transaction History/Pendientes Button'), 25, FailureHandling.OPTIONAL)) {
					String Transaction_Time = Mobile.getText(findTestObject('Object Repository/Transaction History/1- Transaction Time'), 1, FailureHandling.STOP_ON_FAILURE)
					String Concept = Mobile.getText(findTestObject('Object Repository/Transaction History/1 - Concept'), 1, FailureHandling.STOP_ON_FAILURE)
					String Amount = Mobile.getText(findTestObject('Object Repository/Transaction History/1 - Amount'), 1, FailureHandling.STOP_ON_FAILURE)
					
					if(Transaction_Time.contains(GlobalVariable.Result_TransactionTime)&&Concept.equals(GlobalVariable.Confirmacion_Concepto)&&Amount.equals(GlobalVariable.Confirmacion_Monto)) {
						KeywordUtil.markPassed("Submitted Transaction is visible in Pending History!!!")
						KeywordUtil.logInfo("Submitted Transaction is visible in Pending History!!!")
						
						KeywordUtil.logInfo(GlobalVariable.Result_TransactionTime)
						KeywordUtil.logInfo(Transaction_Time)
						KeywordUtil.logInfo(GlobalVariable.Confirmacion_Concepto)
						KeywordUtil.logInfo(Concept)
						KeywordUtil.logInfo(GlobalVariable.Confirmacion_Monto)
						KeywordUtil.logInfo(Amount)
					} else {
						KeywordUtil.markFailed("Submitted Transaction is not visible in Pending History!!!")
						KeywordUtil.logInfo("Submitted Transaction is not visible in Pending History!!!")
						
						KeywordUtil.logInfo(GlobalVariable.Result_TransactionTime)
						KeywordUtil.logInfo(Transaction_Time)
						KeywordUtil.logInfo(GlobalVariable.Confirmacion_Concepto)
						KeywordUtil.logInfo(Concept)
						KeywordUtil.logInfo(GlobalVariable.Confirmacion_Monto)
						KeywordUtil.logInfo(Amount)
					}
				}
			} else {
				CustomKeywords.'helper.customfunction.Screenshot'()
				KeywordUtil.markFailed('Dashboard Not Visible Check Once!!!')
			}
			*/
			
		} else if (Mobile.waitForElementPresent(findTestObject('PaymentSuccess/TransactionFailedMessage'), 10, FailureHandling.OPTIONAL)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			GlobalVariable.PaymentStatus = "Failed"
			GlobalVariable.CaseStatus = "Failed"
			CustomKeywords.'helper.customfunction.PrintResultsStatic'()
				if(Mobile.waitForElementPresent(findTestObject('Object Repository/PaymentSuccess/Monto Disponible Insuficiente (message)'), 2, FailureHandling.OPTIONAL)) {
					KeywordUtil.markPassed('Insufficient balance condition passed!!!')
					KeywordUtil.logInfo('Insufficient balance condition passed!!!')
				} else if(Mobile.waitForElementPresent(findTestObject('Object Repository/PaymentMessages/Esquema De Autorizacion Invalido'), 2, FailureHandling.OPTIONAL)) {
					KeywordUtil.markPassed('Invalid Authorization Scheme condition passed!!!')
					KeywordUtil.logInfo('Invalid Authorization Scheme condition passed!!!')
				}
			 KeywordUtil.markPassed('Payment Failed..' + Description)
		} 	else if (Mobile.waitForElementPresent(findTestObject('Object Repository/PaymentSuccess/La transaccin fue realizada Title'), 25, FailureHandling.OPTIONAL)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			GlobalVariable.PaymentStatus = "Passed"
			KeywordUtil.markPassed('Payment Transaction Success in Authorizer user...!!!')
			CustomKeywords.'helper.customfunction.PrintResultsStatic'()
			
			/*
			'VERIFY THE PAYMENT IS REFLECTED IN TRANSACTION HISTORY:'
			if (Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/HamburgerMenu'), 25, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/HamburgerMenu'), 1, FailureHandling.CONTINUE_ON_FAILURE)
				Mobile.tap(findTestObject('UserDetails/Histrico de transacciones'), 1, FailureHandling.CONTINUE_ON_FAILURE)
				
				if (Mobile.waitForElementPresent(findTestObject('Object Repository/Transaction History/Pendientes Button'), 25, FailureHandling.OPTIONAL)) {
					Mobile.tap(findTestObject('Object Repository/Transaction History/Procesadas Button'), 1, FailureHandling.CONTINUE_ON_FAILURE)
					String Transaction_Time = Mobile.getText(findTestObject('Object Repository/Transaction History/1- Transaction Time'), 1, FailureHandling.STOP_ON_FAILURE)
					String Concept = Mobile.getText(findTestObject('Object Repository/Transaction History/1 - Concept'), 1, FailureHandling.STOP_ON_FAILURE)
					String Amount = Mobile.getText(findTestObject('Object Repository/Transaction History/1 - Amount'), 1, FailureHandling.STOP_ON_FAILURE)
					
					if(Transaction_Time.contains(GlobalVariable.Result_TransactionTime)&&Amount.equals(GlobalVariable.Confirmacion_Monto)) {
						KeywordUtil.markPassed("Submitted Transaction is visible in Pending History!!!")
						KeywordUtil.logInfo("Submitted Transaction is visible in Pending History!!!")
						
						KeywordUtil.logInfo(GlobalVariable.Result_TransactionTime)
						KeywordUtil.logInfo(Transaction_Time)
						KeywordUtil.logInfo(GlobalVariable.Confirmacion_Monto)
						KeywordUtil.logInfo(Amount)
					} else {
						KeywordUtil.markFailed("Submitted Transaction is not visible in Pending History!!!")
						KeywordUtil.logInfo("Submitted Transaction is not visible in Pending History!!!")
						
						KeywordUtil.logInfo(GlobalVariable.Result_TransactionTime)
						KeywordUtil.logInfo(Transaction_Time)
						KeywordUtil.logInfo(GlobalVariable.Confirmacion_Monto)
						KeywordUtil.logInfo(Amount)
					}
				}
			} else {
				CustomKeywords.'helper.customfunction.Screenshot'()
				KeywordUtil.markFailed('Dashboard Not Visible Check Once!!!')
			}
			*/
		} else if (Mobile.waitForElementPresent(findTestObject('ErrorMessage/Actualmente no posees productos activos en Banreservas. Para solicitar nuevos productos contctanos en el 809.960.2121 o a travs de nuestro Chat Banreservas'), 3, FailureHandling.STOP_ON_FAILURE)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			GlobalVariable.PaymentStatus = "Failed"
			KeywordUtil.markFailedAndStop('Payment Failed..' + Description)
	   
		} else {
			CustomKeywords.'helper.customfunction.Screenshot'()
			GlobalVariable.PaymentStatus = "Failed"
			println('Non Payment Transaction Scenario - So Verification Skipped...!!!')
		}
	} else {
		KeywordUtil.markPassed('Codigo Is Not Necessary For Test Case...!!!')
	}