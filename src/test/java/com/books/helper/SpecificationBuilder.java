package com.books.helper;

import com.books.context.ScenarioContext;
import com.books.context.ScenarioContextManager;
import com.books.context.TestContext;
import com.books.listener.RestAssuredListener;
import com.books.models.OrderRequestPojo;
import com.books.utils.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.lessThan;

public class SpecificationBuilder {
	private ObjectMapper objectMapper = new ObjectMapper();
	private final ScenarioContext context = ScenarioContextManager.getContext();
	private RequestSpecification request;
	private OrderRequestPojo orderRequestPojo;

	public SpecificationBuilder() {
	}

	public void requestBuilder(String scenario) {
		if (scenario.contains("background")) {
			orderRequestPojo = TestContext.getInstance().getOrderRequestPojo();
		} else {
			orderRequestPojo = context.getOrderRequestPojo();
		}

		RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
		requestBuilder.setContentType(ContentType.JSON);

		if (scenario.contains("invalid baseurl")) {
			requestBuilder.setBaseUri(ConfigReader.getProperties().getString("invalidBaseURL"));
		} else {
			requestBuilder.setBaseUri(ConfigReader.getProperties().getString("baseURL"));
		}

		if (orderRequestPojo.isIncludeAuth()) {
			String bearerToken = TestContext.getInstance().getAccessToken();
			System.out.println("---------------- Bearer Token: " + bearerToken);
			requestBuilder.addHeader("Authorization", "Bearer " + bearerToken);
		}
		
		requestBuilder.addFilter(new RestAssuredListener());
		requestBuilder.addFilter(new RequestLoggingFilter());
		requestBuilder.addFilter(new ResponseLoggingFilter());

		if ("POST".equalsIgnoreCase(orderRequestPojo.getMethod())
				|| "PUT".equalsIgnoreCase(orderRequestPojo.getMethod())) {
			try {
				String json = objectMapper.writeValueAsString(orderRequestPojo);
				requestBuilder.setBody(json);
				System.out.println("-------------------Request Body:\n" + json);
			} catch (Exception e) {
				throw new RuntimeException("Failed to serialize request body", e);
			}
		}

		request = RestAssured.given().spec(requestBuilder.build());
	}

	public Response sendHttpRequest(String scenario, String endpoint) {
		String method = orderRequestPojo.getMethod();

		if ("PATCH".equals(method) || "DELETE".equals(method)
				|| "GET".equals(method) && !scenario.contains("get all")) {
			if (scenario.contains("invalid order id")) {
				request.pathParam("invalidOrderID", ConfigReader.getProperties().getString("invalidOrderID"));
			} else {
				request.pathParam("orderID", TestContext.getInstance().getOrderId());
				System.out.println("---------------The last order Id: " + TestContext.getInstance().getOrderId());
			}
		}

		switch (method) {
		case "POST":
			return request.post(endpoint);
		case "PATCH":
			return request.patch(endpoint);
		case "GET":
			return request.get(endpoint);
		case "DELETE":
			return request.delete(endpoint);
		default:
			throw new IllegalArgumentException("Unsupported HTTP method: " + method);
		}
	}

	public ResponseSpecification responseBuilder(int expectedStatusCode, String expectedStatusLine,
			String expectedContentType) {
		long maxAllowedResponseTime = Long.parseLong(ConfigReader.getProperties().getString("maxResponseTimeMillis"));

		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(expectedStatusCode);
		responseSpecBuilder.expectResponseTime(lessThan(maxAllowedResponseTime));
		responseSpecBuilder.expectContentType(expectedContentType);
		// responseSpecBuilder.expectStatusLine(expectedStatusLine);

		ResponseSpecification responseSpec = responseSpecBuilder.build();

		return responseSpec;
	}

	public ResponseSpecification responseBuilder(int expectedStatusCode, String expectedStatusLine) {
		long maxAllowedResponseTime = Long.parseLong(ConfigReader.getProperties().getString("maxResponseTimeMillis"));

		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(expectedStatusCode);
		responseSpecBuilder.expectResponseTime(lessThan(maxAllowedResponseTime));
		// responseSpecBuilder.expectStatusLine(expectedStatusLine);

		ResponseSpecification responseSpec = responseSpecBuilder.build();

		return responseSpec;
	}
}
