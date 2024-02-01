package commonFunctions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.Reporter;

import config.AppUtil;

public class FunctionLibrary extends AppUtil{
public static boolean adminlogin(String Username, String password) throws Throwable
{
	driver.get(conpro.getProperty("url"));
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.findElement(By.xpath(conpro.getProperty("objReset"))).click();
	driver.findElement(By.xpath(conpro.getProperty("objuser"))).sendKeys(Username);
	driver.findElement(By.xpath(conpro.getProperty("objpass"))).sendKeys(password);;
	driver.findElement(By.xpath(conpro.getProperty("objlogin"))).click();
	String expected = "dashboard";
	String Actual = driver.getCurrentUrl();
	if(Actual.contains(expected))
	{
		Reporter.log("login successfull:::"+expected+"........."+Actual,true);
		Thread.sleep(2000);
		driver.findElement(By.xpath(conpro.getProperty("objLogout"))).click();
		return true;
	}
	else
	{
		String error_message = driver.findElement(By.xpath(conpro.getProperty("objerrormessage"))).getText();
		driver.findElement(By.xpath(conpro.getProperty("objOk_button"))).click();
		Reporter.log(error_message+"......"+expected+"........."+Actual,true);
		return false;		
	}
}
}
