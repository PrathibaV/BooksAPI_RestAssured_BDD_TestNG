package com.books.stepdefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import com.books.constants.Endpoints;
import com.books.context.ScenarioContext;
import com.books.context.ScenarioContextManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class PostOrderSteps {
	private final ScenarioContext context = ScenarioContextManager.getContext();
	private Response response;
	private ResponseSpecification responseSpec;
	
	
	
	@Given("User creates POST request for creating new Order with valid request body from {string} for {string}")
	public void user_creates_post_request_for_creating_new_order_with_valid_request_body_from_for(String sheetName, String scenario) {
		context.getRequestBodySetup().readTestDataFromExcel(sheetName);
	    context.getRequestBodySetup().orderRequestBodySetup(scenario);
	    context.getSpecificationBuilder().requestBuilder(scenario);	    
	}

	@When("User sends HTTPs request with valid createOrder endpoint for {string}")
	public void user_sends_htt_ps_request_with_valid_endpoint_for(String scenario) {
	    String endpoint = Endpoints.createOrder;
	    response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);	    
	}

	@Then("User receives {int}, {string} and {string} with response body containing the Order details")
	public void user_receives_and_with_response_body_containing_the_order_details(int expectedStatusCode, String expectedStatusLine, String expectedContentType) { 	
		context.getResponseUtils().addIDToList(response);
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine, expectedContentType);
	    response.then().spec(responseSpec);	//then().log().all()
	    assertThat(response.getBody().asString(), matchesJsonSchemaInClasspath("postOrderSchema.json"));	    
	}

	@Given("User creates POST request for creating new Order with missing fields from {string} for {string}")
	public void user_creates_post_request_for_creating_new_order_with_missing_fields_from_for(String sheetName, String scenario) {
		context.getRequestBodySetup().readTestDataFromExcel(sheetName);
	    context.getRequestBodySetup().orderRequestBodySetup(scenario);
	    context.getSpecificationBuilder().requestBuilder(scenario);	  	    
	}

	@Then("User receives {int}, {string}, {string} and {string} with response body containing the Order details")
	public void user_receives_and_with_response_body_containing_the_order_details(int expectedStatusCode, String expectedStatusLine, String expectedContentType, String errorMessage) {
		context.getResponseUtils().addIDToList(response);

		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine, expectedContentType);
	    response.then().spec(responseSpec);	  //.log().all()

	    context.getResponseUtils().errorMessageValidation(response, errorMessage);
	}

	@When("User sends HTTPs request with invalid endpoint for {string}")
	public void user_sends_htt_ps_request_with_invalid_endpoint_from_for(String scenario) {		
	    String endpoint = Endpoints.invalidEndpoint;
	    response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);	    
	}

	@Then("User receives {int}, {string} in the response")
	public void user_receives_and(int expectedStatusCode, String expectedStatusLine) {
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine);
	    response.then().spec(responseSpec);	    //.log().all()
	}

}
