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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

GlobalVariable.BenefUnicoDetails = "True"
GlobalVariable.destino_before_app = "Destino is PagoUnico - No Balance is visible"

if (CtaDestino == '' && !Description.contains("Blank Reference")) {
	GlobalVariable.destino_before_app = "NotAvailable"
	GlobalVariable.OrigenAccountStatus = "NotAvailable"
    println('Destino Account Not Available...!!!')
	
} else if(GlobalVariable.OrigenAccountStatus.equals("NotAvailable")) {
	GlobalVariable.destino_before_app = "NotAvailable"
	GlobalVariable.OrigenAccountStatus = "NotAvailable"
	KeywordUtil.markFailedAndStop('Origen Account Not Selected So Skipping Further Flow..!!!')
	
} else {

    Mobile.tap(findTestObject('PagosYTransferencia/Destino-Title'), 5, FailureHandling.OPTIONAL)

    Mobile.delay(1)
	
	if (Mobile.verifyElementAttributeValue(findTestObject('Object Repository/PagosYTransferencia/3.Pago Unico/Pago Unico Button'), 'clickable',  'true', 2, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/3.Pago Unico/Pago Unico Button'), 2, FailureHandling.OPTIONAL)
	
		if(Banco == 'BanReservas' || Banco == '') {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoBanco'), [('Banco') : Banco], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoMoneda'), [('Moneda') : Moneda], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoTipoDeProducto'), [('TipoDeProducto') : TipoDeProducto], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoReferenceNumber'), [('CuentaDestino') : CuentaDestino, ('Description') : Description], FailureHandling.STOP_ON_FAILURE)
			
			//CustomKeywords.'helper.customfunction.Screenshot'()
			
			Mobile.delay(1)
				
			Mobile.tap(findTestObject('Object Repository/Buttons/Confirmar- 2'), 5, FailureHandling.OPTIONAL)
		} else {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoBanco'), [('Banco') : Banco], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoMoneda'), [('Moneda') : Moneda], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoTipoDeProducto'), [('TipoDeProducto') : TipoDeProducto], FailureHandling.STOP_ON_FAILURE)
		
			WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoReferenceNumber'), [('CuentaDestino') : CuentaDestino, ('Description') : Description], FailureHandling.STOP_ON_FAILURE)
			
			//CustomKeywords.'helper.customfunction.Screenshot'()
				
			WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoTipoDeDocumento'), [('TipoDeDocumento') : TipoDeDocumento], FailureHandling.STOP_ON_FAILURE)
			
			if(TipoDeDocumento == 'RNC' || TipoDeDocumento == 'Cédula') {
				WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoNumeroDeDocumento'), [('NumeroDeDocumento') : NumeroDeDocumento, ('TipoDeDocumento') : TipoDeDocumento], FailureHandling.STOP_ON_FAILURE)
			} else if(TipoDeDocumento == 'Pasaporte'){
				CustomKeywords.'helper.customfunction.swipeUP'()
				WebUI.callTestCase(findTestCase('CommonTestCases/TC-PagoUnicoNumeroDeDocumento'), [('NumeroDeDocumento') : NumeroDeDocumento, ('TipoDeDocumento') : TipoDeDocumento], FailureHandling.STOP_ON_FAILURE)
				Mobile.tap(findTestObject('Object Repository/TransferenciaLBTRPagoUnico/Ingresa nombre del beneficiario'), 1, FailureHandling.CONTINUE_ON_FAILURE)
				Mobile.setText(findTestObject('Object Repository/TransferenciaLBTRPagoUnico/Ingresa nombre del beneficiario'), Banco, 2, FailureHandling.STOP_ON_FAILURE)
				CustomKeywords.'helper.customfunction.HideKeyboard'()
				CustomKeywords.'helper.customfunction.Screenshot'()
			}
			
			//CustomKeywords.'helper.customfunction.Screenshot'()
			
			Mobile.tap(findTestObject('Object Repository/Buttons/Confirmar- 2'), 5, FailureHandling.OPTIONAL)
			
			Mobile.delay(1)
		}
	} else {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.logInfo('Pago Unico Button Is Disabled...!!!')
			GlobalVariable.CaseStatusMessage += "\n Pago Unico Button Is Disabled...!!"
			KeywordUtil.markFailed('Pago Unico Button Is Disabled For Case ' + Description)
			
	}
}
