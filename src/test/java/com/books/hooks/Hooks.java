package com.books.hooks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.fail;

import java.util.ResourceBundle;

import org.testng.annotations.AfterSuite;

import com.books.constants.Endpoints;
import com.books.helper.TestContext;
import com.books.models.AuthorizationPojo;
import com.books.models.ResponseBodyPojo;
import com.books.utils.ConfigReader;

import io.cucumber.java.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Hooks {
	
	private TestContext context;
	private static Boolean isTokenFetched = false;
	
	public Hooks(TestContext context) {
		this.context = context;
	}
	
	public Hooks() {
		
	}
	
	
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
	
	
	
	@AfterAll
	public static void cleanUp() {
	   /* Integer orderId = (Integer) context.get("orderId");
	    if (orderId != null) {
	        given().delete("/orders/" + orderId);
	    } */
	}
}
