package com.books.stepdefinitions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.fail;

import com.books.constants.Endpoints;
import com.books.helper.TestContext;
import com.books.models.ResponseBodyPojo;
import com.books.utils.ConfigReader;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class DeleteOrderSteps {

	private TestContext context;
	private Response response;
	private ResponseSpecification responseSpec;
	
	public DeleteOrderSteps(TestContext context) {
		this.context = context;
	}
	
	
	@Given("User creates DELETE request for deleting the order with valid request details from {string} for {string}")
	public void user_creates_delete_request_for_deleting_the_order_with_valid_request_details_from_for(String sheetName, String scenario) {
		 context.getRequestBodySetup().orderRequestBodySetup(sheetName, scenario);
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
	    response.then().log().all().spec(responseSpec);	    
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
	    response.then().log().all().spec(responseSpec);
	   // ResponseBodyPojo responsePojo = context.getResponseUtils().deserializationToPojo(response);
	    //assertThat(responsePojo.getError(), equalTo(expectedErrorMessage));
	    Object result = context.getResponseUtils().deserializationToPojo(response);
	    if (result instanceof ResponseBodyPojo) {
	        ResponseBodyPojo order = context.getResponseBodyPojo();
	        assertThat(order.getError(), equalTo(expectedErrorMessage));	    
	    } else {
	        fail("Unexpected response format.");
	    }	    
	}

}
