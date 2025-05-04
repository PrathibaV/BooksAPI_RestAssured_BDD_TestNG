@tag
Feature: Books API Order module - DELETE
To validate the delete orders through DELETE request

  @tag1
  Scenario Outline: Check if the User is able to delete the order 
    Given User creates DELETE request for deleting the order with valid request details from "<SheetName>" for "<Scenario>" 
    When User sends HTTPs request with valid deleteOrder endpoint for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>" in the response of the delete request
	
	Examples:
		| SheetName  | Scenario 				| ExpectedStatusCode | ExpectedStatusLine |
    | Delete 		 | delete order by order id | 204				 | No Content					| 
	
		
	 Scenario Outline: Check if the User is able to delete an Order with invalid order id
    Given User creates DELETE request for deleting the order with valid request details from "<SheetName>" for "<Scenario>"
    When User sends HTTPs request with invalid order id for "<Scenario>"
    Then User receives <ExpectedStatusCode>, "<ExpectedStatusLine>", "<ContentType>" and "<ErrorMessage>" in the response body of the delete request
    
	Examples:
		| SheetName  | Scenario 				 		 | ExpectedStatusCode | ExpectedStatusLine  | ContentType			 | ErrorMessage |
		| Delete 		 | delete order with invalid order id | 404		| Not Found						| application/json | No order with id {orderId}. |
		