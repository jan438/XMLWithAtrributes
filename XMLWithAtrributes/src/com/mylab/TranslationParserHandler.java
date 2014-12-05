package com.mylab;
 
import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class TranslationParserHandler extends DefaultHandler {
    
	//This is the list which shall be populated while parsing the XML.
    private ArrayList<Message> messageList = new ArrayList<Message>();
 
    //As we read any XML element we will push that in this stack
    private Stack elementStack = new Stack();
 
    //As we complete one user block in XML, we will push the User instance in userList
    private Stack objectStack = new Stack();
 
    public void startDocument() throws SAXException
    {
        //System.out.println("start of the document   : ");
    }
 
    public void endDocument() throws SAXException
    {
        //System.out.println("end of the document document     : ");
    }
 
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        //Push it in element stack
        this.elementStack.push(qName);
 
        //If this is start of 'user' element then prepare a new User instance and push it in object stack
        if ("message".equals(qName))
        {
            //New Message instance
            Message message = new Message();
 
            //Set all required attributes in any XML element here itself
            if (attributes != null && attributes.getLength() == 1)
            {
                message.setId(Integer.parseInt(attributes.getValue(0)));
            }
            this.objectStack.push(message);
        }
        else
        if ("location".equals(qName))
        {
            //Set all required attributes in any XML element here itself
            if (attributes != null && attributes.getLength() > 0) {
            	Message message = (Message) this.objectStack.peek();
            	String location = attributes.getValue(0);
            	message.addLocation(location);
            }
        }

    }
 
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        //Remove last added  element
        this.elementStack.pop();
 
        //User instance has been constructed so pop it from object stack and push in userList
        if ("message".equals(qName))
        {
            Message object = (Message) this.objectStack.pop();
            this.messageList.add(object);
        }
    }

    /**
     * This will be called everytime parser encounter a value node
     * */
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        String value = new String(ch, start, length).trim();
 
        if (value.length() == 0)
        {
            return; // ignore white space
        }
        if ("source".equals(currentElement()))
        {
            Message message = (Message) this.objectStack.peek();
            message.setSource(value);
        }
        else if ("translation".equals(currentElement()))
        {
            Message message = (Message) this.objectStack.peek();
            message.setTranslation(value);
        }
    }
 
    /**
     * Utility method for getting the current element in processing
     * */
    private String currentElement()
    {
        return (String) this.elementStack.peek();
    }
 
    //Accessor for messageList object
    public ArrayList Messages()
    {
        return messageList;
    }
}