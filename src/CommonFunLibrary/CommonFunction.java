package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import Utilities.PropertyFileUtil;

public class CommonFunction {
	public static WebDriver driver;
	public static WebDriver startBrowser() throws Throwable
	{
		if(PropertyFileUtil.getValueforKey("Browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "D:\\OJTProjectTesting\\Hybrid Framework\\commonDrivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(PropertyFileUtil.getValueforKey("Browser").equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "D:\\OJTProjectTesting\\Hybrid Framework\\commonDrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else
		{
			System.out.println("Browser value is Not matching");
		}
		return driver;
	}
	public static void openApplication(WebDriver driver)throws Throwable
	{
		driver.get(PropertyFileUtil.getValueforKey("Url"));
	}
	public static void waitForElement(WebDriver driver,String locatortype,String locatorvalue,String waittime)
	{
		WebDriverWait myWait = new WebDriverWait(driver, Integer.parseInt(waittime));
		if(locatortype.equalsIgnoreCase("name"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
		}
	}
	public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testData)
	{
		if(locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(testData);
		}
		else if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testData);
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).clear();
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testData);
		}
	}
	public static void clickAction(WebDriver driver,String locatortype,String locatorvalue)
	{
		if(locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
		}
		else if(locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(locatorvalue)).click();
		}
		else if(locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(locatorvalue)).click();
		}
	}
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	//java time stamp
	public static String generateDate()
	{
		Date date= new Date();
		SimpleDateFormat df= new SimpleDateFormat("YYYY_MM_dd_ss");
		return df.format(date);
	}
	//method for capturing snumber into notepad
public static void captureData(WebDriver driver,String locatortype,String locatorvalue)throws Throwable
{
	String expectedsnumber=null;
	if(locatortype.equalsIgnoreCase("id"))
	{
		expectedsnumber=driver.findElement(By.id(locatorvalue)).getAttribute("value");
	}
	else if(locatortype.equalsIgnoreCase("name"))
	{
		expectedsnumber=driver.findElement(By.name(locatorvalue)).getAttribute("value");
	}
	//write expsnumber into notepad
	FileWriter fw = new FileWriter("D:\\OJTProjectTesting\\Hybrid Framework\\CaptureData");
	BufferedWriter bw= new BufferedWriter(fw);
	bw.write(expectedsnumber);
	bw.flush();
	bw.close();
	
}
//method for table validation
public static void stableValidation(WebDriver driver,String columndata)throws Throwable
{
	//read data from notepad
	FileReader fr= new FileReader("D:\\OJTProjectTesting\\Hybrid Framework\\CaptureData");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data=br.readLine();
	//convert columndata into integer
	int column=Integer.parseInt(columndata);
	if(!driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search.textbox"))).isDisplayed())
		//click on serach panel button
		driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search.panel"))).click();
	Thread.sleep(5000);
	//clear search textbox
	WebElement searchtext=driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search.textbox")));
	searchtext.clear();
	Thread.sleep(5000);
	searchtext.sendKeys(Exp_Data);
	Thread.sleep(5000);
	driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search.button"))).click();
	Thread.sleep(5000);
	WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("webtable.path")));
	//count no of rows in a tbale
	List<WebElement> rows=table.findElements(By.tagName("tr"));
	for(int i=1;i<rows.size();i++)
	{
		//capture six cell data in a table
	String act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
	Assert.assertEquals(act_data, Exp_Data,"Column data is Not matching");
	Reporter.log(Exp_Data+"     "+act_data,true);
	break;
	}
		
	
}
public static void stockCategories(WebDriver driver)throws Throwable
{
	Actions ac = new Actions(driver);
	ac.moveToElement(driver.findElement(By.linkText("Stock Items"))).perform();
	Thread.sleep(4000);
	ac.moveToElement(driver.findElement(By.xpath("(//a[text()='Stock Categories'])[2]"))).click().perform();
	Thread.sleep(4000);
}
public static void stockTable(WebDriver driver,String testData)throws Throwable
{
	if(!driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search.textbox"))).isDisplayed());
		driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search.panel"))).click();
	Thread.sleep(4000);
	WebElement searchtext=driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search.textbox")));
	searchtext.clear();
	Thread.sleep(5000);
	searchtext.sendKeys(testData);
	Thread.sleep(5000);
	driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search.button"))).click();
	Thread.sleep(5000);
	WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("webtable.stock")));
	List<WebElement> rows=table.findElements(By.tagName("tr"));
	for(int i=1; i<=rows.size(); i++)
	{
		String actdata=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
	Assert.assertEquals(actdata, testData,"Category Not Matching");
	Reporter.log(testData+"     "+actdata,true);
	break;
	}
	
	
}
}
































