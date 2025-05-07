@noDeleteOrderHooks
Feature: Books API Order module - GET
To validate the get all orders through GET request

	Background: 
		Given User is authenticated

  @tag1
  Scenario Outline: Check if the User is able to get all the order details
    Given User creates GET request for retrieving all the Orders with valid request details from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with valid getAllOrders endpoint for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>" and "<ContentType>" with response body containing all orders details
	
	Examples:
		| SheetName  | Scenario | ExpectedStatusCode | ExpectedStatusLine | ContentType|
    | Get | get all orders | 200	| OK	| application/json |
	
  @tag2
   Scenario Outline: Check if the User is able to get all orders with invalid baseurl
    Given User creates GET request for retrieving all the Orders with valid request details from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with valid getAllOrders endpoint for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>" in the response of the get request
	
	Examples:
		| SheetName  | Scenario | ExpectedStatusCode | ExpectedStatusLine  |
		| Get | get all orders with invalid baseurl | 401	| Unauthorized	| 