package com.books.context;

import java.util.ArrayList;
import java.util.List;

import com.books.models.OrderRequestPojo;
import com.books.models.ResponseBodyPojo;
import com.books.utils.ExcelReader;

public class TestContext {

	 private static final TestContext instance = new TestContext();

	    private String accessToken;
	    private String orderId;
	    private List<String> orderIDs = new ArrayList<>();
	    private OrderRequestPojo orderRequestPojo;


		public OrderRequestPojo getOrderRequestPojo() {
			return orderRequestPojo == null ? orderRequestPojo = new OrderRequestPojo() : orderRequestPojo;
		}

		private TestContext() {}

	    public static TestContext getInstance() {
	        return instance;
	    }

	    // Store the access token (if needed)
	    public void setAccessToken(String token) {
	        this.accessToken = token;
	    }

	    public String getAccessToken() {
	        return accessToken;
	    }
	    
	    public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderID) {
			this.orderId = orderID;
			addOrderIdToList(orderID);
		}
		
		public void addOrderIdToList(String orderID) {
			orderIDs.add(orderID);
		}
		
		public List<String> getOrderIds() {
			return orderIDs;
		}
		
		public void clearOrderId() {
			this.orderId = null;
		}
	    
	    
}
