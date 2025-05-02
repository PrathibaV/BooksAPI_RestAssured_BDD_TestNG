package com.books.helper;

import com.books.models.OrderRequestPojo;
import com.books.models.ResponseBodyPojo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;

public class ResponseUtils {
	private TestContext context;

	public ResponseUtils(TestContext context) {
		this.context = context;
	}

	public Object deserializationToPojo(Response response) {
		String responseBody = response.getBody().asString().trim();

		if (responseBody.startsWith("[")) {
			ResponseBodyPojo[] pojoArray = response.as(ResponseBodyPojo[].class);
			context.setResponseBodyPojoList(pojoArray);
			return pojoArray;
		} else {
			ResponseBodyPojo pojo = response.as(ResponseBodyPojo.class);
			context.setResponseBodyPojo(pojo);
			return pojo;
		}
	}

	public static void validateOrderFields(ResponseBodyPojo order) {
		assertThat("Order ID should not be empty or null", order.getId(), not(is(emptyOrNullString())));
		assertThat("Customer name should not be empty or null", order.getCustomerName(), not(is(emptyOrNullString())));
		assertThat("CreatedBy should not be empty or null", order.getCreatedBy(), not(is(emptyOrNullString())));
		assertThat("Book ID should be positive", order.getBookId(), greaterThan(0));
		assertThat("Quantity should be at least 1", order.getQuantity(), greaterThanOrEqualTo(1));
		assertThat("Timestamp should be a valid number", order.getTimestamp(), greaterThan(0L));
	}

	public static void validateOrderFieldsData(ResponseBodyPojo order, OrderRequestPojo payload, String orderId) {
		assertThat("Customer name mismatch", order.getCustomerName(), equalTo(payload.getCustomerName()));
		assertThat("Book ID mismatch", order.getBookId(), equalTo(payload.getBookId()));
		assertThat("Order ID mismatch", order.getId(), equalTo(orderId));
	}
}
