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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC

Login()

def Login() {
    'LOGIN SCENARIOS '
    WebUI.comment((ID + ' - ') + Description)
		
    if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TuBnco Empresas'), 30, FailureHandling.CONTINUE_ON_FAILURE) || Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 3, FailureHandling.CONTINUE_ON_FAILURE)) {
		
		if(Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 2, FailureHandling.OPTIONAL)) {
			Mobile.setText(findTestObject('Login/IDENTIFICACIN'), RNC, 2, FailureHandling.OPTIONAL)
			Mobile.delay(1)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
			Mobile.setText(findTestObject('Login/USUARIO'), Username, 2, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
		} else {
			Mobile.setText(findTestObject('Login/IDENTIFICACIN'), RNC, 1, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
			//Mobile.delay(3)
			Mobile.setText(findTestObject('Login/USUARIO'), Username, 1, FailureHandling.OPTIONAL)
			CustomKeywords.'helper.customfunction.HideKeyboard'()
		}
		/*
        String executionProfile = RC.getExecutionProfile()
        if (executionProfile.equals('Solicitante') && Description.equals(GlobalVariable.Description)) {
            GlobalVariable.Username = 'margaritaf'
            Username = GlobalVariable.Username
            Mobile.setText(findTestObject('Login/USUARIO'), Username, 3, FailureHandling.OPTIONAL)
            CustomKeywords.'helper.customfunction.HideKeyboard'()
        } else {
            Mobile.setText(findTestObject('Login/USUARIO'), Username, 3, FailureHandling.OPTIONAL)
            CustomKeywords.'helper.customfunction.HideKeyboard'()
        }*/
        
		//CustomKeywords.'helper.customfunction.Screenshot'()
		//Mobile.delay(1)
        Mobile.tap(findTestObject('Login/CONTINUAR'), 2, FailureHandling.OPTIONAL)

        if (Mobile.waitForElementPresent(findTestObject('Login/CONTRASEA'), 2, FailureHandling.CONTINUE_ON_FAILURE)) {
            if (Password == '') {
				//CustomKeywords.'helper.customfunction.Screenshot'()
				Mobile.delay(1)
                Mobile.tap(findTestObject('Login/CONTINUAR'), 2, FailureHandling.OPTIONAL)
            } else {
                Mobile.tap(findTestObject('Login/CONTRASEA'), 2, FailureHandling.OPTIONAL)
                Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 2, FailureHandling.OPTIONAL)  
				CustomKeywords.'helper.customfunction.HideKeyboard'()
				//CustomKeywords.'helper.customfunction.Screenshot'()
				//Mobile.delay(1)
                Mobile.tap(findTestObject('Login/Acceder'), 2, FailureHandling.OPTIONAL)
				//Mobile.tap(findTestObject('Login/CONTINUAR'), 1, FailureHandling.OPTIONAL)
            }
        } else {
            GlobalVariable.CaseStatusMessage += "\n CONTRASEA Field Is not found...!!"
            CustomKeywords.'helper.customfunction.Screenshot'()
        }

		if(Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error communicating with provider'), 5, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error'), 2, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
			WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
		} else if (Mobile.waitForElementPresent(findTestObject('Login/Registro de dispositivo'), 20, FailureHandling.CONTINUE_ON_FAILURE)) {
            println('Valid RNC Username Password Is Passed...!!!')
        } else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Continuar'), 1, FailureHandling.OPTIONAL)) {
//            Mobile.tap(findTestObject('Login/ClearFields'), 2, FailureHandling.OPTIONAL)
        } else if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 1, FailureHandling.OPTIONAL)) {
//            Mobile.tap(findTestObject('Login/ClearFields'), 2, FailureHandling.OPTIONAL)
        } else if (Mobile.waitForElementPresent(findTestObject('ErrorMessage/USUARIO BLOQUEADO POR CONTRASEA'), 1, FailureHandling.OPTIONAL)) {
			  GlobalVariable.CaseStatusMessage += "\n *** User is blocked ***"
              CustomKeywords.'helper.customfunction.Screenshot'()
			  KeywordUtil.markFailedAndStop("*** User is blocked ***")
        } else if (Mobile.waitForElementPresent(findTestObject('ErrorMessage/USUARIO YO CONTRASEA INCORRECTOS'), 1, FailureHandling.OPTIONAL)) {
			  GlobalVariable.CaseStatusMessage += "\n *** Username or password is incorrect ***"
              CustomKeywords.'helper.customfunction.Screenshot'()
			  KeywordUtil.markFailedAndStop("*** Username or password is incorrect ***")
        } else {
			CustomKeywords.'helper.customfunction.LoginResponses'(Description)
			//CustomKeywords.'helper.customfunction.Screenshot'()
			//Mobile.delay(1)
			//Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
		}
       
        if(Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error communicating with provider'), 1, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error'), 2, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
			WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
		} else if (Mobile.waitForElementPresent(findTestObject('Login/Registro de dispositivo'), 20, FailureHandling.CONTINUE_ON_FAILURE)) {
            println('Valid RNC Username Password Is Passed...!!!')
			//CustomKeywords.'helper.customfunction.Screenshot'()
        } else if (Mobile.waitForElementPresent(findTestObject('Login/ClearFields'), 2, FailureHandling.OPTIONAL)) {
                Mobile.tap(findTestObject('Login/ClearFields'), 2, FailureHandling.OPTIONAL)
        } else if (Mobile.waitForElementPresent(findTestObject('Login/GetText-RNC'), 2, FailureHandling.OPTIONAL)) {
				Mobile.clearText(findTestObject('Login/GetText-RNC'), 3, FailureHandling.OPTIONAL)
		}

    } else if (Mobile.waitForElementPresent(findTestObject('Login/Registro de dispositivo'), 20, FailureHandling.CONTINUE_ON_FAILURE)) {
        println('Valid RNC Username Password Scenario Is Passed...!!!')
		//CustomKeywords.'helper.customfunction.Screenshot'()
    }
}