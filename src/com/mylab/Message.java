package com.mylab;

import java.util.ArrayList;
import java.util.List;
 
/**
 * Model class. Its instances will be populated using SAX parser.
 * */
public class Message
{
    //XML element location
    private List<String> locations = new ArrayList<String>();;
    
    //XML element source
    private String source;
    //XML element translation
    private String translation;
 
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
        return this.locations + ":" + this.source +  ":" + this.translation;
    }
}
