package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;

public class AppTest extends AppUtil{
	String Inputpath= "./FileInput/logindata.xlsx";
	String outputpath="./FileOutput/DataDrivenresults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	@Test
	public void startTest() throws Throwable
	{
		report = new ExtentReports("./target/Reports/DataDriven.html");
		ExcelFileUtil xl = new ExcelFileUtil(Inputpath);
		int rc = xl.rowCount("login");
		Reporter.log("No. of rows are::"+rc,true);
		for (int i = 1; i <=rc; i++)
		{
			logger = report.startTest("validate login function");
			String User = xl.getCellData("login", i, 0);
			String pass = xl.getCellData("login", i, 1);
			boolean res = FunctionLibrary.adminlogin(User, pass);
			if(res)
			{
				xl.setCellData("login", i, 2, "Login Success", outputpath);
				xl.setCellData("login", i, 3, "Pass", outputpath);
				logger.log(LogStatus.PASS, "Valid Credentials");
			}
			else
			{
				File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screen, new File("./Screenshot/Iteration/"+i+"loginpage.png"));
				xl.setCellData("login", i, 2, "Login UnSuccess", outputpath);
				xl.setCellData("login", i, 3, "Fail", outputpath);
				logger.log(LogStatus.FAIL, "Invalid Credentials");
			}
			report.endTest(logger);
			report.flush();
		}
		
	}

}
