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
import com.kms.katalon.core.util.KeywordUtil

if (Monto == '') {
    println('Monto Option Is Skipped...!!!')
   //CustomKeywords.'helper.customfunction.continuar'()
} else if (GlobalVariable.OrigenAccountStatus == 'NotAvailable') {
	GlobalVariable.origen_before_app = "SelectionFailed"
    println('Origen Account Not Available - Skipping Monto Flow...!!!')
} else if (GlobalVariable.DestinoAccountStatus == 'NotAvailable') {
	GlobalVariable.destino_before_app = "SelectionFailed Account or Beneficiary not visible"
    //CustomKeywords.'helper.customfunction.Screenshot'()
    println('Destino Account Not Available...!!!')
} else {
    Mobile.tap(findTestObject('PagosYTransferencia/Digita un monto'), 5, FailureHandling.OPTIONAL)

    String data

    int j = 0

    //int i = 0

    int num

    try {
        num = Monto.length()
		for(int i=0;i<num;i++) {
			Mobile.tap(findTestObject('Keypad/android.widget.Button - '+ Monto.substring(j, i+1)), 3, FailureHandling.OPTIONAL)
			j++
		}
    } catch(Exception e) {
			KeywordUtil.logInfo("KEYPAD NOT VISIBLE!!!")
	}
   
    Mobile.tap(findTestObject('PagosYTransferencia/Confirmar'), 3, FailureHandling.OPTIONAL)

    Mobile.delay(2)
}

GlobalVariable.OriginalMonto = Monto
