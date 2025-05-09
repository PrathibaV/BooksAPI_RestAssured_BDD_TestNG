package com.books.context;

import com.books.constants.Endpoints;
import com.books.helper.RequestBodySetup;
import com.books.helper.ResponseUtils;
import com.books.helper.SpecificationBuilder;
import com.books.models.OrderRequestPojo;
import com.books.models.ResponseBodyPojo;
import com.books.utils.ExcelReader;


public class ScenarioContext {

	private OrderRequestPojo orderRequestPojo = new OrderRequestPojo();
	private ResponseBodyPojo responseBodyPojo = new ResponseBodyPojo();
	private ExcelReader excelReader;
	private SpecificationBuilder specificationBuilder;
	private RequestBodySetup requestBodySetup;
	private ResponseUtils responseUtils;
	private ResponseBodyPojo[] responseBodyPojoArray;
	

	
	public OrderRequestPojo getOrderRequestPojo() {
		return orderRequestPojo;
	}
	
	public ResponseBodyPojo getResponseBodyPojo() {
		return responseBodyPojo;
	}
	
    public void setResponseBodyPojo(ResponseBodyPojo responseBody) {
    	responseBodyPojo = responseBody;
    }
	
	public ExcelReader getExcelReader() {
		return excelReader == null ? excelReader = new ExcelReader() : excelReader;
	}
	
	public SpecificationBuilder getSpecificationBuilder() {
		return specificationBuilder == null ? specificationBuilder = new SpecificationBuilder() : specificationBuilder;
	}	
	
	public RequestBodySetup getRequestBodySetup() {
		return requestBodySetup == null ? requestBodySetup = new RequestBodySetup() : requestBodySetup;
	}
	
	public ResponseUtils getResponseUtils() {
		return responseUtils == null ? responseUtils = new ResponseUtils() : responseUtils;
	}

	public void setResponseBodyPojoList(ResponseBodyPojo[] pojoArray) {
	    this.responseBodyPojoArray = pojoArray;
	}

	public ResponseBodyPojo[] getResponseBodyPojoArray() {
	    return responseBodyPojoArray;
	}
	
}
