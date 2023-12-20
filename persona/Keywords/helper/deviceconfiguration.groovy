package helper
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.kms.katalon.core.appium.constants.AppiumStringConstants;
import com.kms.katalon.core.appium.driver.AppiumDriverManager;
import com.kms.katalon.core.appium.exception.AppiumStartException;
import com.kms.katalon.core.appium.exception.MobileDriverInitializeException;
import com.kms.katalon.core.configuration.RunConfiguration;
import com.kms.katalon.core.driver.DriverType;
import com.kms.katalon.core.exception.StepFailedException;
import com.kms.katalon.core.logging.KeywordLogger;
import com.kms.katalon.core.webui.constants.StringConstants;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
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
import groovy.ui.SystemOutputInterceptor
import org.openqa.selenium.Keys as Keys
import java.lang.Integer as Integer
import io.appium.java_client.MultiTouchAction as MultiTouchAction
import io.appium.java_client.TouchAction as TouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.CapabilityType;
import io.appium.java_client.android.AndroidDriver;

public class deviceconfiguration {

	@Keyword
	def SwitchDevice(String Username) {
		AppiumDriver<?> driver = MobileDriverFactory.getDriver()
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if(Username.equalsIgnoreCase("margaritaf")) {
			capabilities.setCapability("udid", "127.0.0.1:21503")
			capabilities.setCapability("platformName", "Android")
			capabilities.setCapability("noReset", true)
			capabilities.setCapability("launch", true)
			capabilities.setCapability("--session-override",true)
			capabilities.setCapability("app","E:\\BanReservas\\BanReservas-Android-New\\APK\\Dev\\3.3.5-64bits.apk")
			new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities)
		} else if(Username.equalsIgnoreCase("automati1")) {
			capabilities.setCapability("udid", "127.0.0.1:21513")
			capabilities.setCapability("platformName", "Android")
			capabilities.setCapability("noReset", true)
			capabilities.setCapability("launch", true)
			capabilities.setCapability("--session-override",true)
			capabilities.setCapability("app","E:\\BanReservas\\BanReservas-Android-New\\APK\\Dev\\3.3.5-64bits.apk")
			new AppiumDriver(new URL("http://127.0.0.1:4725/wd/hub"), capabilities)
		}
	}
}

