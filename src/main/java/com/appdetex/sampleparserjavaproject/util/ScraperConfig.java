package com.appdetex.sampleparserjavaproject.util;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Christopher
 */
public class ScraperConfig {
 
    private Properties properties;
    
    public ScraperConfig() throws Exception {
        
        this.properties = new Properties();
        
        try {
            this.properties.load(getClass().getResourceAsStream("/googleplay.properties"));
        } catch (IOException e) {
            throw new Exception("Could not load google play properties!!!! Can not continue...");            
        }        
    }
      
    public String getValue(String key) {
        return this.properties.getProperty(key);
    }
}
