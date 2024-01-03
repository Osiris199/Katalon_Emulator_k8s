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

'HISTORICO TRANSACCIONES : '
WebUI.comment((ID + ' - ') + Description)

'PROFILE VERIFICATION FOR DATABASE CONNECTION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProfileVerification'), [('Verification') : Verification, ('Description') : Description,('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino,
	('Impuesto'):Impuesto,('Monto'):Monto,('TransactionMessage'):TransactionMessage,('TransactionMessageAuthorizer1'):TransactionMessageAuthorizer1,('TransactionMessageAuthorizer2'):TransactionMessageAuthorizer2], FailureHandling.STOP_ON_FAILURE)

'APPLICATION LAUNCH : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)

'USERLOGGED IN VERIFICATION : '
CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1, Username2,Username3,Password, Email, Role, Type)

'SELECT TRANSACCIONES : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_TransactionType'), [('Type') : Type, ('Concept') : Concept], FailureHandling.CONTINUE_ON_FAILURE)

'ATTEMPT TRANSACTION PAYMENTS : '
if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Origen-Title'), 5, FailureHandling.OPTIONAL)) {

	'SELECT ORIGEN ACCOUNT :'
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_OrigenAccount'), [('CtaOrigen') : CtaOrigen, ('Monto') : Monto], FailureHandling.OPTIONAL)
	
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
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccount'), [('CtaDestino') : CtaDestino], FailureHandling.OPTIONAL)

	'ENTER MONTO FOR PAYMENT :'
	println "Monto For Transaction Is : " + Monto
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : Monto], FailureHandling.OPTIONAL)

	'TASA VERIFICATION : '
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)

	'ENTER CONCEPT FOR PAYMENT : '
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept, ('Description') : Description], FailureHandling.OPTIONAL)

	'VERIFICATION CODIGO : '
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification'), [('Monto') : Monto, ('Password') : Password, ('Description') : Description], FailureHandling.OPTIONAL)

	'VERIFICATION SCENARIO TEST CASE :'
	CustomKeywords.'helper.paymenthelper.PaymentResponses'()
	//CustomKeywords.'helper.customfunction.VerifyTransactionMessage'(Username1, Username2, Username3)
	//CustomKeywords.'helper.customfunction.HistoricoPendiente'()
	//CustomKeywords.'helper.customfunction.UnlinkUser'()
}

'SELECT TRANSACTION HISTORY : '
TransactionType()

'VERIFY PENDING TRANSACTIONS : '
CheckPendiente()

'VERIFY PROCESSED TRANSACTIONS : '
CheckProcesada()

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
	CustomKeywords.'helper.customfunction.UnlinkUser'()
}


def CheckPendiente() {
	if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Pendientes'), 35, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Procesadas'), 5, FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('Historico De Transacciones Pendientes Screen Is Present..!!')
		//CheckAprobadores()
		if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/android.view.ViewGroup - TransList1'), 5, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed('Pending Transaction List Is Present - User Can Check Details..!!')
			CustomKeywords.'helper.customfunction.Screenshot'()
			CheckAprobadores()
			
		} else if (Mobile.waitForElementPresent(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)) {
			KeywordUtil.markFailed('Historico De Transacciones Screen Is Blank - Message Displayed - No hay autorizaciones pendientes..!!')
			GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.Screenshot'()
			
		} else if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/No hay Aprobadores'), 3, FailureHandling.OPTIONAL)) {
			KeywordUtil.markFailed('Historico De Transacciones Screen Is Blank - Message Displayed - No hay autorizaciones pendientes..!!')
			GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.Screenshot'()
			
		} else {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailed('Historico De Transacciones Screen Not Present..!!')
		}
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed('Historico De Transacciones Pendientes Screen Not Present..!!')
	}
}


def CheckProcesada() {
	
	Mobile.tap(findTestObject('Historico_De_Transacciones/Procesadas'), 10, FailureHandling.OPTIONAL)
	
	if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Procesadas'), 35, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Pendientes'), 5, FailureHandling.OPTIONAL)) {

		if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/android.view.ViewGroup - TransList1'), 3, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed('Processed Transaction List Is Present - User Can Check Details..!!')
			CustomKeywords.'helper.customfunction.Screenshot'()
			CheckAprobadores()
			
		} else if (Mobile.waitForElementPresent(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)) {
			KeywordUtil.markFailed('Historico De Transacciones Screen Is Blank - Message Displayed - No hay autorizaciones pendientes..!!')
			GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.Screenshot'()
			
		} else if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/No hay Aprobadores'), 3, FailureHandling.OPTIONAL)) {
			KeywordUtil.markFailed('Historico De Transacciones Screen Is Blank - Message Displayed - No hay autorizaciones pendientes..!!')
			GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.Screenshot'()
			
		} else {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailed('Historico De Transacciones Screen Not Present..!!')
		}	
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed('Historico De Transacciones Procesadas Screen Not Present..!!')
	}
}


def CheckAprobadores() {

	Mobile.tap(findTestObject('Historico_De_Transacciones/android.view.ViewGroup - TransList1'), 3, FailureHandling.OPTIONAL)
	Mobile.delay(0.2)
	
	if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Detalle'), 35, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Aprobadores'), 5, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Informacin general'), 1, FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('Detalle Transaction Screen Is Present..!!')
		CustomKeywords.'helper.customfunction.Screenshot'()
		
		String Attribute = Mobile.getAttribute(findTestObject('Historico_De_Transacciones/Aprobadores'), 'enabled', 3, FailureHandling.CONTINUE_ON_FAILURE)
		
		if ((Attribute == 'True') || (Attribute == 'true')) {
			Mobile.tap(findTestObject('Historico_De_Transacciones/Aprobadores'), 3, FailureHandling.OPTIONAL)
			Mobile.delay(0.2)
			//Object Repository/Historico_De_Transacciones/android.view.ViewGroup - AprobadoresList1
			if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Detalle'), 35, FailureHandling.OPTIONAL)
				||Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Aprobadores'), 5, FailureHandling.OPTIONAL)) {
				
				if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/android.view.ViewGroup - AprobadoresList1'), 3, FailureHandling.OPTIONAL)) {
					KeywordUtil.markPassed('Aprobadores Transaction List Is Present - User Can Check Details..!!')
					CustomKeywords.'helper.customfunction.Screenshot'()
					
				} else if (Mobile.waitForElementPresent(findTestObject('Authorize/No hay autorizaciones pendientes'), 2, FailureHandling.OPTIONAL)) {
					//KeywordUtil.markFailed('Historico De Transacciones Screen Is Blank - Message Displayed - No hay autorizaciones pendientes..!!')
					GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
					CustomKeywords.'helper.customfunction.Screenshot'()
					
				} else if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/No hay Aprobadores'), 1, FailureHandling.OPTIONAL)) {
					//KeywordUtil.markFailed('Historico De Transacciones Screen Is Blank - Message Displayed - No hay autorizaciones pendientes..!!')
					GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
					CustomKeywords.'helper.customfunction.Screenshot'()
					
				} else {
					CustomKeywords.'helper.customfunction.Screenshot'()
					KeywordUtil.markFailed('Historico De Transacciones Screen Not Present..!!')
				}
			
			} else if (Mobile.waitForElementPresent(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)) {
				KeywordUtil.markFailed('Aprobadores Transaction Screen Not Present - Message Displayed - No hay autorizaciones pendientes..!!')
				GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
				CustomKeywords.'helper.customfunction.Screenshot'()
				
			} else if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/No hay Aprobadores'), 3, FailureHandling.OPTIONAL)) {
				KeywordUtil.markFailed('Aprobadores Transaction Screen Not Present - Message Displayed - No hay autorizaciones pendientes..!!')
				GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
				CustomKeywords.'helper.customfunction.Screenshot'()
				
			} else {
				CustomKeywords.'helper.customfunction.Screenshot'()
				KeywordUtil.markFailed('Aprobadores Transaction Screen Not Present..!!')
			}
		} else {
			println('Aprobadores Button Is Disabled...!!!')
			KeywordUtil.markFailed('Aprobadores Button Is Disabled For Case ' + Description)
		}
	
	} else if (Mobile.waitForElementPresent(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)) {
		KeywordUtil.markFailed('Detalle Transaction Screen Not Present - Message Displayed - No hay autorizaciones pendientes..!!')
		GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.Screenshot'()
		
	} else if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/No hay Aprobadores'), 3, FailureHandling.OPTIONAL)) {
		KeywordUtil.markFailed('Detalle Transaction Screen Not Present - Message Displayed - No hay autorizaciones pendientes..!!')
		GlobalVariable.AppPaymentMessage = Mobile.getText(findTestObject('Authorize/No hay autorizaciones pendientes'), 3, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.Screenshot'()
		
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed('Detalle Transaction Screen Not Present..!!')
	}
	
	Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 2, FailureHandling.OPTIONAL)
	Mobile.delay(3)
}


def TransactionType() {
	//'TAP ON HAMBURGER MENU : '
	CustomKeywords.'helper.customfunction.Refresh1'()
	Mobile.delay(1)
	Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
	
	if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 8, FailureHandling.OPTIONAL)) {
		
		Mobile.tap(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	
		Mobile.delay(1)
	
		//CustomKeywords.'helper.customfunction.Screenshot'()
	} else {
		Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
	
		if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 5, FailureHandling.OPTIONAL)) {
			
			Mobile.tap(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	
			Mobile.delay(1)
	
		    //CustomKeywords.'helper.customfunction.Screenshot'()
		} else {
			Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
	
			Mobile.tap(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	
			Mobile.delay(1)
	
			//CustomKeywords.'helper.customfunction.Screenshot'()
		}
	}
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