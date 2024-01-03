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
import com.kms.katalon.core.configuration.RunConfiguration as RC
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

SetGlobalValues()

Profile()

def Profile() {
	def executionProfile = RC.getExecutionProfile()
	
	if (executionProfile.equalsIgnoreCase('local')) {
		println('Profile Set Is Local - Bypass DB Connection TCs')
		WebUI.comment('Profile Set Is Local - Bypass DB Connection TCs')
	
	} else if (executionProfile.equalsIgnoreCase('default')) {
		println('Profile Set Is Server - Executing DB Connection TCs ')
		WebUI.comment('Profile Set Is Server - Executing DB Connection TCs ')
		
		'CONNECT DATABASE : '
		WebUI.callTestCase(findTestCase('CommonTestCases/TC_ConnectionToDatabase'), [('Verification') : Verification, ('Description') : Description,('CuentaOrigen') : CuentaOrigen,('CuentaDestino') : CuentaDestino], FailureHandling.CONTINUE_ON_FAILURE)

		'GET DATABASE ORIGIN ACCOUNT BALANCE  : '
		CustomKeywords.'helper.functionsupdate.ConsultaBalance_Origen'(CuentaOrigen)
		
		'GET DATABASE DESTINO ACCOUNT BALANCE  : '
		if (Description.contains('PrestamoPropia')) {
			CustomKeywords.'helper.functionsupdate.ConsultaBalancePrestamos'(CuentaDestino)
			
		} else if(Description.contains('Multiple Transaction')) {
			int TotalDestinoDB = Integer.parseInt(DestinoCount)
			ArrayList<String> DestinoAccDB = new ArrayList<String>(Arrays.asList(CuentaDestino1, CuentaDestino2, CuentaDestino3, CuentaDestino4))
			
			for(int count = 0; count < TotalDestinoDB; count++) {
				if(DestinoAccDB[count].length() == 10 && DestinoAccDB[count] != "NA" && DestinoAccDB[count].matches("[0-9]+")) {
					CustomKeywords.'helper.functionsupdate.ConsultaBalance_Destino'(DestinoAccDB[count])
					CustomKeywords.'helper.customfunction.DestinoBeforeDBMap'(count, TotalDestinoDB)
					
				} else if(DestinoAccDB[count].contains("PagoUnico") && DestinoAccDB[count] != "NA") {
					GlobalVariable.destino_before_db = "Destino is PagoUnico Account - No Balance is visible"
					CustomKeywords.'helper.customfunction.DestinoBeforeDBMap'(count, TotalDestinoDB)
					
				} else {
					GlobalVariable.destino_before_db = "Destino is Beneficiary Account - No Balance is visible"
					CustomKeywords.'helper.customfunction.DestinoBeforeDBMap'(count, TotalDestinoDB)
				}
			}
		} else if (Description.contains('TarjetaPropia')) {
			CustomKeywords.'helper.functionsupdate.ConsultaBalance_TarjetaBefore'(CuentaDestino, MontoCurrency)
		} else {
			CustomKeywords.'helper.functionsupdate.ConsultaBalance_Destino'(CuentaDestino)
		}
		
		'CLEAR PREVIOUS AUTHORIZATION : '
		CustomKeywords.'helper.functionsupdate.ClearAuthorization'()
	}
}

def SetGlobalValues() {
	GlobalVariable.CaseDescription = Description
	GlobalVariable.CuentaOrigen = CuentaOrigen
	GlobalVariable.CuentaDestino = CuentaDestino
	GlobalVariable.Impuesto = Impuesto
	GlobalVariable.Verification = Verification
	GlobalVariable.OriginalMonto = Monto
	GlobalVariable.TransactionMessage = TransactionMessage
	GlobalVariable.TransactionMessageAuthorizer1 = TransactionMessageAuthorizer1
	GlobalVariable.TransactionMessageAuthorizer2 = TransactionMessageAuthorizer2
}
