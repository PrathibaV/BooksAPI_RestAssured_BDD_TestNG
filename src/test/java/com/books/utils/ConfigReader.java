package com.books.utils;

import java.util.ResourceBundle;

public class ConfigReader {

	public static ResourceBundle getProperties() {
		ResourceBundle properties = ResourceBundle.getBundle("configuration");
		return properties;
	}
}
