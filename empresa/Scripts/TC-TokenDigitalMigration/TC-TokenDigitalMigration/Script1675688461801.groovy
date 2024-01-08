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

//'DELETE PREVIOUS TOKEN EMAILS : '
//WebUI.callTestCase(findTestCase('TC-TokenDigitalMigration/TC_DeletePreviousMails'), [:], FailureHandling.STOP_ON_FAILURE)

'CONNECT DB IBKEmpData'
CustomKeywords.'helper.functionsupdate.connectDB3'(user2, pass2)

'UPDATE TOKENREQUESTSTATUS ID : '
CustomKeywords.'helper.functionsupdate.MigrateToken'()

GlobalVariable.TokenProcessStatus = 'True'
//Mobile.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)

//String ActualUser = Mobile.getText(findTestObject('Login/GetText-Username'), 2, FailureHandling.OPTIONAL)
//println "Logged UserName Is " + ActualUser
//if (ActualUser.equals(GlobalVariable.Username)) {
////if(Mobile.waitForElementPresent(findTestObject('Login/CONTRASEA'), 2, FailureHandling.OPTIONAL)) {
//} else {
//	'RELAUNCH APPLICATION : '
//	Mobile.callTestCase(findTestCase('CommonTestCases/TC_QuickLaunchApp'), [:], FailureHandling.STOP_ON_FAILURE)
//}

'RELOGIN IN APP : '
CustomKeywords.'helper.customfunction.QuickLoginTokenImport'(RNC, Username1, Username2, Username3, Password, Email, Role, Type)

VerifyDashboard()

def VerifyDashboard() {
	if(Mobile.waitForElementPresent(findTestObject('TokenImport/Dispositivo pendiente de activacin'), 5, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('TokenImport/Salir'), 3, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Continuar'), 5,FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('Token Screen Presented..!!!')
		
		Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 5,FailureHandling.OPTIONAL)
		
		if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 15, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
		} else if (Mobile.waitForElementPresent(findTestObject('UserDetails/HamburgerMenu'), 5, FailureHandling.OPTIONAL)){
			KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
		} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 10, FailureHandling.STOP_ON_FAILURE)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailedAndStop('User Logged In - Dashboard Accounts Not Available...!!!')
		} else {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailedAndStop('Dashboard Not Found - Terminating Test Case...!!!')
		}
		
	} else if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 15, FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
//		Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
//		CustomKeywords.'helper.customfunction.GetUserDetails'()
//		Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
			
	} else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Continuar'), 10,FailureHandling.OPTIONAL)){
		Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 5,FailureHandling.OPTIONAL)
		
		if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 15, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
		} else if (Mobile.waitForElementPresent(findTestObject('UserDetails/HamburgerMenu'), 5, FailureHandling.OPTIONAL)){
			KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
		} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 10, FailureHandling.STOP_ON_FAILURE)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailedAndStop('User Logged In - Dashboard Accounts Not Available...!!!')
		} else {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailedAndStop('Dashboard Not Found - Terminating Test Case...!!!')
		}
		 
	} else if(Mobile.waitForElementPresent(findTestObject('TokenImport/Dispositivo pendiente de activacin'), 1, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('TokenImport/Salir'), 1, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Continuar'), 1,FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('Token Screen Presented..!!!')
		
		// add continuar button
		Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 5,FailureHandling.OPTIONAL)

		if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 15, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
		} else if (Mobile.waitForElementPresent(findTestObject('UserDetails/HamburgerMenu'), 5, FailureHandling.OPTIONAL)){
			KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
		} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 10, FailureHandling.STOP_ON_FAILURE)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailedAndStop('User Logged In - Dashboard Accounts Not Available...!!!')
		} else {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailedAndStop('Dashboard Not Found - Terminating Test Case...!!!')
		}
		
	} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 10, FailureHandling.STOP_ON_FAILURE)) {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailedAndStop('User Logged In - Dashboard Accounts Not Available...!!!')
	} else if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 3, FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
	} else if (Mobile.waitForElementPresent(findTestObject('UserDetails/HamburgerMenu'), 3, FailureHandling.OPTIONAL)){
		KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
	} else if(Mobile.waitForElementPresent(findTestObject('TokenImport/Dispositivo pendiente de activacin'), 5, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('TokenImport/Salir'), 1, FailureHandling.OPTIONAL)
		||Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Continuar'), 10,FailureHandling.OPTIONAL)) {
		KeywordUtil.markPassed('Token Screen Presented..!!!')
		
		// add continuar button
		Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 5,FailureHandling.OPTIONAL)
		if (Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 15, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
		} else if (Mobile.waitForElementPresent(findTestObject('UserDetails/HamburgerMenu'), 5, FailureHandling.OPTIONAL)){
			KeywordUtil.markPassed('User Logged In To - Dashboard...!!!')
		} else if (Mobile.waitForElementPresent(findTestObject('Authorize/Ha ocurrido un error al momento de procesar tu solicitud, por favor intenta nuevamente'), 10, FailureHandling.STOP_ON_FAILURE)) {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailedAndStop('User Logged In - Dashboard Accounts Not Available...!!!')
		} else {
			CustomKeywords.'helper.customfunction.Screenshot'()
			KeywordUtil.markFailedAndStop('Dashboard Not Found - Terminating Test Case...!!!')
		}
		
	} else {
		CustomKeywords.'helper.customfunction.Screenshot'()
		KeywordUtil.markFailedAndStop('Dashboard Not Found - Terminating Test Case...!!!')
	}
}