package com.books.stepdefinitions;

import static org.testng.Assert.fail;

import com.books.constants.Endpoints;
import com.books.context.ScenarioContext;
import com.books.context.ScenarioContextManager;
import com.books.context.TestContext;
import com.books.models.AuthorizationPojo;
import com.books.models.ResponseBodyPojo;
import com.books.utils.ConfigReader;
import com.books.utils.ScenarioCounterManager;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CommonSteps {

	private final ScenarioContext context = ScenarioContextManager.getContext();
	private Response response;

	@Given("User is authenticated")
	public void user_is_authenticated() {
		if (TestContext.getInstance().getAccessToken() == null) {
			AuthorizationPojo authPojo = new AuthorizationPojo();
			authPojo.setClientName(ConfigReader.getProperties().getString("clientName"));
			authPojo.setClientEmail(ConfigReader.getProperties().getString("clientEmail"));

			Response response = RestAssured.given().baseUri(ConfigReader.getProperties().getString("baseURL"))
					.contentType(ContentType.JSON).body(authPojo).when().post(Endpoints.registerClient);

			response.then().log().all();
			if (response.getStatusCode() == 201) {
				String token = response.jsonPath().get("accessToken");
				TestContext.getInstance().setAccessToken(token);
			} else {
				System.out.println("Check the user details");
			}
		} else {
			System.out.println("User is authenticated");
		}
	}

	@Given("user creates a new order from sheetName {string} and scenario {string}")
	public synchronized void user_creates_a_new_order(String sheetName, String scenario) {
		System.out.println("=================check if orderId is present: " + TestContext.getInstance().getOrderId());
		if (TestContext.getInstance().getOrderId() == null) {
			// Given
			int createOrderIndex = ScenarioCounterManager.getNextIndex();
			context.getRequestBodySetup().readTestDataFromExcel(sheetName);
			context.getRequestBodySetup().getOrderDataWithIndex(scenario, createOrderIndex);
			context.getSpecificationBuilder().requestBuilder(scenario);

			// When
			String endpoint = Endpoints.createOrder;
			response = context.getSpecificationBuilder().sendHttpRequest(scenario, endpoint);

			// Then
			response.then().log().all();
			Object result = context.getResponseUtils().deserializationToPojo(response);
			if (result instanceof ResponseBodyPojo) {
				ResponseBodyPojo order = context.getResponseBodyPojo();
				TestContext.getInstance().setOrderId(order.getOrderId());
				System.out.println("-------------- Order ID created via background: " + order.getOrderId());
			} else {
				fail("Unexpected response format.");
			}
		}
	}
}
