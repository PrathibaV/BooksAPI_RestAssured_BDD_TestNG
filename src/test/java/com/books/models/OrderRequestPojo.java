package com.books.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderRequestPojo {

    private String bookId;
    private String customerName;

    @JsonIgnore
    private String method;

    @JsonIgnore
    private boolean includeAuth;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {    	
            this.bookId = bookId;
        }    

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isIncludeAuth() {
        return includeAuth;
    }

    public void setIncludeAuth(String includeAuth) {
        this.includeAuth = Boolean.parseBoolean(includeAuth);
    }
}

