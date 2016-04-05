package demo.selenium;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.validator.PublicClassValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.thoughtworks.selenium.webdriven.commands.GetAlert;

public class TestDataDrivenByCSVFile {

	public WebDriver driver;
	String baseUrl = "http://10.13.6.55:8013/kiwi/";

	@DataProvider(name = "testdata")
	public static Object[][] words() throws IOException {

		Object[][] test = getTestData("D:\\My Lesson\\Automation\\workspace\\Demo04-csv\\loginInfo.txt");
		return getTestData("D:\\My Lesson\\Automation\\workspace\\Demo04-csv\\loginInfo.txt");
	}

	@Test(dataProvider = "testdata")
	public void testLogin(String userName, String passWord, String checkName) {

		driver.get(baseUrl);

		try {

			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		System.out.println(userName + passWord + checkName);

		WebElement userNameBtn = driver.findElement(By
				.xpath("//*[@id='textfield-1018-inputEl']"));
		WebElement passWordBtn = driver.findElement(By
				.xpath("//*[@id='textfield-1019-inputEl']"));
		WebElement loginBtn = driver.findElement(By
				.xpath("//*[@id='button-1020-btnIconEl']"));
		WebElement checkNameBtn = driver.findElement(By
				.xpath("//*[@id='component-1028']"));

		userNameBtn.sendKeys(userName);
		passWordBtn.sendKeys(passWord);
		loginBtn.click();

		try {

			Thread.sleep(5000);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		Assert.assertEquals(checkNameBtn.getText(), checkName);
	}

	@Parameters("browser")
	@BeforeMethod
	public void beforeMethod(String Browser) {

		if (Browser.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.firefox.bin",
					"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			driver = new FirefoxDriver();

		} else if (Browser.equalsIgnoreCase("ie")) {

			System.setProperty("webdriver.ie.driver",
					"C:\\Program Files (x86)\\Internet Explorer\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();

		} else {

			System.setProperty("webdriver.chrome.driver",
					"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
			driver = new ChromeDriver();

		}
	}

	@AfterMethod
	public void afterMethod() {

		driver.close();
	}

	public static Object[][] getTestData(String fileName) throws IOException {

		List<Object[]> records = new ArrayList<Object[]>();
		String record;
		BufferedReader file = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName)));
		// file.readLine();

		while ((record = file.readLine()) != null) {

			System.out.println("record is " + record);
			String fields[] = record.split(",");
			records.add(fields);
		}

		file.close();

		Object[][] results = new Object[records.size()][];

		for (int i = 0; i < records.size(); i++) {

			results[i] = records.get(i);
		}
		System.out.println(results);
		return results;

	}

}
