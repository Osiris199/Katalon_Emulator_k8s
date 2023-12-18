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
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Transparency;

class customfunction {

	@Keyword
	def QuickLogin(RNC,Username1,Username2,Username3,Password,Email,Role,Type) {
		String ActualUsername = ''

		if(Mobile.verifyElementExist(findTestObject('Login/GetText-Username'), 2, FailureHandling.OPTIONAL)) {
			ActualUsername = Mobile.getText(findTestObject('Login/GetText-Username'), 5, FailureHandling.OPTIONAL)
		} else if(Mobile.verifyElementExist(findTestObject('Login/GetText-Username1'), 2, FailureHandling.OPTIONAL)) {
			ActualUsername = Mobile.getText(findTestObject('Login/GetText-Username1'), 5, FailureHandling.OPTIONAL)
		}

		println "Logged UserName Is " + ActualUsername

		if (Role == "Solicitante" && Type == "Payment") {
			println "Role Is " + Role
			println "PaymentType Is " + Type
			GlobalVariable.Username = Username1
		} else if (Role == "Autorizador" && Type == "Payment") {
			println "Role Is " + Role
			println "PaymentType Is " + Type
			GlobalVariable.Username = Username2
		} else if (Role == "Autorizador2" && Type == "Payment") {
			println "Role Is " + Role
			println "PaymentType Is " + Type
			GlobalVariable.Username = Username3
		}

		println "GlobalVariable.Username Is " + GlobalVariable.Username

		if (ActualUsername.equals(GlobalVariable.Username)) {
			'ENTER CORRECT PASSWORD:'
			Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 5, FailureHandling.CONTINUE_ON_FAILURE)
			HideKeyboard()
			'CLICK ON CONTINUAR BUTTON:'
			Mobile.tap(findTestObject('Buttons/CONTINUAR'), 3, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Login/Acceder'), 1, FailureHandling.OPTIONAL)
			if(Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error communicating with provider'), 20, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error'), 2, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
				WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
				WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_EMAIL'), [('Email') : Email], FailureHandling.CONTINUE_ON_FAILURE)
				Mobile.delay(5)
			}
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboard'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
		} else {

			'LOG OUT AND USER REGISTRATION FLOW :'
			if (ActualUsername.equals(GlobalVariable.Username)) {
				println "Logged User Is " + GlobalVariable.Username
			} else if (ActualUsername.equals('USUARIO') || Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 1, FailureHandling.OPTIONAL)){
			} else {
				//				Mobile.tap(findTestObject('Deregistration/ClearUsername'), 5, FailureHandling.OPTIONAL)
				//				Mobile.tap(findTestObject('Deregistration/ClearUsername1'), 1, FailureHandling.OPTIONAL)
				//				Mobile.tap(findTestObject('Login/ACEPTAR'), 3, FailureHandling.OPTIONAL)
				//				Mobile.delay(2)
				Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 2, FailureHandling.OPTIONAL)
				HideKeyboard()
				Mobile.tap(findTestObject('Login/Acceder'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(2)
				Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 5,FailureHandling.OPTIONAL)
				Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 20, FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 1,FailureHandling.OPTIONAL)
				UnlinkUser()
			}
			//changes in TC_Login
			WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
			// Temp - Check for 'Error Communicating With Provider..'
			if(Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error communicating with provider'), 1, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error'), 2, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
				WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
			}
			WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_EMAIL'), [('Email') : Email], FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboard'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
		}
	}


	@Keyword
	def QuickLoginTokenImport(RNC,Username1,Username2,Username3,Password,Email,Role,Type) {

		String ActualUsername = ''
		if(Mobile.verifyElementExist(findTestObject('Login/GetText-Username'), 2, FailureHandling.OPTIONAL)) {
			ActualUsername = Mobile.getText(findTestObject('Login/GetText-Username'), 5, FailureHandling.OPTIONAL)
		} else if(Mobile.verifyElementExist(findTestObject('Login/GetText-Username1'), 2, FailureHandling.OPTIONAL)) {
			ActualUsername = Mobile.getText(findTestObject('Login/GetText-Username1'), 5, FailureHandling.OPTIONAL)
		}

		println "Logged UserName Is " + ActualUsername
		println "GlobalVariable.Username Is " + GlobalVariable.Username

		if (ActualUsername.equals(GlobalVariable.Username)) {
			'ENTER CORRECT PASSWORD:'
			Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 5, FailureHandling.CONTINUE_ON_FAILURE)
			HideKeyboard()
			'CLICK ON CONTINUAR BUTTON:'
			Mobile.tap(findTestObject('Buttons/CONTINUAR'), 3, FailureHandling.OPTIONAL)
			//WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboard'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
		} else {

			'LOG OUT AND USER REGISTRATION FLOW :'
			if (ActualUsername.equals(GlobalVariable.Username)) {
				println "Logged User Is " + GlobalVariable.Username
			} else if (ActualUsername.equals('USUARIO') || Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 1, FailureHandling.OPTIONAL)){
			} else {
				//				Mobile.tap(findTestObject('Deregistration/ClearUsername'), 2, FailureHandling.OPTIONAL)
				//				Mobile.tap(findTestObject('Deregistration/ClearUsername1'), 1, FailureHandling.OPTIONAL)
				//				Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
				//				Mobile.delay(2)
				Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 2, FailureHandling.OPTIONAL)
				HideKeyboard()
				Mobile.tap(findTestObject('Login/Acceder'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(2)
				Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 5,FailureHandling.OPTIONAL)
				Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 10, FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 1,FailureHandling.OPTIONAL)
				UnlinkUser()
			}
			WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
			// Temp - Check for 'Error Communicating With Provider..'
			if(Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error communicating with provider'), 1, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error'), 2, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
				WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
			}
			WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_EMAIL'), [('Email') : Email], FailureHandling.CONTINUE_ON_FAILURE)
			//Mobile.delay(5)
			//WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboard'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
		}
	}

	@Keyword
	def QuickLoginSwitch(RNC,Username1,Username2,Username3,Password,Email,Role,Verification,Type) {
		Mobile.waitForElementPresent(findTestObject('AppLaunch/TuBnco Empresas'), 45, FailureHandling.CONTINUE_ON_FAILURE)

		String ActualUsername = ''
		if(Mobile.verifyElementExist(findTestObject('Login/GetText-Username'), 2, FailureHandling.OPTIONAL)) {
			ActualUsername = Mobile.getText(findTestObject('Login/GetText-Username'), 5, FailureHandling.OPTIONAL)
		} else if(Mobile.verifyElementExist(findTestObject('Login/GetText-Username1'), 2, FailureHandling.OPTIONAL)) {
			ActualUsername = Mobile.getText(findTestObject('Login/GetText-Username1'), 5, FailureHandling.OPTIONAL)
		}

		println "Currently Logged UserName Is " + ActualUsername
		if (Role == "Solicitante" && Verification == "Authorize" || Verification =="Reject") {
			println "Role Is " + Role
			GlobalVariable.Username = Username2
		} else if (Role == "Solicitante" && Verification == "D-Authorize" || Verification == "D-Reject") {
			if(GlobalVariable.Counter == "Authorize-1") {

				GlobalVariable.Username = Username2
				println "GlobalVariable.Username Is " + GlobalVariable.Username
			} else if(GlobalVariable.Counter == "Authorize-2") {

				GlobalVariable.Username = Username3
				println "GlobalVariable.Username Is " + GlobalVariable.Username
			}
		}
		Mobile.delay(2)
		if (ActualUsername.equals(GlobalVariable.Username)) {

			'ENTER CORRECT PASSWORD:'
			Mobile.delay(1)
			Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 5, FailureHandling.CONTINUE_ON_FAILURE)
			HideKeyboard()
			'CLICK ON CONTINUAR BUTTON:'
			Mobile.tap(findTestObject('Buttons/CONTINUAR'), 3, FailureHandling.OPTIONAL)
			Mobile.delay(2)
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboard'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
			//RefreshDasboard()
		} else {
			'LOG OUT AND USER REGISTRATION FLOW :'
			if (ActualUsername.equals(GlobalVariable.Username)) {
				println "Logged User Is " + GlobalVariable.Username
			} else if (ActualUsername.equals('USUARIO') || Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 1, FailureHandling.OPTIONAL)){
			} else {
				//				Mobile.tap(findTestObject('Deregistration/ClearUsername'), 2, FailureHandling.OPTIONAL)
				//				Mobile.tap(findTestObject('Deregistration/ClearUsername1'), 1, FailureHandling.OPTIONAL)
				//				Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
				//				Mobile.delay(1)
				//				Mobile.tap(findTestObject('Login/CONTRASEA'), 2, FailureHandling.OPTIONAL)
				Mobile.setText(findTestObject('Login/CONTRASEA'), Password, 2, FailureHandling.OPTIONAL)
				HideKeyboard()
				Mobile.tap(findTestObject('Login/Acceder'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(1)
				Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 5,FailureHandling.OPTIONAL)
				Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 20, FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 1,FailureHandling.OPTIONAL)
				UnlinkUser()
			}
			WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
			// Temp - Check for 'Error Communicating With Provider..'
			if(Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error communicating with provider'), 1, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('ErrorMessage/Error'), 2, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
				WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_Login'), [('RNC') : RNC, ('Username') : GlobalVariable.Username, ('Password') : Password],FailureHandling.CONTINUE_ON_FAILURE)
			}
			WebUI.callTestCase(findTestCase('TC-Scenarios/LOGIN/TC_EMAIL'), [('Email') : Email], FailureHandling.CONTINUE_ON_FAILURE)
			Mobile.delay(5)
			WebUI.callTestCase(findTestCase('CommonTestCases/TC_VerifyDashboard'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
			//RefreshDasboard()
		}
	}


	@Keyword
	def VerifyTransactionMessage(Username1, Username2, Username3) {

		if(GlobalVariable.Username == Username1) {
			GlobalVariable.ExpectedPaymentMessage = GlobalVariable.TransactionMessage
		} else if(GlobalVariable.Username == Username2) {
			//GlobalVariable.PaymentMessage = GlobalVariable.AuthorizePaymentMesg1
			GlobalVariable.ExpectedPaymentMessage = GlobalVariable.TransactionMessageAuthorizer1
		} else if(GlobalVariable.Username == Username3) {
			//GlobalVariable.PaymentMessage = GlobalVariable.AuthorizePaymentMesg2
			GlobalVariable.ExpectedPaymentMessage = GlobalVariable.TransactionMessageAuthorizer2
		}

		if(GlobalVariable.ExpectedPaymentMessage == 'NA' || GlobalVariable.ExpectedPaymentMessage == '' || GlobalVariable.ExpectedPaymentMessage == null || GlobalVariable.ExpectedPaymentMessage == 'null') {
			//GlobalVariable.CaseStatusMessage += "\n Expected Transaction Message is null - No need to check with actual message..!! \n"
		} else if(GlobalVariable.ExpectedPaymentMessage == GlobalVariable.AppPaymentMessage ) {
			GlobalVariable.CaseStatusMessage += "\n Expected Payment Message Matches With App Payment Message..!!"
		} else {
			KeywordUtil.markFailed("Expected Payment Message Doesn't Match With Actual Payment Message..!!")
			GlobalVariable.CaseStatusMessage += "\n Expected Payment Message Doesn't Match With App Payment Message..!!"
		}

		GlobalVariable.ExpectedPaymentMessage = ''
		GlobalVariable.AppPaymentMessage = ''
		GlobalVariable.AuthorizePaymentMesg = ''
	}

	@Keyword
	def OrigenAccount(String CtaOrigen) {
		if(Mobile.verifyElementExist(findTestObject('PagosYTransferencia/Cuentas-List'), 05, FailureHandling.OPTIONAL)) {
			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			try {
				Mobile.scrollToText(CtaOrigen, FailureHandling.OPTIONAL)
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaOrigen+"' or . = '"+CtaOrigen+"')]"))

				if(Element.displayed.TRUE) {
					Element.click()
					if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Origen-Title'), 5, FailureHandling.OPTIONAL)
					||Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Destino-Title'), 5, FailureHandling.OPTIONAL)){
						//Screenshot()
						KeywordUtil.markPassed("Origen Account Selected Sucessfuly...!!!")
						GlobalVariable.OrigenAccountStatus = "Available"
						if(Mobile.waitForElementPresent(findTestObject('GetBalances/GetOrigenBalance'), 3, FailureHandling.OPTIONAL)) {
							GlobalVariable.origen_before_app = Mobile.getText(findTestObject('GetBalances/GetOrigenBalance'), 3, FailureHandling.OPTIONAL)
						} else if(Mobile.waitForElementPresent(findTestObject('MultipleTransactions/GetOrigenBalance-multiple transaction'), 3, FailureHandling.OPTIONAL)){
							GlobalVariable.origen_before_app = Mobile.getText(findTestObject('MultipleTransactions/GetOrigenBalance-multiple transaction'), 3, FailureHandling.OPTIONAL)
						} else if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/GetOriginBalance'), 1, FailureHandling.OPTIONAL)){
							GlobalVariable.origen_before_app = Mobile.getText(findTestObject('ImpuestosYServicious/GetOriginBalance'), 3, FailureHandling.OPTIONAL)
						}
						String CurrencyOrigen = GetCurrency(GlobalVariable.origen_before_app)
						GlobalVariable.CurrencyTypeOrigen = CurrencyOrigen
						String OrigenBalanceBefore = FormatBalance(GlobalVariable.origen_before_app)
						GlobalVariable.origen_before_app = OrigenBalanceBefore
					} else {
						GlobalVariable.CaseStatusMessage += "CtaOrigen Account Not Found " + CtaOrigen
						GlobalVariable.origen_before_app = "SelectionFailed"
						GlobalVariable.OrigenAccountStatus = "NotAvailable"
						//Screenshot()
						//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
						KeywordUtil.markPassed('Origen Account Not Selected...!!!')
					}
				} else {
					GlobalVariable.CaseStatusMessage += "CtaOrigen Account Not Found " + CtaOrigen
					GlobalVariable.origen_before_app = "No Info For Acct " + CtaOrigen
					println "CtaOrigen Account Not Found " + CtaOrigen
					GlobalVariable.OrigenAccountStatus = "NotAvailable"
					KeywordUtil.markFailed('CtaOrigen Account Not Found ' + CtaOrigen)
					//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
				}
			} catch(Exception e) {
				GlobalVariable.CaseStatusMessage += "CtaOrigen Account Not Found " + CtaOrigen
				GlobalVariable.origen_before_app = "CtaOrigen Account Not Found " + CtaOrigen
				GlobalVariable.OrigenAccountStatus = "NotAvailable"
				KeywordUtil.markFailed('CtaOrigen Account Not Found ' + CtaOrigen)
				//Screenshot()
				//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
				//e.printStackTrace()
			}
		} else {
			GlobalVariable.CaseStatusMessage += "CtaOrigen Account Not Found " + CtaOrigen
			GlobalVariable.origen_before_app = "No Info For Acct " + CtaOrigen
			GlobalVariable.OrigenAccountStatus = "NotAvailable"
			KeywordUtil.markFailed('CtaOrigen Account Not Found ' + CtaOrigen)
			//Screenshot()
			//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
		}
	}

	@Keyword
	def DestinoAccount(String CtaDestino) {

		if(Mobile.verifyElementExist(findTestObject('PagosYTransferencia/Cuentas-List'), 05, FailureHandling.OPTIONAL) || Mobile.verifyElementExist(findTestObject('Object Repository/PagosYTransferencia/Nombre del beneficiario, Nmero del producto'), 05, FailureHandling.OPTIONAL) || Mobile.verifyElementExist(findTestObject('PagosYTransferencia/Prestamo-List'), 05, FailureHandling.OPTIONAL)) {
			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			try {
				Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaDestino+"' or . = '"+CtaDestino+"')]"))
				if(Element.displayed.TRUE) {
					Element.click()

					if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Destino-Title'), 5, FailureHandling.OPTIONAL)){
						//Screenshot()
						KeywordUtil.markPassed("Destino Account Selected Sucessfuly...!!!")
						GlobalVariable.DestinoAccountStatus = "Available"
						GlobalVariable.destino_before_app = Mobile.getText(findTestObject('GetBalances/GetDestinoBalance'), 3, FailureHandling.OPTIONAL)

						String CurrencyDestino = GetCurrency(GlobalVariable.destino_before_app)
						if(CurrencyDestino == null) {
							GlobalVariable.CurrencyTypeDestino = "No Currency Info"
						}else {
							GlobalVariable.CurrencyTypeDestino = CurrencyDestino
						}
						String DestinoBalanceBefore = FormatBalance(GlobalVariable.destino_before_app)
						if(DestinoBalanceBefore == null) {
							GlobalVariable.destino_before_app = "No Info For Acct " + CtaDestino
						}else {
							GlobalVariable.destino_before_app = DestinoBalanceBefore
						}
					} else {
						GlobalVariable.CaseStatusMessage += "CtaDestino Account Not Found " + CtaDestino
						GlobalVariable.destino_before_app = "SelectionFailed Account or Beneficiary not visible"
						GlobalVariable.OrigenAccountStatus = "NotAvailable"
						//Screenshot()
						//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
						KeywordUtil.markPassed('Destino Account Not Selected...!!!')
					}
				} else {
					GlobalVariable.destino_before_app = "CtaDestino Account Not Found " + CtaDestino
					GlobalVariable.DestinoAccountStatus = "NotAvailable"
					//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
					println "CtaDestino Account Not Found " + CtaDestino
					KeywordUtil.markFailed('CtaDestino Account Not Found ' + CtaDestino)
				}
			} catch(Exception e) {
				GlobalVariable.CaseStatusMessage += "CtaDestino Account Not Found " + CtaDestino
				GlobalVariable.destino_before_app = "CtaDestino Account Not Found " + CtaDestino
				GlobalVariable.DestinoAccountStatus = "NotAvailable"
				println "CtaDestino Account Not Found " + CtaDestino
				//e.printStackTrace()
				KeywordUtil.markFailed('CtaDestino Account Not Found ' + CtaDestino)
			}
		} else {
			GlobalVariable.CaseStatusMessage += "CtaDestino Account Not Found " + CtaDestino
			GlobalVariable.destino_before_app = "CtaDestino Account Not Found " + CtaDestino
			GlobalVariable.DestinoAccountStatus = "NotAvailable"
			//Screenshot()
			//KeywordUtil.markPassed("Destino Account Not Available - Redirected To Test Case...!!!")
			//Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
			//Mobile.tap(findTestObject('Login/ACEPTAR'), 1,FailureHandling.OPTIONAL)
			KeywordUtil.markFailed('CtaDestino Account Not Found ' + CtaDestino)
		}
	}

	@Keyword
	def OrigenAccount_AfterBalance(String CtaOrigen) {
		if(CtaOrigen == '' || CtaOrigen == null || CtaOrigen == 'NA') {
			GlobalVariable.origen_after_app =  "Account Not Available " + CtaOrigen
		} else {
			Refresh()
			try {
				Mobile.scrollToText(CtaOrigen, FailureHandling.OPTIONAL)
				AppiumDriver<?> driver = MobileDriverFactory.getDriver()
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaOrigen+"' or . = '"+CtaOrigen+"')]"))
				Element.click()
				Mobile.delay(5)
				try {
					if (Mobile.waitForElementPresent(findTestObject('GetBalances/TuBnco Empresas'), 25, FailureHandling.OPTIONAL)){
						GlobalVariable.origen_after_app = Mobile.getText(findTestObject('GetBalances/GetBalance-AfterPayment'), 3, FailureHandling.OPTIONAL)
						String OrigenBalanceAfter = FormatBalance(GlobalVariable.origen_after_app)
						GlobalVariable.origen_after_app = OrigenBalanceAfter
					} else {
						println "Account Is Not Available : " + CtaOrigen
						GlobalVariable.origen_after_app = "Account Is Not Available : " + CtaOrigen
					}
				} catch(Exception e) {
					GlobalVariable.origen_after_app =  "Account Not Available " + CtaOrigen
					//e.printStackTrace()
				}
			}catch(Exception e) {
				GlobalVariable.origen_after_app =  "Account Not Available " + CtaOrigen
				//e.printStackTrace()
			}
		}
	}

	@Keyword
	def DestinoAccount_AfterBalance(String CtaDestino) {
		if(CtaDestino == '' || CtaDestino == null || CtaDestino == 'NA') {
			GlobalVariable.origen_after_app =  "Account Not Available " + CtaDestino

		} else  if(GlobalVariable.BenefUnicoDetails == 'True') {
			GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino

		} else {
			Refresh()
			try {
				Mobile.scrollToText(CtaDestino)
				AppiumDriver<?> driver = MobileDriverFactory.getDriver()
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaDestino+"' or . = '"+CtaDestino+"')]"))
				Element.click()
				Mobile.delay(5)
				try {
					if (Mobile.waitForElementPresent(findTestObject('GetBalances/TuBnco Empresas'), 25, FailureHandling.OPTIONAL)){
						GlobalVariable.destino_after_app = Mobile.getText(findTestObject('GetBalances/GetBalance-AfterPayment'), 3, FailureHandling.OPTIONAL)
						String DestinoBalanceAfter = FormatBalance(GlobalVariable.destino_after_app)
						GlobalVariable.destino_after_app = DestinoBalanceAfter
					} else {
						println "Account Is Not Available : " + CtaDestino
						GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
					}
				} catch(Exception e) {
					GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
					//e.printStackTrace()
				}
			}catch(Exception e) {
				GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
				//e.printStackTrace()
			}
		}
	}

	@Keyword
	def FormatBalance(String Balance) {
		try {
			String Balance1 = Balance.replaceAll('[,]', '')
			String Balance2 = Balance1.substring(4)
			return Balance2
		} catch(Exception e) {
			KeywordUtil.logInfo("Entered Catch Block of FormatBalance")
			//e.printStackTrace()
		}
	}

	@Keyword
	def GetCurrency(String Currency) {
		try {
			String Curr
			if(Currency.contains("DOP") && Currency.contains("USD")) {
				Curr  = "DOP/USD"
			} else if(Currency.contains("DOP") || Currency.contains("USD")) {
				Curr = Currency.substring(0,3)
			} else if(Currency.contains("EUR")) {
				Curr = Currency.substring(0,3)
			} else {
				Curr = "NA"
			}
			return Curr
		} catch(Exception e) {
			KeywordUtil.logInfo("Currency Is Not Available")
			//e.printStackTrace()
		}
	}

	@Keyword
	def UpdateMonto(String Amount) {
		try {
			double Balance1 = Double.parseDouble(GlobalVariable.origen_before_db)
			double Balance2 = Double.parseDouble(Amount)
			double UpdatedValue = Balance1+Balance2
			Amount = UpdatedValue
			println "Origen Monto Balance Updated Is : " + Amount
			return Amount
		} catch(Exception e) {
			KeywordUtil.logInfo("Monto Amount Value Is Null")
			//e.printStackTrace()
		}
	}

	def Authorization(String Concept) {
		if(Mobile.verifyElementExist(findTestObject('Authorization/Autorizaciones-Title'), 05, FailureHandling.OPTIONAL)) {
			Mobile.delay(2)
			println "CONCEPT IS " + Concept
			Mobile.scrollToText(Concept)
			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+Concept+"' or . = '"+Concept+"')]"))

			//Mobile.tap(findTestObject('Authorize/CheckBox'), 05, FailureHandling.OPTIONAL)
		} else {
			//Screenshot()
			KeywordUtil.markFailed("Autorizaciones-Title Page Is Not Available - Redirected To Dashboard Case...!!!")
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
		}
	}

	@Keyword
	def VerificationDashboard(String Description) {
		if (Mobile.waitForElementPresent(findTestObject('Login/Izquierda'), 2, FailureHandling.OPTIONAL)) {
			KeywordUtil.markPassed('Scenario Passed...!!!' + Description)
			Mobile.tap(findTestObject('RegistroDeDispositivo/Confirmar'), 5,FailureHandling.OPTIONAL)
			Mobile.delay(2)
			//Screenshot()
			Mobile.tap(findTestObject('Buttons/Si'), 5,FailureHandling.OPTIONAL)
			Mobile.delay(2)
			//Screenshot()
			Mobile.tap(findTestObject('Buttons/Si'), 5,FailureHandling.OPTIONAL)
			Mobile.delay(2)

			//			if(Mobile.waitForElementPresent(findTestObject('TokenImport/Dispositivo pendiente de activacin'), 10, FailureHandling.OPTIONAL)
			//				||Mobile.waitForElementPresent(findTestObject('TokenImport/Salir'), 3, FailureHandling.OPTIONAL)) {
			//				KeywordUtil.markPassed('Token Import Required..!!!')
			//
			//				Mobile.callTestCase(findTestCase('Test Cases/TC-TokenDigitalMigration/TC-TokenDigitalMigration'), [('RNC') : RNC, ('Username1') : Username1, ('Username2') : Username2, ('Username3') : Username3, ('Password') : Password, ('Email') : Email, ('Role') : Role, ('Type') : Type], FailureHandling.STOP_ON_FAILURE)
			//
			//			} else
			if(Mobile.verifyElementExist(findTestObject('RegistroDeDispositivo/Resumen de productos'), 30, FailureHandling.CONTINUE_ON_FAILURE)){
				//Screenshot()

				Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
				GetUserDetails()
				Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
			}
		}
	}

	@Keyword
	def SIB(String SIB) {
		if (SIB == "TRUE" || SIB == "true" ) {
			Mobile.tap(findTestObject('SIB/android.widget.ImageView'), 2,FailureHandling.OPTIONAL)
			Mobile.delay(5)
			Mobile.tap(findTestObject('SIB/Browser-Chrome/ACCEPT CONTINUE'), 2, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('SIB/Browser-Chrome/NO THANKS'), 2, FailureHandling.OPTIONAL)

			if (Mobile.waitForElementPresent(findTestObject('SIB/Contctanos'), 45, FailureHandling.CONTINUE_ON_FAILURE)) {
				KeywordUtil.markPassed('SIB Site Visible...!!!')
				Screenshot()
				/*
				 String SIBURL1 = Mobile.getText(findTestObject('SIB/SIB-GetURL'), 5, FailureHandling.OPTIONAL)
				 String SIBURL2 = Mobile.getText(findTestObject('SIB/SIB-GetURL-1'), 5, FailureHandling.OPTIONAL)
				 if(SIBURL1.equals(GlobalVariable.SIBURL) || SIBURL2.equals(GlobalVariable.SIBURL1)) {
				 KeywordUtil.markPassed('SIB URL Is Validated...!!!')
				 Screenshot()
				 Mobile.pressBack()
				 }else {
				 KeywordUtil.markFailed('SIB URL Validation Failed...!!!')
				 Screenshot()
				 Mobile.pressBack()
				 }*/
			} else {
				Mobile.pressBack()
				Screenshot()
				KeywordUtil.markFailedAndStop('SIB Site Not Opened - Time Exceed More Then 45 Seconds Redirecting...!!!')
			}
		} else {
			println "SIB Verification Is ByPassed...!!!"
		}
	}

	@Keyword
	public void UserName(String Username) {

		def executionProfile = RC.getExecutionProfile()
		Mobile.comment("executionProfile=$executionProfile")

		if (executionProfile.equals('Solicitante') && GlobalVariable.Description.equals("Valid RNC Username Password")){
			GlobalVariable.Username = "margaritaf"
			Username = GlobalVariable.Username
			println "USERNAME UPDATED TO : " + Username
		} else {
			println "Valid RNC Username Password Scenario Not Executed"
		}
	}

	@Keyword
	public void GetUserDetails() {
		String Username = Mobile.getText(findTestObject('UserDetails/LoggedUserName'), 1, FailureHandling.OPTIONAL)
		String UserLoggedTime = Mobile.getText(findTestObject('UserDetails/LoggedUserTime'), 1, FailureHandling.OPTIONAL)
		//Screenshot()
		Mobile.delay(0.5)
		Mobile.tap(findTestObject('UserDetails/Balance disponible'), 5,FailureHandling.OPTIONAL)
		println('LOGGED USERNAME : ' +  Username + " LOGGED TIME : " + UserLoggedTime)
	}

	@Keyword
	def RefreshDasboard() {
		if (Mobile.waitForElementPresent(findTestObject('RegistroDeDispositivo/Resumen de productos'), 5, FailureHandling.STOP_ON_FAILURE)) {
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 10,FailureHandling.OPTIONAL)
			GetUserDetails()
			Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
		}else {
			println "User Is On DashBoard Screen"
		}
		//Screenshot()
	}

	@Keyword
	def Refresh() {
		if(Mobile.waitForElementPresent(findTestObject('UserDetails/HamburgerMenu'), 3, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 3,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/Productos'), 3,FailureHandling.OPTIONAL)
			Mobile.delay(2)
			if (Mobile.waitForElementPresent(findTestObject('RegistroDeDispositivo/Resumen de productos'), 30, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 2,FailureHandling.OPTIONAL)
				println "User Is On DashBoard Screen"
			} else {
				println "TRY AGAIN"
				Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 3,FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('UserDetails/Productos'), 3,FailureHandling.OPTIONAL)
				Mobile.delay(5)
			}
		} else {
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 3,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 3,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/Productos'), 3,FailureHandling.OPTIONAL)
			Mobile.delay(2)
			if (Mobile.waitForElementPresent(findTestObject('RegistroDeDispositivo/Resumen de productos'), 25, FailureHandling.OPTIONAL)) {
				println "User Is On DashBoard Screen"
				Mobile.tap(findTestObject('QuickAccess/Resumen de productos'), 2,FailureHandling.OPTIONAL)
			} else {
				println "TRY AGAIN"
				Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 3,FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('UserDetails/Productos'), 3,FailureHandling.OPTIONAL)
				Mobile.delay(5)
			}
		}
	}

	@Keyword
	public void GetText(String GetRNC, String GetUser) {
		println "RNC Value Is " + GetRNC
		println "RNC Value Is " + GetUser
		if(!GetRNC.equals(GlobalVariable.RNC)) {
			Mobile.clearText(findTestObject('Login/GetText-RNC'), 3, FailureHandling.OPTIONAL)
		} else if(!GetUser.equals(GlobalVariable.Username)) {
			if(Mobile.verifyElementExist(findTestObject('Login/GetText-Username'), 2, FailureHandling.OPTIONAL)) {
				Mobile.clearText(findTestObject('Login/GetText-Username'),1,FailureHandling.OPTIONAL)
			} else if(Mobile.verifyElementExist(findTestObject('Login/GetText-Username1'), 2, FailureHandling.OPTIONAL)) {
				Mobile.clearText(findTestObject('Login/GetText-Username1'),1,FailureHandling.OPTIONAL)
			}
			println "RNC Value Is " + GetUser
		} else {
			println "No Data Cleared...!!!"
		}
	}

	@Keyword
	public void LoginResponses(String Description) {
		try {
			Mobile.delay(2)
			for(int rowNum=1; rowNum<=findTestData("Excel-Data/Responses").getRowNumbers();rowNum++) {

				if (Mobile.waitForElementPresent(findTestObject(findTestData("Excel-Data/Responses").getValue(3,rowNum)),1, FailureHandling.CONTINUE_ON_FAILURE)) {
					//Screenshot()
					KeywordUtil.markPassed("Scenario Passed " + findTestData("Excel-Data/Responses").getValue(2,rowNum))
					break
				} else {
					println "Try Other"
				}
			}
		}catch(Exception e) {
			//println e.printStackTrace()
		}
	}

	@Keyword
	public void VerifyLaunch() {
		Mobile.verifyElementExist(findTestObject('AppLaunch/TuBnco Empresas'), 5, FailureHandling.CONTINUE_ON_FAILURE)

		if (Mobile.waitForElementPresent(findTestObject('AppLaunch/TuBnco Empresas'), 3, FailureHandling.CONTINUE_ON_FAILURE)) {
			//Screenshot()
			KeywordUtil.markPassed("Application Is Launched Successfully...!!!")
		} else {
			Screenshot()
			KeywordUtil.markFailed('Continuar Button Not Found...!!!')
		}
	}

	@Keyword
	public void Login() {
		Mobile.verifyElementExist(findTestObject('AppLaunch/TuBnco Empresas'), 5, FailureHandling.CONTINUE_ON_FAILURE)

		for (int rowNum=1; rowNum<=findTestData("DB_LoginAuthorizador").getRowNumbers(); rowNum++) {
			String Description = findTestData("DB_LoginAuthorizador").getValue(2,rowNum)

			println 'CASE NO : ' + GlobalVariable.Row
			println 'CASE DESCRIPTION : ' + GlobalVariable.Column

			Mobile.setText(findTestObject('Object Repository/Login/IDENTIFICACIN'), findTestData("DB_LoginAuthorizador").getValue(3,rowNum), 3,FailureHandling.OPTIONAL)
			HideKeyboard()
			Mobile.setText(findTestObject('Object Repository/Login/USUARIO'), findTestData("DB_LoginAuthorizador").getValue(4,rowNum), 3,FailureHandling.OPTIONAL)
			HideKeyboard()
			Mobile.tap(findTestObject('Object Repository/Login/CONTINUAR'),1,FailureHandling.OPTIONAL)
			if (Mobile.waitForElementPresent(findTestObject('Login/CONTRASEA'),2, FailureHandling.CONTINUE_ON_FAILURE)) {
				if(findTestData("DB_LoginAuthorizador").getValue(5,rowNum)=="") {
					Mobile.tap(findTestObject('Object Repository/Login/CONTINUAR'),1,FailureHandling.OPTIONAL)
				}else {
					Mobile.tap(findTestObject('Object Repository/Login/CONTRASEA'), 3,FailureHandling.OPTIONAL)
					Mobile.setText(findTestObject('Object Repository/Login/CONTRASEA'), findTestData("DB_LoginAuthorizador").getValue(5,rowNum), 3,FailureHandling.OPTIONAL)
					HideKeyboard()
					Mobile.delay(3)
					Mobile.tap(findTestObject('Object Repository/Login/CONTINUAR'),1,FailureHandling.OPTIONAL)
				}
			}
			//Verification()
			Mobile.tap(findTestObject('Object Repository/Login/ACEPTAR'), 1,FailureHandling.OPTIONAL)
			if (Mobile.waitForElementPresent(findTestObject('Login/ClearFields'),1, FailureHandling.CONTINUE_ON_FAILURE)) {
				Mobile.tap(findTestObject('Login/ClearFields'),1,FailureHandling.OPTIONAL)
			}
		}
	}

	@Keyword
	public void continuar() {

		String Value = Mobile.getAttribute(findTestObject('PagosYTransferencia/Continuar'),"enabled", 2, FailureHandling.OPTIONAL)
		println "Value Is " + Value
		if(Value == "true") {
			Mobile.tap(findTestObject('PagosYTransferencia/Continuar'), 3, FailureHandling.OPTIONAL)
		} else if(Value == "null")  {
			println "Continuar Button Not Available For Test Case...!!!"
		}
	}

	@Keyword
	def SwipeVerification(String CtaOrigen, String CtaDestino, String Concept) {
		if(Concept == "SwipeVerification") {
			println "CtaOrigen is " + CtaOrigen
			println "CtaOrigen is " + CtaDestino

			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			try {
				Mobile.scrollToText(CtaOrigen, FailureHandling.OPTIONAL)
				MobileElement Element1 = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaOrigen+"' or . = '"+CtaOrigen+"')]"))
				int x,y,x1,y1
				println "Value is Element1 " + Element1.getAttribute("bounds")
				println "Value is ElementX " + Element1.getLocation().getX()
				println "Value is ElementY " + Element1.getLocation().getY()

				x = Element1.getLocation().getX()
				y = Element1.getLocation().getY()
				x1 = Mobile.getDeviceWidth() - 10
				y1 = Element1.getLocation().getY() + 30

				//Mobile.swipe(x,y,x1,y1, FailureHandling.STOP_ON_FAILURE)
				Mobile.swipe(x,y,x1,y1, FailureHandling.OPTIONAL)
				Mobile.delay(5)

				if(Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/1.Confirmacion Screen Elements/android.widget.TextView - Transferir'), 5, FailureHandling.OPTIONAL)) {
					KeywordUtil.markPassed("Origin Account Swipe For Transaction Is present")
				}else {
					SwipeVerificationRetry(CtaOrigen, CtaDestino, Concept)
					Screenshot()
					KeywordUtil.markFailed("Origin Account Swipe Failed - Please Check...!!!")
				}
				//Screenshot()

			}catch (Exception e) {
				//e.printStackTrace()
			}
			Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
			Mobile.waitForElementPresent(findTestObject('QuickAccess/Resumen de productos'), 5, FailureHandling.OPTIONAL)
			try {
				Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
				MobileElement Element2 = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaDestino+"' or . = '"+CtaDestino+"')]"))
				int x,y,x1,y1
				println "Value is Element2 " + Element2.getAttribute("bounds")
				println "Value is ElementX " + Element2.getLocation().getX()
				println "Value is ElementY " + Element2.getLocation().getY()

				x = Element2.getLocation().getX()
				y = Element2.getLocation().getY()

				x1 =  Mobile.getDeviceWidth() - 10
				y1 = Element2.getLocation().getY() + 30
				Mobile.swipe(x,y,x1,y1, FailureHandling.OPTIONAL)
				Mobile.delay(5)
				if(Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/1.Confirmacion Screen Elements/android.widget.TextView - Transferir'), 5, FailureHandling.OPTIONAL)) {
					Screenshot()
					KeywordUtil.markFailed("Account Swipe Successfull For Destino Account - Transaction Is present")
				}else {
					SwipeVerificationRetry(CtaOrigen, CtaDestino, Concept)
					KeywordUtil.markPassed("Account Swipe Failed For Destino - Account Swipe For Transaction Is Restricted...!!!")
				}
			}catch (Exception e) {
				//e.printStackTrace()
				KeywordUtil.markPassed('Unable to swipe for destino account')

			}


			//				if (Mobile.waitForElementPresent(findTestObject('Swipe/Todo'), 15, FailureHandling.OPTIONAL)) {
			//					Screenshot()
			//					Mobile.tap(findTestObject('UserDetails/HamburgerMenu'), 5,FailureHandling.OPTIONAL)
			//					Mobile.tap(findTestObject('UserDetails/Productos'), 5,FailureHandling.OPTIONAL)
			//					KeywordUtil.markPassed("Account Swipe For Transaction Is Restricted - User Redirected To Detailed Page")
			//				}else {
			//					Screenshot()
			//					KeywordUtil.markFailed("Account Swipe Failed - Please Check...!!!")
			//				}



		} else {
			println "Test Case Doesnt Require Swipe Feature"
		}

	}

	def SwipeVerificationRetry(String CtaOrigen, String CtaDestino, String Concept) {
		int device_Height = Mobile.getDeviceHeight()
		int device_Width = Mobile.getDeviceWidth()
		int startX = device_Width / 2
		int endX = startX
		int startY = device_Height * 0.80
		int endY = device_Height * 1
		Mobile.swipe(startX, endY, endX, startY)
		SwipeVerification(CtaOrigen, CtaDestino, Concept)
	}

	def HorizontalSwipe(String CtaOrigen, String CtaDestino) {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()

		if(CtaDestino == "") {
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+DestinoAccount+"' or . = '"+DestinoAccount+"')]"))
		}
	}

	//swipe up
	@Keyword
	public void swipeUP() {
		try {
			int device_Height = Mobile.getDeviceHeight()
			int device_Width = Mobile.getDeviceWidth()
			int startX = device_Width / 2
			int endX = startX
			int startY = device_Height * 0.30
			int endY = device_Height * 0.70
			Mobile.swipe(startX, endY, endX, startY)
		}catch (Exception e) {
			KeywordUtil.logInfo("Can't perform swipe Popup windown maybe opened...!!!")
		}
	}

	//swipe down
	@Keyword
	public void swipeDown() {
		try {
			int device_Height = Mobile.getDeviceHeight()
			int device_Width = Mobile.getDeviceWidth()
			int startX = device_Width / 2
			int endX = startX
			int startY = device_Height * 0.30
			int endY = device_Height * 0.70
			Mobile.swipe(startX, startY, endX, endY)
		}catch (Exception e) {
			KeywordUtil.logInfo("Can't perform swipe Popup windown maybe opened...!!!")
		}
	}

	//Check Data After going back
	@Keyword
	public void ConfirmacionToDatosVerify () {

		GlobalVariable.Confirmacion_Destino =  Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Destino Value'),1 , FailureHandling.STOP_ON_FAILURE)
		GlobalVariable.Confirmacion_Monto =  Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Monto Value'),1 , FailureHandling.STOP_ON_FAILURE)
		if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuesto Title'), 1, FailureHandling.OPTIONAL) && Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Cargos por comisiones Title'), 1, FailureHandling.OPTIONAL)) {
			GlobalVariable.Confirmacion_Impuesto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuesto Value'),1 , FailureHandling.STOP_ON_FAILURE)
			GlobalVariable.Confirmacion_Concepto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto Value with Impuesto and Cargos'),1 , FailureHandling.STOP_ON_FAILURE)
		} else if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuesto Title'), 1, FailureHandling.OPTIONAL) && Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Tasa Title'), 1, FailureHandling.OPTIONAL)) {
			GlobalVariable.Confirmacion_Impuesto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuesto Value'),1 , FailureHandling.STOP_ON_FAILURE)
			GlobalVariable.Confirmacion_Concepto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto Value with Impuesto and Cargos'),1 , FailureHandling.STOP_ON_FAILURE)
		} else if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuesto Title'), 1, FailureHandling.OPTIONAL)){
			GlobalVariable.Confirmacion_Impuesto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuesto Value'),1 , FailureHandling.STOP_ON_FAILURE)
			GlobalVariable.Confirmacion_Concepto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto Value With Impuesto'),1 , FailureHandling.STOP_ON_FAILURE)
		} else if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Tasa Title'), 1, FailureHandling.OPTIONAL)) {
			GlobalVariable.Confirmacion_Impuesto = 'Impuesto not applicable'
			GlobalVariable.Confirmacion_Concepto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto Value With Impuesto'),1 , FailureHandling.STOP_ON_FAILURE)
		} else if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Cargos por comisiones Title'), 1, FailureHandling.OPTIONAL)) {
			GlobalVariable.Confirmacion_Impuesto = 'Impuesto not applicable'
			GlobalVariable.Confirmacion_Concepto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto Value With Impuesto'),1 , FailureHandling.STOP_ON_FAILURE)
		} else {
			GlobalVariable.Confirmacion_Impuesto = 'Impuesto not applicable'
			GlobalVariable.Confirmacion_Concepto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Concepto Value'),1 , FailureHandling.STOP_ON_FAILURE)
		}

		Mobile.tap(findTestObject('Object Repository/PaymentSuccess/Volver'), 3, FailureHandling.OPTIONAL)

		if(Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/2.Datos Screen Elements/Datos Concepto'), 1, FailureHandling.OPTIONAL)
		&&Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/2.Datos Screen Elements/Datos amount'), 1, FailureHandling.OPTIONAL)
		&&Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/2.Datos Screen Elements/Datos Destino Beneficiary'), 1, FailureHandling.OPTIONAL)
		&&Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/2.Datos Screen Elements/Datos Origen'), 1, FailureHandling.OPTIONAL)) {

			KeywordUtil.markPassed('Condition passed Origen, Origen account balance, Destino, Amount, Concepto fields are pre filled!!!')
			KeywordUtil.logInfo('Condition passed Origen, Origen account balance, Destino, Amount, Concepto fields are pre filled!!!')

		} else {

			KeywordUtil.markFailed('Condition passed Origen, Origen account balance, Destino, Amount, Concepto fields are not visible/filled!!!')
			KeywordUtil.logInfo('Condition passed Origen, Origen account balance, Destino, Amount, Concepto fields are not visible/filled!!!')
		}

		Mobile.tap(findTestObject('Object Repository/PagosYTransferencia/Continuar'), 3, FailureHandling.OPTIONAL)
	}


	@Keyword
	public void PrintResultsStatic() {
		String Transaction_message = Mobile.getText(findTestObject('PaymentSuccess/TransactionMessage'), 1, FailureHandling.STOP_ON_FAILURE)
		GlobalVariable.PaymentMessage = Transaction_message
		String Payment_time = Mobile.getText(findTestObject('PaymentSuccess/TransactionTime'), 1, FailureHandling.STOP_ON_FAILURE)
		GlobalVariable.PaymentTime = Payment_time
		String Monto = Mobile.getText(findTestObject('PaymentSuccess/Amount'), 1, FailureHandling.STOP_ON_FAILURE)
		String Impuesto = '';
		String Destino = '';
		String Concept = '';

		if(Mobile.verifyElementVisible(findTestObject('Object Repository/PagosYTransferencia/1.Confirmacion Screen Elements/Impuesto Title'), 1, FailureHandling.OPTIONAL)) {
			Impuesto = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/4.Completado Screen Element/Impuesto Value'), 1, FailureHandling.STOP_ON_FAILURE)
			Destino = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/4.Completado Screen Element/Destino With Impuesto'), 1, FailureHandling.STOP_ON_FAILURE)
			Concept = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/4.Completado Screen Element/Concepto With Impuesto'), 1, FailureHandling.STOP_ON_FAILURE)
		} else {
			Impuesto = 'Impuesto not applicable';
			Destino = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/4.Completado Screen Element/Destino Without Impuesto'), 1, FailureHandling.STOP_ON_FAILURE)
			Concept = Mobile.getText(findTestObject('Object Repository/PagosYTransferencia/4.Completado Screen Element/Concepto Without Impuesto'), 1, FailureHandling.STOP_ON_FAILURE)
		}

		String[] arr;
		arr =  Payment_time.split(" ")
		GlobalVariable.Result_TransactionTime = arr[0]

		//comparing values
		if(Monto.equals(GlobalVariable.Confirmacion_Monto)&&Impuesto.equals(GlobalVariable.Confirmacion_Impuesto)&&Destino.equals(GlobalVariable.Confirmacion_Destino)&&Concept.equals(GlobalVariable.Confirmacion_Concepto)) {
			KeywordUtil.markPassed("Data is same on Confirmation screen and Receipt screen!!!")
			KeywordUtil.logInfo("Data is same on Confirmation screen and Receipt screen!!!")
		} else {
			KeywordUtil.markFailed("Data is not same on Confirmation screen and Receipt screen!!!")
			KeywordUtil.logInfo("Data is not same on Confirmation screen and Receipt screen!!!")
		}

		'TRANSACTION DETAILS :'
		WebUI.comment('### - TRANSACTION PAYMENT MESSAGE ### : ' + Transaction_message)
		WebUI.comment('### - PAYMENT TIME ### : ' + Payment_time)
		WebUI.comment('### - MONTO AMOUNT ### : ' + Monto + '-' + GlobalVariable.Confirmacion_Monto)
		WebUI.comment('### - IMPUESTO ### : ' + Impuesto + '-' + GlobalVariable.Confirmacion_Impuesto)
		WebUI.comment('### - DESTINO ACCOUNT ### : ' + Destino + '-' + GlobalVariable.Confirmacion_Destino)
		WebUI.comment('### - CONCEPTO ### : ' + Concept + '-' + GlobalVariable.Confirmacion_Concepto)
	}

	//pago unico helper
	@Keyword
	def PagoUnicoHelper(String Selector, String Title) {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		String elementSelector = Selector
		boolean flag = false;
		boolean oneTime = false;
		Mobile.scrollToText(elementSelector, FailureHandling.CONTINUE_ON_FAILURE)

		while(!flag) {
			try {
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+elementSelector+"' or . = '"+elementSelector+"')]"))
				if(Element.displayed.TRUE) {
					flag = true
					Element.click()
					if (Mobile.waitForElementPresent(findTestObject('Object Repository/Buttons/Confirmar- 2'), 5, FailureHandling.OPTIONAL)){
						//Screenshot()
						KeywordUtil.markPassed(Selector+" Selected Sucessfully...!!!")
					} else if(Mobile.waitForElementPresent(findTestObject('Object Repository/PagosYTransferencia/Continuar'), 5, FailureHandling.OPTIONAL)){
						//Screenshot()
						KeywordUtil.markPassed(Selector+" Selected Sucessfully...!!!")
					} else if(Mobile.waitForElementPresent(findTestObject('ImpuestosYServicious/Button - Guardar'), 1, FailureHandling.OPTIONAL)){
						//Screenshot()
						KeywordUtil.markPassed(Selector+" Selected Sucessfully...!!!")
					} else {
						//Screenshot()
						KeywordUtil.markFailed(Selector+' Not Selected...!!!')
					}
				} else {
					KeywordUtil.logInfo('Entered else part')
					if(oneTime == false) {
						oneTime = true
						Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
						Mobile.tap(findTestObject(Title), 1,FailureHandling.OPTIONAL)
					}
					WebUI.callTestCase(findTestCase('CommonTestCases/Pago Unico Element Selector'), [:], FailureHandling.STOP_ON_FAILURE)
				}
			} catch(Exception e) {
				KeywordUtil.logInfo('Entered catch block part')
				if(oneTime == false) {
					oneTime = true
					Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 1,FailureHandling.OPTIONAL)
					Mobile.tap(findTestObject(Title), 1,FailureHandling.OPTIONAL)
				}
				WebUI.callTestCase(findTestCase('CommonTestCases/Pago Unico Element Selector'), [:], FailureHandling.STOP_ON_FAILURE)
			}
		}
	}

	//Tarjeta monto selector
	@Keyword
	def TarjetaMonto(String Selector) {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		String elementSelector = Selector

		try {
			Mobile.scrollToText(elementSelector, FailureHandling.CONTINUE_ON_FAILURE)
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+elementSelector+"' or . = '"+elementSelector+"')]"))
			if(Element.displayed.TRUE) {
				Element.click()
				if(elementSelector == "DOP" || elementSelector == "USD") {
					KeywordUtil.markPassed(Selector+" Selected Sucessfully...!!!")
				} else {
					if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Origen-Title'), 5, FailureHandling.OPTIONAL) || Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/Confirmar'), 5, FailureHandling.OPTIONAL)){
						//Screenshot()
						KeywordUtil.markPassed(Selector+" Selected Sucessfully...!!!")
					} else {
						//Screenshot()
						KeywordUtil.markFailed(Selector+' Not Selected...!!!')
					}
				}
			} else {
				//Screenshot()
				KeywordUtil.logInfo("Given monto details is not found please check!!!")
				KeywordUtil.markFailedAndStop("Given monto details is not found please check!!!")
			}
		} catch(Exception e) {
			//Screenshot()
			KeywordUtil.logInfo("Given monto details is not found please check!!!")
			KeywordUtil.markFailedAndStop("Given monto details is not found please check!!!")
		}
	}

	@Keyword
	def UnlinkUser() {
		Refresh1()
		Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 2, FailureHandling.OPTIONAL)
		if(Mobile.waitForElementPresent(findTestObject('Authorize/Cerrar sesin'), 3, FailureHandling.OPTIONAL)) {
		} else {
			Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 2, FailureHandling.OPTIONAL)
		}
		Mobile.tap(findTestObject('Authorize/Cerrar sesin'), 2, FailureHandling.OPTIONAL)
		Mobile.delay(1)
		Mobile.tap(findTestObject('Authorize/Button - CERRAR SESIN'), 3, FailureHandling.OPTIONAL)

		Mobile.waitForElementPresent(findTestObject('AppLaunch/TuBnco Empresas'), 60, FailureHandling.OPTIONAL)

		if (Mobile.waitForElementPresent(findTestObject('Deregistration/ClearUsername'), 5, FailureHandling.OPTIONAL)||
		Mobile.waitForElementPresent(findTestObject('Deregistration/ClearUsername1'), 5, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Deregistration/ClearUsername'), 2, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Deregistration/ClearUsername1'), 2, FailureHandling.OPTIONAL)
			Mobile.delay(2)
			if (Mobile.waitForElementPresent(findTestObject('Deregistration/Desvincular'), 5, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Deregistration/Desvincular'), 3, FailureHandling.OPTIONAL)
				Mobile.delay(2)
				Mobile.tap(findTestObject('Login/ACEPTAR'), 120, FailureHandling.OPTIONAL)
				Mobile.delay(0.2)
			} else {
				Mobile.tap(findTestObject('Deregistration/Desvincular'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(2)
				Mobile.tap(findTestObject('Login/ACEPTAR'), 120, FailureHandling.OPTIONAL)
				Mobile.delay(0.2)
			}
			Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 60, FailureHandling.OPTIONAL)
		} else {
			Mobile.tap(findTestObject('Deregistration/ClearUsername'), 2, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Deregistration/ClearUsername1'), 2, FailureHandling.OPTIONAL)
			Mobile.delay(2)
			Mobile.tap(findTestObject('Deregistration/Desvincular'), 5, FailureHandling.OPTIONAL)
			Mobile.delay(0.2)
			Mobile.tap(findTestObject('Login/ACEPTAR'), 120, FailureHandling.OPTIONAL)
			Mobile.waitForElementPresent(findTestObject('Login/IDENTIFICACIN'), 60, FailureHandling.OPTIONAL)
		}
		//(new helper.functionsupdate()).ClearRequest()
	}

	@Keyword
	def Refresh1() {
		if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/HamburgerMenu'), 2, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 2, FailureHandling.OPTIONAL)
		} else if (Mobile.waitForElementPresent(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)) {
			Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
			Mobile.delay(0.2)
			if (Mobile.waitForElementPresent(findTestObject('PagosYTransferencia/HamburgerMenu'), 2, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(0.2)
				Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 2, FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 2, FailureHandling.OPTIONAL)
				Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(0.2)
			}
		} else {
			Mobile.tap(findTestObject('Login/ACEPTAR'), 2, FailureHandling.OPTIONAL)
			Mobile.delay(0.2)
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 2, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 2, FailureHandling.OPTIONAL)
			Mobile.tap(findTestObject('Buttons/Back-ArrowButton'), 2, FailureHandling.OPTIONAL)
			Mobile.delay(0.2)
		}
	}

	@Keyword
	def Screenshot() {
		Mobile.delay(1)
		String img = Mobile.takeScreenshot(FailureHandling.OPTIONAL)
		File original = new File(img)
		File updated = new File(img)
		Mobile.delay(1)
		Resize(original,updated,330,500,"png")
		// 260,430
		// 350,520
	}

	def Resize(File originalImage, File resizedImage, int width, int height, String format) {
		try {
			BufferedImage original = ImageIO.read(originalImage);
			BufferedImage resized = new BufferedImage(width, height, original.getType());
			Graphics2D g2 = resized.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			//g2.setTransform(AffineTransform.getScaleInstance(4, 4));
			g2.drawImage(original, 0, 0, width, height,null)
			g2.dispose()
			ImageIO.write(resized, format, resizedImage);
			Mobile.delay(1)
		} catch(IOException ex) {
			//ex.printStackTrace();
		}
	}

	@Keyword
	def HistoricoPendiente() {
		if(GlobalVariable.Verification == 'NA') {
			println('Verification is NA - No Need To Check For Transaction History..!!')
		} else {
			Refresh1()
			if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(0.2)
				if (Mobile.waitForElementPresent(findTestObject('Object Repository/Historico_De_Transacciones/Pendientes'), 500, FailureHandling.OPTIONAL)) {
					Screenshot()
				}
			} else {
				Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(0.2)
				if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)) {
					Mobile.tap(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)
					Mobile.delay(0.2)
					if (Mobile.waitForElementPresent(findTestObject('Object Repository/Historico_De_Transacciones/Pendientes'), 500, FailureHandling.OPTIONAL)) {
						Screenshot()
					}
				} else {
					Mobile.tap(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)
					Mobile.delay(0.2)
					if (Mobile.waitForElementPresent(findTestObject('Object Repository/Historico_De_Transacciones/Pendientes'), 500, FailureHandling.OPTIONAL)) {
						Screenshot()
					}
				}
			}
		}
	}

	@Keyword
	def HistoricoProcesada() {
		if(GlobalVariable.Verification == 'NA') {
			println('Verification is NA - No Need To Check For Transaction History..!!')
		} else {
			Refresh1()
			if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)) {
				Mobile.tap(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(0.2)
				if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Procesadas'), 10, FailureHandling.OPTIONAL)) {
					Mobile.tap(findTestObject('Historico_De_Transacciones/Procesadas'), 2, FailureHandling.OPTIONAL)
					Mobile.delay(0.2)
					Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Procesadas'), 10, FailureHandling.OPTIONAL)
					Screenshot()
				}
			} else {
				Mobile.tap(findTestObject('PagosYTransferencia/HamburgerMenu'), 2, FailureHandling.OPTIONAL)
				Mobile.delay(0.2)
				if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)) {
					Mobile.tap(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)
					Mobile.delay(0.2)
					if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Procesadas'), 10, FailureHandling.OPTIONAL)) {
						Mobile.tap(findTestObject('Historico_De_Transacciones/Procesadas'), 2, FailureHandling.OPTIONAL)
						Mobile.delay(0.2)
						Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Procesadas'), 10, FailureHandling.OPTIONAL)
						Screenshot()
					}
				} else {
					Mobile.tap(findTestObject('Historico_De_Transacciones/Histrico de transacciones - Hamburger'), 2, FailureHandling.OPTIONAL)
					Mobile.delay(0.2)
					if (Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Procesadas'), 10, FailureHandling.OPTIONAL)) {
						Mobile.tap(findTestObject('Historico_De_Transacciones/Procesadas'), 2, FailureHandling.OPTIONAL)
						Mobile.delay(0.2)
						Mobile.waitForElementPresent(findTestObject('Historico_De_Transacciones/Procesadas'), 10, FailureHandling.OPTIONAL)
						Screenshot()
					}
				}
			}
		}
	}

	@Keyword
	def DestinoBeforeAppMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.destino_before_app0 = GlobalVariable.destino_before_app
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.destino_before_app1 = GlobalVariable.destino_before_app
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.destino_before_app2 = GlobalVariable.destino_before_app
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.destino_before_app3 = GlobalVariable.destino_before_app
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoBeforeDBMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.destino_before_db0 = GlobalVariable.destino_before_db
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.destino_before_db1 = GlobalVariable.destino_before_db
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.destino_before_db2 = GlobalVariable.destino_before_db
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.destino_before_db3 = GlobalVariable.destino_before_db
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoAfterAppMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.destino_after_app0 = GlobalVariable.destino_after_app
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.destino_after_app1 = GlobalVariable.destino_after_app
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.destino_after_app2 = GlobalVariable.destino_after_app
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.destino_after_app3 = GlobalVariable.destino_after_app
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoAfterDBMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.destino_after_db0 = GlobalVariable.destino_after_db
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.destino_after_db1 = GlobalVariable.destino_after_db
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.destino_after_db2 = GlobalVariable.destino_after_db
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.destino_after_db3 = GlobalVariable.destino_after_db
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}


	@Keyword
	def ImpuestosMap (int count, int DestinoCount, double tax) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta1 = tax
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta2 = tax
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta3 = tax
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.GetImpuesta4 = tax
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def ComissionMap (int count, int DestinoCount, double comission) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.GetComission1 = comission
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.GetComission2 = comission
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.GetComission3 = comission
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.GetComission4 = comission
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoCurrMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino1 = GlobalVariable.CurrencyTypeDestino
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino2 = GlobalVariable.CurrencyTypeDestino
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino3 = GlobalVariable.CurrencyTypeDestino
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.CurrencyTypeDestino4 = GlobalVariable.CurrencyTypeDestino
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def TaxAfterTransactionMap (int count, int DestinoCount) {
		if(count == 0 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after1 = GlobalVariable.Tax_Destinobalance_after
		} else if(count == 1 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after2 = GlobalVariable.Tax_Destinobalance_after
		} else if(count == 2 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after3 = GlobalVariable.Tax_Destinobalance_after
		} else if(count == 3 && DestinoCount!=count) {
			GlobalVariable.Tax_Destinobalance_after4 = GlobalVariable.Tax_Destinobalance_after
		} else {
			KeywordUtil.logInfo("END OF ACCOUNT")
		}
	}

	@Keyword
	def DestinoAccount_AfterBalanceMultipleTransaction(String CtaDestino) {
		if(CtaDestino == '' || CtaDestino == null || CtaDestino == 'NA') {
			GlobalVariable.origen_after_app =  "Account Not Available " + CtaDestino
		} else {
			Refresh()
			try {
				Mobile.scrollToText(CtaDestino)
				AppiumDriver<?> driver = MobileDriverFactory.getDriver()
				MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaDestino+"' or . = '"+CtaDestino+"')]"))
				Element.click()
				Mobile.delay(5)
				try {
					if (Mobile.waitForElementPresent(findTestObject('GetBalances/TuBnco Empresas'), 25, FailureHandling.OPTIONAL)){
						GlobalVariable.destino_after_app = Mobile.getText(findTestObject('GetBalances/GetBalance-AfterPayment'), 3, FailureHandling.OPTIONAL)
						String DestinoBalanceAfter = FormatBalance(GlobalVariable.destino_after_app)
						GlobalVariable.destino_after_app = DestinoBalanceAfter
					} else {
						println "Account Is Not Available : " + CtaDestino
						GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
					}
				} catch(Exception e) {
					GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
					//e.printStackTrace()
				}
			}catch(Exception e) {
				GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
				//e.printStackTrace()
			}
		}
	}

	@Keyword
	def DestinoAccount_AfterBalance_Tarjeta(String CtaDestino, String CurrType) {
		Refresh()
		try {
			Mobile.scrollToText(CtaDestino, FailureHandling.OPTIONAL)
			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			MobileElement Element = driver.findElement(By.xpath("//*[@class = 'android.widget.TextView' and (@text = '"+CtaDestino+"' or . = '"+CtaDestino+"')]"))
			Element.click()
			Mobile.delay(10)

			try {
				if (Mobile.waitForElementPresent(findTestObject('GetBalances/TuBnco Empresas'), 25, FailureHandling.OPTIONAL)){
					if(CurrType.equals("DOP")) {
						Mobile.tap(findTestObject('TarjetaPropia/Pesos'), 2, FailureHandling.OPTIONAL)
					} else {
						Mobile.tap(findTestObject('TarjetaPropia/Dolares'), 2, FailureHandling.OPTIONAL)
					}
					Mobile.delay(10)
					String bal
					bal = Mobile.getText(findTestObject('GetBalances/GetBalance-AfterPayment'), 3, FailureHandling.OPTIONAL)
					GlobalVariable.destino_after_app = bal.replaceAll('[,]', '')
				} else {
					println "Account Is Not Available : " + CtaDestino
					GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
				}
			} catch(Exception e) {
				GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
				//e.printStackTrace()
			}
		}catch(Exception e) {
			GlobalVariable.destino_after_app = "No Info For Acct " + CtaDestino
			//e.printStackTrace()
		}
	}

	@Keyword
	def GetCommisones() {
		if (Mobile.waitForElementPresent(findTestObject('ImpuestosValue/Cargos por comisiones'), 30, FailureHandling.OPTIONAL)) {

			if(Mobile.waitForElementPresent(findTestObject('ImpuestosValue/GetCommissionValue'), 5, FailureHandling.OPTIONAL)) {
				GlobalVariable.CommissionAmount = Mobile.getText(findTestObject('ImpuestosValue/GetCommissionValue'), 1, FailureHandling.OPTIONAL)
			}

			WebUI.comment('### - COMMISSION AMOUNT ### : ' + GlobalVariable.CommissionAmount)

			double CommissionAmount = Double.parseDouble(GlobalVariable.CommissionAmount)

			GlobalVariable.CommissionAmount = CommissionAmount
			println('### - COMMISSION AMOUNT ### : ' + GlobalVariable.CommissionAmount)
		} else {
			GlobalVariable.CommissionAmount = '0.0'

			double CommissionAmount = Double.parseDouble(GlobalVariable.CommissionAmount)

			Mobile.delay(0.5)

			GlobalVariable.CommissionAmount = CommissionAmount
			println('### - COMMISSION AMOUNT ### : ' + GlobalVariable.CommissionAmount)
			WebUI.comment('### - COMMISSION AMOUNT ### : ' + GlobalVariable.CommissionAmount)
		}
	}

	@Keyword
	def HideKeyboard() {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		boolean check = driver.getKeyboard()
		if(check && GlobalVariable.device == "Android Studio") {
			Mobile.hideKeyboard(FailureHandling.OPTIONAL)
		}
	}

}