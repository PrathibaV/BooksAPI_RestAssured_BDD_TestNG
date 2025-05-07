package com.books.stepdefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.fail;

import com.books.constants.Endpoints;
import com.books.helper.ResponseUtils;
import com.books.helper.TestContext;
import com.books.helper.TestContextManager;
import com.books.helper.TestContextManager;
import com.books.models.OrderRequestPojo;
import com.books.models.ResponseBodyPojo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;


public class GetOrderSteps {

	private final TestContext context = TestContextManager.getContext();	
	private Response response;
	private ResponseSpecification responseSpec;
	
	
	@Given("User creates GET request for retrieving all the Orders with valid request details from {string} for {string}")
	public void user_creates_get_request_for_retrieving_all_the_orders_with_valid_request_details_from_for(String sheetName, String scenario) {
		context.getRequestBodySetup().readTestDataFromExcel(sheetName);
	    context.getRequestBodySetup().orderRequestBodySetup(scenario);
	    context.getSpecificationBuilder().requestBuilder(scenario);		    
	}

	@When("User sends HTTPs request with valid getAllOrders endpoint for {string}")
	public void user_sends_htt_ps_request_with_valid_get_all_orders_endpoint_for(String scenario) {
		String endpoint = Endpoints.getAllOrders;
	    response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);	    
	}

	@Then("User receives {int}, {string} and {string} with response body containing all orders details")
	public void user_receives_and_with_response_body_containing_all_orders_details(int expectedStatusCode, String expectedStatusLine, String expectedContentType) {
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine, expectedContentType);
	    response.then().log().all().spec(responseSpec);
	    assertThat(response.getBody().asString(), matchesJsonSchemaInClasspath("getAllOrdersSchema.json"));	
	    
	    Object result = context.getResponseUtils().deserializationToPojo(response);	

	    //Field validations
	    if (result instanceof ResponseBodyPojo[]) {
	        ResponseBodyPojo[] orders = context.getResponseBodyPojoArray();
	        for (ResponseBodyPojo order : orders) {
	        	ResponseUtils.validateOrderFields(order);
	        }
	    }  else {
	        fail("Unexpected response structure");
	    }
	}

	@Given("User creates GET request for retrieving an Order with valid request details from {string} for {string}")
	public void user_creates_get_request_for_retrieving_an_order_with_valid_request_details_from_for(String sheetName, String scenario) {
		context.getRequestBodySetup().readTestDataFromExcel(sheetName);
	    context.getRequestBodySetup().orderRequestBodySetup(scenario);
	    context.getSpecificationBuilder().requestBuilder(scenario);	    
	}

	@When("User sends HTTPs request with valid orderID for {string}")
	public void user_sends_htt_ps_request_with_valid_order_id_for(String scenario) {
		String endpoint = Endpoints.getOrder;
	    response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);		    
	}

	@Then("User receives {int}, {string} and {string} in the response body")
	public void user_receives_and_in_the_response_body(int expectedStatusCode, String expectedStatusLine, String expectedContentType) {
		String orderId = context.getResourcesManager().getLastOrderId();
		OrderRequestPojo payload = context.getOrderRequestPojo();
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine, expectedContentType);
	    response.then().log().all().spec(responseSpec);
	   // ResponseBodyPojo responsePojo = context.getResponseUtils().deserializationToPojo(response);	
	    assertThat(response.getBody().asString(), matchesJsonSchemaInClasspath("getSingleOrderSchema.json"));	
	    
	    Object result = context.getResponseUtils().deserializationToPojo(response);
	    if (result instanceof ResponseBodyPojo) {
	        ResponseBodyPojo order = context.getResponseBodyPojo();
	        ResponseUtils.validateOrderFields(order);
	        ResponseUtils.validateOrderFieldsData(order, payload, orderId);
	    } else {
	        fail("Unexpected response format");
	    }
	}
	
	@When("User sends HTTPs request with invalid endpoint and valid orderId for {string}")
	public void user_sends_htt_ps_request_with_invalid_endpoint_from_for(String scenario) {		
	    String endpoint = Endpoints.invalidEndpointWithOrderId;
	    response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);	    
	}
	
	@Then("User receives {int}, {string} in the response of the get request")
	public void user_receives_and(int expectedStatusCode, String expectedStatusLine) {
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine);
	    response.then().log().all().spec(responseSpec);	    
	}

}
