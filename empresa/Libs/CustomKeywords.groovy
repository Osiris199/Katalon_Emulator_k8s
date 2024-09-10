
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import java.lang.String

import com.kms.katalon.core.testobject.TestObject

import java.util.List



def static "sample.Login.loginIntoApplication"(
    	String applicationURL	
     , 	String username	
     , 	String password	) {
    (new sample.Login()).loginIntoApplication(
        	applicationURL
         , 	username
         , 	password)
}


def static "sample.Login.loginIntoApplicationWithGlobalVariable"() {
    (new sample.Login()).loginIntoApplicationWithGlobalVariable()
}


def static "sample.Login.logoutFromApplication"() {
    (new sample.Login()).logoutFromApplication()
}

 /**
	 * Select an option by label.
	 *
	 * @param to The select2 box object that tagged as "select" in the document.
	 * @param option The label of the option needs to select.
	 */ 
def static "sample.Select2.selectOptionByLabel"(
    	TestObject to	
     , 	String option	) {
    (new sample.Select2()).selectOptionByLabel(
        	to
         , 	option)
}

 /**
	 * Select many options by labels. This method just applies for multiple value select boxes.
	 *
	 * @param to The select2 box object that tagged as "select" in the document.
	 * @param options The labels of the options need to select.
	 */ 
def static "sample.Select2.selectManyOptionsByLabel"(
    	TestObject to	
     , 	java.util.List<String> options	) {
    (new sample.Select2()).selectManyOptionsByLabel(
        	to
         , 	options)
}

 /**
	 * Get labels of selected options.
	 *
	 * @param to The select2 box object that tagged as "select" in the document.
	 * @return A labels list of selected options.
	 */ 
def static "sample.Select2.getSelectedOptionsLabel"(
    	TestObject to	) {
    (new sample.Select2()).getSelectedOptionsLabel(
        	to)
}

 /**
	 * Get elements of selected options.
	 *
	 * @param to The select2 box object that tagged as "select" in the document.
	 * @return A list WebElement of selected options.
	 */ 
def static "sample.Select2.getSelectedOptionsList"(
    	TestObject to	) {
    (new sample.Select2()).getSelectedOptionsList(
        	to)
}

 /**
	 * Get labels of all options.
	 *
	 * @param to The select2 box object that tagged as "select" in the document.
	 * @return A list string of all options.
	 */ 
def static "sample.Select2.getAllOptionsLabel"(
    	TestObject to	) {
    (new sample.Select2()).getAllOptionsLabel(
        	to)
}

 /**
	 * Remove selected options. This method just applies for multiple value select boxes.
	 *
	 * @param to The select2 box object that tagged as "select" in the document.
	 * @param options The list of options needs to remove.
	 */ 
def static "sample.Select2.removeOptions"(
    	TestObject to	
     , 	java.util.List<String> options	) {
    (new sample.Select2()).removeOptions(
        	to
         , 	options)
}


def static "sample.Shop.navigatetoDetailPage"(
    	String productName	
     , 	String urlProduct	) {
    (new sample.Shop()).navigatetoDetailPage(
        	productName
         , 	urlProduct)
}


def static "sample.Shop.addToCart"(
    	String productName	
     , 	String urlProduct	) {
    (new sample.Shop()).addToCart(
        	productName
         , 	urlProduct)
}


def static "sample.Shop.applyCouponAndAddToCart"(
    	String productName	
     , 	String urlProduct	
     , 	String coupon	) {
    (new sample.Shop()).applyCouponAndAddToCart(
        	productName
         , 	urlProduct
         , 	coupon)
}


def static "sample.Shop.addToCartWithGlobalVariable"() {
    (new sample.Shop()).addToCartWithGlobalVariable()
}


def static "sample.Shop.applyCouponAndAddToCartWithGlobalVariable"() {
    (new sample.Shop()).applyCouponAndAddToCartWithGlobalVariable()
}


def static "sample.Checkout.CheckoutShop"(
    	String firstName	
     , 	String lastName	
     , 	String companyName	
     , 	String country	
     , 	String address	
     , 	String city	
     , 	String postCode	
     , 	String Phone	) {
    (new sample.Checkout()).CheckoutShop(
        	firstName
         , 	lastName
         , 	companyName
         , 	country
         , 	address
         , 	city
         , 	postCode
         , 	Phone)
}


def static "sample.Checkout.CheckoutShopWithGlobalVariable"() {
    (new sample.Checkout()).CheckoutShopWithGlobalVariable()
}

 /**
	 * Click on select2 text box.
	 *
	 * @param to The select2 box object that tagged as "select" in the document.
	 */ 
def static "sample.Utils.clickOnSelect2"(
    	TestObject to	) {
    (new sample.Utils()).clickOnSelect2(
        	to)
}

 /**
	 * Find the container class after the select element.
	 *
	 * @param to The select2 box object that tagged as "select" in the document.
	 * @return The WebElement that seen as container class after the select element.
	 */ 
def static "sample.Utils.findContainer"(
    	TestObject to	) {
    (new sample.Utils()).findContainer(
        	to)
}

 /**
	 * Select the result after filling text search to select2 text box.
	 *
	 * @param option The label of option need to select.
	 * @param subContainerOpenClass The Xpath string of the container class.
	 */ 
def static "sample.Utils.selectResult"(
    	String option	
     , 	String subContainerOpenClass	) {
    (new sample.Utils()).selectResult(
        	option
         , 	subContainerOpenClass)
}

 /**
	 * Enter text search to the select2 text box.
	 *
	 * @param option The label of option need to search.
	 * @return The Xpath string of the container class.
	 */ 
def static "sample.Utils.enterText"(
    	String option	) {
    (new sample.Utils()).enterText(
        	option)
}
