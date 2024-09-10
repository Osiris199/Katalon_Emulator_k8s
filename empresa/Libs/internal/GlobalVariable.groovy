package internal

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseMain


/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */
public class GlobalVariable {
     
    /**
     * <p></p>
     */
    public static Object urlLogin
     
    /**
     * <p></p>
     */
    public static Object username
     
    /**
     * <p></p>
     */
    public static Object password
     
    /**
     * <p></p>
     */
    public static Object waitPresentTimeout
     
    /**
     * <p></p>
     */
    public static Object urlShop
     
    /**
     * <p></p>
     */
    public static Object companyName
     
    /**
     * <p></p>
     */
    public static Object address
     
    /**
     * <p></p>
     */
    public static Object city
     
    /**
     * <p></p>
     */
    public static Object country
     
    /**
     * <p></p>
     */
    public static Object postCode
     
    /**
     * <p></p>
     */
    public static Object Phone
     
    /**
     * <p></p>
     */
    public static Object uploadPlaceOrderTimeout
     
    /**
     * <p></p>
     */
    public static Object dataFile
     
    /**
     * <p></p>
     */
    public static Object inputColumHeader
     
    /**
     * <p></p>
     */
    public static Object urlProduct
     
    /**
     * <p></p>
     */
    public static Object productName
     
    /**
     * <p></p>
     */
    public static Object coupon
     
    /**
     * <p></p>
     */
    public static Object firstName
     
    /**
     * <p></p>
     */
    public static Object lastName
     

    static {
        try {
            def selectedVariables = TestCaseMain.getGlobalVariables("default")
			selectedVariables += TestCaseMain.getGlobalVariables(RunConfiguration.getExecutionProfile())
            selectedVariables += TestCaseMain.getParsedValues(RunConfiguration.getOverridingParameters(), selectedVariables)
    
            urlLogin = selectedVariables['urlLogin']
            username = selectedVariables['username']
            password = selectedVariables['password']
            waitPresentTimeout = selectedVariables['waitPresentTimeout']
            urlShop = selectedVariables['urlShop']
            companyName = selectedVariables['companyName']
            address = selectedVariables['address']
            city = selectedVariables['city']
            country = selectedVariables['country']
            postCode = selectedVariables['postCode']
            Phone = selectedVariables['Phone']
            uploadPlaceOrderTimeout = selectedVariables['uploadPlaceOrderTimeout']
            dataFile = selectedVariables['dataFile']
            inputColumHeader = selectedVariables['inputColumHeader']
            urlProduct = selectedVariables['urlProduct']
            productName = selectedVariables['productName']
            coupon = selectedVariables['coupon']
            firstName = selectedVariables['firstName']
            lastName = selectedVariables['lastName']
            
        } catch (Exception e) {
            TestCaseMain.logGlobalVariableError(e)
        }
    }
}
