package com.books.helper;

import java.util.List;
import java.util.Map;

import com.books.context.ScenarioContext;
import com.books.context.ScenarioContextManager;
import com.books.context.TestContext;
import com.books.models.OrderRequestPojo;

public class RequestBodySetup {

	private final ScenarioContext context = ScenarioContextManager.getContext();
	private List<Map<String, String>> testData;

	public void readTestDataFromExcel(String sheetName) {
		testData = context.getExcelReader().readData(sheetName);
	}

	public void getOrderDataWithIndex(String scenario, int index) {

		Map<String, String> orderData = testData.get(index);
		System.out.println("============Order data at index: " + index + " ==============is: " + orderData);
		if (scenario.equals(orderData.get("scenario"))) {
			OrderRequestPojo orderRequestPojo = TestContext.getInstance().getOrderRequestPojo();
			orderRequestPojo.setBookId(orderData.get("bookId"));
			orderRequestPojo.setCustomerName(orderData.get("customerName"));
			orderRequestPojo.setMethod(orderData.get("method"));
			orderRequestPojo.setIncludeAuth(orderData.get("includeAuth"));
		} else {
			System.out.println("Ran out of test data for background");
		}
	}

	public void orderRequestBodySetup(String scenario) {
		OrderRequestPojo orderRequestPojo = context.getOrderRequestPojo();

		for (Map<String, String> orderData : testData) {
			if (orderData.get("scenario").equals(scenario)) {
				String method = orderData.get("method");
				orderRequestPojo.setMethod(method);
				orderRequestPojo.setIncludeAuth(orderData.get("includeAuth"));
				if (method.equals("POST") || method.equals("PATCH")) {
					orderRequestPojo.setBookId(orderData.get("bookId"));
					orderRequestPojo.setCustomerName(orderData.get("customerName"));
				}
				break;
			}
		}
	}
}
