package com.books.stepdefinitions;

import static org.testng.Assert.fail;

import org.testng.Assert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.books.constants.Endpoints;
import com.books.context.ScenarioContext;
import com.books.context.ScenarioContextManager;
import com.books.models.ResponseBodyPojo;
import com.books.utils.ConfigReader;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class PatchOrderSteps {

	private final ScenarioContext context = ScenarioContextManager.getContext();
	private Response response;
	private ResponseSpecification responseSpec;

	@Given("User creates PATCH request for updating Order with valid request body from {string} for {string}")
	public void user_creates_patch_request_for_updating_order_with_valid_request_body_from_for(String sheetName,
			String scenario) {
		context.getRequestBodySetup().readTestDataFromExcel(sheetName);
		context.getRequestBodySetup().orderRequestBodySetup(scenario);
		context.getSpecificationBuilder().requestBuilder(scenario);
	}

	@When("User sends HTTPs request with valid updateOrder endpoint for {string}")
	public void user_sends_htt_ps_request_with_valid_update_order_endpoint_for(String scenario) {
		String endpoint = Endpoints.updateOrder;
		response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);
	}

	@Then("User receives {int}, {string} and {string} with response body containing the updated order details")
	public void user_receives_and_with_response_body_containing_the_updated_order_details(int expectedStatusCode,
			String expectedStatusLine, String expectedContentType) {
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine);
		response.then().spec(responseSpec);
	}

	@Then("User receives {int}, {string}, {string} and {string} with response body containing the updated Order details")
	public void user_receives_and_with_response_body_containing_the_updated_order_details(int expectedStatusCode,
			String expectedStatusLine, String expectedContentType, String errorMessage) {
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine,
				expectedContentType);
		response.then().spec(responseSpec);

		context.getResponseUtils().errorMessageValidation(response, errorMessage);
	}

	@When("User sends HTTPs request with invalid orderID for {string}")
	public void user_sends_htt_ps_request_with_invalid_orderid_for(String scenario) {
		String endpoint = Endpoints.updateOrderWithInvalidID;
		response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);
	}

	@Then("User receives {int}, {string}, {string} and {string} in the response body")
	public void user_receives_and_in_the_response_body(int expectedStatusCode, String expectedStatusLine,
			String expectedContentType, String errorMessage) {
		String expectedErrorMessage = errorMessage.replace("{orderId}",
				ConfigReader.getProperties().getString("invalidOrderID"));
		responseSpec = context.getSpecificationBuilder().responseBuilder(expectedStatusCode, expectedStatusLine,
				expectedContentType);
		response.then().spec(responseSpec);

		context.getResponseUtils().errorMessageValidation(response, expectedErrorMessage);
	}
}
