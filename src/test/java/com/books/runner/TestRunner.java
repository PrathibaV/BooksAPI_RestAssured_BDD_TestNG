package com.books.runner;

import java.util.Arrays;
import java.util.Comparator;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleWrapper;

@CucumberOptions(features = "src/test/resources/features", glue = { "com.books.stepdefinitions",
				"com.books.hooks" }, plugin = { "pretty", "html:target/cucumber-reports.html", }, monochrome = true // Clean
																													// console
																													// output
// tags = "@SmokeTest"
)
public class TestRunner extends AbstractTestNGCucumberTests {

	

}
