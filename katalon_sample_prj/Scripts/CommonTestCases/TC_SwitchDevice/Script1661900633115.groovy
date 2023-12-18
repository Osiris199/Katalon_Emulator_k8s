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
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.appium.java_client.android.AndroidDriver;
/*
AndroidDriver driver
DesiredCapabilities capability = new DesiredCapabilities();
capability.setCapability("deviceName","google G011A (Android 7.1.2)");
capability.setCapability("platformVersion","7.1.2" );
capability.setCapability("platformName","Android");
capability.setCapability("udid","127.0.0.1:21503");
capability.setCapability("automationName","uiautomator2");
File file = new File("E:\\BanReservas\\BanReservas-Android-New\\APK\\Dev\\3.3.5-64bits.apk");
capability.setCapability("app", file.getAbsolutePath());
capability.setCapability("noReset", true);
capability.setCapability("launch", true);
capability.setCapability("--session-override",true)
capability.setCapability("appPackage","com.android.settings" );
capability.setCapability("newCommandTimeout","60000");

driver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capability)
*/

//String cmd = 'taskkill /im node.exe /f'
//Runtime.getRuntime().exec(cmd)


AndroidDriver driver
DesiredCapabilities capabilities = new DesiredCapabilities();

	capabilities.setCapability("udid", "127.0.0.1:21503")
	capabilities.setCapability("platformName", "Android")
	capabilities.setCapability("noReset", true)
	capabilities.setCapability("launch", true)
	capabilities.setCapability("--session-override",true)
	capabilities.setCapability("deviceName","google G011A (Android 7.1.2)")
	capabilities.setCapability("platformVersion","7.1.2" )
	capabilities.setCapability("platformName","Android")
	capabilities.setCapability("udid","127.0.0.1:21503")
	capabilities.setCapability("automationName","uiautomator2")
	capabilities.setCapability("noReset", true)
	capabilities.setCapability("launch", true)
	capabilities.setCapability("--session-override",true)
	capabilities.setCapability("appPackage","com.konylabs.Banreservas" )
	capabilities.setCapability("newCommandTimeout","60000")
	capabilities.setCapability("app","E:\\BanReservas\\BanReservas-Android-New\\APK\\Dev\\3.3.5-64bits.apk")
	new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities)

	/*
} else if(Username.equalsIgnoreCase("automati1")) {
	capabilities.setCapability("udid", "127.0.0.1:21513")
	capabilities.setCapability("platformName", "Android")
	capabilities.setCapability("noReset", true)
	capabilities.setCapability("launch", true)
	capabilities.setCapability("--session-override",true)
	capabilities.setCapability("app","E:\\BanReservas\\BanReservas-Android-New\\APK\\Dev\\3.3.5-64bits.apk")
	new AndroidDriver(new URL("http://127.0.0.1:4725/wd/hub"), capabilities)
}
*/
