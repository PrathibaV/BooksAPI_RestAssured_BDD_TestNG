package com.books.runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/resources/features", glue = { "com.books.stepdefinitions",
				"com.books.hooks" }, plugin = { "pretty", "html:target/cucumber-reports.html", }, monochrome = true
				//, tags = "@wip"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
