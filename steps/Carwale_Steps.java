package steps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
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

public class Carwale_Steps {
public static ChromeDriver driver;
public WebDriverWait wait;
public JavascriptExecutor click;

@Given("User launches the Carwale website")
public void launchCarwale()
{
	ChromeOptions options=new ChromeOptions();
	options.addArguments("--disable-notifications");
	System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	driver = new ChromeDriver();
	driver.get("https://www.carwale.com/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	String title = driver.getTitle();
	if (title.contains("CarWale"))
		System.out.println("CarWale launched successfully: " + title);
	else
		System.err.println("CarWale launch failed");
}

@And("User clicks on used cars")
public void clickUsedCars()
{
	driver.findElementByXPath("//li[@data-tabs='usedCars']").click();
}
	
@And("User selects City as Chennai")
public void selectCity() throws InterruptedException
{
	wait=new WebDriverWait(driver,10);
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//div[@class='used-cars-search']")));
	driver.findElementByXPath("//div[@class='used-cars-search']/input").click();
	driver.findElementByXPath("//input[@id='usedCarsList']").sendKeys("Chennai");
	Thread.sleep(1000);
	driver.findElementByXPath("//input[@id='usedCarsList']").sendKeys(Keys.ENTER);
	 
}

@And("User selects budget min and max and searches for cars")
public void setBudget() throws InterruptedException
{
	Thread.sleep(1000);
	click = (JavascriptExecutor) driver;
	click.executeScript("arguments[0].click();", driver.findElementByXPath("//span[text()='Choose your budget']"));
	click.executeScript("arguments[0].click();", driver.findElementByXPath("//li[text()='8 Lakh']"));
	click.executeScript("arguments[0].click();", driver.findElementByXPath("//input[@id='maxInput']/parent::div/following-sibling::ul[2]/li[text()='12 Lakh']"));
	driver.findElementByXPath("//button[@id='btnFindCar']").click();
	if(driver.getTitle().contains("Used Cars"))
	{
		System.out.println("Navigated to used cars in Chennai");
	}
	if(driver.findElementByXPath("//span[text()='8L - 12L']").isDisplayed())
	{
		System.out.println("Budget filtered with 8-12L");
	}
	//Handling pop-up
	driver.findElementByXPath("//input[@id='placesQuery']").sendKeys("Chennai");
	driver.findElementByXPath("//a[@cityname='chennai,tamilnadu']").click();
	 
}
	
@And("User selects Cars with Photos under Only Show Cars With Section")
public void selectCarWithPhotos()
{
	driver.findElementByName("CarsWithPhotos").click();
	if(driver.findElementByXPath("//span[@id='filterbyadditional']/span[text()=' Cars with Photos ']").isDisplayed())
	{
		System.out.println("Cars with photos are displayed");
	}
	 
}
	
@And("User selects Manufacturer as Hyundai - Creta")
public void selectCarMake()
{
	click.executeScript("arguments[0].click();", driver.findElementByXPath("//li[@data-manufacture-en='Hyundai']"));
	wait.until(ExpectedConditions.elementToBeClickable(driver.findElementByXPath("//span[text()='Creta']")));
	click.executeScript("arguments[0].click();", driver.findElementByXPath("//span[text()='Creta']"));
	System.out.println("Hyundai creta model selected");
	 
}

@And("User selects Fuel Type as Petrol")
public void chooseFuelType() throws InterruptedException
{
	click.executeScript("arguments[0].click();", driver.findElementByXPath("//h3[contains(text(),'Fuel Type')]"));
	Thread.sleep(2000);
	click.executeScript("arguments[0].click();", driver.findElementByXPath("//span[text()='Petrol']/parent::li"));
	System.out.println("Fuel type selected as petrol");
	 
}

@And("User selects Best Match as KM: Low to High")
public void sortByLowToHighKM() throws InterruptedException
{
	Thread.sleep(2000);
	driver.findElementById("sort").click();
	Select eleSort=new Select(driver.findElementById("sort"));
	eleSort.selectByVisibleText("KM: Low to High");
	if(driver.findElementByXPath("//option[text()='KM: Low to High']").isSelected())
	{
		System.out.println("KM Low to High sort is selected");
	}
	 
}
	
@And("User ensures that Cars are listed with KMs Low to High order and adds the least KM ran car to Wishlist")
public void ensureSortIsLowToHighKM() throws InterruptedException
{
	Map<Integer,String> leastKMmap=new LinkedHashMap<Integer,String>();
	List<WebElement> eleKM = driver.findElementsByXPath("//span[contains(@class,'slkms vehicle')]");
	List<Integer> vehicleKM=new ArrayList<Integer>();
	for(int i=0;i<eleKM.size();i++)
	{
		String rawKM = eleKM.get(i).getText();
		int intKM = Integer.parseInt(rawKM.replaceAll("\\D", ""));
		vehicleKM.add(intKM);
		leastKMmap.put(intKM, rawKM);
	}
//Create copy of the Integer List, sort and compare two lists
	List<Integer> sortedvehicleKM=new ArrayList<Integer>(vehicleKM);
	Collections.sort(sortedvehicleKM);
	if(vehicleKM.equals(sortedvehicleKM))
	{
		System.out.println("Hyundai Creta cars are sorted by KM: Low to high");
	}
	else System.err.println("Hyundai Creta cars are NOT sorted by KM: Low to high");
	Integer leastKM = sortedvehicleKM.get(0);
	String strLeastKM="";
	for (Entry<Integer, String> eachEntry : leastKMmap.entrySet())
	{
		if(eachEntry.getKey().equals(leastKM))
		{
			strLeastKM = eachEntry.getValue();
		}
	}
	System.out.println("Least KM utilised by Hyundai Creta car: "+strLeastKM);
	Thread.sleep(1000);
	WebElement eleCartoWishlist = driver.findElementByXPath("(//span[text()='"+strLeastKM+"']//ancestor::div[@class='stock-detail']//span[contains(@class,'shortlist')])[2]");
	click.executeScript("arguments[0].click();", eleCartoWishlist);
	 
}
	
@When("Go to Wishlist and click on More details")
public void clickMoreDetails() throws InterruptedException
{
	Thread.sleep(2000);
	driver.findElementByXPath("//li[@data-action='ShortList&CompareWindow_Click']").click();
		/*
		 * carWithLeastKM =
		 * driver.findElementByXPath("//a[@data-action='ShortListedCar_Click']/span").
		 * getText(); System.out.println("Car that utolised least KM: "+carWithLeastKM);
		 */
	Thread.sleep(1000);
	driver.findElementByXPath("//a[text()='More details »']").click();
	Set<String> setHandle = driver.getWindowHandles();
	List<String> listHandle=new ArrayList<String>(setHandle);
	driver.switchTo().window(listHandle.get(1));
	 
}
	
@Then("Print all the details under Overview")
public void printDeets() throws InterruptedException
{
	Thread.sleep(2000);
	Map<String,String> carDeets=new LinkedHashMap<String,String>();
	List<WebElement> eleDesc = driver.findElementsByXPath("//div[contains(@class,'overview-list')]//div[contains(@class,'text-light-grey')]");
	List<WebElement> eleValue = driver.findElementsByXPath("//div[contains(@class,'dark-text')]");
	for(int i=0;i<eleDesc.size();i++)
	{
		String strDeet = eleDesc.get(i).getText();
		String strDeetValue = eleValue.get(i).getText();
		carDeets.put(strDeet, strDeetValue);
	}
	System.out.println("Overview details:");
	for (Entry<String, String> eachDeet : carDeets.entrySet())
	{
		System.out.println(eachDeet.getKey()+" -> "+eachDeet.getValue());
	}
	 
}	

}
