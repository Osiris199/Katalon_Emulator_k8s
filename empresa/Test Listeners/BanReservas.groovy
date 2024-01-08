import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import io.appium.java_client.AppiumDriver

class BanReservas {

	@BeforeTestCase
	def BeforeTestCase(TestCaseContext testCaseContext) {
		//println testCaseContext.getTestCaseId()
		//println testCaseContext.getTestCaseStatus()
		
		try {
			String cmd = 'taskkill /im node.exe /f'
			Runtime.getRuntime().exec(cmd)
			Mobile.delay(0.5)
		} catch (Exception e) {
			KeywordUtil.logInfo("Cannot execute windows cmd on mac os")
		}
		
		String TestCaseName=testCaseContext.getTestCaseId()

		if(TestCaseName.contains("APPLAUNCH") || TestCaseName.contains("LOGIN") || TestCaseName.contains("RESPONSES")) {
			
			if("PASSED".equalsIgnoreCase(GlobalVariable.Status)) {
				println "CURRENT TEST CASE EXECUTION : " + testCaseContext.getTestCaseId()
								
			} else if ("FAILED".equalsIgnoreCase(GlobalVariable.Status) || "SKIPPED".equalsIgnoreCase(GlobalVariable.Status)) {	
				
				println "SKIPPED TEST CASE NAME : " + testCaseContext.getTestCaseId()
				testCaseContext.skipThisTestCase()
			}			
					
		} else {
			println "CURRENT TEST CASE EXECUTION IS " + testCaseContext.getTestCaseId()
						
			'----------------------------RESETTING THE VALUES--------------------------------------'
			
				GlobalVariable.origen_before_db = ''
				GlobalVariable.destino_before_db = ''
				GlobalVariable.origen_after_db = ''
				GlobalVariable.destino_after_db = ''
				GlobalVariable.origen_before_app = ''
				GlobalVariable.destino_before_app = ''
				GlobalVariable.origen_after_app = ''
				GlobalVariable.destino_after_app = ''
				
				GlobalVariable.PaymentStatus = ''
				GlobalVariable.OrigenAccountStatus = ''
				GlobalVariable.DestinoAccountStatus = ''
				GlobalVariable.CaseDescription = ''
				
				GlobalVariable.Tasa = ''
				
				GlobalVariable.PaymentMessage = ''
				GlobalVariable.PaymentTime = ''
				GlobalVariable.PaymentReciept = ''
				GlobalVariable.PaymentAmount = ''
				GlobalVariable.PaymentDestino = ''
				GlobalVariable.AuthorizePaymentMesg = ''
				GlobalVariable.CuentaOrigen = ''
				GlobalVariable.CuentaDestino = ''
				GlobalVariable.Impuesto = ''
				GlobalVariable.Verification = ''
				
				GlobalVariable.TokenImportStatus = ''
				GlobalVariable.Otp = ''
				GlobalVariable.ExpectedPaymentMessage = ''
				GlobalVariable.PaymentMessage = ''
				GlobalVariable.AppPaymentMessage = ''
				GlobalVariable.CaseStatusMessage = ''
				GlobalVariable.TransactionMessage = ''
				GlobalVariable.TransactionMessageAuthorizer1 = ''
				GlobalVariable.TransactionMessageAuthorizer2 = ''
				GlobalVariable.Tax_Origenbalance_after = ''
				GlobalVariable.Tax_Destinobalance_after = ''
				GlobalVariable.PaymentMessageMultiple = ''
				GlobalVariable.Counter = ''
				GlobalVariable.CommissionAmount = ''
	'--------------------------------------------------------------------------------------'
		}
	}
		

	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		Mobile.closeApplication()
		println testCaseContext.getTestCaseId()
		println testCaseContext.getTestCaseStatus()
		
		String TestCaseName=testCaseContext.getTestCaseId()
		String Status=testCaseContext.getTestCaseStatus()
		GlobalVariable.Status = Status
		
		if(TestCaseName.contains("APPLAUNCH") || TestCaseName.contains("LOGIN") || TestCaseName.contains("RESPONSES")) {
			
			KeywordUtil.logInfo('Test Case Id Is : ' + testCaseContext.getTestCaseId())
			KeywordUtil.logInfo('Test Case Status Is : ' + testCaseContext.getTestCaseStatus())
					
		} else if(GlobalVariable.Verification == 'NA') {	
		// else if(GlobalVariable.Verification == 'NA' || GlobalVariable.Verification == 'ExceedOrigenAmount') {
			KeywordUtil.logInfo(('------------------ : DETAILED REPORT : ------------------' +
				'\n'+
				'\n'+
				'BALANCE VERIFICATION NOT NECESSARY : ' + 
				'CASE DESCRIPTION : ' + GlobalVariable.CaseDescription +
				'\n'+
				'\n'+
				' * EXPECTED TRANSACTION MESSAGE : ' + GlobalVariable.TransactionMessage +
				'\n'+
				' * TRANSACTION MESSAGE IN APP : ' + GlobalVariable.PaymentMessage +
				'\n'+
				'\n'+
				GlobalVariable.CaseStatusMessage+
				'\n'+
				'-----------------------------------------------------------'))
			
		} else if (testCaseContext.testCaseId.contains('TRANSACCIONES_MULTIPLES')) {
			KeywordUtil.logInfo(GlobalVariable.MultiDestinoValue)
		} else if(GlobalVariable.TransactionMessageAuthorizer1 == 'NA' || GlobalVariable.TransactionMessageAuthorizer1== '') {
			if(testCaseContext.testCaseId.contains('PAGO_TARJETA_CREDITO_PROPIA_DOP_USD')){
				KeywordUtil.logInfo((
				'\n'+
				'------------------------ : DETAILED REPORT : -----------------------' +
				'\n'+
				'\n'+
				'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
				'\n'+
				'\n'+
				' * TRANSACTION MESSAGE EXPECTED: ' + GlobalVariable.TransactionMessage +
				'\n'+
				' * TRANSACTION MESSAGE APP: ' + GlobalVariable.PaymentMessage +
				'\n'+
				'\n'+
				' * PAYMENT TIME : ' + GlobalVariable.PaymentTime +
				'\n'+
				' * PAYMENT RECIEPT : ' + GlobalVariable.PaymentReciept +
				'\n'+
				' * TRANSFER MONTO AMOUNT IS : '+ GlobalVariable.montoReplace +' '+ GlobalVariable.OriginalMonto +
				'\n'+
				' * RATE APPLIED : ' + GlobalVariable.Tasa +
				'\n'+
				' * TAX APPLIED : ' + GlobalVariable.Impuesto +
//				'\n'+
//				' * AUTHORIZATION MESSAGE : ' + GlobalVariable.AuthorizePaymentMesg +
				'\n'+
				' * TOTAL TRANSFERED (MONTO / TASA / TAX) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.montoReplace + ' : ' + GlobalVariable.UpdatedMonto +
				'\n'+
				' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
				'\n'+
				'\n'+
				'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_db + '\n' +
				'\n'+
				'\n'+
				'BALANCES BEFORE TRANSACTIONS (APPLICATION) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_app + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_app + '\n' +
				'\n'+
				'\n'+
				'BALANCES AFTER TRANSACTIONS (DATABASE) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_db  + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_db  + '\n' +
				'\n'+
				'\n'+
				'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : ' +
				'\n'+
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.Tax_Origenbalance_after  + '\n' +
				//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.Tax_Destinobalance_after  + '\n' +
				'\n'+
				'\n'+
				'BALANCES AFTER TRANSACTIONS (APPLICATION) : \n' +
				'Origen Account :-  ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_app  + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_app + '\n' +
				'\n'+
				'\n'+
				GlobalVariable.CaseStatusMessage +
				'\n'+
				'-------------------------------------------------------------------------'))
			} else {
				KeywordUtil.logInfo((
				'\n'+
				'------------------------ : DETAILED REPORT : -----------------------' +
				'\n'+
				'\n'+
				'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
				'\n'+
				'\n'+
				' * TRANSACTION MESSAGE EXPECTED: ' + GlobalVariable.TransactionMessage +
				'\n'+
				' * TRANSACTION MESSAGE APP: ' + GlobalVariable.PaymentMessage +
				'\n'+
				'\n'+
				' * PAYMENT TIME : ' + GlobalVariable.PaymentTime +
				'\n'+
				' * PAYMENT RECIEPT : ' + GlobalVariable.PaymentReciept +
				'\n'+
				' * TRANSFER MONTO AMOUNT IS : ' + GlobalVariable.OriginalMonto +
				'\n'+
				' * RATE APPLIED : ' + GlobalVariable.Tasa +
				'\n'+
				' * TAX APPLIED : ' + GlobalVariable.Impuesto +
//				'\n'+
//				' * AUTHORIZATION MESSAGE : ' + GlobalVariable.AuthorizePaymentMesg +
				'\n'+
				' * TOTAL TRANSFERED (MONTO / TASA) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.CurrencyTypeOrigen + ' : ' + GlobalVariable.UpdatedMonto +
				'\n'+
				' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
				'\n'+
				'\n'+
				'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_db + '\n' +
				'\n'+
				'\n'+
				'BALANCES BEFORE TRANSACTIONS (APPLICATION) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_app + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_app + '\n' +
				'\n'+
				'\n'+
				'BALANCES AFTER TRANSACTIONS (DATABASE) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_db  + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_db  + '\n' +
				'\n'+
				'\n'+
				'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : ' +
				'\n'+
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.Tax_Origenbalance_after  + '\n' +
				//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.Tax_Destinobalance_after  + '\n' +
				'\n'+
				'\n'+
				'BALANCES AFTER TRANSACTIONS (APPLICATION) : \n' +
				'Origen Account :-  ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_app  + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_app + '\n' +
				'\n'+
				'\n'+
				GlobalVariable.CaseStatusMessage +
				'\n'+
				'-------------------------------------------------------------------------'))
			}
		} else if(GlobalVariable.TransactionMessageAuthorizer2 == 'NA' || GlobalVariable.TransactionMessageAuthorizer2 == '') {
			if(testCaseContext.testCaseId.contains('PAGO_TARJETA_CREDITO_PROPIA_DOP_USD')) {
				KeywordUtil.logInfo((
					'\n'+
					'------------------------ : DETAILED REPORT : -----------------------' +
					'\n'+
					'\n'+
					'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
					'\n'+
					'\n'+
					' * TRANSACTION MESSAGE EXPECTED: ' + GlobalVariable.TransactionMessage +
					'\n'+
					' * TRANSACTION MESSAGE APP: ' + GlobalVariable.PaymentMessage +
					'\n'+
					' * TRANSACTION MESSAGE AUTHORIZER1 EXPECTED : ' + GlobalVariable.TransactionMessageAuthorizer1 +
					'\n'+
					' * TRANSACTION MESSAGE AUTHORIZER1 APP : ' + GlobalVariable.AuthorizePaymentMesg1 +
					'\n'+
					'\n'+
					' * PAYMENT TIME : ' + GlobalVariable.PaymentTime +
					'\n'+
					' * PAYMENT RECIEPT : ' + GlobalVariable.PaymentReciept +
					'\n'+
					' * TRANSFER MONTO AMOUNT IS : '+ GlobalVariable.montoReplace +' '+ GlobalVariable.OriginalMonto +
					'\n'+
					' * RATE APPLIED : ' + GlobalVariable.Tasa +
					'\n'+
					' * TAX APPLIED : ' + GlobalVariable.Impuesto +
	//				'\n'+
	//				' * AUTHORIZATION MESSAGE : ' + GlobalVariable.AuthorizePaymentMesg +
					'\n'+
					' * TOTAL TRANSFERED (MONTO / TASA / TAX) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.montoReplace + ' : ' + GlobalVariable.UpdatedMonto +
					'\n'+
					' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
					'\n'+
					'\n'+
					'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
					'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
					'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_db + '\n' +
					'\n'+
					'\n'+
					'BALANCES BEFORE TRANSACTIONS (APPLICATION) : \n' +
					'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_app + '\n' +
					'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_app + '\n' +
					'\n'+
					'\n'+
					'BALANCES AFTER TRANSACTIONS (DATABASE) : \n' +
					'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_db  + '\n' +
					'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_db  + '\n' +
					'\n'+
					'\n'+
					'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : ' +
					'\n'+
					'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.Tax_Origenbalance_after  + '\n' +
					//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.Tax_Destinobalance_after  + '\n' +
					'\n'+
					'\n'+
					'BALANCES AFTER TRANSACTIONS (APPLICATION) : \n' +
					'Origen Account :-  ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_app  + '\n' +
					'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_app + '\n' +
					'\n'+
					'\n'+
					GlobalVariable.CaseStatusMessage +
					'\n'+
					'-------------------------------------------------------------------------'))
			} else {
				KeywordUtil.logInfo((
					'\n'+
					'------------------------ : DETAILED REPORT : -----------------------' +
					'\n'+
					'\n'+
					'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
					'\n'+
					'\n'+
					' * TRANSACTION MESSAGE EXPECTED: ' + GlobalVariable.TransactionMessage +
					'\n'+
					' * TRANSACTION MESSAGE APP: ' + GlobalVariable.PaymentMessage +
					'\n'+
					' * TRANSACTION MESSAGE AUTHORIZER1 EXPECTED : ' + GlobalVariable.TransactionMessageAuthorizer1 +
					'\n'+
					' * TRANSACTION MESSAGE AUTHORIZER1 APP : ' + GlobalVariable.AuthorizePaymentMesg1 +
					'\n'+
					'\n'+
					' * PAYMENT TIME : ' + GlobalVariable.PaymentTime +
					'\n'+
					' * PAYMENT RECIEPT : ' + GlobalVariable.PaymentReciept +
					'\n'+
					' * TRANSFER MONTO AMOUNT IS : ' + GlobalVariable.OriginalMonto +
					'\n'+
					' * RATE APPLIED : ' + GlobalVariable.Tasa +
					'\n'+
					' * TAX APPLIED : ' + GlobalVariable.Impuesto +
	//				'\n'+
	//				' * AUTHORIZATION MESSAGE : ' + GlobalVariable.AuthorizePaymentMesg +
					'\n'+
					' * TOTAL TRANSFERED (MONTO / TASA) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.CurrencyTypeOrigen + ' : ' + GlobalVariable.UpdatedMonto +
					'\n'+
					' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
					'\n'+
					'\n'+
					'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
					'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
					'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_db + '\n' +
					'\n'+
					'\n'+
					'BALANCES BEFORE TRANSACTIONS (APPLICATION) : \n' +
					'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_app + '\n' +
					'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_app + '\n' +
					'\n'+
					'\n'+
					'BALANCES AFTER TRANSACTIONS (DATABASE) : \n' +
					'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_db  + '\n' +
					'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_db  + '\n' +
					'\n'+
					'\n'+
					'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : ' +
					'\n'+
					'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.Tax_Origenbalance_after  + '\n' +
					//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.Tax_Destinobalance_after  + '\n' +
					'\n'+
					'\n'+
					'BALANCES AFTER TRANSACTIONS (APPLICATION) : \n' +
					'Origen Account :-  ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_app  + '\n' +
					'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_app + '\n' +
					'\n'+
					'\n'+
					GlobalVariable.CaseStatusMessage +
					'\n'+
					'-------------------------------------------------------------------------'))
			}
		} else {
				if(testCaseContext.testCaseId.contains('PAGO_TARJETA_CREDITO_PROPIA_DOP_USD')){
				KeywordUtil.logInfo((
				'\n'+
				'------------------------ : DETAILED REPORT : -----------------------' +
				'\n'+
				'\n'+
				'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
				'\n'+
				'\n'+
				' * TRANSACTION MESSAGE EXPECTED: ' + GlobalVariable.TransactionMessage +
				'\n'+
				' * TRANSACTION MESSAGE APP: ' + GlobalVariable.PaymentMessage +
				'\n'+
				' * TRANSACTION MESSAGE AUTHORIZER1 EXPECTED : ' + GlobalVariable.TransactionMessageAuthorizer1 +
				'\n'+
				' * TRANSACTION MESSAGE AUTHORIZER1 APP : ' + GlobalVariable.AuthorizePaymentMesg1 +
				'\n'+
				' * TRANSACTION MESSAGE AUTHORIZER2 EXPECTED: ' + GlobalVariable.TransactionMessageAuthorizer2 +
				'\n'+
				' * TRANSACTION MESSAGE AUTHORIZER2 APP: ' + GlobalVariable.AuthorizePaymentMesg2 +
				'\n'+
				'\n'+
				' * PAYMENT TIME : ' + GlobalVariable.PaymentTime +
				'\n'+
				' * PAYMENT RECIEPT : ' + GlobalVariable.PaymentReciept +
				'\n'+
				' * TRANSFER MONTO AMOUNT IS : '+ GlobalVariable.montoReplace +' '+ GlobalVariable.OriginalMonto +
				'\n'+
				' * RATE APPLIED : ' + GlobalVariable.Tasa +
				'\n'+
				' * TAX APPLIED : ' + GlobalVariable.Impuesto +
//				'\n'+
//				' * AUTHORIZATION MESSAGE : ' + GlobalVariable.AuthorizePaymentMesg +
				'\n'+
				' * TOTAL TRANSFERED (MONTO / TASA / TAX) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.montoReplace + ' : ' + GlobalVariable.UpdatedMonto +
				'\n'+
				' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
				'\n'+
				'\n'+
				'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_db + '\n' +
				'\n'+
				'\n'+
				'BALANCES BEFORE TRANSACTIONS (APPLICATION) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_app + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_app + '\n' +
				'\n'+
				'\n'+
				'BALANCES AFTER TRANSACTIONS (DATABASE) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_db  + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_db  + '\n' +
				'\n'+
				'\n'+
				'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : ' +
				'\n'+
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.Tax_Origenbalance_after  + '\n' +
				//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.Tax_Destinobalance_after  + '\n' +
				'\n'+
				'\n'+
				'BALANCES AFTER TRANSACTIONS (APPLICATION) : \n' +
				'Origen Account :-  ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_app  + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_app + '\n' +
				'\n'+
				'\n'+
				GlobalVariable.CaseStatusMessage +
				'\n'+
				'-------------------------------------------------------------------------'))
				} else {
				KeywordUtil.logInfo((
				'\n'+
				'------------------------ : DETAILED REPORT : -----------------------' +
				'\n'+
				'\n'+
				'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
				'\n'+
				'\n'+
				' * TRANSACTION MESSAGE EXPECTED: ' + GlobalVariable.TransactionMessage +
				'\n'+
				' * TRANSACTION MESSAGE APP: ' + GlobalVariable.PaymentMessage +
				'\n'+
				' * TRANSACTION MESSAGE AUTHORIZER1 EXPECTED : ' + GlobalVariable.TransactionMessageAuthorizer1 +
				'\n'+
				' * TRANSACTION MESSAGE AUTHORIZER1 APP : ' + GlobalVariable.AuthorizePaymentMesg1 +
				'\n'+
				' * TRANSACTION MESSAGE AUTHORIZER2 EXPECTED: ' + GlobalVariable.TransactionMessageAuthorizer2 +
				'\n'+
				' * TRANSACTION MESSAGE AUTHORIZER2 APP: ' + GlobalVariable.AuthorizePaymentMesg2 +
				'\n'+
				'\n'+
				' * PAYMENT TIME : ' + GlobalVariable.PaymentTime +
				'\n'+
				' * PAYMENT RECIEPT : ' + GlobalVariable.PaymentReciept +
				'\n'+
				' * TRANSFER MONTO AMOUNT IS : ' + GlobalVariable.OriginalMonto +
				'\n'+
				' * RATE APPLIED : ' + GlobalVariable.Tasa +
				'\n'+
				' * TAX APPLIED : ' + GlobalVariable.Impuesto +
//				'\n'+
//				' * AUTHORIZATION MESSAGE : ' + GlobalVariable.AuthorizePaymentMesg +
				'\n'+
				' * TOTAL TRANSFERED (MONTO / TASA) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.CurrencyTypeOrigen + ' : ' + GlobalVariable.UpdatedMonto +
				'\n'+
				' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
				'\n'+
				'\n'+
				'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_db + '\n' +
				'\n'+
				'\n'+
				'BALANCES BEFORE TRANSACTIONS (APPLICATION) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_app + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_before_app + '\n' +
				'\n'+
				'\n'+
				'BALANCES AFTER TRANSACTIONS (DATABASE) : \n' +
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_db  + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_db  + '\n' +
				'\n'+
				'\n'+
				'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : ' +
				'\n'+
				'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.Tax_Origenbalance_after  + '\n' +
				//'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.Tax_Destinobalance_after  + '\n' +
				'\n'+
				'\n'+
				'BALANCES AFTER TRANSACTIONS (APPLICATION) : \n' +
				'Origen Account :-  ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_app  + '\n' +
				'Destino Account :- ' + GlobalVariable.CuentaDestino + ' Balance Is :- ' + GlobalVariable.CurrencyTypeDestino + ' - ' + GlobalVariable.destino_after_app + '\n' +
				'\n'+
				'\n'+
				GlobalVariable.CaseStatusMessage +
				'\n'+
				'-------------------------------------------------------------------------'))
				}
		}
		
	}


	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
	
	}

	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()

	}
}