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

'IMPUESTOS Y SERVICIOS - AGREGAR BENEF : '
WebUI.comment((ID + ' - ') + Description)

//'PROFILE VERIFICATION FOR DATABASE CONNECTION : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_ProfileVerification'), [('Verification') : Verification, ('Description') : Description,('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino, ('Impuesto'):Impuesto], FailureHandling.STOP_ON_FAILURE)

'APPLICATION LAUNCH : '
WebUI.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)

'USER LOGGED IN VERIFICATION : '
CustomKeywords.'helper.customfunction.QuickLogin'(RNC, Username1,Username2,Username3, Password, Email, Role, Type)

TransactionType()

if(Concept.contains('Agregar')) {
	
	'CHECK FOR EXISTING BENEFICIARY : '
	CheckExistingBenf()
	
	'ATTEMPT AGREGAR BENEFICIARIO : '
	Mobile.tap(findTestObject('ImpuestosYServicious/Agregar beneficiario de servicio'), 20, FailureHandling.OPTIONAL)
	Mobile.tap(findTestObject('ImpuestosYServicious/Aadir beneficiario de producto'), 1, FailureHandling.OPTIONAL)
//	Mobile.tap(findTestObject('ImpuestosYServicious/Agregar beneficiario de servicio'), 1, FailureHandling.OPTIONAL)
//	Mobile.tap(findTestObject('ImpuestosYServicious/Aadir beneficiario de producto'), 1, FailureHandling.OPTIONAL)
	Mobile.delay(1)
	
	if (CtaDestino == '') {
		println('Destino Account Not Available...!!!')
	} else {
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_ImpuestosYServicios_AgregarBenef'), [('CtaDestino') : CtaDestino, ('Banco') : Banco, ('Moneda') : Moneda,
		('TipoDeProducto') : TipoDeProducto, ('CuentaDestino') : CuentaDestino, ('Description') : Description, ('TipoDeDocumento') : TipoDeDocumento,
		('Alias') : Alias, ('NumeroDeDocumento') : NumeroDeDocumento], FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	// Check for beneficiary added or not
	if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Agregar beneficiario de servicio'), 30, FailureHandling.OPTIONAL)||
		Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Aadir beneficiario de producto'), 2, FailureHandling.OPTIONAL)) {
		try {
			Mobile.scrollToText(CtaDestino, FailureHandling.CONTINUE_ON_FAILURE)
			KeywordUtil.markPassed('Beneficiary Added Successfully..!!')
		} catch(Exception e) {
			KeywordUtil.markFailed('Failed To Add Beneficiary..!!')
		}
	} else {
		 KeywordUtil.markFailed('Failed To Add Beneficiary..!!')
	}
	CustomKeywords.'helper.customfunction.Screenshot'()

} else if(Concept.contains('Eliminar')) {
	WebUI.callTestCase(findTestCase('CommonTestCases/TC_ImpuestosYServicios_EliminarBenef'), [('Alias') : Alias, ('Concept') : Concept, ('CtaDestino') : CtaDestino, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
}

//'VERIFICATION SCENARIO TEST CASE :'
//CustomKeywords.'helper.paymenthelper.PaymentResponses'()
//CustomKeywords.'helper.customfunction.VerifyTransactionMessage'(Username1, Username2, Username3)
//CustomKeywords.'helper.customfunction.HistoricoPendiente'()


def TransactionType() {
	if (Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Configuracin - Title'), 8, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('ImpuestosYServicious/Configuracin - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
	} else {
		Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
		if (Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Configuracin - Title'), 5, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('ImpuestosYServicious/Configuracin - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
		} else {
			Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('ImpuestosYServicious/Configuracin - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(1)
		}
	}
	
	if(Description.contains('Servicios')){
		Mobile.tap(findTestObject('ImpuestosYServicious/Servicios - Title'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
		if (Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Agregar beneficiario de servicio'), 30, FailureHandling.CONTINUE_ON_FAILURE)) {
		} 
	} else if(Description.contains('Productos')){
		Mobile.tap(findTestObject('ImpuestosYServicious/Productos - Title'), 10, FailureHandling.CONTINUE_ON_FAILURE)
		Mobile.delay(1)
		if (Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Aadir beneficiario de producto'), 30, FailureHandling.CONTINUE_ON_FAILURE)) {
		}
	}
}

def CheckExistingBenf() {
	try {
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_ImpuestosYServicios_EliminarBenef'), [('Alias') : Alias, ('Concept') : Concept, ('CtaDestino') : CtaDestino, ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
		//RefreshBenef()
	} catch(Exception e) {
		KeywordUtil.markPassed('Beneficiary For Account: ' + CtaDestino + ' Does Not Exist - User Can Add Beneficiary..!!')
	}
}

def RefreshBenef() {
	//Mobile.tap(findTestObject('TransferenciaPropia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
	if (Mobile.waitForElementPresent(findTestObject('Buttons/Back-ArrowButton'), 3, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	} else {
		Mobile.pressBack(FailureHandling.OPTIONAL)
	}
	
	Mobile.delay(1)
	
	if(Description.contains('Servicios')){
		Mobile.tap(findTestObject('ImpuestosYServicious/Servicios - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	} else if(Description.contains('Productos')){
		Mobile.tap(findTestObject('ImpuestosYServicious/Productos - Title'), 3, FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	Mobile.delay(3)
}