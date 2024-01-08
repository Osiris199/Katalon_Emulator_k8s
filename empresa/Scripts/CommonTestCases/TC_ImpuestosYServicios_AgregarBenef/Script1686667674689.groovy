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

if (CtaDestino == 'NA'|| CtaDestino == '') {
   KeywordUtil.markPassed('Destino Account Not Available...!!!')
   GlobalVariable.destino_before_app = "SelectionFailed"
   GlobalVariable.DestinoAccountStatus = "NotAvailable"
} else {
	GlobalVariable.destino_before_app = "Destino Is Beneficiario - No Balance Is Visible"
	GlobalVariable.CurrencyTypeDestino = "No Currency Info"
	
	if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Impuestos y servicios'), 3, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Impuestos y servicios'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(1)
		Mobile.tap(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Servicios'), 5, FailureHandling.OPTIONAL)
		if(Alias!='') {
			CustomKeywords.'helper.customfunction.PagoUnicoHelper'(Alias, 'ImpuestosYServicious/ImpuestosYServicios - Servicios')
		} else {
			// Go back to 'Impuestos y servicios' screen
			for(int i=0;i<4;i++) {
				if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/ImpuestosYServicios - Impuestos y servicios'), 2, FailureHandling.OPTIONAL)) {
					break
				} else {
					Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3, FailureHandling.OPTIONAL)
					Mobile.delay(1)
				}
			}
		}
		
		
		Mobile.setText(findTestObject('ImpuestosYServicious/Digita el cdigo de referencia'),CtaDestino,  3, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
		Mobile.delay(5)
		Mobile.setText(findTestObject('ImpuestosYServicious/Digita un alias para el servicio'), Alias, 3, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
		
		if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Button - Guardar'), 5, FailureHandling.OPTIONAL)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			Mobile.tap(findTestObject('ImpuestosYServicious/Button - Guardar'), 3, FailureHandling.OPTIONAL)
		} else {
			KeywordUtil.markFailed('Guardar Button Not Found..!!')
		}
	} else {
		KeywordUtil.markFailed('Impuestos Y Servicios Button Not Found - Can Not Add Beneficiary..!!')
	}
}
