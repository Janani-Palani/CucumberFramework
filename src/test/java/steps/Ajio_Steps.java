package steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class Ajio_Steps {
public static ChromeDriver driver;
public WebDriverWait wait;
public JavascriptExecutor click;
public Actions action;

@Given("User launches Ajio site")
public void launchAjio()
{
	ChromeOptions options=new ChromeOptions();
	options.addArguments("--disable-notifications");
	System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	driver = new ChromeDriver();
	click = (JavascriptExecutor) driver;
	wait=new WebDriverWait(driver,10);
	action=new Actions(driver);
	driver.get("https://www.ajio.com/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	String title = driver.getTitle();
	if (title.contains("AJIO"))
		System.out.println("Ajio launched successfully: " + title);
	else
		System.err.println("Ajio launch failed");
}

@And("User mouseovers on Women, CATEGORIES and click on Kurtas")
public void navigateToKurtas() throws InterruptedException
{
	driver.findElementByXPath("//a[@data-ga-event-label='Women']").click();
	Thread.sleep(2000);
//	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//a[text()='WOMEN']")));
	action.moveToElement(driver.findElementByXPath("//a[text()='WOMEN']")).pause(2000).perform();
	action.moveToElement(driver.findElementByXPath("//a[text()='CATEGORIES']")).perform();
	Thread.sleep(500);
	action.moveToElement(driver.findElementByXPath("(//a[text()='Kurtas'])[2]")).click().build().perform();
	if(driver.findElementByXPath("//div[@class='header2']").getText().equalsIgnoreCase("Kurtas"))
	{
		System.out.println("Women Kurtas are displayed");
	}
}
	
@And("User clicks on Brands and chooses Ajio")
public void selectBrand() throws InterruptedException
{
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='brands']")));
	driver.findElementByXPath("//span[text()='brands']").click();
	Thread.sleep(1000);
	driver.findElementByXPath("//label[@for='AJIO']").click();
}

@And("User checks all the results are Ajio")
public void checkResultsAreAjio() throws InterruptedException
{
	Thread.sleep(2000);
	//wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@class='brand']")));
	List<WebElement> eleBrandNames = driver.findElementsByXPath("//div[@class='brand']");
	int brandCount=0;
	for(int i=0;i<eleBrandNames.size();i++)
	{
	if(eleBrandNames.get(i).getText().equalsIgnoreCase("AJIO"))
	{
		brandCount++;
	}}
	if(brandCount==eleBrandNames.size())
	{
		System.out.println("All results fetched are of brand AJIO");
	}
	else System.err.println("All results fetched are NOT of brand AJIO");
}
	
@And("User sets Sort by Discount")
public void sortByDiscount() throws InterruptedException
{
	Thread.sleep(1000);
	Select eleSortBy=new Select(driver.findElementByXPath("//label[@class='sortby-filter']/parent::div//select"));
	eleSortBy.selectByVisibleText("Discount");
	System.out.println("Results Sorted by discount");
}
	
@And("User selects the Color and clicks ADD TO BAG")
public void selectColor() throws InterruptedException
{
	Thread.sleep(2000);
	click.executeScript("window.scrollBy(0,300)");
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='colors']")));
	click.executeScript("arguments[0].click();", driver.findElementByXPath("//span[text()='colors']"));
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//label[@for='Blue']")));
	click.executeScript("arguments[0].click();", driver.findElementByXPath("//label[@for='Blue']/i"));
	 System.out.println("Blue color Kurtas are selected");
	 Thread.sleep(3000);
	 wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("(//a[@class='rilrtl-products-list__link'])[1]")));
	 click.executeScript("arguments[0].click();", driver.findElementByXPath("(//a[@class='rilrtl-products-list__link'])[1]"));
	Set<String> setHandle = driver.getWindowHandles();
	List<String> listHandle = new ArrayList<String>(setHandle);
	Thread.sleep(2000);
	driver.switchTo().window(listHandle.get(1));
	driver.findElementByXPath("//span[text()='ADD TO BAG']").click();
}

@And("User verifies the error message Select your size to know your estimated delivery date")
public void verifyErrorMsg() throws InterruptedException
{
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//span[text()='Please select a size']")));
	WebElement eleError = driver.findElementByXPath("//span[@class='edd-pincode-msg-details']");
	if(eleError.isDisplayed())
	{
		System.out.println("Error msg is displayed as: "+eleError.getText());
	}
}

@And("User selects size and clicks ADD TO BAG")
public void selectSize() throws InterruptedException
{
	//Select first available size
	List<WebElement> eleSizesInStock = driver.findElementsByXPath("//div[@class='size-swatch']/div[contains(@class,'size-instock')]");
	if(eleSizesInStock.size()>0)
	{
		eleSizesInStock.get(0).click();
		driver.findElementByXPath("//span[text()='ADD TO BAG']").click();
		System.out.println("First available size selected is: "+eleSizesInStock.get(0).getText());
	}
	else System.err.println("No sizes available");
}
	
@And("User clicks on Enter pin-code to know estimated delivery date")
public void clickPincode() throws InterruptedException
{
	Thread.sleep(5000);
	click.executeScript("window.scrollBy(0,300)");
	click.executeScript("arguments[0].click()", driver.findElementByXPath("//div[@class='edd-pincode-msg-container']/span[2]"));
}

@And("User enters the pincode as 603103 and clicks Confirm pincode")
public void enterPincode() throws InterruptedException
{
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//input[@class='edd-pincode-modal-text']")));
	driver.findElementByXPath("//input[@class='edd-pincode-modal-text']").sendKeys("603103");
	Thread.sleep(1000);
	click.executeScript("arguments[0].click()", driver.findElementByXPath("//button[@class='edd-pincode-modal-submit-btn']"));
}

@And("User prints the message and clicks Go to Bag")
public void printMsg() throws InterruptedException
{
	Thread.sleep(2000);
	System.out.println("Below messages displayed are reldated to Delivery");
	List<WebElement> eleMessages = driver.findElementsByXPath("//ul[@class='edd-message-success-details']/li");
	for (WebElement eachMsg : eleMessages) 
	{
		System.out.println(eachMsg.getText());
	}
	Thread.sleep(1000);
	driver.findElementByXPath("//div[@class='btn-cart']").click();
}

@When("User clicks on Proceed to Shipping")
public void proceedToShipping() throws InterruptedException
{
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//button[text()='Proceed to shipping']")));
	driver.findElementByXPath("//button[text()='Proceed to shipping']").click();
}
	
@Then("User should be able to see the popup and close the browser")
public void viewDetailsAndCloseBrowser() throws InterruptedException
{
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@class='ic-close-quickview']")));
	driver.findElementByXPath("//div[@class='ic-close-quickview']").click();
	driver.quit();
}
}