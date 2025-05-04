@tag
Feature: Books API Order module - POST
To validate the user creation through POST request

	Background: 
		Given User is authenticated
	
  @tag2
   Scenario Outline: Check if the User is able to create a new Order with missing customer name
    Given User creates POST request for creating new Order with missing fields from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with valid createOrder endpoint for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>", "<ContentType>" and "<ErrorMessage>" with response body containing the Order details
	
	Examples:
		| SheetName  | Scenario 				 | ExpectedStatusCode | ExpectedStatusLine  | ContentType			 | ErrorMessage |
		| Post 			 | with missing name | 400								| Bad Request					| application/json | Name can't be empty |
		| Post 			 | with missing bookId | 400								| Bad Request					| application/json | Invalid or missing bookId. |

     @tag3
   Scenario Outline: Check if the User is able to create a new Order with invalid endpoint
    Given User creates POST request for creating new Order with valid request body from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with invalid endpoint for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>" in the response
    
	Examples:
		| SheetName  | Scenario 				 		 | ExpectedStatusCode | ExpectedStatusLine  |
		| Post 			 | with invalid endpoint | 404								| Not Found						|
		
  @tag1
  Scenario Outline: Check if the User is able to create a new Order with valid data
    Given User creates POST request for creating new Order with valid request body from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with valid createOrder endpoint for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>" and "<ContentType>" with response body containing the Order details
	
	Examples:
		| SheetName  | Scenario 				| ExpectedStatusCode | ExpectedStatusLine | ContentType|
    | Post 			 | with valid data1 | 201								 | Created						| application/json |
    | Post 			 | with valid data2 | 201								 | Created						| application/json | 