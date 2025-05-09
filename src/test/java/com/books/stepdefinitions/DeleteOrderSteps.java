package com.books.stepdefinitions;


import com.books.constants.Endpoints;
import com.books.context.ScenarioContext;
import com.books.context.ScenarioContextManager;
import com.books.context.TestContext;
import com.books.utils.ConfigReader;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class DeleteOrderSteps {

	private final ScenarioContext context = ScenarioContextManager.getContext();
	private Response response;
	private ResponseSpecification responseSpec;

	
	@Given("User creates DELETE request for deleting the order with valid request details from {string} for {string}")
	public void user_creates_delete_request_for_deleting_the_order_with_valid_request_details_from_for(String sheetName, String scenario) {
		context.getRequestBodySetup().readTestDataFromExcel(sheetName);
	    context.getRequestBodySetup().orderRequestBodySetup(scenario);
	    context.getSpecificationBuilder().requestBuilder(scenario);	    
	}

	@When("User sends HTTPs request with valid deleteOrder endpoint for {string}")
	public void user_sends_htt_ps_request_with_valid_delete_order_endpoint_for(String scenario) {
		String endpoint = Endpoints.deleteOrder;
	    response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);	    
	}
	
	@Then("User receives {int}, {string} in the response of the delete request")
	public void user_receives_and(int expectedStatusCode, String expectedStatusLine) {
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine);
	    response.then().spec(responseSpec);	//.log().all()
	    TestContext.getInstance().clearOrderId();
	}

	@When("User sends HTTPs request with invalid order id for {string}")
	public void user_sends_htt_ps_request_with_invalid_order_id_for(String scenario) {
		String endpoint = Endpoints.deleteOrderWithInvalidID;
	    response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);	    
	}

	@Then("User receives {int}, {string}, {string} and {string} in the response body of the delete request")
	public void user_receives_and_in_the_response_body(int expectedStatusCode, String expectedStatusLine, String expectedContentType, String errorMessage) {
		String expectedErrorMessage = errorMessage.replace("{orderId}", ConfigReader.getProperties().getString("invalidOrderID"));
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine, expectedContentType);
	    response.then().spec(responseSpec);  //.log().all()
	    
	    context.getResponseUtils().errorMessageValidation(response, expectedErrorMessage);    
	}

}
