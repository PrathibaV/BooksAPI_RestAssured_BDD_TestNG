package com.books.helper;

import java.util.ArrayList;
import java.util.List;

public class ResourcesManager {

	private List<String> orderIDs;
	private String accessToken;
	
	public ResourcesManager () {
		this.orderIDs = new ArrayList<>();
	}
	
	public void addOrderId(String orderId) {
		orderIDs.add(orderId);
	}
	
	public List<String> getOrderIds() {
		return orderIDs;
	}
	
	public String getOrderId(int index) {
		if (index >= 0 && index <orderIDs.size()) {
			return orderIDs.get(index);
		} else {
			return null;
		}
	}
	
	public String getLastOrderId() {
		return orderIDs.get(orderIDs.size()-1);
	}
	
	public void setAccessToken(String token) {
		this.accessToken = token;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
}
