import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GlobalVariable

CheckDestino()

def CheckDestino() {
	GlobalVariable.BenefUnicoDetails = "True"
	GlobalVariable.destino_before_app = "Destino is PagoUnico - No Balance is visible"
	
	if (CtaDestino == '') {
		GlobalVariable.destino_before_app = "NotAvailable"
		GlobalVariable.OrigenAccountStatus = "NotAvailable"
		println('Destino Account Not Available...!!!')
		KeywordUtil.markPassed('Payment Transaction Success...!!!')
		
	} else if (GlobalVariable.OrigenAccountStatus == "NotAvailable"){
		GlobalVariable.destino_before_app = "NotAvailable"
		GlobalVariable.OrigenAccountStatus = "NotAvailable"
		KeywordUtil.markPassed('Origen Account Not Available So Skipping Further Flow...!!!')
		
	} else {
		if (Catagory=='Servicios') {
			SelectServicios()
		} else if (Catagory=='Impuestos') {
			SelectImpuestos()
		} else if (Catagory=='Recargas') {
			SelectRecargas()
		}
	}
}


def SelectServicios() {
	Mobile.tap(findTestObject('ImpuestosYServicious/android.widget.TextView - Servicio'), 5, FailureHandling.OPTIONAL)
	Mobile.delay(1)
	
	if(Description.contains('PagoUnico')) {
		Mobile.tap(findTestObject('PagosYTransferencia/3.Pago Unico/Pago Unico Button'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(1)
		if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Servicios - Selecciona'), 15, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('ImpuestosYServicious/Servicios - Selecciona'), 2, FailureHandling.OPTIONAL)
			Mobile.delay(1)
			CustomKeywords.'helper.customfunction.PagoUnicoHelper'(CtaDestino, 'ImpuestosYServicious/android.widget.TextView - Servicio')
			
			Mobile.setText(findTestObject('ImpuestosYServicious/Digita el cdigo de referencia'), CuentaDestino, 5, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
			Mobile.delay(2)
			Mobile.setText(findTestObject('ImpuestosYServicious/Digita un alias para el servicio'), Alias, 3, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
			
			if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Servicios - Datos del servicio'), 2, FailureHandling.OPTIONAL)) {
				CustomKeywords.'helper.customfunction.Screenshot'()
			}
			Mobile.tap(findTestObject('Buttons/Continuar-2'), 1, FailureHandling.OPTIONAL)
		}
	} else {
		CustomKeywords.'helper.customfunction.PagoUnicoHelper'(CtaDestino, 'ImpuestosYServicious/android.widget.TextView - Servicio')
	}
}


def SelectImpuestos() {
	Mobile.tap(findTestObject('ImpuestosYServicious/android.widget.TextView - Impuesto'), 5, FailureHandling.OPTIONAL)
	Mobile.delay(1)
	
	if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Selecciona el tipo de impuesto'), 15, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('ImpuestosYServicious/Selecciona el tipo de impuesto'), 5, FailureHandling.OPTIONAL)
		Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Heading-Impuesto'), 15, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.PagoUnicoHelper'(CtaDestino, 'ImpuestosYServicious/Selecciona el tipo de impuesto')
	} else {
		CustomKeywords.'helper.customfunction.PagoUnicoHelper'(CtaDestino, 'ImpuestosYServicious/Selecciona el tipo de impuesto')
	}
	
	Mobile.delay(1)
	Mobile.setText(findTestObject('ImpuestosYServicious/Digita el cdigo de referencia (1)'), CuentaDestino, 5, FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.HideKeyboard'()
	Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 1, FailureHandling.OPTIONAL)
}


def SelectRecargas() {
	Mobile.tap(findTestObject('ImpuestosYServicious/android.widget.TextView - Proveedor'), 5, FailureHandling.OPTIONAL)
	Mobile.delay(1)
	
	if(Description.contains('PagoUnico')) {
		Mobile.tap(findTestObject('PagosYTransferencia/3.Pago Unico/Pago Unico Button'), 15, FailureHandling.OPTIONAL)
		Mobile.delay(1)
		if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Servicios - Selecciona'), 15, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('ImpuestosYServicious/Servicios - Selecciona'), 2, FailureHandling.OPTIONAL)
			Mobile.delay(1)
			CustomKeywords.'helper.customfunction.PagoUnicoHelper'(CtaDestino, 'ImpuestosYServicious/Servicios - Selecciona')
			
			Mobile.setText(findTestObject('ImpuestosYServicious/Digita el cdigo de referencia'), CuentaDestino, 5, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
			Mobile.delay(2)
			Mobile.setText(findTestObject('ImpuestosYServicious/Digita un alias para el servicio'), Alias, 3, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
			
			if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Servicios - Datos del servicio'), 2, FailureHandling.OPTIONAL)) {
				CustomKeywords.'helper.customfunction.Screenshot'()
			}
			Mobile.tap(findTestObject('Buttons/Continuar-2'), 1, FailureHandling.OPTIONAL)
		}
	} else {
		CustomKeywords.'helper.customfunction.PagoUnicoHelper'(CtaDestino, 'ImpuestosYServicious/android.widget.TextView - Proveedor')
	}
}