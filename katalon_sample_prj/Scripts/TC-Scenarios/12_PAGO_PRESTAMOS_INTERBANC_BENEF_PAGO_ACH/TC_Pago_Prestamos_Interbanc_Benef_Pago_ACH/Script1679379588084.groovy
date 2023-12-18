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

'PAGO PRESTAMOS INTERBANC BENEFICIARY PAGO ACH : '
WebUI.comment((ID + ' - ') + Description)

'PROFILE VERIFICATION FOR DATABASE CONNECTION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProfileVerification'), [('Verification') : Verification, ('Description') : Description,('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino, 
	('Impuesto'):Impuesto,('Monto'):Monto,('TransactionMessage'):TransactionMessage,('TransactionMessageAuthorizer1'):TransactionMessageAuthorizer1,('TransactionMessageAuthorizer2'):TransactionMessageAuthorizer2], FailureHandling.STOP_ON_FAILURE)

'APPLICATION LAUNCH : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)

'USERLOGGED IN VERIFICATION : '
CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1, Username2,Username3,Password, Email, Role, Type)

if(Concept == "SwipeVerification") {
	'SWIPE VERIFICATION '
	CustomKeywords.'helper.customfunction.SwipeVerification'(CtaOrigen, CtaDestino, Concept)
	GlobalVariable.PaymentMessage = "NA"
} else {

	'SELECT TRANSACCIONES : '
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_TransactionType'), [('Type') : Type, ('Concept') : Concept], FailureHandling.CONTINUE_ON_FAILURE)
	
	'ATTEMPT TRANSACTION PAYMENTS : '
	if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Origen-Title'), 5, FailureHandling.OPTIONAL)) {

		'SELECT ORIGEN ACCOUNT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_OrigenAccount'), [('CtaOrigen') : CtaOrigen, ('Monto') : Monto], FailureHandling.CONTINUE_ON_FAILURE)
		
		if (Verification == "ExceedOrigenAmount") {
			try {
				println "Origen Monto Balance Updated Is : " + GlobalVariable.origen_before_db
				double Balance1 = Double.parseDouble(Monto)
				double Balance2 = GlobalVariable.origen_before_db + Balance1
				println "Balance2 Is : " + Balance2
				String UpdatedMonto = String.format("%.0f", Balance2)
				Monto = UpdatedMonto
				println "Updated Monto Is : " + Monto
			}catch(Exception e) {
				KeywordUtil.logInfo("ExceedOrigen block failed")
			}
			Verification = "SelfAuthorize"
		}
			
		'SELECT DESTINO ACCOUNT :'
    	WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoBeneficiary'), [('CtaDestino') : CtaDestino, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)

		'ENTER MONTO FOR PAYMENT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : Monto], FailureHandling.CONTINUE_ON_FAILURE)
	
		'PAGO TYPE : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoMethod'), [('PagoType') : PagoType], FailureHandling.CONTINUE_ON_FAILURE)
		
		'TASA VERIFICATION : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
	
		'ENTER CONCEPT FOR PAYMENT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
	
		'VERIFICATION CODIGO : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification'), [('Monto') : Monto, ('Password') : Password, ('Description') : Description], FailureHandling.OPTIONAL)
	
		'VERIFICATION SCENARIO TEST CASE :'
		CustomKeywords.'helper.paymenthelper.PaymentResponses'()
		CustomKeywords.'helper.customfunction.VerifyTransactionMessage'(Username1, Username2, Username3)
		CustomKeywords.'helper.customfunction.HistoricoPendiente'()
	}
}
if ((Verification == 'Authorize' || Verification == 'Reject')) {
	
	CustomKeywords.'helper.customfunction.UnlinkUser'()
	
	'AUTHORIZE USER LOG IN : '
	WebUI.callTestCase(findTestCase('TC-Scenarios/AUTHORIZE-REJECT/TC-Authorize-Reject'), [('ID') : ID
				, ('CaseNo') : CaseNo, ('Role') : Role, ('Description') : Description, ('RNC') : RNC, ('Username1') : Username1,('Username2') : Username2,('Username3') : Username3
				, ('Password') : Password, ('Email') : Email, ('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino, ('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino
				, ('Monto') : Monto, ('Concept') : Concept, ('Impuesto') : Impuesto, ('Type') : Type, ('Verification') : Verification],
				,FailureHandling.CONTINUE_ON_FAILURE)
	
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
		WebUI.callTestCase(findTestCase('TC-Scenarios/AUTHORIZE-REJECT/TC-Authorize-Reject'), [('ID') : ID
				, ('CaseNo') : CaseNo, ('Role') : Role, ('Description') : Description, ('RNC') : RNC, ('Username1') : Username1,('Username2') : Username2,('Username3') : Username3
				, ('Password') : Password, ('Email') : Email, ('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino, ('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino
				, ('Monto') : Monto, ('Concept') : Concept, ('Impuesto') : Impuesto, ('Type') : Type, ('Verification') : Verification],
				,FailureHandling.CONTINUE_ON_FAILURE)
		
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
	Mobile.delay(2)

	'PAYMENT VERIFICATION - CHECK THE BALANCE UPDATES : '
	PaymentVerification()
	CustomKeywords.'helper.customfunction.UnlinkUser'()
	
} else {
	println('Unknown Verification - Approve / Reject Authorization Flow Is Skipped For Test Case ' + Description)
	//CustomKeywords.'helper.customfunction.UnlinkUser'()
}

def PaymentVerification() {
		if (Role == 'Autorizador' && Verification == 'SelfAuthorize') {
			
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResults'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : Monto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)
	
		} else if(Role == 'Solicitante' && Verification == 'Authorize' || Verification == 'Reject') {
		CustomKeywords.'helper.customfunction.UnlinkUser'()
			
		'SOLICIANTE USER LOG IN : '
		CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1,Username2,Username3, Password, Email, Role, Type)
	
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResults'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : Monto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)

		} else if(Role == 'Solicitante' && Verification == 'D-Authorize' || Verification == 'D-Reject') {
		CustomKeywords.'helper.customfunction.UnlinkUser'()
		
		'SOLICIANTE USER LOG IN : '
		CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1,Username2,Username3, Password, Email, Role, Type)
	
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResults'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : Monto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)

		} else if (Role == 'Solicitante' && Verification == 'SelfAuthorize') {
			
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResults'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : Monto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)
	
		}
}