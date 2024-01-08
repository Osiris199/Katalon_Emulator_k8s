import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil

'24.2 - IMPUESTOS Y SERVICIOS : '
WebUI.comment((ID + ' - ') + Description)

//'PROFILE VERIFICATION FOR DATABASE CONNECTION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProfileVerification'), [('Verification') : Verification, ('Description') : Description,('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino, 
	('Impuesto'):Impuesto,('Monto'):Monto,('TransactionMessage'):TransactionMessage,('TransactionMessageAuthorizer1'):TransactionMessageAuthorizer1,('TransactionMessageAuthorizer2'):TransactionMessageAuthorizer2], FailureHandling.STOP_ON_FAILURE)

'APPLICATION LAUNCH : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)

'USERLOGGED IN VERIFICATION : '
CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1, Username2,Username3,Password, Email, Role, Type)

'SELECT TRANSACCIONES : '
TransactionType()

'ATTEMPT TRANSACTION PAYMENTS : '
if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Origen-Title'), 5, FailureHandling.OPTIONAL)) {
	//CustomKeywords.'helper.customfunction.Screenshot'()
	
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
			//e.printStackTrace()
		}
		Verification = "SelfAuthorize"
	}
	
	'SELECT DESTINO ACCOUNT :'
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_ImpuestosYServicios_SelectDestino'), [('Description') : Description, ('Catagory') : Catagory, ('CuentaDestino') : CuentaDestino, ('CtaDestino') : CtaDestino, ('Alias') : Alias], FailureHandling.CONTINUE_ON_FAILURE)
	
	'ENTER MONTO FOR PAYMENT :'
	println "Monto For Transaction Is : " + Monto
	if(Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Digita un monto'), 5, FailureHandling.OPTIONAL)) {
		
		try {
			Mobile.delay(1)
			String checkMonto = Monto
			if(checkMonto.matches(".*[a-zA-Z]+.*")) {
				SelectMonto()
			} else {
				WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : Monto], FailureHandling.CONTINUE_ON_FAILURE)
			}
		} catch (Exception e) {
			GlobalVariable.OriginalMonto = Monto
		}
		
		'TASA VERIFICATION : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
	
		'ENTER CONCEPT FOR PAYMENT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	'VERIFICATION CODIGO : '
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_CodigoVerification'), [('Monto') : Monto, ('Password') : Password, ('Description') : Description], FailureHandling.OPTIONAL)

	'VERIFICATION SCENARIO TEST CASE :'
	CustomKeywords.'helper.paymenthelper.PaymentResponses'()
	CustomKeywords.'helper.customfunction.VerifyTransactionMessage'(Username1, Username2, Username3)
	CustomKeywords.'helper.customfunction.HistoricoPendiente'()
}


//if ((Verification == 'Authorize' || Verification == 'Reject') && GlobalVariable.PaymentStatus.equals("Passed")) {
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
	
//} else if (Verification == 'D-Authorize' && GlobalVariable.PaymentStatus.equals("Passed")) {
} else if (Verification == 'D-Authorize') {
	
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
	//if (Verification == 'SelfAuthorize' && GlobalVariable.PaymentStatus.equals("Passed"))
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

def PaymentVerification() {

//	if (GlobalVariable.AuthorizationStatus == "Passed" || GlobalVariable.PaymentStatus == "Passed") {
//
		if (Role == 'Autorizador' && Verification == 'SelfAuthorize') {
			
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResults'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : GlobalVariable.OriginalMonto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)
	
		} else if(Role == 'Solicitante' && Verification == 'Authorize' || Verification == 'Reject') {
		CustomKeywords.'helper.customfunction.UnlinkUser'()
			
		'SOLICIANTE USER LOG IN : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)
	
		CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1,Username2,Username3, Password, Email, Role, Type)
	
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResults'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : GlobalVariable.OriginalMonto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)

		} else if(Role == 'Solicitante' && Verification == 'D-Authorize') {
		CustomKeywords.'helper.customfunction.UnlinkUser'()
		
		'SOLICIANTE USER LOG IN : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)
	
		CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1,Username2,Username3, Password, Email, Role, Type)
	
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResults'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : GlobalVariable.OriginalMonto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)

		} else if (Role == 'Solicitante' && Verification == 'SelfAuthorize') {
			
		'VERIFY THE BALANCES : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyResults'), [('Verification') : Verification,('Description') : Description,('CuentaOrigen') : CuentaOrigen, ('CuentaDestino') : CuentaDestino,('CtaOrigen') : CtaOrigen, ('CtaDestino') : CtaDestino,('Monto') : GlobalVariable.OriginalMonto,('Impuesto') : Impuesto], FailureHandling.OPTIONAL)
	
		}

//	} else if (GlobalVariable.AuthorizationStatus == "Failed" || GlobalVariable.AuthorizationStatus == "" ) {
//
//		println('Authorization Flow Is Failed - Please Review')
//	}
}

def SelectMonto() {
	if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
		GlobalVariable.origen_before_app = "SelectionFailed"
		println('Origen Account Not Available - Skipping Monto Flow...!!!')
	} else {
		Mobile.tap(findTestObject('PagosYTransferencia/Digita un monto'), 5, FailureHandling.OPTIONAL)
		if(Mobile.waitForElementPresent(findTestObject('TarjetaPropia/Monto a pagar Title'), 30, FailureHandling.OPTIONAL)) {
			println('Monto a pagar screen is present - User can select monto..!!!')
		}
		
		if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Monto total'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('ImpuestosYServicious/Monto total'), 5, FailureHandling.CONTINUE_ON_FAILURE)
			
		} else if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Monto - ViewGroup1'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('ImpuestosYServicious/Monto - ViewGroup1'), 5, FailureHandling.CONTINUE_ON_FAILURE)
			
		} else if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Monto - ViewGroup2'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('ImpuestosYServicious/Monto - ViewGroup2'), 5, FailureHandling.CONTINUE_ON_FAILURE)
			
		} else if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/ContainsMonto'), 1, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('ImpuestosYServicious/ContainsMonto'), 5, FailureHandling.CONTINUE_ON_FAILURE)
			
		} else {
			println('Monto Not Found..')
			KeywordUtil.markFailed('Monto Not Found - Unable To Select Monto..!!')
		}
	}
	
	try {
		Mobile.delay(1)
		String checkMonto = Monto
		if(checkMonto.matches(".*[a-zA-Z]+.*")) {
			String preDefinedMonto = Mobile.getText(findTestObject('ImpuestosYServicious/Monto on Datos screen'), 3, FailureHandling.OPTIONAL)
			String Balance1 = preDefinedMonto.replaceAll('[,]', '')
			String Balance2 = Balance1.substring(4)
			GlobalVariable.OriginalMonto = Balance2
		}
	} catch (Exception e) {
		GlobalVariable.OriginalMonto = Monto
	}
}

def TransactionType() {
	'TAP ON HAMBURGER MENU : '
	Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
	if(Mobile.verifyElementVisible(findTestObject('UserDetails/LoggedUserName'), 1, FailureHandling.OPTIONAL) == false){
		Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 1, FailureHandling.OPTIONAL)
	}
	
	if (Type == 'NA') {
		println('Non Payment Transaction Scenario - So Transaction Part Is Skipped...!!!')
	} else if ((Type == 'Payment') && (Concept == 'SwipeVerification')) {
		println('Non Payment Transaction Scenario - So Transaction Part Is Skipped...!!!')
	} else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('PagosYTransferencia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.tap(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Impuestos y servicios'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	
	} else {
		Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
		if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('PagosYTransferencia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Impuestos y servicios'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	
		} else {
			Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('PagosYTransferencia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
			Mobile.tap(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Impuestos y servicios'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		}
	}
	
	Mobile.delay(1)
	
	if (Catagory=='Servicios') {
		Mobile.tap(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Servicios'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	} else if (Catagory=='Impuestos') {
		Mobile.tap(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Impuestos'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	} else if (Catagory=='Recargas') {
		Mobile.tap(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Recargas'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
}


//'CLOSING THE APPLICATION : '
//Mobile.closeApplication()