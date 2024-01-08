import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.model.FailureHandling

import internal.GlobalVariable
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.android.nativekey.KeyEvent

if (NumeroDeDocumento == '') {
    println('Numero documento Not Available...!!!')
} else {
	CustomKeywords.'helper.customfunction.swipeUP'()
	
	Mobile.tap(findTestObject('Object Repository/TransferenciaLBTRPagoUnico/Introducir nmero de documento'), 1, FailureHandling.CONTINUE_ON_FAILURE)
	
    Mobile.setText(findTestObject('Object Repository/TransferenciaLBTRPagoUnico/Introducir nmero de documento'), NumeroDeDocumento, 2, FailureHandling.CONTINUE_ON_FAILURE)
	
	if(GlobalVariable.device == "Android Studio") {
		int device_Height, device_Width
		
		device_Height = Mobile.getDeviceHeight() - 5
		device_Width = Mobile.getDeviceWidth() - 5
		
		Mobile.tapAtPosition(device_Width, device_Height, FailureHandling.OPTIONAL)
	} else {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
	}
	
	CustomKeywords.'helper.customfunction.HideKeyboard'()
	
	Mobile.delay(8)
	
	if(TipoDeDocumento != 'Pasaporte') {
		CustomKeywords.'helper.customfunction.Screenshot'()
	}
}