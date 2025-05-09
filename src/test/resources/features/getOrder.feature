@tag
Feature: Books API Order module - GET
To validate the get order through GET request

		Background: 
		Given User is authenticated
		And user creates a new order from sheetName "Post" and scenario "post order background"
		
     @tag3 @wip
   Scenario Outline: Check if the User is able to get an Order with valid orderID
    Given User creates GET request for retrieving an Order with valid request details from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with valid orderID for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>" and "<ContentType>" in the response body
    
	Examples:
		| SheetName  | Scenario | ExpectedStatusCode | ExpectedStatusLine  | ContentType | 
		| Get | get order by order id | 200	| OK	| application/json | 
		
		
	 Scenario Outline: Check if the User is able to get an Order with invalid endpoint
    Given User creates GET request for retrieving an Order with valid request details from "<SheetName>" for "<Scenario>"
    When User sends HTTPs request with invalid endpoint and valid orderId for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>" in the response of the get request
    
	Examples:
		| SheetName  | Scenario | ExpectedStatusCode | ExpectedStatusLine  |
		| Get | get order by order id with invalid endpoint | 404	| Not Found	| 
		