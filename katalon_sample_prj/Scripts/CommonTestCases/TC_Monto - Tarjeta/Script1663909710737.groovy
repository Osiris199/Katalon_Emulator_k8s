import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable as GlobalVariable

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

	if(Mobile.verifyElementVisible(findTestObject('Object Repository/TarjetaPropia/Monto a pagar Title'), 5, FailureHandling.STOP_ON_FAILURE)){
		
		CustomKeywords.'helper.customfunction.TarjetaMonto'(MontoCurrency)
		
		if(MontoType.equals("Otro monto")) {
			CustomKeywords.'helper.customfunction.TarjetaMonto'(MontoType)
			
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
				
			Mobile.tap(findTestObject('PagosYTransferencia/Confirmar'), 3, FailureHandling.OPTIONAL)
			
			Mobile.delay(2)
			
		} else {
			CustomKeywords.'helper.customfunction.TarjetaMonto'(MontoType)
		}
			
	} else {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.logInfo('Monto Screen not visible please check!!!')
			KeywordUtil.markFailed('Monto Screen not visible please check!!!' + Description)
	}
}

GlobalVariable.OriginalMonto = Monto
