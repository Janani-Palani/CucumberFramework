Feature: Ajio site

Scenario: Add women apparel to cart in Ajio site
Given User launches Ajio site
And User mouseovers on Women, CATEGORIES and click on Kurtas
And User clicks on Brands and chooses Ajio
And User checks all the results are Ajio
And User sets Sort by Discount
And User selects the Color and clicks ADD TO BAG
And User verifies the error message Select your size to know your estimated delivery date
And User selects size and clicks ADD TO BAG
And User clicks on Enter pin-code to know estimated delivery date
And User enters the pincode as 603103 and clicks Confirm pincode
And User prints the message and clicks Go to Bag
When User clicks on Proceed to Shipping 
Then User should be able to see the popup and close the browser