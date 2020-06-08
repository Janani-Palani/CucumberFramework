Feature: Play around Carwale

Scenario: Print the details of least KM travelled Hyundai Creta car
Given User launches the Carwale website
And User clicks on used cars
And User selects City as Chennai
And User selects budget min and max and searches for cars
And User selects Cars with Photos under Only Show Cars With Section
And User selects Manufacturer as Hyundai - Creta
And User selects Fuel Type as Petrol
And User selects Best Match as KM: Low to High
And User ensures that Cars are listed with KMs Low to High order and adds the least KM ran car to Wishlist
When Go to Wishlist and click on More details
Then Print all the details under Overview