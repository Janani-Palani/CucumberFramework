Feature: Trivago

Scenario: Hotel reservation in Trivago
Given User launches Trivago site
And User types Agra in Destination and select Agra, Uttar Pradesh
And User chooses May 15 as check in and June 30 as check out
And User selects room as Family Room
And User chooses Number of Adults 2, Childern 1 and set Child's Age as 4
And User clicks Confirm button and Search
And User selects Accommodation type as Hotels only and chooses 4 stars
And User selects Guest rating as Very Good
And User sets Hotel Location as Agra Fort and click Done
And User selects Air conditioning, Restaurant and WiFi in more Filters and clicks Done
And User sorts the result as Rating & Recommended
And User prints the Hotel name, Rating, Number of Reviews and Clicks on View Deal
And User prints the URL of the Page
And User prints the Price of the Room and click choose Your Room
When User clicks Reserve and I'll Reserve
Then User should be able to view the screen requesting for details and close browser