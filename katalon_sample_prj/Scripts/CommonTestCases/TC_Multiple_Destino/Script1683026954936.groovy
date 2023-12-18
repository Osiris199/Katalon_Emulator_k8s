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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import java.text.DecimalFormat
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import java.time.Duration

//Variables
AppiumDriver<?> driver = MobileDriverFactory.getDriver()
int TotalDestino = Integer.parseInt(DestinoCount)
ArrayList<String> DestinoAcc = new ArrayList<String>(Arrays.asList(Destino1, Destino2, Destino3, Destino4))
ArrayList<String> DestinoAccNumber = new ArrayList<String>(Arrays.asList(DestinoNumber1, DestinoNumber2, DestinoNumber3, DestinoNumber4))
ArrayList<String> monto = new ArrayList<String>(Arrays.asList(Monto, Monto1, Monto2, Monto3))
ArrayList<String> pagoType = new ArrayList<String>(Arrays.asList(PagoType, PagoType1))
int i = 0;
double totalTax
double totalComission

//Get Tax Value
for(int a = 0; a < TotalDestino; a++) {
	Mobile.tap(findTestObject('MultipleTransactions/Add destino button'), 10, FailureHandling.OPTIONAL)
	if(DestinoAcc[a].charAt(0) == "*" && DestinoAcc[a] != "NA") {
		println("Destino is Account")
		'SELECT DESTINO ACCOUNT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccount'), [('CtaDestino') : DestinoAcc[a]], FailureHandling.STOP_ON_FAILURE)
		
		'ENTER MONTO FOR PAYMENT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[a]], FailureHandling.CONTINUE_ON_FAILURE)
		
		Mobile.tap(findTestObject('Buttons/Continuar-2'), 10, FailureHandling.OPTIONAL)
				
	} else if(DestinoAcc[a].matches("[0-9]+") && DestinoAcc[a] != "NA") {
		println("Destino is PagoUnico")
		'SELECT DESTINO ACCOUNT PAGO UNICO : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccountPagoUnico'), [('CtaDestino') : DestinoAcc[a], ('Banco') : Banco, ('Moneda') : Moneda, ('TipoDeProducto') : TipoDeProducto, ('CuentaDestino') : DestinoAccNumber[a], ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
		
		'ENTER MONTO FOR PAYMENT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[a]], FailureHandling.CONTINUE_ON_FAILURE)
		
		'ENTER PAGO TYPE ACH/LBTR FOR PAYMENT :'
		if(Mobile.verifyElementVisible(findTestObject('TransferenciaLBTRPagoUnico/Mtodo de pago - Title'), 2, FailureHandling.OPTIONAL)) {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoMethod'), [('PagoType') : pagoType[i]], FailureHandling.CONTINUE_ON_FAILURE)
			i++
		}
		
		Mobile.tap(findTestObject('Buttons/Continuar-2'), 10, FailureHandling.OPTIONAL)
		
	} else if(DestinoAcc[a] != "NA") {
		println("Destino is Beneficiary")
		'SELECT DESTINO ACCOUNT BENEFICIARY : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoBeneficiary'), [('CtaDestino') : DestinoAcc[a], ('Description') : Description], FailureHandling.STOP_ON_FAILURE)
		
		'ENTER MONTO FOR PAYMENT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[a]], FailureHandling.CONTINUE_ON_FAILURE)
		
		'ENTER PAGO TYPE ACH/LBTR FOR PAYMENT :'
		if(Mobile.verifyElementVisible(findTestObject('TransferenciaLBTRPagoUnico/Mtodo de pago - Title'), 2, FailureHandling.OPTIONAL)) {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoMethod'), [('PagoType') : pagoType[i]], FailureHandling.CONTINUE_ON_FAILURE)
			i++
		}
		
		Mobile.tap(findTestObject('Buttons/Continuar-2'), 10, FailureHandling.OPTIONAL)
	} else {
		println("Destino is Blank")
	}
	
	Mobile.delay(1)
	String Attribute = Mobile.getAttribute(findTestObject('Buttons/Continuar-2'), 'enabled', 10, FailureHandling.CONTINUE_ON_FAILURE)
	
	if ((Attribute == 'True') || (Attribute == 'true')) {
		Mobile.tap(findTestObject('Buttons/Continuar-2'), 10, FailureHandling.OPTIONAL)
	}else {
        CustomKeywords.'helper.customfunction.Screenshot'()
		GlobalVariable.CaseStatusMessage += "\n *** Continue Button Is Disabled For Case *** \n"
        KeywordUtil.markFailedAndStop('Continue Button Is Disabled For Case ' + Description)
    }
	
	//tax update
	String TAX = ""
	GlobalVariable.GetImpuesta = ""
	double ImpuestoUpdated
	
	if(Mobile.verifyElementVisible(findTestObject('MultipleTransactions/Single Tax Element'), 3, FailureHandling.OPTIONAL)) {
		TAX = Mobile.getText(findTestObject('MultipleTransactions/Single Tax Element'), 1, FailureHandling.OPTIONAL)
		try {
			ImpuestoUpdated = Double.parseDouble(TAX.split("DOP ")[1])		
		} catch(Exception e) {
			ImpuestoUpdated = Double.parseDouble(TAX.split("USD ")[1])
			ImpuestoUpdated *= GlobalVariable.USD_to_DOP_rate
		}
	} else {
		TAX = '0.0'
		ImpuestoUpdated = Double.parseDouble(TAX)
	}
	
	//comission update
	String COMISSION = ""
	GlobalVariable.CommissionAmount = ""
	double ComissionUpdated
	
	if(Mobile.verifyElementVisible(findTestObject('MultipleTransactions/Single Comission Element'), 3, FailureHandling.OPTIONAL)) {
		COMISSION = Mobile.getText(findTestObject('MultipleTransactions/Single Comission Element'), 1, FailureHandling.OPTIONAL)
		try {
			ComissionUpdated = Double.parseDouble(COMISSION.split("DOP ")[1])
		} catch(Exception e) {
			ComissionUpdated = Double.parseDouble(COMISSION.split("USD ")[1])
			ComissionUpdated *= GlobalVariable.USD_to_DOP_rate
		}
	} else {
		COMISSION = '0.0'
		ComissionUpdated = Double.parseDouble(COMISSION)
	}
	
	totalTax += ImpuestoUpdated
	totalComission+=ComissionUpdated

	//tax & comission mapping
	CustomKeywords.'helper.customfunction.ImpuestosMap'(a, TotalDestino, ImpuestoUpdated)
	CustomKeywords.'helper.customfunction.ComissionMap'(a, TotalDestino, ComissionUpdated)
	
	Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3, FailureHandling.OPTIONAL)
	Mobile.delay(2)
	ArrayList<String> Element = new ArrayList<String>();
	Element = driver.findElementsByXPath("//*[@class = 'android.widget.TextView' and (@text = 'Label' or . = 'Label')]")
	MobileElement secondElement = Element.get(1);						
	int midOfY = secondElement.getLocation().y +(secondElement.getSize().height/2);
	int fromXLocation = secondElement.getLocation().x;
	int toXLocation = Mobile.getDeviceWidth()-1;
	
	Mobile.delay(2)
	TouchAction action = new TouchAction(driver);
	action.longPress(PointOption.point(fromXLocation, midOfY)).moveTo(PointOption.point(toXLocation, midOfY)).release().perform();
	Mobile.delay(2)
	Mobile.tap(findTestObject('MultipleTransactions/Eliminar Button'), 3, FailureHandling.OPTIONAL)
}

//Selecting multiple destino account
 i = 0;
for(int count = 0; count < TotalDestino; count++) {
	String tempDestinoCurreny = ''
	Mobile.tap(findTestObject('MultipleTransactions/Add destino button'), 10, FailureHandling.OPTIONAL)

	if(DestinoAcc[count].charAt(0) == "*" && DestinoAcc[count] != "NA") {
		println("Destino is Account")
		'SELECT DESTINO ACCOUNT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccount'), [('CtaDestino') : DestinoAcc[count]], FailureHandling.STOP_ON_FAILURE)
		
		'TASA VERIFICATION : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
		
		'ENTER MONTO FOR PAYMENT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[count]], FailureHandling.CONTINUE_ON_FAILURE)
		
		'ENTER CONCEPT FOR PAYMENT :'
	    WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept+'.'+(count), ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
		
		//get currenyType
		CustomKeywords.'helper.customfunction.DestinoCurrMap'(count, TotalDestino)
				
	} else if(DestinoAcc[count].matches("[0-9]+") && DestinoAcc[count] != "NA") {
		println("Destino is PagoUnico")
		'SELECT DESTINO ACCOUNT PAGO UNICO : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoAccountPagoUnico'), [('CtaDestino') : DestinoAcc[count], ('Banco') : Banco, ('Moneda') : Moneda, ('TipoDeProducto') : TipoDeProducto, ('CuentaDestino') : DestinoAccNumber[count], ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
		
		'TASA VERIFICATION : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
		
		'ENTER MONTO FOR PAYMENT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[count]], FailureHandling.CONTINUE_ON_FAILURE)
		
		'SELECT ACH/LBTR'
		if(Mobile.verifyElementVisible(findTestObject('TransferenciaLBTRPagoUnico/Mtodo de pago - Title'), 2, FailureHandling.OPTIONAL)) {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoMethod'), [('PagoType') : pagoType[i]], FailureHandling.CONTINUE_ON_FAILURE)
			i++
		}
		
		'ENTER CONCEPT FOR PAYMENT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept+'.'+(count), ('Description') : Description], FailureHandling.CONTINUE_ON_FAILURE)
		
		//get currenyType
		CustomKeywords.'helper.customfunction.DestinoCurrMap'(count, TotalDestino)
		
	} else if(DestinoAcc[count] != "NA") {
		println("Destino is Beneficiary")
		'SELECT DESTINO ACCOUNT BENEFICIARY : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_DestinoBeneficiary'), [('CtaDestino') : DestinoAcc[count], ('Description') : Description], FailureHandling.STOP_ON_FAILURE)

		'TASA VERIFICATION : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_GetTasa'), [:], FailureHandling.OPTIONAL)
		
		'ENTER MONTO FOR PAYMENT :'
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Monto'), [('Monto') : monto[count]], FailureHandling.CONTINUE_ON_FAILURE)
		
		'SELECT ACH/LBTR'
		if(Mobile.verifyElementVisible(findTestObject('TransferenciaLBTRPagoUnico/Mtodo de pago - Title'), 2, FailureHandling.OPTIONAL)) {
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_PagoMethod'), [('PagoType') : pagoType[i]], FailureHandling.CONTINUE_ON_FAILURE)
			i++
		}
		
		'ENTER CONCEPT FOR PAYMENT : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_Concept'), [('Concept') : Concept+'.'+(count), ('Description') : Description], FailureHandling.STOP_ON_FAILURE)
		
		//get currenyType
		CustomKeywords.'helper.customfunction.DestinoCurrMap'(count, TotalDestino)
		
	} else {
		println("Destino is Blank")
	}
	
	CustomKeywords.'helper.customfunction.DestinoBeforeAppMap'(count, TotalDestino)
}

Mobile.tap(findTestObject('Buttons/Continuar-2'), 10, FailureHandling.OPTIONAL)
Mobile.delay(2)

//Total Tax
GlobalVariable.GetImpuesta = totalTax
GlobalVariable.CommissionAmount = totalComission
KeywordUtil.logInfo('Tax-----'+GlobalVariable.GetImpuesta)
KeywordUtil.logInfo('Comission-----'+GlobalVariable.CommissionAmount)
