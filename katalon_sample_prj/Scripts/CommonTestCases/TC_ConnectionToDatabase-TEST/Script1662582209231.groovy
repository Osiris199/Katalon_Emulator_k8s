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
import com.kms.katalon.core.annotation.Keyword as Keyword

	//172.22.30.34
    CustomKeywords.'helper.functionsupdate.connectDB1'(host, db, port, user, pass)
	
	//'GET DATABASE ORIGIN ACCOUNT BALANCE  : '
	CustomKeywords.'helper.functionsupdate.ConsultaBalance_Origen'(CuentaOrigen)
		
	//'GET DATABASE DESTINO ACCOUNT BALANCE  : '
	//CustomKeywords.'helper.functionsupdate.ConsultaBalance_Destino'(CuentaDestino)
	
	//172.20.50.186
	//CustomKeywords.'helper.functionsupdate.connectDB2'(host1, db1, port1, user1, pass1)
	
	//172.20.50.188
	//CustomKeywords.'helper.functionsupdate.connectDB2'(host2, db1, port1, user1, pass1)
	
	//172.20.50.184
	//CustomKeywords.'helper.functionsupdate.connectDB2'(host3, db1, port1, user1, pass1)
	
	//172.22.50.212
	//CustomKeywords.'helper.functionsupdate.connectDB3'(user2, pass2)

    //CustomKeywords.'helper.functionsupdate.ClearAuthorization'()
