import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.annotation.Keyword as Keyword


if (Monto == '') {
    println('Monto Option Is Skipped...!!!')
    CustomKeywords.'helper.customfunction.continuar'()
	
} else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
    println('Origen Account Not Available - Skipping Monto Flow...!!!')
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
    //CustomKeywords.'helper.customfunction.Screenshot'()
    println('Destino Account Not Available...!!!')
} else {	
    Mobile.tap(findTestObject('PagosYTransferencia/Digita un monto'), 5, FailureHandling.CONTINUE_ON_FAILURE)
	
	if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/PrestamoPropia/Otro monto'), 40, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('PagosYTransferencia/PrestamoPropia/Otro monto'),5, FailureHandling.OPTIONAL)
		String data
		int j = 0
		int i = 0
		int num
		num = Monto.length()
		while (i < num) {
			Mobile.tap(findTestObject('Buttons/Numeros/Button' + Monto.substring(j, i + 1)), 0)
			j++
			i++
		}
	} else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/PrestamoPropia/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 30, FailureHandling.OPTIONAL)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('PagosYTransferencia/PrestamoPropia/ACEPTAR'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(2)
		Mobile.tap(findTestObject('PagosYTransferencia/PrestamoPropia/Digita un monto'),5, FailureHandling.OPTIONAL)
		Mobile.delay(5)
		Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/PrestamoPropia/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 5, FailureHandling.OPTIONAL)
		//CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('PagosYTransferencia/PrestamoPropia/Otro monto'),5, FailureHandling.OPTIONAL)
		String data
		int j = 0
		int i = 0
		int num
		num = Monto.length()
		while (i < num) {
			Mobile.tap(findTestObject('Buttons/Numeros/Button' + Monto.substring(j, i + 1)), 0)
			j++
			i++
		}		
	} 
	if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/PrestamoPropia/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 5, FailureHandling.OPTIONAL)) {
		//Mobile.tap(findTestObject('PagosYTransferencia/PrestamoPropia/ACEPTAR'), 5, FailureHandling.OPTIONAL)
		KeywordUtil.markFailedAndStop("Terminating Test Case Due To Error Message...!!!")
	}else {
		Mobile.tap(findTestObject('PagosYTransferencia/Confirmar'), 3, FailureHandling.OPTIONAL)
		Mobile.delay(2)
	}

}

GlobalVariable.OriginalMonto = Monto