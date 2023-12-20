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

if (Method == 'ACH') {
	Mobile.tap(findTestObject('Interbancario_Beneficiary/Selecciona el mtodo de pago'),3, FailureHandling.OPTIONAL)	
	Mobile.delay(1)
	if(Mobile.verifyElementVisible(findTestObject('Interbancario_Beneficiary/ACH'), 2, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Interbancario_Beneficiary/ACH'), 3, FailureHandling.OPTIONAL)
	} else {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed("ACH not found please check !!")
	}
	
} else if (Method == 'Pago al Instante BCRD') {
	Mobile.tap(findTestObject('Interbancario_Beneficiary/Selecciona el mtodo de pago'),2)
	Mobile.delay(1)
	if(Mobile.verifyElementVisible(findTestObject('Object Repository/Interbancario_Beneficiary/Pago al Instante BCRD'), 2, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Object Repository/Interbancario_Beneficiary/Pago al Instante BCRD'),3, FailureHandling.OPTIONAL)
	} else {
		//CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailed("Pago al Instante BCRD not found please check !!")
	}
} else {
	println "Skip Selecting Method Scenario...!!!"
	Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 3, FailureHandling.OPTIONAL)
}
