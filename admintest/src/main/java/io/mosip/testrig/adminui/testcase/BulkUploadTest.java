package io.mosip.testrig.adminui.testcase;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITest;
// Generated by Selenium IDE
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.mosip.testrig.adminui.kernel.util.ConfigManager;
import io.mosip.testrig.adminui.utility.BaseClass;
import io.mosip.testrig.adminui.utility.Commons;
import io.mosip.testrig.adminui.utility.JsonUtil;
import io.mosip.testrig.adminui.utility.PropertiesUtil;
import io.mosip.testrig.adminui.utility.SetTestName;
import io.mosip.testrig.adminui.utility.TestRunner;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
public class BulkUploadTest extends BaseClass {
	private static final Logger logger = Logger.getLogger(BulkUploadTest.class);

/*implements ITest{
 * 
 
 private String newTestName = "";

	 private void setTestName(String newTestName){
	     this.newTestName = newTestName;
	 }

	 public String getTestName() {

	     return newTestName;
	 }


	 @BeforeMethod(alwaysRun=true)
	 public void getTheNameFromParemeters(Method method, Object [] parameters){
		 
	     SetTestName setTestName = method.getAnnotation(SetTestName.class);
	     String testCaseName = (String) parameters[setTestName.idx()];
	     setTestName(testCaseName+"BulkUpload");
	 }
  
	  @SetTestName(idx=0) */
	 
	@Test (dataProvider = "data-provider",groups = "BU")
  public void bulkUploadCRUD(String table) throws Exception {
	
		test=extent.createTest("bulkUploadCRUD", "verify Login");	
    Commons.click(test,driver,By.id("admin/bulkupload"));
    Commons.click(test,driver,By.xpath("//a[@href='#/admin/bulkupload/masterdataupload']"));
    
    for(int count=0;count<=2;count++) {
    Commons.click(test,driver,By.id("Upload Data"));
 
    
  
   if(count==0) Commons.dropdown(test,driver,By.id("operation"),By.id("Insert"));
   if(count==1) Commons.dropdown(test,driver,By.id("operation"),By.id("Update"));
   if(count==2) Commons.dropdown(test,driver,By.id("operation"),By.id("Delete"));
    
    Commons.dropdown(test,driver,By.id("tableName"),By.id(table));
    // Commons.click(test,driver,By.xpath("//div[@class='custom-file-input']"));
   // Commons.click(test,driver,By.id("fileInput"));
    

    String filePath = TestRunner.getResourcePath()+ "//BulkUploadFiles//"+ ConfigManager.getloginlang()+"//"+table+".csv";
   Commons.enter(test, driver, By.id("fileInput"), filePath);
   
 
    
    Commons.click(test,driver,By.xpath("//button[@id='createButton']"));
    Commons.click(test,driver,By.id("confirmpopup")); 
    test.log(Status.INFO, "Click on FileUploaded");
    Thread.sleep(2000);
    String divText=driver.findElement(By.xpath("//div[@class='mat-dialog-content']//div")).getText();
    String divTextArr[]=divText.split(":");
    logger.info(divTextArr[1].trim());
    
    Commons.click(test,driver,By.id("confirmmessagepopup")); //DONE
    Thread.sleep(Long.parseLong(ConfigManager.getbulkwait()));

    String transId=driver.findElement(By.xpath("//table[@class='mat-table']//tr[2]//td[1]")).getText();
    String status=driver.findElement(By.xpath("//table[@class='mat-table']//tr[2]//td[5]")).getText();
  Assert.assertTrue(transId.equals(divTextArr[1].trim()));
  Assert.assertTrue(status.equalsIgnoreCase("COMPLETED"),"Status Should be COMPLETED");
}
 }
}
