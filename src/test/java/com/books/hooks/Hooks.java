package com.books.hooks;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.fail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;

import com.books.constants.Endpoints;
import com.books.context.ScenarioContext;
import com.books.context.ScenarioContextManager;
import com.books.context.TestContext;
import com.books.models.AuthorizationPojo;
import com.books.models.ResponseBodyPojo;
import com.books.stepdefinitions.CommonSteps;
import com.books.utils.ConfigReader;

import io.cucumber.java.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Hooks {
	
	private final ScenarioContext context = ScenarioContextManager.getContext();

	

	
	/*@After
	public void deleteOrder(Scenario scenario) {
		try {
		System.out.println("===========Context Hash: " + context.hashCode() + "===============Scenario name: "+scenario.getName());
	    if (scenario.getSourceTagNames().contains("@testttttDeleteOrderHooks")) {
	        String bearerToken = TestContext.getInstance().getAccessToken();
	        String orderId = TestContext.getInstance().getOrderId();
	        
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
	        System.out.println("Scenario not tagged with @DeleteOrderHooks, skipping deletion.");
	    }
		} finally {
	        ScenarioContextManager.removeContext();  // Clear thread-local context
	    }
	} */
	
	@AfterAll
	public static void deleteAllOrders() {
		
	        String bearerToken = TestContext.getInstance().getAccessToken();
	        List<String> orderIds = TestContext.getInstance().getOrderIds();
	        
	        for (String orderId :  orderIds) {
	            RestAssured.given()
	                .baseUri(ConfigReader.getProperties().getString("baseURL"))
	                .header("Authorization", "Bearer " + bearerToken)
	                .pathParam("orderID", orderId)
	                .when()
	                .delete(Endpoints.deleteOrder)
	                .then().log().all();
	        } 
	}

}
