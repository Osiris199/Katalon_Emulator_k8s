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

Email()

Permissions()

//CustomKeywords.'helper.customfunction.VerificationDashboard'(Description)

def Email() {
    'LOGIN EMAIL SCENARIOS '

    if (Mobile.waitForElementPresent(findTestObject('Login/Registro de dispositivo'), 2, FailureHandling.OPTIONAL)) {
        //CustomKeywords.'helper.customfunction.Screenshot'()

        KeywordUtil.markPassed('Valid RNC Username Password Is Passed...!!!')

        Mobile.tap(findTestObject('Login/Email-box'), 1, FailureHandling.OPTIONAL)

        Mobile.delay(1)

        Mobile.tap(findTestObject('Login/CONTINUAR'), 1, FailureHandling.OPTIONAL)

        Mobile.delay(1)

        Mobile.tap(findTestObject('Login/ACEPTAR'), 150, FailureHandling.OPTIONAL)

        Mobile.delay(1)
    } else if(Mobile.waitForElementPresent(findTestObject('TokenImport/Dispositivo pendiente de activacin'), 3, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Continuar'), 1, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('TokenImport/Salir'), 1, FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('Token Import Required..!!!')
		Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 2,FailureHandling.OPTIONAL)
		
	} else if(Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 1, FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('Dashboard Is Present..!!!')
		Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 2,FailureHandling.OPTIONAL)
		
	} else {
       //CustomKeywords.'helper.customfunction.Screenshot'()
       // KeywordUtil.markFailedAndStop('Valid RNC Username Password - Scenario Is Failed...!!!')
    }
    
    Mobile.setText(findTestObject('Login/Email-Codigo'), Email, 1, FailureHandling.OPTIONAL)
	CustomKeywords.'helper.customfunction.HideKeyboard'()
	KeywordUtil.markPassed('Valid RNC Username Password Is Passed...!!!')
    Mobile.tap(findTestObject('Login/CONTINUAR'), 1, FailureHandling.OPTIONAL)

    //CustomKeywords.'helper.customfunction.LoginResponses'()
	//CustomKeywords.'helper.customfunction.Screenshot'()
    Mobile.tap(findTestObject('Login/ACEPTAR'), 1, FailureHandling.OPTIONAL)

//    if (Mobile.waitForElementPresent(findTestObject('Login/ClearEmail-Codigo'), 1, FailureHandling.OPTIONAL)) {
//        Mobile.clearText(findTestObject('Login/ClearEmail-Codigo'), 1, FailureHandling.OPTIONAL)
//        Mobile.delay(1)
//    }
}

def Permissions() {
	if (Mobile.waitForElementPresent(findTestObject('Login/Izquierda'), 500, FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('Scenario Passed...!!!' + Description)
		Mobile.tap(findTestObject('RegistroDeDispositivo/Confirmar'), 5,FailureHandling.OPTIONAL)
		Mobile.delay(0.5)
		//CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('Buttons/Si'), 5,FailureHandling.OPTIONAL)
		Mobile.delay(0.5)
		//CustomKeywords.'helper.customfunction.Screenshot'()
		Mobile.tap(findTestObject('Buttons/No'), 5,FailureHandling.OPTIONAL)
		Mobile.delay(0.5)
	} else if (Mobile.waitForElementPresent(findTestObject('Buttons/Si'), 1, FailureHandling.OPTIONAL)) {
		Mobile.tap(findTestObject('Buttons/Si'), 2,FailureHandling.OPTIONAL)
		Mobile.tap(findTestObject('Buttons/No'), 2,FailureHandling.OPTIONAL)
	}
}
