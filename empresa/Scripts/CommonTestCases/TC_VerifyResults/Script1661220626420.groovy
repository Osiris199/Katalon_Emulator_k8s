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
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.sql.Connection as Connection
import java.sql.DriverManager as DriverManager
import java.sql.ResultSet as ResultSet
import java.sql.Statement as Statement
import java.text.DecimalFormat as DecimalFormat
import com.ibm.db2.jcc.DB2Driver as DB2Driver
import com.kms.katalon.core.logging.KeywordLogger as KeywordLogger
import com.opencsv.*
import java.io.FileWriter.*
import java.io.IOException as IOException
import java.io.Reader as Reader
import java.nio.file.Files as Files
import java.nio.file.Paths as Paths
import org.eclipse.persistence.platform.database.DB2Platform as DB2Platform
import org.junit.After as After
import org.eclipse.persistence.platform.database.DB2MainframePlatform as DB2MainframePlatform
import java.sql.SQLException as SQLException
import java.io.*
import java.util.*
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

if((GlobalVariable.CaseDescription).contains('PagoUnico') || (GlobalVariable.CaseDescription).contains('Prestamo PagoUnico')) {
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_OrigenAfter'(CuentaOrigen)
	CustomKeywords.'helper.customfunction.OrigenAccount_AfterBalance'(CtaOrigen)
	GlobalVariable.destino_after_db = "No Info For Pago Unico Acct " + CtaDestino
	GlobalVariable.destino_before_db = "No Info For Pago Unico Acct " + CtaDestino
	GlobalVariable.destino_before_app = "Destino is PagoUnico - No Balance is visible"
	GlobalVariable.destino_after_app = "Destino is PagoUnico - No Balance is visible"
	GlobalVariable.CurrencyTypeDestino = "No Currency Info"
} else if ((GlobalVariable.CaseDescription).contains('TarjetaPropia')) {
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_OrigenAfter'(CuentaOrigen)
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_TarjetaAfter'(CuentaDestino, MontoCurrency)
	CustomKeywords.'helper.customfunction.OrigenAccount_AfterBalance'(CtaOrigen)
	CustomKeywords.'helper.customfunction.DestinoAccount_AfterBalance_Tarjeta'(CtaDestino, MontoCurrency)
	
} else if ((GlobalVariable.CaseDescription).contains('Prestamo')) {
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_OrigenAfter'(CuentaOrigen)
	CustomKeywords.'helper.customfunction.OrigenAccount_AfterBalance'(CtaOrigen)
	CustomKeywords.'helper.functionsupdate.ConsultaBalancePrestamosAfter'(CuentaDestino)
	CustomKeywords.'helper.customfunction.DestinoAccount_AfterBalance'(CtaDestino)
} else {
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_DestinoAfter'(CuentaDestino)
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_OrigenAfter'(CuentaOrigen)
	CustomKeywords.'helper.customfunction.OrigenAccount_AfterBalance'(CtaOrigen)
	CustomKeywords.'helper.customfunction.DestinoAccount_AfterBalance'(CtaDestino)
	
}


double Balance;
if ((Impuesto.equals('True') || Impuesto.equals('true')) || Impuesto.equals('TRUE')) {
	try {
		CustomKeywords.'helper.functionsupdate.CalculateTax'(Monto, CuentaOrigen, CuentaDestino, Impuesto)
	}
	catch (Exception e) {
		KeywordUtil.logInfo("Calculate tax failed")
	}
} else {
	GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_after_db
	GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
}

try {	
	if(GlobalVariable.Tasa == "No Conversion Rate") {
		println('GlobalVariable.Tasa1' + GlobalVariable.Tasa)
		UpdatedMonto = Double.parseDouble(GlobalVariable.OriginalMonto)
		println('UpdatedMonto1 ' + UpdatedMonto)
		GlobalVariable.UpdatedMonto = UpdatedMonto + GlobalVariable.GetImpuesta + GlobalVariable.CommissionAmount
		DecimalFormat df2 = new DecimalFormat("###.##");
		double input = GlobalVariable.UpdatedMonto
		GlobalVariable.UpdatedMonto= df2.format(input)
		println('UpdatedMonto3 Is : ' + GlobalVariable.UpdatedMonto)
		//GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_after_db
		//GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
	} else {
		Balance = Double.parseDouble(GlobalVariable.OriginalMonto)
		println('Balance2' + Balance)
		/*double UpdatedMonto
		
		if(GlobalVariable.CurrencyTypeOrigen == "USD") {
			UpdatedMonto = Balance
		} else {
			UpdatedMonto = GlobalVariable.Tasa * Balance
		}

		println('UpdatedMonto2' + UpdatedMonto)
		GlobalVariable.UpdatedMonto = UpdatedMonto
		GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_after_db
		GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
		*/
		UpdatedMonto = GlobalVariable.Tasa * Balance
		println('UpdatedMonto4 Is : ' + UpdatedMonto)
		GlobalVariable.UpdatedMonto = (UpdatedMonto) + (GlobalVariable.GetImpuesta) + (GlobalVariable.CommissionAmount);
		println('UpdatedMonto5 Is : ' + 	GlobalVariable.UpdatedMonto)
		GlobalVariable.UpdatedMonto = Double.parseDouble(GlobalVariable.UpdatedMonto)
		
		DecimalFormat df2 = new DecimalFormat("###.##");
		double input = GlobalVariable.UpdatedMonto
		GlobalVariable.UpdatedMonto= df2.format(input)
		println('UpdatedMonto6 Is : ' + GlobalVariable.UpdatedMonto)
	}
} catch (Exception e) {
	KeywordUtil.logInfo("Entered catch block of tasa verification in verify results")
}

CustomKeywords.'helper.customfunction.HistoricoProcesada'()