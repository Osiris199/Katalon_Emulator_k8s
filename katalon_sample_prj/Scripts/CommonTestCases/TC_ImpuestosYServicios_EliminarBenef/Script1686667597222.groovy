import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory as MobileDriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import io.appium.java_client.AppiumDriver as AppiumDriver
import io.appium.java_client.MobileElement as MobileElement
import org.openqa.selenium.interactions.Actions


AppiumDriver<?> driver = MobileDriverFactory.getDriver()
Actions action = new Actions(driver);

try {
	Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
	MobileElement firstElement = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (contains(@text, '" + CtaDestino + "') or contains(., '"+ CtaDestino + "'))]"))
	KeywordUtil.markPassed('Deleting Beneficiary..!!')
	
	if(firstElement.displayed) {					
//		int midOfY = firstElement.getLocation().y +(firstElement.getSize().height/2);
//		int fromXLocation = firstElement.getLocation().x + 10;
//		int toXLocation = firstElement.getSize().width
//		Mobile.delay(1)
//		Mobile.swipe(fromXLocation, midOfY, toXLocation, midOfY)
//		//TouchAction action = new TouchAction(driver);
//		//action.longPress(PointOption.point(fromXLocation, midOfY)).moveTo(PointOption.point(toXLocation, midOfY)).perform()
//		Mobile.delay(2)
		
		int DeviceWidth = Mobile.getDeviceWidth(FailureHandling.OPTIONAL)
		int XLocation = firstElement.getLocation().getX()
		action.clickAndHold(firstElement).moveByOffset((DeviceWidth-XLocation),0).release().perform();
		WebUI.delay(2)
		
	} else {
		if(Concept.contains('Eliminar')) {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			GlobalVariable.CaseStatusMessage += "\n" + CtaDestino +" Beneficiary Is Not Dislayed - Unable To Delete Beneficiary..!! \n"
			KeywordUtil.markFailed(CtaDestino + ' Beneficiary Is Not Dislayed - Unable To Delete Beneficiary For Case ' + Description)
		} else {
			//CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markPassed(CtaDestino + ' Beneficiary Does Not Exist..!!')
		}	
	}
	
} catch(Exception e) {
	if(Concept.contains('Eliminar')) {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.CaseStatusMessage += "\n" + CtaDestino +" Beneficiary Is Not Dislayed - Unable To Delete Beneficiary..!! \n"
		KeywordUtil.markFailed(CtaDestino + ' Beneficiary Is Not Dislayed - Unable To Delete Beneficiary For Case ' + Description)
	} else {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markPassed(CtaDestino + ' Beneficiary Does Not Exist..!!')
	}
}


if(Concept.contains('Eliminar')) {
	if (Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Button - Eliminar'), 5, FailureHandling.OPTIONAL)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('ImpuestosYServicious/Button - Eliminar'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(2)
		
		// wait for page load
		if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Agregar beneficiario de servicio'), 15, FailureHandling.OPTIONAL)||
			Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Aadir beneficiario de producto'), 10, FailureHandling.OPTIONAL)) {
		}
		try {
			Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
			println('Failed To Eliminar Beneficiary..!!')
		} catch (Exception e) {
			KeywordUtil.markPassed('Beneficiary Deleted Successfully...!!')
		}
	} else {
		KeywordUtil.markFailed('Eliminar Button Not Found..!!')
	}
} else {
	if (Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Button - Eliminar'), 5, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('ImpuestosYServicious/Button - Eliminar'), 5, FailureHandling.OPTIONAL)
		Mobile.delay(2)
		
		// wait for page load
		if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Agregar beneficiario de servicio'), 15, FailureHandling.OPTIONAL)||
			Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Aadir beneficiario de producto'), 10, FailureHandling.OPTIONAL)) {
		}
		try {
			Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
			KeywordUtil.markPassed('Beneficiary Deleted Successfully...!!')
		} catch (Exception e) {
			println('Failed To Eliminar Beneficiary..!!')
		}
	} else {
		KeywordUtil.markWarning('Eliminar Button Not Found..!!')
	}
}