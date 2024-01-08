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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import org.openqa.selenium.Keys as Keys

GlobalVariable.BenefUnicoDetails = "True"

if (CtaDestino == '') {
	GlobalVariable.destino_before_app = "NotAvailable"
	GlobalVariable.OrigenAccountStatus = "NotAvailable"
	println('Destino Beneficiary Not Available...!!!')
	
} else if(GlobalVariable.OrigenAccountStatus.equals("NotAvailable")) {
	GlobalVariable.destino_before_app = "NotAvailable"
	GlobalVariable.OrigenAccountStatus = "NotAvailable"
	KeywordUtil.markFailed('Origen Account Not Selected So Skipping Further Flow!!!')
	
} else  {
	Mobile.tap(findTestObject('PagosYTransferencia/Destino-Title'), 5, FailureHandling.OPTIONAL)

    Mobile.delay(1)

    String Attribute = Mobile.getAttribute(findTestObject('Interbancario_Beneficiary/Beneficiarios'), 'enabled', 2, FailureHandling.CONTINUE_ON_FAILURE)

    if ((Attribute == 'True') || (Attribute == 'true')) {
        Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/Beneficiarios'), 3, FailureHandling.OPTIONAL)

        CustomKeywords.'helper.customfunction.DestinoAccount'(CtaDestino)
        
    } else {
        //CustomKeywords.'helper.customfunction.Screenshot'()

        println('Beneficiary Button Is Disabled...!!!')

        KeywordUtil.markPassed('Beneficiary Button Is Disabled For Case ' + Description)
        GlobalVariable.CaseStatusMessage += "\n *** Beneficiary Button Is Disabled For Case *** \n"
    }
	GlobalVariable.destino_before_app = "Destino is Beneficiary Account - No Balance is visible"
}
