package com.books.helper;

import java.util.List;
import java.util.Map;
import com.books.models.OrderRequestPojo;


public class RequestBodySetup {
	
	private TestContext context;
	private List<Map<String, String>> testData; 
	
	public RequestBodySetup(TestContext context) {
		this.context = context;
	}
	
	
	public void orderRequestBodySetup(String sheetName, String scenario) {
		OrderRequestPojo orderRequestPojo = context.getOrderRequestPojo();
        
		testData = context.getExcelReader().readData(sheetName);
		
		for (Map<String, String> orderDate : testData) {
			if (orderDate.get("scenario").equals(scenario)) {
				if((orderDate.get("method").equals("GET")) || (orderDate.get("method").equals("DELETE"))) {
				orderRequestPojo.setMethod(orderDate.get("method"));
				orderRequestPojo.setIncludeAuth(orderDate.get("includeAuth"));									
			} else {
				orderRequestPojo.setBookId(orderDate.get("bookId"));
				orderRequestPojo.setCustomerName(orderDate.get("customerName"));
				orderRequestPojo.setMethod(orderDate.get("method"));
				orderRequestPojo.setIncludeAuth(orderDate.get("includeAuth"));	
			}
				break;
		}	
	}
}
}
