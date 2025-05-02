package com.books.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features", 
		glue = {"com.books.stepdefinitions",  "com.books.hooks"},             
	    plugin = {
	        "pretty",                                      
	        "html:target/cucumber-reports.html",            
	    },
	    monochrome = true                                // Clean console output
	    //tags = "@SmokeTest"                                
	)
public class TestRunner extends AbstractTestNGCucumberTests {

}
