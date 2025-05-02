@tag
Feature: Books API Order module - PATCH
To validate the update order through PATCH request

  @tag1
  Scenario Outline: Check if the User is able to update a new Order with valid data
    Given User creates PATCH request for updating Order with valid request body from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with valid updateOrder endpoint for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>" and "<ContentType>" with response body containing the updated order details
	
	Examples:
		| SheetName  | Scenario 				| ExpectedStatusCode | ExpectedStatusLine | ContentType|
    | Patch 		 | update with valid data | 204								 | Created						| application/json |
	
  @tag2
   Scenario Outline: Check if the User is able to update an Order without authorization
    Given User creates PATCH request for updating Order with valid request body from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with valid updateOrder endpoint for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>", "<ContentType>" and "<ErrorMessage>" with response body containing the updated Order details
	
	Examples:
		| SheetName  | Scenario 				 | ExpectedStatusCode | ExpectedStatusLine  | ContentType			 | ErrorMessage |
		| Patch 			 | update without auth | 401								| Unauthorized					| application/json | Missing Authorization header. |

     @tag3
   Scenario Outline: Check if the User is able to update an Order with invalid orderID
    Given User creates PATCH request for updating Order with valid request body from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with invalid orderID for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>", "<ContentType>" and "<ErrorMessage>" in the response body
    
	Examples:
		| SheetName  | Scenario 				 		 | ExpectedStatusCode | ExpectedStatusLine  | ContentType			 | ErrorMessage |
		| Patch 			 | update with invalid order id | 404								| Not Found						| application/json | No order with id {orderId}. |
		
		
		