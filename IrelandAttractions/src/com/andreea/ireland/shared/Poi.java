package com.andreea.ireland.shared;

import java.io.Serializable;
import java.util.HashMap;

public class Poi implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String title;
	private String description;
    private float longitude;
    private float latitude;

    private HashMap<String, String> details;
    
    public Poi() {
    	title = "";
    	description = "";
    	longitude = 0;
    	latitude = 0;
    }

    public Poi(String title, String description, float longitude, float latitude) {
    	this.title = title;
    	this.description = description;
    	this.longitude = longitude;
        this.latitude = latitude;
        
        details = new HashMap<String, String>();
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
    
    public HashMap<String, String> getDetails() {
    	return details;
    }

	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void addDetail(String name, String value) {
		details.put(name, value);
	}
}