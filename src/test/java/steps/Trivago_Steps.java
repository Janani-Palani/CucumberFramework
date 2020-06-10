package steps;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

public class Trivago_Steps {
public static ChromeDriver driver;
public WebDriverWait wait;
public JavascriptExecutor click;

@Given("User launches Trivago site")
public void launchTrivago()
{
	ChromeOptions options=new ChromeOptions();
	options.addArguments("--disable-notifications");
	System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	driver = new ChromeDriver();
	click = (JavascriptExecutor) driver;
	wait=new WebDriverWait(driver,10);
	driver.get("https://www.trivago.com/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	String title = driver.getTitle();
	if (title.contains("trivago"))
		System.out.println("Trivago launched successfully: " + title);
	else
		System.err.println("Trivago launch failed");
}

@And("User types Agra in Destination and select Agra, Uttar Pradesh")
public void setCity() throws InterruptedException
{
	driver.findElementById("querytext").sendKeys("Agra");
	Thread.sleep(2000);
	driver.findElementById("querytext").sendKeys(Keys.ENTER);
}
	
@And("User chooses May 15 as check in and June 30 as check out")
public void selectDates() throws InterruptedException
{
	wait=new WebDriverWait(driver,10);
	WebElement eleCheckinButton = driver.findElementByXPath("//button[@data-qa='search-button']");
	click.executeScript("arguments[0].click();", eleCheckinButton);
	LocalDate date=LocalDate.now();
	int currentMonth = date.getMonthValue();
	WebElement eleCheckin = driver.findElementByXPath("//table[@class='cal-month']/tbody/tr/td/time[contains(@datetime,'"+currentMonth+"') and contains(@datetime,'15')]");
	WebElement eleCheckout = driver.findElementByXPath("//table[@class='cal-month']/tbody/tr/td/time[contains(@datetime,'"+currentMonth+"') and contains(@datetime,'30')]");
	click.executeScript("arguments[0].click();", eleCheckin);
	click.executeScript("arguments[0].click();", eleCheckout);
	Thread.sleep(1000);
	if(driver.findElementByXPath("//time[contains(text(),'15')]").isDisplayed() && driver.findElementByXPath("//time[contains(text(),'30')]").isDisplayed())
	{
		System.out.println("Check-in and Check-out dates chosen as: "+"15 and 30 "+date.getMonth());
	}
}

@And("User selects room as Family Room")
public void selectRoom() throws InterruptedException
{
	Thread.sleep(1000);
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//li[contains(@class,'roomtype')]")));
	driver.findElementByXPath("//li[contains(@class,'roomtype')]//span[text()='Family rooms']/parent::div").click();
	System.out.println("Family room chosen");
}
	
@And("User chooses Number of Adults 2, Childern 1 and set Child's Age as 4")
public void chooseStayNumbers() throws InterruptedException
{
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//label[text()='Adults']")));
	if(driver.findElementById("select-num-adults-2").isDisplayed())
	{
		System.out.println("Adults selected as 2 by default");
	}
	else 
	{
		driver.findElementByXPath("//span[contains(@class,'df-select')]").click();
		Select eleAdult=new Select(driver.findElementByXPath("//select[contains(@id,'select-num-adults')]"));
		eleAdult.selectByVisibleText("2");
		System.out.println("Adults selected as 2");
	}
	Thread.sleep(1000);
	Select eleChild=new Select(driver.findElementByXPath("//select[contains(@id,'select-num-children')]"));
	eleChild.selectByVisibleText("1");
	System.out.println("Child select as 1");
	wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//label[contains(text(),'age')]")));
	Select eleChildAge=new Select(driver.findElementByXPath("//select[contains(@id,'ages')]"));
	eleChildAge.selectByVisibleText("4");
	System.out.println("Child age selected as 4");
}
	
@And("User clicks Confirm button and Search")
public void confirmAndSearch()
{
	 driver.findElementByXPath("//span[text()='Confirm']/parent::button").click();
	 driver.findElementByXPath("//span[text()='Search']/parent::button").click();
}

@And("User selects Accommodation type as Hotels only and chooses 4 stars")
public void selectAccommodationType() throws InterruptedException
{
	 driver.findElementByXPath("//strong[text()='Accommodation']/parent::button").click();
	 wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//label[text()='Hotels only']/preceding-sibling::input")));
	 driver.findElementByXPath("//label[text()='Hotels only']/preceding-sibling::input").click();
	 driver.findElementByXPath("//button[@title='4-star hotels']").click();
	 driver.findElementByXPath("//button[text()='Done']").click();
	 System.out.println("4 star Hotels only selected in Accommodation");
}

@And("User selects Guest rating as Very Good")
public void selectGuestRating() throws InterruptedException
{
	 Thread.sleep(1000);
	 driver.findElementByXPath("//strong[text()='Guest rating']/parent::button").click();
	 wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//span[text()='Very good']/parent::button")));
	 driver.findElementByXPath("//span[text()='Very good']/parent::button").click();
	 System.out.println("Guest rating set as very good");
}
	
@And("User sets Hotel Location as Agra Fort and click Done")
public void setHotelLocation() throws InterruptedException
{
	Thread.sleep(1000);
	 driver.findElementByXPath("//strong[text()='Hotel location']/parent::button").click();
	 wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//label[text()='this popular site:']")));
	 Select eleSite=new Select(driver.findElementByXPath("//select[@id='pois']"));
	 eleSite.selectByVisibleText("Agra Fort");
	 driver.findElementByXPath("//button[text()='Done']").click();
	 System.out.println("Hotel Location selected as Agra Fort");
}

@And("User selects Air conditioning, Restaurant and WiFi in more Filters and clicks Done")
public void selectFacilities() throws InterruptedException
{
	 driver.findElementByXPath("//strong[text()='More filters']/parent::button").click();
	 wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//label[text()='Air conditioning']")));
	 driver.findElementByXPath("//label[text()='Air conditioning']/parent::li//input").click();
	 driver.findElementByXPath("//label[text()='Restaurant']/parent::li//input").click();
	 driver.findElementByXPath("//label[text()='WiFi']/parent::li//input").click();
	 driver.findElementByXPath("//button[text()='Done']").click();
	 wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//span[text()='Applied Filters']")));
	 if(driver.findElementByXPath("//span[text()='Applied Filters']/parent::span//span[text()='3']").isDisplayed())
	 {
		 System.out.println("Air conditioning, Resturant and WiFi facilities are selected in More Filters");
	 }
}

@And("User sorts the result as Rating & Recommended")
public void sotyByRating() throws InterruptedException
{
	Select eleSort=new Select(driver.findElementByXPath("//select[contains(@id,'sortby')]"));
	eleSort.selectByVisibleText("Rating & Recommended");
	System.out.println("Results sorted by Rating & Recommended");
}

@And("User prints the Hotel name, Rating, Number of Reviews and Clicks on View Deal")
public void printHotelDeets() throws InterruptedException
{
	Thread.sleep(2000);
	String hotelName = driver.findElementByXPath("//ol[contains(@class,'hotel-list')]/li[contains(@class,'item-order')]//div[contains(@class,'name')]//span").getText();
	String hotelRating = driver.findElementByXPath("//ol[contains(@class,'hotel-list')]/li[contains(@class,'item-order')]//strong[contains(@class,'rating')]").getText();
	String strReview = driver.findElementByXPath("//ol[contains(@class,'hotel-list')]/li[contains(@class,'item-order')]//span[contains(@class,'rating')][2]").getText();
	String reviewCount = strReview.replaceAll("[^0-9]", "");
	System.out.println("Hotel: "+hotelName+"\n"+"Rating: "+hotelRating+"\n"+"Number of reviews: "+reviewCount);
	Thread.sleep(500);
	driver.findElementByXPath("//ol[contains(@class,'hotel-list')]/li[contains(@class,'item-order')]//span[text()='View Deal']/ancestor::button").click();
}
	
@And("User prints the URL of the Page")
public void printURL() throws InterruptedException
{
	Set<String> setHandle = driver.getWindowHandles();
	List<String> listHandle=new ArrayList<String>(setHandle);
	driver.switchTo().window(listHandle.get(1));
	System.out.println("URL of the current page: "+driver.getCurrentUrl());
}

@And("User prints the Price of the Room and click choose Your Room")
public void printRoomPriceAndChooseRoom() throws InterruptedException
{
	String strPrice = driver.findElementByXPath("//div[@id='hotellist_inner']/div//div[contains(@class,'price-display__value')]").getText();
	System.out.println("Price of the Hotel room: "+strPrice.replaceAll("[^0-9]", ""));
	driver.findElementByXPath("//div[@id='hotellist_inner']/div//span[contains(text(),'Choose your room')]/parent::a").click();
	if(driver.findElementByXPath("//span[contains(text(),'Hotel Alleviate')]").isDisplayed())
	{
		System.out.println("Navigated to Hotel Alleviate page");
	}
}
	
@When("User clicks Reserve and I'll Reserve")
public void clickReserve() throws InterruptedException
{
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[contains(text(),'Reserve')]/parent::button")));
	driver.findElementByXPath("//span[contains(text(),'Reserve')]/parent::button").click();
	Thread.sleep(3000);
	Select eleRooms=new Select(driver.findElementByXPath("//select[@class='hprt-nos-select']"));
	eleRooms.selectByValue("1");
	driver.findElementByXPath("//div[@class='hprt-reservation-cta']").click();
}
	
@Then("User should be able to view the screen requesting for details and close browser")
public void printDeets() throws InterruptedException
{
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//strong[text()='Enter your details']")));
	List<WebElement> eleHeader = driver.findElementsByXPath("//strong[text()='Enter your details']");
	if(eleHeader.size()>0)
	{
		System.out.println("Reservation initiated");
		Thread.sleep(2000);
		driver.quit();
	}	 
}	
}