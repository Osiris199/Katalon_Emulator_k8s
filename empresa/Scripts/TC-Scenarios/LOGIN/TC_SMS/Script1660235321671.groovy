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

SMS()

Permissions()
//CustomKeywords.'helper.customfunction.VerificationDashboard'(Description)

def SMS() {
    'LOGIN SMS SCENARIOS '

    if (Mobile.waitForElementPresent(findTestObject('Login/INGRESA EL CDIGO DE VERIFICACIN'), 2, FailureHandling.STOP_ON_FAILURE)) {
        Mobile.setText(findTestObject('Login/INGRESA EL CDIGO DE VERIFICACIN'), SMS, 2, FailureHandling.OPTIONAL)
		CustomKeywords.'helper.customfunction.HideKeyboard'()
       // CustomKeywords.'helper.customfunction.Screenshot'()

        Mobile.tap(findTestObject('Login/CONTINUAR'), 1, FailureHandling.OPTIONAL)

        CustomKeywords.'helper.customfunction.LoginResponses'()
		
		//CustomKeywords.'helper.customfunction.Screenshot'()
		
        Mobile.tap(findTestObject('Login/ACEPTAR'), 1, FailureHandling.OPTIONAL)

        Mobile.clearText(findTestObject('Login/ClearText'), 1, FailureHandling.OPTIONAL)
    } else {
        CustomKeywords.'helper.customfunction.Screenshot'()

        KeywordUtil.markFailed('Scenario Failed...!!!' + Description)
    }
}

def Permissions() {
	if (Mobile.waitForElementPresent(findTestObject('Login/Izquierda'), 2, FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('Scenario Passed...!!!' + Description)
		Mobile.tap(findTestObject('RegistroDeDispositivo/Confirmar'), 5,FailureHandling.OPTIONAL)
		Mobile.delay(0.5)
		//CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('Buttons/Si'), 5,FailureHandling.OPTIONAL)
		Mobile.delay(0.5)
		//CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('Buttons/Si'), 5,FailureHandling.OPTIONAL)
		Mobile.delay(0.5)
	} else if (Mobile.waitForElementPresent(findTestObject('Buttons/Si'), 2, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Buttons/Si'), 2,FailureHandling.OPTIONAL)
		Mobile.tap(findTestObject('Buttons/Si'), 2,FailureHandling.OPTIONAL)
	}
}