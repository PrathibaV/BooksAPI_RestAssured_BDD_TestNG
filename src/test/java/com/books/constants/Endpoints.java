package com.books.constants;

public class Endpoints {
 
	public static String registerClient = "/api-clients/";
	public static String createOrder = "/orders";
	public static String getOrder = "/orders/{orderID}";
	public static String getAllOrders = "/orders";
	public static String updateOrder = "/orders/{orderID}";
	public static String deleteOrder = "/orders/{orderID}";
	public static String invalidEndpoint = "/orderstest";
	public static String invalidEndpointWithOrderId = "/orderstest/{orderID}";
	public static String updateOrderWithInvalidID = "/orders/{invalidOrderID}";
	public static String deleteOrderWithInvalidID = "/orders/{invalidOrderID}";

}
