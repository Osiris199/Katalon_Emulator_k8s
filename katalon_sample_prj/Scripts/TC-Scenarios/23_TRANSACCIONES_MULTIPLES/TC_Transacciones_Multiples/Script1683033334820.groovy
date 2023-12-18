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

'TRANSACCIONES MULTIPLES : '
WebUI.comment((ID + ' - ') + Description)

'PROFILE VERIFICATION FOR DATABASE CONNECTION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProfileVerification'), [('Verification') : Verification, ('Description') : Description, ('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,
('Impuesto') : Impuesto, ('Monto') : Monto, ('TransactionMessage') : TransactionMessage, ('TransactionMessageAuthorizer1') : TransactionMessageAuthorizer1, 
('TransactionMessageAuthorizer2') : TransactionMessageAuthorizer2, ('CuentaDestino1') : CuentaDestino1, ('CuentaDestino2') : CuentaDestino2, ('CuentaDestino3') : CuentaDestino3, 
('CuentaDestino4') : CuentaDestino4, ('DestinoCount') : DestinoCount], FailureHandling.STOP_ON_FAILURE)

'APPLICATION LAUNCH : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)

'USERLOGGED IN VERIFICATION : '
CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1, Username2, Username3, Password, Email, Role, Type)

'TAP ON HAMBURGER MENU : '
Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)

'SELECT TRANSACCIONES : '
if (Type == 'NA') {
	println('Non Payment Transaction Scenario - So Transaction Part Is Skipped...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
	Mobile.tap(findTestObject('PagosYTransferencia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	Mobile.delay(1)
	Mobile.tap(findTestObject('MultipleTransactions/Transacciones mltiples'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	Mobile.delay(1)
} else {
	Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
		
	if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('PagosYTransferencia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
		Mobile.tap(findTestObject('MultipleTransactions/Transacciones mltiples'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
	} else {
		Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
		Mobile.tap(findTestObject('PagosYTransferencia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
		Mobile.tap(findTestObject('MultipleTransactions/Transacciones mltiples'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
	}
}

'ATTEMPT TRANSACTION PAYMENTS : '
if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Origen-Title'), 10, FailureHandling.OPTIONAL)) {
	
    'SELECT ORIGEN ACCOUNT :'
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_OrigenAccount'), [('CtaOrigen') : CtaOrigen, ('Monto') : Monto], FailureHandling.STOP_ON_FAILURE)
	
    'SELECT DESTINO ACCOUNT :'
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_Multiple_Destino'), [('DestinoNumber1') : CuentaDestino1, ('DestinoNumber2') : CuentaDestino2, ('DestinoNumber3') : CuentaDestino3, ('DestinoNumber4') : CuentaDestino4 ,('Destino') : CtaDestino, ('Destino1') : CtaDestino1, ('Destino2') : CtaDestino2, ('Destino3') : CtaDestino3, ('Destino4') : CtaDestino4, ('DestinoCount') : DestinoCount, ('Monto') : Monto, ('Monto1') : Monto1, ('Monto2') : Monto2, ('Monto3') : Monto3, ('Concept') : Concept, ('Description') : Description, ('Banco') : Banco, ('Moneda') : Moneda, ('TipoDeProducto') : TipoDeProducto, ('PagoType') : PagoType, ('PagoType1') : PagoType1], FailureHandling.CONTINUE_ON_FAILURE)
	
	'TOTAL MONTO : '
	String Balance = Mobile.getText(findTestObject('MultipleTransactions/Total Monto'), 2, FailureHandling.OPTIONAL)
	String Balance2
	try {
		String Balance1 = Balance.replaceAll('[,]', '')
		Balance2 = Balance1.substring(4)
	} catch(Exception e) {
		GlobalVariable.CaseStatusMessage += "\n *** Something wrong selecting destino accounts *** \n"
	} 
	GlobalVariable.OriginalMonto = Balance2	
	
	
    'VERIFICATION CODIGO : '
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification - Multiple Transaction'), [('Monto') : Monto, ('Password') : Password, ('Description') : Description], FailureHandling.OPTIONAL)
	
	'VERIFICATION SCENARIO TEST CASE :'
	//CustomKeywords.'helper.paymenthelper.PaymentResponses'()
	CustomKeywords.'helper.customfunction.VerifyTransactionMessage'(Username1, Username2, Username3)
	CustomKeywords.'helper.customfunction.HistoricoPendiente'()
}

if ((Verification == 'Authorize' || Verification == 'Reject')) {
	CustomKeywords.'helper.customfunction.UnlinkUser'()

	'AUTHORIZE USER LOG IN : '
	WebUI.callTestCase(findTestCase('TC-Scenarios/AUTHORIZE-REJECT/TC-Authorize-Reject_Multiple'), [('RNC') : RNC, ('Username1') : Username1,('Username2') : Username2,('Username3') : Username3,
	('Password') : Password, ('Email') : Email, ('Role') : Role, ('Verification') : Verification, ('Type') : Type, ('Concept') : Concept, ('DestinoCount') : DestinoCount], FailureHandling.CONTINUE_ON_FAILURE)
	

	GlobalVariable.AuthorizePaymentMesg1 = GlobalVariable.AuthorizePaymentMesg
	GlobalVariable.AppPaymentMessage = GlobalVariable.AuthorizePaymentMesg
	CustomKeywords.'helper.customfunction.VerifyTransactionMessage'(Username1, Username2, Username3)
	
	
	'PAYMENT VERIFICATION - CHECK THE BALANCE UPDATES : '
	PaymentVerification()
	CustomKeywords.'helper.customfunction.UnlinkUser'()
	
} else if (Verification == 'D-Authorize' || Verification == 'D-Reject') {	
	for(int i=1; i<=2; i++) {
	
		if(i==1) {
			GlobalVariable.Counter = "Authorize-1"
		}else {
			GlobalVariable.Counter = "Authorize-2"
		}
		println "Counter Name Is Set As : " + GlobalVariable.Counter
		
		CustomKeywords.'helper.customfunction.UnlinkUser'()

		'AUTHORIZE USER LOG IN : '
		WebUI.callTestCase(findTestCase('TC-Scenarios/AUTHORIZE-REJECT/TC-Authorize-Reject_Multiple'), [('RNC') : RNC, ('Username1') : Username1,('Username2') : Username2,('Username3') : Username3,
		('Password') : Password, ('Email') : Email, ('Role') : Role, ('Verification') : Verification, ('Type') : Type, ('Concept') : Concept, ('DestinoCount') : DestinoCount], FailureHandling.CONTINUE_ON_FAILURE)
	
		if(i==1) {
			GlobalVariable.AuthorizePaymentMesg1 = GlobalVariable.AuthorizePaymentMesg
		} else {
			GlobalVariable.AuthorizePaymentMesg2 = GlobalVariable.AuthorizePaymentMesg
		}
		GlobalVariable.AppPaymentMessage = GlobalVariable.AuthorizePaymentMesg
		CustomKeywords.'helper.customfunction.VerifyTransactionMessage'(Username1, Username2, Username3)
	}

	'PAYMENT VERIFICATION - CHECK THE BALANCE UPDATES : '
	PaymentVerification()
	CustomKeywords.'helper.customfunction.UnlinkUser'()
	
} else if (Verification == 'SelfAuthorize') {
	GlobalVariable.AuthorizationStatus = "Passed"
	if(Role == "Autorizador") {
		GlobalVariable.AuthorizePaymentMesg1 =  GlobalVariable.PaymentMessage
		GlobalVariable.PaymentMessage = "NA"
	}
	Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 10,FailureHandling.OPTIONAL)
	Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
	Mobile.delay(5)

	'PAYMENT VERIFICATION - CHECK THE BALANCE UPDATES : '
	PaymentVerification()
	CustomKeywords.'helper.customfunction.UnlinkUser'()
	
} else {
	println('Approve / Reject Authorization Flow Is Skipped For Test Case ' + Description)
	//CustomKeywords.'helper.customfunction.UnlinkUser'()
}

def PaymentVerification() {

		if (Role == 'Autorizador' && Verification == 'SelfAuthorize') {
			'VERIFY THE BALANCES : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResultsModified - MultiTransaction'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino, ('CuentaDestino1') : CuentaDestino1, ('CuentaDestino2') : CuentaDestino2, ('CuentaDestino3') : CuentaDestino3, ('CuentaDestino4') : CuentaDestino4, ('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino, ('CtaDestino1') : CtaDestino1, ('CtaDestino2') : CtaDestino2, ('CtaDestino3') : CtaDestino3, ('CtaDestino4') : CtaDestino4, ('Monto') : Monto, ('Monto1') : Monto1, ('Monto2') : Monto2, ('Monto3') : Monto3, ('Impuesto') : Impuesto, ('DestinoCount') : DestinoCount, ('PagoType') : PagoType, ('PagoType1') : PagoType1], FailureHandling.OPTIONAL)
		} else if(Role == 'Solicitante' && Verification == 'Authorize' || Verification == 'Reject') {
			CustomKeywords.'helper.customfunction.UnlinkUser'()
			'SOLICIANTE USER LOG IN : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)
			CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1, Username2, Username3, Password, Email, Role, Type)
			'VERIFY THE BALANCES : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResultsModified - MultiTransaction'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino, ('CuentaDestino1') : CuentaDestino1, ('CuentaDestino2') : CuentaDestino2, ('CuentaDestino3') : CuentaDestino3, ('CuentaDestino4') : CuentaDestino4, ('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino, ('CtaDestino1') : CtaDestino1, ('CtaDestino2') : CtaDestino2, ('CtaDestino3') : CtaDestino3, ('CtaDestino4') : CtaDestino4, ('Monto') : Monto, ('Monto1') : Monto1, ('Monto2') : Monto2, ('Monto3') : Monto3, ('Impuesto') : Impuesto, ('DestinoCount') : DestinoCount, ('PagoType') : PagoType, ('PagoType1') : PagoType1], FailureHandling.OPTIONAL)
		} else if(Role == 'Solicitante' && Verification == 'D-Authorize') {
			CustomKeywords.'helper.customfunction.UnlinkUser'()
			'SOLICIANTE USER LOG IN : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)
			CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1,Username2,Username3, Password, Email, Role, Type)
			'VERIFY THE BALANCES : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResultsModified - MultiTransaction'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino, ('CuentaDestino1') : CuentaDestino1, ('CuentaDestino2') : CuentaDestino2, ('CuentaDestino3') : CuentaDestino3, ('CuentaDestino4') : CuentaDestino4, ('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino, ('CtaDestino1') : CtaDestino1, ('CtaDestino2') : CtaDestino2, ('CtaDestino3') : CtaDestino3, ('CtaDestino4') : CtaDestino4, ('Monto') : Monto, ('Monto1') : Monto1, ('Monto2') : Monto2, ('Monto3') : Monto3, ('Impuesto') : Impuesto, ('DestinoCount') : DestinoCount, ('PagoType') : PagoType, ('PagoType1') : PagoType1], FailureHandling.OPTIONAL)
		} else if(Role == 'Solicitante' && Verification == 'SelfAuthorize') {	
			'VERIFY THE BALANCES : '
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResultsModified - MultiTransaction'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino, ('CuentaDestino1') : CuentaDestino1, ('CuentaDestino2') : CuentaDestino2, ('CuentaDestino3') : CuentaDestino3, ('CuentaDestino4') : CuentaDestino4, ('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino, ('CtaDestino1') : CtaDestino1, ('CtaDestino2') : CtaDestino2, ('CtaDestino3') : CtaDestino3, ('CtaDestino4') : CtaDestino4, ('Monto') : Monto, ('Monto1') : Monto1, ('Monto2') : Monto2, ('Monto3') : Monto3, ('Impuesto') : Impuesto, ('DestinoCount') : DestinoCount, ('PagoType') : PagoType, ('PagoType1') : PagoType1], FailureHandling.OPTIONAL)
		}
		
}