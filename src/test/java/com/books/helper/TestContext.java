package com.books.helper;

import com.books.constants.Endpoints;
import com.books.models.OrderRequestPojo;
import com.books.models.ResponseBodyPojo;
import com.books.utils.ExcelReader;


public class TestContext {

	private static OrderRequestPojo orderRequestPojo = new OrderRequestPojo();
	private static ResponseBodyPojo responseBodyPojo = new ResponseBodyPojo();
	private ExcelReader excelReader;
	private static ResourcesManager resourcesManager = new ResourcesManager();
	private SpecificationBuilder specificationBuilder;
	private RequestBodySetup requestBodySetup;
	private ResponseUtils responseUtils;
	private static ResponseBodyPojo authResponse;
	private ResponseBodyPojo[] responseBodyPojoArray;
	
	
    public void setAuthResponse(ResponseBodyPojo responseBody) {
    	authResponse = responseBody;
    }

    // Method to retrieve the auth response 
    public ResponseBodyPojo getAuthResponse() {
        return authResponse;
    }
	
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
	
	public ResourcesManager getResourcesManager() {
		return resourcesManager;
	}	
	
	public SpecificationBuilder getSpecificationBuilder() {
		return specificationBuilder == null ? specificationBuilder = new SpecificationBuilder(this) : specificationBuilder;
	}	
	
	public RequestBodySetup getRequestBodySetup() {
		return requestBodySetup == null ? requestBodySetup = new RequestBodySetup(this) : requestBodySetup;
	}
	
	public ResponseUtils getResponseUtils() {
		return responseUtils == null ? responseUtils = new ResponseUtils(this) : responseUtils;
	}

	public void setResponseBodyPojoList(ResponseBodyPojo[] pojoArray) {
	    this.responseBodyPojoArray = pojoArray;
	}

	public ResponseBodyPojo[] getResponseBodyPojoArray() {
	    return responseBodyPojoArray;
	}
	
}
