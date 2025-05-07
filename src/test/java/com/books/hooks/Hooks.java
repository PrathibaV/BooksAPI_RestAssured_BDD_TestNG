package com.books.hooks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.fail;

import java.util.ResourceBundle;

import org.testng.annotations.AfterSuite;

import com.books.constants.Endpoints;
import com.books.helper.TestContext;
import com.books.helper.TestContextManager;
import com.books.models.AuthorizationPojo;
import com.books.models.ResponseBodyPojo;
import com.books.utils.ConfigReader;

import io.cucumber.java.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Hooks {
	
	private final TestContext context = TestContextManager.getContext();
	private Boolean isTokenFetched = true;
	
	
	@Before (order=0)
	public void authorization() {
		if(!isTokenFetched) {
		AuthorizationPojo authPojo = new AuthorizationPojo();
		authPojo.setClientName(ConfigReader.getProperties().getString("clientName"));
		authPojo.setClientEmail(ConfigReader.getProperties().getString("clientEmail"));
		
		Response response = RestAssured.given()
					.baseUri(ConfigReader.getProperties().getString("baseURL"))
					.contentType(ContentType.JSON)
					.body(authPojo)
					.when()
					.post(Endpoints.registerClient);					
				
		response.then().log().all();
		if (response.getStatusCode() == 201) {
			//String accessToken = response.jsonPath().get("accessToken");
			//ResponseBodyPojo authResponse = response.getBody().as(ResponseBodyPojo.class);
			//ResponseBodyPojo responsePojo = context.getResponseUtils().deserializationToPojo(response);
			//System.out.println("-----------------Access Token: "+ responsePojo.getAccessToken());
			 Object result = context.getResponseUtils().deserializationToPojo(response);
			    if (result instanceof ResponseBodyPojo) {
			        ResponseBodyPojo auth = context.getResponseBodyPojo();
			        System.out.println("-----------------Access Token: "+ auth.getAccessToken());
					context.setAuthResponse(auth);
			        } else {
			        fail("Unexpected response format.");
			    }
			
			 isTokenFetched = true;
		} else {
			System.out.println("Check the user details");
		}	
	 }
	}
	
	
	
	@After
	public void deleteOrder(Scenario scenario) {
		try {
		System.out.println("===========Context Hash: " + context.hashCode() + "===============Scenario name: "+scenario.getName());
	    if (!scenario.getSourceTagNames().contains("@noDeleteOrderHooks")) {
	        String bearerToken = "4663fb456db25aed9b046ef866857a467cfb87a6936de22d28723a970852ec5c"; 
	        String orderId = context.getResourcesManager().getLastOrderId();
	        
	        System.out.println("=================== OrderID for deletion: " + orderId);
	        
	        if (orderId != null) {
	            RestAssured.given()
	                .baseUri(ConfigReader.getProperties().getString("baseURL"))
	                .header("Authorization", "Bearer " + bearerToken)
	                .pathParam("orderID", orderId)
	                .when()
	                .delete(Endpoints.deleteOrder)
	                .then().log().all();
	        } else {
	            System.out.println("No orderId found, skipping deletion.");
	        }
	    } else {
	        System.out.println("Scenario tagged with @noDeleteOrderHooks, skipping deletion.");
	    }
		} finally {
	        TestContextManager.removeContext();  // Clear thread-local context
	    }
	}

}
