package com.books.stepdefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.fail;

import com.books.constants.Endpoints;
import com.books.helper.TestContext;
import com.books.models.ResponseBodyPojo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class PostOrderSteps {
	private TestContext context;
	private Response response;
	private ResponseSpecification responseSpec;
	
	public PostOrderSteps(TestContext context) {
		this.context = context;
	}

	@Given("User is authenticated")
	public void user_is_authenticated() {
		if (!context.getResourcesManager().getAccessToken().isEmpty()) {
			System.out.println("User is authenticated");
		} else {
			System.out.println("User is not authenticated");
		}
	}
	
	
	@Given("User creates POST request for creating new Order with valid request body from {string} for {string}")
	public void user_creates_post_request_for_creating_new_order_with_valid_request_body_from_for(String sheetName, String scenario) {
	    context.getRequestBodySetup().orderRequestBodySetup(sheetName, scenario);
	    context.getSpecificationBuilder().requestBuilder(scenario);	    
	}

	@When("User sends HTTPs request with valid createOrder endpoint for {string}")
	public void user_sends_htt_ps_request_with_valid_endpoint_for(String scenario) {
	    String endpoint = Endpoints.createOrder;
	    response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);	    
	}

	@Then("User receives {int}, {string} and {string} with response body containing the Order details")
	public void user_receives_and_with_response_body_containing_the_order_details(int expectedStatusCode, String expectedStatusLine, String expectedContentType) {
	    //ResponseBodyPojo responsePojo = context.getResponseUtils().deserializationToPojo(response);	        
	    //String orderID = response.jsonPath().get("orderId");
	    Object result = context.getResponseUtils().deserializationToPojo(response);
	    if (result instanceof ResponseBodyPojo) {
	        ResponseBodyPojo order = context.getResponseBodyPojo();
	        context.getResourcesManager().addOrderId(order.getOrderId());
		    System.out.println("-------------- Order ID: "+order.getOrderId());
		} else {
	        fail("Unexpected response format.");
	    }	    	
	    
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine, expectedContentType);
	    response.then().log().all().spec(responseSpec);	
	    assertThat(response.getBody().asString(), matchesJsonSchemaInClasspath("postOrderSchema.json"));	
	}

	@Given("User creates POST request for creating new Order with missing fields from {string} for {string}")
	public void user_creates_post_request_for_creating_new_order_with_missing_fields_from_for(String sheetName, String scenario) {
	    context.getRequestBodySetup().orderRequestBodySetup(sheetName, scenario);
	    context.getSpecificationBuilder().requestBuilder(scenario);	  	    
	}

	@Then("User receives {int}, {string}, {string} and {string} with response body containing the Order details")
	public void user_receives_and_with_response_body_containing_the_order_details(int expectedStatusCode, String expectedStatusLine, String expectedContentType, String errorMessage) {
	    //ResponseBodyPojo responsePojo = context.getResponseUtils().deserializationToPojo(response);	        

		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine, expectedContentType);
	    response.then().log().all().spec(responseSpec);
	    //Assert.assertEquals(responsePojo.getError(),errorMessage);
	    Object result = context.getResponseUtils().deserializationToPojo(response);
	    if (result instanceof ResponseBodyPojo) {
	        ResponseBodyPojo order = context.getResponseBodyPojo();
	        assertThat(order.getError(), equalTo(errorMessage));	    
	    } else {
	        fail("Unexpected response format.");
	    }
	}

	@When("User sends HTTPs request with invalid endpoint for {string}")
	public void user_sends_htt_ps_request_with_invalid_endpoint_from_for(String scenario) {		
	    String endpoint = Endpoints.invalidEndpoint;
	    response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);	    
	}

	@Then("User receives {int}, {string} in the response")
	public void user_receives_and(int expectedStatusCode, String expectedStatusLine) {
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine);
	    response.then().log().all().spec(responseSpec);	    
	}

}
