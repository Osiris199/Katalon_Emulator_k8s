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

'TAP ON HAMBURGER MENU : '
Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)
if(Mobile.verifyElementVisible(findTestObject('UserDetails/LoggedUserName'), 1, FailureHandling.OPTIONAL) == false){
    Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 1, FailureHandling.OPTIONAL)
}

if (Type == 'NA') {
    println('Non Payment Transaction Scenario - So Transaction Part Is Skipped...!!!')
} else if ((Type == 'Payment') && (Concept == 'SwipeVerification')) {
    println('Non Payment Transaction Scenario - So Transaction Part Is Skipped...!!!')
} else if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
    Mobile.tap(findTestObject('PagosYTransferencia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)

    Mobile.tap(findTestObject('PagosYTransferencia/Pagos y transferencias'), 3, FailureHandling.CONTINUE_ON_FAILURE)

} else {
    Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)

    if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Transacciones'), 5, FailureHandling.OPTIONAL)) {
        Mobile.tap(findTestObject('PagosYTransferencia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)

        Mobile.delay(1)

        Mobile.tap(findTestObject('PagosYTransferencia/Pagos y transferencias'), 3, FailureHandling.CONTINUE_ON_FAILURE)

    } else {
        Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 3, FailureHandling.OPTIONAL)

        Mobile.tap(findTestObject('PagosYTransferencia/Transacciones'), 3, FailureHandling.CONTINUE_ON_FAILURE)

        Mobile.delay(1)

        Mobile.tap(findTestObject('PagosYTransferencia/Pagos y transferencias'), 3, FailureHandling.CONTINUE_ON_FAILURE)
    }
}