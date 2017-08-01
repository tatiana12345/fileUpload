package com.upload.file;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class UploadFile {
	WebDriver wd;
	String baseURL;
	ExtentTest test;
	ExtentReports report;

	String username = "kesler01test@gmail.com";
	String password = "Sophiegirl0807!";
	String addressee = "kesler01test@gmail.com";
	String subject = "Upload file test";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", "/Users/tatianakesler/Desktop/Selenium/installation/geckodriver");

		report = UploadReportPathName.getInstance();
		test = report.startTest("Upload file test");

		wd = new FirefoxDriver();
		baseURL = "https://www.gmail.com/";

		wd.manage().window().maximize();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wd.get(baseURL);
		test.log(LogStatus.INFO, "Browser started");
	}

	@Test
	public void Test() throws InterruptedException, AWTException {
		// Click OK button - pop-up
		boolean PopupIsPresent = wd.findElements(By.xpath("//div[@id='McfNlf']//span[text()='OK']")).size() !=0;
		if (PopupIsPresent==true){
		WebElement closePopup = wd.findElement(By.xpath("//div[@id='McfNlf']//span[text()='OK']"));
		closePopup.click();
		test.log(LogStatus.INFO, "Popup closed");
		}

		WebElement emailFiled = wd.findElement(By.id("identifierId"));
		emailFiled.sendKeys(username);
		test.log(LogStatus.INFO, "Login email typed: " + username);

		WebElement nextButton = wd.findElement(By.xpath("//content[@class='CwaK9']//span[text()='Next']"));
		nextButton.click();
		test.log(LogStatus.INFO, "Next button clicked");

		Thread.sleep(2000);

		WebElement passwordField = wd.findElement(By.xpath("//input[@type='password']"));
		passwordField.sendKeys(password);
		test.log(LogStatus.INFO, "Password typed: " + password);

		WebElement loginButton = wd.findElement(By.id("passwordNext"));
		loginButton.click();
		test.log(LogStatus.INFO, "Login/Next Button clicked");

		WebElement compose = wd.findElement(By.xpath("//div[@class='z0']//div[text()='COMPOSE']"));
		compose.click();
		test.log(LogStatus.INFO, "Compose button clicked");

		Thread.sleep(2000);

		WebElement EmailField3 = wd.findElement(By.xpath("//textarea[@name='to']"));
		EmailField3.sendKeys(addressee);
		test.log(LogStatus.INFO, "Typed addressee email");

		Thread.sleep(2000);

		WebElement subjectField = wd.findElement(By.xpath("//input[@placeholder='Subject']"));
		subjectField.sendKeys(subject);
		test.log(LogStatus.INFO, "Subject typed");

		WebElement emailText = wd.findElement(By.xpath("//div[@aria-label='Message Body']"));
		emailText.sendKeys("TEXT BODY");
		test.log(LogStatus.INFO, "Email body text typed");

		WebElement attachFile = wd.findElement(By.xpath("//div[@aria-label='Attach files']"));
		attachFile.click();
		test.log(LogStatus.INFO, "Attacg file button clicked");

		String filePath = "/Users/tatianakesler/Desctop/Selenium/file/testfile.txt";
		StringSelection stringselection = new StringSelection(filePath);

		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);

		Robot robot = new Robot();
		// CMD +Tab
		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.delay(2000);
		// Go to window
		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_G);
		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_G);
		// paste the clipboard value
		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_V);
		robot.delay(2000);
		// hit enter key
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(2000);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(2000);
		test.log(LogStatus.INFO, "File attached");

		WebElement sendButton = wd.findElement(By.xpath("//div[@aria-label='Send ‪(⌘Enter)‬']"));
		sendButton.click();
		test.log(LogStatus.INFO, "'Send' button clicked");

		WebElement viewMess = wd.findElement(By.id("link_vsm"));
		viewMess.click();
		test.log(LogStatus.INFO, "Clicked 'View message'");

		WebElement filePresent = null;

		try {
			filePresent = wd.findElement(By.xpath("//span[text()='testfile.txt']"));
		} catch (NoSuchElementException e) {
			System.out.println(e.getMessage());
		}
		Assert.assertTrue(filePresent != null);
		test.log(LogStatus.PASS, "Attached file present - PASS");

	}

	@AfterMethod
	public void tearDown(ITestResult testResult) throws IOException, InterruptedException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String path = FailedTC_Screenshot.NewScreenshotMethod.takeScreenshot(wd, testResult.getName());
			String imgPath = test.addScreenCapture(path);
			test.log(LogStatus.FAIL, "No attached file found ", imgPath);
		}
	}

	@AfterClass
	public void AfterClass() {
		wd.quit();
		report.endTest(test);
		report.flush();
	}
}
