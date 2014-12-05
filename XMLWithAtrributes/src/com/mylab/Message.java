package com.mylab;

import java.util.ArrayList;
import java.util.List;
 
/**
 * Model class. Its instances will be populated using SAX parser.
 * */
public class Message
{
    //XML attribute id
    private int id;
    
    //XML element location
    private List<String> locations = new ArrayList();;
    
    //XML element source
    private String source;
    //XML element translation
    private String translation;
 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String isource) {
        this.source = isource;
    }
    public String getTranslation() {
        return translation;
    }
    public void setTranslation(String itranslation) {
        this.translation = itranslation;
    }
    public void addLocation(String ilocation) {
    	locations.add(ilocation);
    }
    public List<String> getLocations() {
    	return locations;
    }
    @Override
    public String toString() {
        return this.id + ":" + this.source +  ":" +this.translation;
    }
}
