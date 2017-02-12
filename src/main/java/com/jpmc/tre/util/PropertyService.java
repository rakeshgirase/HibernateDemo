package com.jpmc.tre.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jpmc.tre.exception.FatalException;

public class PropertyService {
	
	private Properties properties;
	
	private PropertyService(){
		properties = new Properties();
		String fileName = "tre.default.properties";
		try {
			InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
			properties.load(inStream);
		} catch (IOException e) {
			throw new FatalException(String.format("Couldnt read properties file %s", fileName));
		}		
	}
	
	private static class SingletonPropertyService{
        private static final PropertyService INSTANCE = new PropertyService();
    }
	
	public static PropertyService getInstance(){
		return SingletonPropertyService.INSTANCE;
	}
	
	public String getProperty(String key){
		return properties.getProperty(key);
	}

}
