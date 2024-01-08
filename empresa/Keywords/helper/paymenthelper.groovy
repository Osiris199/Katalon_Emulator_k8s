package helper
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
import internal.GlobalVariable
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.mobile.keyword.internal.MobileAbstractKeyword
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import io.appium.java_client.AppiumDriver as AppiumDriver
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang3.StringUtils as StringUtils
import java.lang.Integer as Integer
import io.appium.java_client.MobileElement as MobileElement
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.configuration.RunConfiguration as RC
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

public class paymenthelper {

	@Keyword
	public void PaymentResponses() {
		Mobile.delay(1)
		try {
			for(int rowNum=1; rowNum<=findTestData("Excel-Data/PaymentResponses").getRowNumbers();rowNum++) {

				if (Mobile.waitForElementPresent(findTestObject(findTestData("Excel-Data/PaymentResponses").getValue(3,rowNum)),1, FailureHandling.OPTIONAL)) {
					//(new helper.customfunction()).Screenshot()
					//Mobile.delay(1)
					KeywordUtil.markPassed("Scenario Passed " + findTestData("Excel-Data/PaymentResponses").getValue(2,rowNum))
					GlobalVariable.PaymentMessage = Mobile.getText(findTestObject(findTestData("Excel-Data/PaymentResponses").getValue(3,rowNum)),3, FailureHandling.OPTIONAL)
					GlobalVariable.AppPaymentMessage = GlobalVariable.PaymentMessage
					break
					//(new helper.customfunction()).Screenshot()
				} else {
					println "Try Other"
				}
			}

			Mobile.tap(findTestObject('Login/ACEPTAR'), 1, FailureHandling.OPTIONAL)

			/*
			 if (Mobile.waitForElementPresent(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)) {
			 Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
			 }else {
			 KeywordUtil.markPassed("ACEPTAR Button Not Available...!!!")
			 }
			 */
		}catch(Exception e) {
			//println e.printStackTrace()
		}
	}


	def PrintResults() {
		//String TransactionMessage = Mobile.getText(findTestObject('PaymentSuccess/TransactionMessage'), 1, FailureHandling.STOP_ON_FAILURE)
		//String TransactionTime = Mobile.getText(findTestObject('PaymentSuccess/TransactionTime'), 1, FailureHandling.STOP_ON_FAILURE)
		//String Amount = Mobile.getText(findTestObject('PaymentSuccess/Amount'), 1, FailureHandling.STOP_ON_FAILURE)
		//String DestinoAccount = Mobile.getText(findTestObject('PaymentSuccess/DestinoAccount'), 1, FailureHandling.STOP_ON_FAILURE)

		WebUI.comment("### - TRANSACTION PAYMENT MESSAGE ### : " + TransactionMessage)
		WebUI.comment("### - PAYMENT TIME ### : " + TransactionTime)
		WebUI.comment("### - Monto Amount ### : " + Amount)
		WebUI.comment("### - DESTINO ACCOUNT ### : " + DestinoAccount)

		WebUI.comment("### - TRANSACTION PAYMENT MESSAGE ### : " + Mobile.getText(findTestObject('PaymentSuccess/TransactionMessage'), 1, FailureHandling.STOP_ON_FAILURE))
		WebUI.comment("### - PAYMENT TIME ### : " + Mobile.getText(findTestObject('PaymentSuccess/TransactionTime'), 1, FailureHandling.STOP_ON_FAILURE))
		WebUI.comment("### - MONTO AMOUNT ### : " + Mobile.getText(findTestObject('PaymentSuccess/Amount'), 1, FailureHandling.STOP_ON_FAILURE))
		WebUI.comment("### - DESTINO ACCOUNT ### : " + Mobile.getText(findTestObject('PaymentSuccess/DestinoAccount'), 1, FailureHandling.STOP_ON_FAILURE))

	}
}


