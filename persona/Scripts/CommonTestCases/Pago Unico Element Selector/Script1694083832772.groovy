import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.util.KeywordUtil
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.offset.ElementOption

AppiumDriver<?> driver = MobileDriverFactory.getDriver()
TouchAction touch = new TouchAction(driver)
ArrayList<String> BancoElements = new ArrayList<String>();
ArrayList<String> TopElement = new ArrayList<String>();

List<MobileElement> viewGroup = driver.findElementsByXPath("//*[@class = 'android.view.ViewGroup' and (@text = '' or . = '')]")

for(int i = 0; i<viewGroup.size() && i<10; i++) {
	String banco = viewGroup[i].getSize().getHeight()
	if(banco == '158') {
		BancoElements.add(i)
	} else if(banco == '9') {
		TopElement.add(i)
	} else {
		KeywordUtil.logInfo('No element with height 158 found')
	}
}

int top = BancoElements[0];
int bottom = BancoElements[BancoElements.size() - 1];

def bottomElement = ElementOption.element(viewGroup[bottom])
def topElement = ElementOption.element(viewGroup[top])
touch.longPress(bottomElement).moveTo(topElement).release().perform()

