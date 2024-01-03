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


if (Monto == "NA") {
    println('Monto Option Is Skipped...!!!')
} else {
    Mobile.tap(findTestObject('PagosYTransferencia/Digita un monto'), 5, FailureHandling.CONTINUE_ON_FAILURE)

	String data
	
	int j = 0
	int i = 0
	int num
	
    num = Monto.length()

    while (i < num) {
        Mobile.tap(findTestObject('Buttons/Numeros/Button' + Monto.substring(j, i + 1)), 0)
        j++
        i++
    }
}

Mobile.tap(findTestObject('PagosYTransferencia/Confirmar'), 3, FailureHandling.OPTIONAL)
		