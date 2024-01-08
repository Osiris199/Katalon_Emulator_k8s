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
import com.kms.katalon.core.annotation.Keyword as Keyword

Mobile.delay(3)

if (Mobile.waitForElementPresent(findTestObject('Tasa/GetTasa'), 5, FailureHandling.OPTIONAL)) {
	GlobalVariable.Tasa = Mobile.getText(findTestObject('Tasa/GetTasa'), 3, FailureHandling.OPTIONAL)
	//CustomKeywords.'helper.customfunction.Screenshot'()
	
	if ((GlobalVariable.Tasa == 'Digita un concepto') || (GlobalVariable.Tasa == 'null')) {
		GlobalVariable.Tasa = "No Conversion Rate"
		println('Tasa Value : ' + GlobalVariable.Tasa)
	} 
//	else if(Mobile.waitForElementPresent(findTestObject('Tasa/GetTasa'), 5, FailureHandling.OPTIONAL)) {
//		Mobile.tap(findTestObject('Buttons/Continuar-2'), 3, FailureHandling.OPTIONAL)
//		GlobalVariable.Tasa = Mobile.getText(findTestObject('Tasa/GetTasa'), 60, FailureHandling.OPTIONAL)
//		double tasa = Double.parseDouble(GlobalVariable.Tasa)
//		GlobalVariable.Tasa = tasa
//	} 
	else {
		try {
			double tasa = Double.parseDouble(GlobalVariable.Tasa)
			println('Tasa Value : ' + tasa)
			GlobalVariable.Tasa = tasa
			//Mobile.tap(findTestObject('Buttons/Confirmar- 2'), 3, FailureHandling.OPTIONAL)
		 }
		catch (Exception e) {
			GlobalVariable.Tasa = '1'
			double tasa = Double.parseDouble(GlobalVariable.Tasa)
			GlobalVariable.Tasa = tasa
			println('Tasa Value In Catch : ' + GlobalVariable.Tasa)
			//e.printStackTrace()
		}
	}
	
} else {
	GlobalVariable.Tasa = "No Conversion Rate"
	println('Tasa Value : ' + GlobalVariable.Tasa)
	KeywordUtil.markPassed('Tasa Value Is Not Applicable...!!!')
}