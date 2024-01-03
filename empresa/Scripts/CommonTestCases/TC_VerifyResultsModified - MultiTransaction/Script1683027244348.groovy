import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.configuration.RunConfiguration as RC
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

GlobalVariable.CaseDescription = Description
GlobalVariable.CuentaOrigen = CuentaOrigen
GlobalVariable.CuentaDestino1 = CuentaDestino1
GlobalVariable.CuentaDestino2 = CuentaDestino2
GlobalVariable.CuentaDestino3 = CuentaDestino3
GlobalVariable.CuentaDestino4 = CuentaDestino4
GlobalVariable.Impuesto = Impuesto
GlobalVariable.Verification = Verification

println('DescriptionUpdated' + GlobalVariable.CaseDescription)
String Description = GlobalVariable.CaseDescription

'GET DATABASE ORIGIN ACCOUNT BALANCE AFTER TRANSACTION : '
CustomKeywords.'helper.functionsupdate.ConsultaBalance_OrigenAfter'(CuentaOrigen)

'GET APP ORIGIN ACCOUNT BALANCE AFTER TRANSACTION : '
CustomKeywords.'helper.customfunction.OrigenAccount_AfterBalance'(CtaOrigen)

'GET DATABASE DESTINO ACCOUNT BALANCE AFTER TRANSACTION : '
int TotalDestinoDB = Integer.parseInt(DestinoCount)
ArrayList<String> DestinoAccDB = new ArrayList<String>(Arrays.asList(CuentaDestino1, CuentaDestino2, CuentaDestino3, CuentaDestino4))
ArrayList<String> pagoType = new ArrayList<String>(Arrays.asList(PagoType, PagoType1))
int currValue = 0;

for(int count = 0; count < TotalDestinoDB; count++) {
	if(DestinoAccDB[count].length() == 10 && DestinoAccDB[count] != "NA" && DestinoAccDB[count].matches("[0-9]+")) {
		CustomKeywords.'helper.functionsupdate.ConsultaBalance_DestinoAfter'(DestinoAccDB[count])
		CustomKeywords.'helper.customfunction.DestinoAfterDBMap'(count, TotalDestinoDB)
	} else if(DestinoAccDB[count].contains("PagoUnico") && DestinoAccDB[count] != "NA") {
		GlobalVariable.destino_after_db = "Destino is PagoUnico Account - No Balance is visible"
		CustomKeywords.'helper.customfunction.DestinoAfterDBMap'(count, TotalDestinoDB)
	} else {
		GlobalVariable.destino_after_db = "Destino is Beneficiary Account - No Balance is visible"
		CustomKeywords.'helper.customfunction.DestinoAfterDBMap'(count, TotalDestinoDB)
	}
}

'GET APP DESTINO ACCOUNT BALANCE AFTER TRANSACTION : '
int TotalDestino = Integer.parseInt(DestinoCount)
ArrayList<String> DestinoAcc = new ArrayList<String>(Arrays.asList(CtaDestino1, CtaDestino2, CtaDestino3, CtaDestino4))
int a = 0;
for(int count = 0; count < TotalDestino; count++) {
	if(DestinoAcc[count].charAt(0) == "*" && DestinoAcc[count] != "NA") {
		CustomKeywords.'helper.customfunction.DestinoAccount_AfterBalanceMultipleTransaction'(DestinoAcc[count])
		CustomKeywords.'helper.customfunction.DestinoAfterAppMap'(count, TotalDestino)
	} else if(DestinoAcc[count].matches("[0-9]+") && DestinoAcc[count] != "NA") {
		GlobalVariable.destino_after_app = "No Info For Acct Destino Maybe its PagoUnico"
		CustomKeywords.'helper.customfunction.DestinoAfterAppMap'(count, TotalDestino)
	} else if(DestinoAcc[count] != "NA") {
		GlobalVariable.destino_after_app = "No Info For Acct Destino Maybe its Beneficiary"
		CustomKeywords.'helper.customfunction.DestinoAfterAppMap'(count, TotalDestino)
	} else {
		println("Destino is Blank")
	}
}

ArrayList<String> destinoBeforeDB = new ArrayList<String>(Arrays.asList(GlobalVariable.destino_before_db0, GlobalVariable.destino_before_db1, GlobalVariable.destino_before_db2, GlobalVariable.destino_before_db3))
ArrayList<String> destinoAfterDB = new ArrayList<String>(Arrays.asList(GlobalVariable.destino_after_db0, GlobalVariable.destino_after_db1, GlobalVariable.destino_after_db2, GlobalVariable.destino_after_db3))
ArrayList<String> destinoBeforeApp = new ArrayList<String>(Arrays.asList(GlobalVariable.destino_before_app0, GlobalVariable.destino_before_app1, GlobalVariable.destino_before_app2, GlobalVariable.destino_before_app3))
ArrayList<String> destinoAfterApp = new ArrayList<String>(Arrays.asList(GlobalVariable.destino_after_app0, GlobalVariable.destino_after_app1, GlobalVariable.destino_after_app2, GlobalVariable.destino_after_app3))

double Balance;
if ((Impuesto.equals('True') || Impuesto.equals('true')) || Impuesto.equals('TRUE')) {
	try {
		ArrayList<String> monto = new ArrayList<String>(Arrays.asList(Monto, Monto1, Monto2, Monto3))
		ArrayList<String> ImpuestosVal = new ArrayList<String>(Arrays.asList(GlobalVariable.GetImpuesta1, GlobalVariable.GetImpuesta2, GlobalVariable.GetImpuesta3, GlobalVariable.GetImpuesta4))
		
		for(int i = 0; i < TotalDestinoDB; i++) {
			CustomKeywords.'helper.functionsupdate.CalculateTaxMultipleTransaction'(ImpuestosVal[i], monto[i], destinoAfterDB[i], destinoBeforeDB[i], i, TotalDestinoDB, GlobalVariable.GetImpuesta, GlobalVariable.OriginalMonto)
		}
		
	} catch (Exception e) {
		e.printStackTrace()
	}
} else {
	GlobalVariable.GetImpuesta = '0.0'
	double ImpuestaValue = Double.parseDouble(GlobalVariable.GetImpuesta)
	GlobalVariable.GetImpuesta = ImpuestaValue
	println('TAX IS NOT CHARGED...!!!')	
		
    GlobalVariable.Tax_Origenbalance_after = GlobalVariable.origen_after_db
	GlobalVariable.Tax_Destinobalance_after = GlobalVariable.destino_after_db
}

ArrayList<String> TaxCalculationAfterTrans = new ArrayList<String>(Arrays.asList(GlobalVariable.Tax_Destinobalance_after1, GlobalVariable.Tax_Destinobalance_after2, GlobalVariable.Tax_Destinobalance_after3, GlobalVariable.Tax_Destinobalance_after4))


if(GlobalVariable.Tasa == "No Conversion Rate") {
	println('Tasa Value Is ' + GlobalVariable.Tasa)
	UpdatedMonto = Double.parseDouble(GlobalVariable.OriginalMonto)
	println('UpdatedMonto1 Is : ' + UpdatedMonto)
	
	GlobalVariable.UpdatedMonto = UpdatedMonto + GlobalVariable.GetImpuesta + GlobalVariable.CommissionAmount
	println('UpdatedMonto2 Is : ' + 	GlobalVariable.UpdatedMonto)
		
	DecimalFormat df2 = new DecimalFormat("###.##");
	double input = GlobalVariable.UpdatedMonto
	GlobalVariable.UpdatedMonto= df2.format(input)
	println('UpdatedMonto3 Is : ' + GlobalVariable.UpdatedMonto)
} else {
	try {
	Balance = Double.parseDouble(GlobalVariable.OriginalMonto)
	println('Balance2' + Balance)

	UpdatedMonto = GlobalVariable.Tasa * Balance
	println('UpdatedMonto4 Is : ' + UpdatedMonto)
	GlobalVariable.UpdatedMonto = (UpdatedMonto) + (GlobalVariable.GetImpuesta) + (GlobalVariable.CommissionAmount);
	println('UpdatedMonto5 Is : ' + 	GlobalVariable.UpdatedMonto)
	GlobalVariable.UpdatedMonto = Double.parseDouble(GlobalVariable.UpdatedMonto)
	
	DecimalFormat df2 = new DecimalFormat("###.##");
	double input = GlobalVariable.UpdatedMonto
	GlobalVariable.UpdatedMonto= df2.format(input)
	println('UpdatedMonto6 Is : ' + GlobalVariable.UpdatedMonto)
	} catch (Exception e) {
		KeywordUtil.logInfo('Else block of Commission amount failed..!!')
	}
}

ArrayList<String> Currtype = new ArrayList<String>(Arrays.asList(GlobalVariable.CurrencyTypeDestino1, GlobalVariable.CurrencyTypeDestino2, GlobalVariable.CurrencyTypeDestino3, GlobalVariable.CurrencyTypeDestino4))
//VerifyBalance()

 GlobalVariable.MultiDestinoValue = ('\n'+
			'------------------------ : DETAILED REPORT : -----------------------' +
			'\n'+
			'TEST EXECUTION BALANCE REPORT FOR : ' + GlobalVariable.CaseDescription +
			'\n'+
			' * TRANSACTION MESSAGE EXPECTED: ' + GlobalVariable.TransactionMessage +
			'\n'+
			' * TRANSACTION MESSAGE APP: ' + GlobalVariable.PaymentMessageMultiple +
			'\n'+
			' * TRANSACTION MESSAGE AUTHORIZER1 EXPECTED : ' + GlobalVariable.TransactionMessageAuthorizer1 +
			'\n'+
			' * TRANSACTION MESSAGE AUTHORIZER1 APP : ' + GlobalVariable.AuthorizePaymentMesg1 +
			'\n'+
			' * TRANSFER MONTO AMOUNT IS : ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.OriginalMonto +
			'\n'+
			' * RATE APPLIED : ' + GlobalVariable.Tasa +
			'\n'+
			' * TAX APPLIED : ' + GlobalVariable.Impuesto +
			'\n'+
			' * TOTAL TAX AMOUNT : ' + GlobalVariable.GetImpuesta +
			'\n'+
			' * TOTAL COMISSION : ' + GlobalVariable.CommissionAmount +
			'\n'+
			' * TRANSFERED (MONTO / TASA / COMMISIONES ) FROM ACCOUNT : ' + GlobalVariable.CuentaOrigen + " IS : " + GlobalVariable.CurrencyTypeOrigen + ' : ' + GlobalVariable.UpdatedMonto +
			'\n'+
			' * PAYMENT VERIFICATION IS : ' + GlobalVariable.Verification +
			'\n'+
			'\n'+
			'\n'+'BALANCES BEFORE TRANSACTIONS (DATABASE) : \n' +
			'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_db + '\n' +
			printMultiDestinoValues(TotalDestino, DestinoAccDB, Currtype, destinoBeforeDB) +
			'\n'+
			'\n'+
			'BALANCES BEFORE TRANSACTIONS (APPLICATION) : \n' +
			'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_before_app + '\n' +
			 printMultiDestinoValues(TotalDestino, DestinoAccDB, Currtype, destinoBeforeApp) +
			'\n'+
			'\n'+
			'BALANCES AFTER TRANSACTIONS (DATABASE) : \n' +
			'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_db  + '\n' +
			printMultiDestinoValues(TotalDestino, DestinoAccDB, Currtype, destinoAfterDB) +
			'\n'+
			'\n'+
			'TAX APPLIED ' + GlobalVariable.Impuesto + ' CALCULATION VIA FUNCTION : \n' +
			'\n'+
			'\n'+
			'Origen Account :- ' + GlobalVariable.CuentaOrigen + ' Balance Calculation Via Function :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.Tax_Origenbalance_after  + '\n' +
			printMultiDestinoValuesTax(TotalDestino, DestinoAccDB, Currtype, TaxCalculationAfterTrans) +
			'\n'+
			'\n'+
			'BALANCES AFTER TRANSACTIONS (APPLICATION) : \n' +
			'Origen Account :-  ' + GlobalVariable.CuentaOrigen + ' Balance Is :- ' + GlobalVariable.CurrencyTypeOrigen + ' - ' + GlobalVariable.origen_after_app  + '\n' +
			printMultiDestinoValues(TotalDestino, DestinoAccDB, Currtype, destinoAfterApp) +
			'\n'+
			GlobalVariable.CaseStatusMessage+
			'\n'+
			'-------------------------------------------------------------------------')
 
 def printMultiDestinoValues(int size, def destino, def CurrencyType, def balance) {
	 String val = ''
	 for(int i = 0; i<size; i++) {
		 val += 'Destino Account :- ' + destino[i] + ' Balance Is :- ' + CurrencyType[i] + ' - ' + balance[i] + '\n'
	 }
	 return val
 }
 
 def printMultiDestinoValuesTax(int size, def destino, def CurrencyType, def balance) {
	 String value = ''
	 for(int i = 0; i<size; i++) {
		 value += 'Destino Account :- ' + destino[i] + ' Balance Calculation Via Function :- ' + CurrencyType[i] + ' - ' + balance[i] + '\n'
	 }
	 return value
 }
  
 def VerifyBalance() {
	 
	 def executionProfile = RC.getExecutionProfile()
	 if (executionProfile.equalsIgnoreCase('local')) {
		 println('Profile Set Is Local - Bypass DB Connection TCs - Can not verify BD and App Balance..!!')
		 KeywordUtil.logInfo('Profile Set Is Local - Bypass DB Connection TCs - Can not verify BD and App Balance..!!')
	 } else {
		 //origin
		 GlobalVariable.origen_before_db = GlobalVariable.origen_before_db.toString()
		 GlobalVariable.origen_after_db = GlobalVariable.origen_after_db.toString()
		 
		 // Check For Balance Before Transaction For Origin Accounts
		 if(!GlobalVariable.origen_before_app.contains(GlobalVariable.origen_before_db)){
			 'Origin Balance Before Transaction - Database Doesn\'t Match With App..!!'
			 KeywordUtil.markFailed('Origin Balance Before Transaction In Database Doesn\'t Match With Origin Balance Before Transaction In App..!!')
			 GlobalVariable.CaseStatusMessage += "\n Origin Balance Before Transaction In Database Doesn\'t Match With Origin Balance Before Transaction In App..!! \n"
		 }
		 
		 // Check For Balance After Transaction For Origin Accounts
		 if(!GlobalVariable.origen_after_app.contains(GlobalVariable.origen_after_db)){
			 'Origin Balance After Transaction - Database Doesn\'t Match With App..!!'
			 KeywordUtil.markFailed('Origin Balance After Transaction In Database Doesn\'t Match With Origin Balance After Transaction In App..!!')
			 GlobalVariable.CaseStatusMessage += "\n Origin Balance After Transaction In Database Doesn\'t Match With Origin Balance After Transaction In App..!! \n"
		 }
		 
		//destino
		GlobalVariable.destino_before_db0 = GlobalVariable.destino_before_db0.toString()
		GlobalVariable.destino_before_db1 = GlobalVariable.destino_before_db1.toString()
		GlobalVariable.destino_before_db2 = GlobalVariable.destino_before_db2.toString()
		GlobalVariable.destino_before_db3 = GlobalVariable.destino_before_db3.toString()
		GlobalVariable.destino_after_db0 = GlobalVariable.destino_after_db0.toString()
		GlobalVariable.destino_after_db1 = GlobalVariable.destino_after_db1.toString()
		GlobalVariable.destino_after_db2 = GlobalVariable.destino_after_db2.toString()
		GlobalVariable.destino_after_db3 = GlobalVariable.destino_after_db3.toString()
			 
		// Check For Balance Before Transaction For Destino Accounts
		if(!(GlobalVariable.destino_before_app0.contains(GlobalVariable.destino_before_db0) && GlobalVariable.destino_before_app1.contains(GlobalVariable.destino_before_db1) && GlobalVariable.destino_before_app2.contains(GlobalVariable.destino_before_db2) && GlobalVariable.destino_before_app3.contains(GlobalVariable.destino_before_db3))){
			'Destino Balance Befor Transaction - Database Doesn\'t Match With App..!!'
			KeywordUtil.markFailed('Destino Balance Before Transaction In Database Doesn\'t Match With Destino Balance Before Transaction In App..!!')
			GlobalVariable.CaseStatusMessage += "\n Destino Balance Before Transaction In Database Doesn\'t Match With Destino Balance Before Transaction In App..!! \n"
		}
			 
		// Check For Balance After Transaction For Destino Accounts
		if(!(GlobalVariable.destino_after_app0.contains(GlobalVariable.destino_after_db0) && GlobalVariable.destino_after_app1.contains(GlobalVariable.destino_after_db1) && GlobalVariable.destino_after_app2.contains(GlobalVariable.destino_after_db2) && GlobalVariable.destino_after_app3.contains(GlobalVariable.destino_after_db3))){
			'Destino Balance After Transaction - Database Doesn\'t Match With App..!!'
			 KeywordUtil.markFailed('Destino Balance After Transaction In Database Doesn\'t Match With Destino Balance After Transaction In App..!!')
			 GlobalVariable.CaseStatusMessage += "\n Destino Balance After Transaction In Database Doesn\'t Match With Destino Balance After Transaction In App..!! \n"
		}
	 }
 }
