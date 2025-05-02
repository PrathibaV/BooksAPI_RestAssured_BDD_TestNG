package com.books.helper;

import com.books.models.OrderRequestPojo;
import com.books.utils.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecificationBuilder {
	private ObjectMapper objectMapper = new ObjectMapper();
	private TestContext context;
	private RequestSpecification request;
	private OrderRequestPojo orderRequestPojo;
	
	public SpecificationBuilder(TestContext context) {
		this.context = context;
	}
	
	public SpecificationBuilder() {		
	}
	
	public void requestBuilder(String scenario) {
		
		orderRequestPojo = context.getOrderRequestPojo();
		
	    RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
	    requestBuilder.setContentType(ContentType.JSON);
	    
	    if (scenario.contains("invalid baseurl")) {
		    requestBuilder.setBaseUri(ConfigReader.getProperties().getString("invalidBaseURL"));
	    } else {
		    requestBuilder.setBaseUri(ConfigReader.getProperties().getString("baseURL"));
	    }

	    if (orderRequestPojo.isIncludeAuth()) {			
	        //String bearerToken = context.getAuthResponse().getAccessToken(); 
	    	String bearerToken = "d1d7db7cb800aae4927a13f1ab2916934296e652b64823ed5ce3f5215b31e3bc"; //Delete after trial run
	        System.out.println("---------------- Bearer Token: " + bearerToken);
	        requestBuilder.addHeader("Authorization", "Bearer " + bearerToken);
	    }

	    if ("POST".equalsIgnoreCase(orderRequestPojo.getMethod()) || "PUT".equalsIgnoreCase(orderRequestPojo.getMethod())) {
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

		if ("PATCH".equals(method) || "DELETE".equals(method) || "GET".equals(method) && !scenario.contains("get all")) {
			if (scenario.contains("invalid order id")) {
				request.pathParam("invalidOrderID", ConfigReader.getProperties().getString("invalidOrderID"));
			} else {
				request.pathParam("orderID", context.getResourcesManager().getLastOrderId());
				System.out.println("---------------The last order Id: "+context.getResourcesManager().getLastOrderId());
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

	public ResponseSpecification responseBuilder(int expectedStatusCode, String expectedStatusLine, String expectedContentType) {
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(expectedStatusCode);
		//responseSpecBuilder.expectContentType(expectedContentType);
		//responseSpecBuilder.expectStatusLine(expectedStatusLine);
		
		ResponseSpecification responseSpec = responseSpecBuilder.build();
		
		return responseSpec;
	}
	
	public ResponseSpecification responseBuilder(int expectedStatusCode, String expectedStatusLine) {
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder.expectStatusCode(expectedStatusCode);
		//responseSpecBuilder.expectStatusLine(expectedStatusLine);
		
		ResponseSpecification responseSpec = responseSpecBuilder.build();
		
		return responseSpec;
	}
}
