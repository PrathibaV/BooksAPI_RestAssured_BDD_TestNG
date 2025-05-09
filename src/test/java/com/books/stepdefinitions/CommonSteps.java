package com.books.stepdefinitions;

import static org.testng.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	private static final Logger logger = LogManager.getLogger(CommonSteps.class);
	private final ScenarioContext context = ScenarioContextManager.getContext();
	private Response response;

	@Given("User is authenticated")
	public void user_is_authenticated() {
		if (TestContext.getInstance().getAccessToken() == null) {
			logger.info("User authentication begins");
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
				logger.info("Check the user details");
			}
		} else {
			logger.info("User is authenticated");
		}
	}

	@Given("user creates a new order from sheetName {string} and scenario {string}")
	public synchronized void user_creates_a_new_order(String sheetName, String scenario) {
		logger.info("=================check if orderId is present: " + TestContext.getInstance().getOrderId());
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
			Object result = context.getResponseUtils().deserializationToPojo(response);
			if (result instanceof ResponseBodyPojo) {
				ResponseBodyPojo order = context.getResponseBodyPojo();
				TestContext.getInstance().setOrderId(order.getOrderId());
				logger.info("-------------- Order ID created via background: " + order.getOrderId());
			} else {
				fail("Unexpected response format.");
			}
		}
	}
}
