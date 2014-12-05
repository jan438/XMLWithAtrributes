package com.mylab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
 
public class TranslationXmlParser {	
	
	public static void translate(String file, ArrayList<String> sources, Map<String,String> translations) throws IOException {
		if (file != null && sources != null) {
			BufferedWriter bufferedWriter = null;
		    FileReader fr;
		    try {		        
		    	fr = new FileReader(new File("/home/keanu/GCompris-qt/src/" + file));
		        bufferedWriter = new BufferedWriter(new FileWriter("/home/keanu/GCompris-qt/src/" + file + "2"));
		        BufferedReader br = new BufferedReader(fr);
		        String line = br.readLine();
		        while (line != null) {
		        	Iterator<String> it = sources.iterator();
		        	while (it.hasNext()) {
		        		String src = (String) it.next();
		        		String tra = translations.get(src);
		        		if (tra != null) line = line.replace("qsTr(\"" + src + "\")", "\"" + tra + "\"");
		        	}
		            bufferedWriter.write(line);
		            bufferedWriter.newLine();
		            line = br.readLine();
		        }
		        br.close();

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        //Close the BufferedWriter
		        try {
		            if (bufferedWriter != null) {
		                bufferedWriter.flush();
		                bufferedWriter.close();
		            }
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		    }
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		FileInputStream fis = null;
		ArrayList<Message> messages = null;
		Map<String, ArrayList<String>> filetranslations = new HashMap<String, ArrayList<String>>();
		Map<String, String> translations = new HashMap<String,String>();
				
		try {
			
			fis = new FileInputStream("gcompris_nl.xml");
    
			//Create a empty link of users initially
			messages = new ArrayList<Message>();
        
            //Create default handler instance
            TranslationParserHandler handler = new TranslationParserHandler();
 
            //Create parser from factory
            XMLReader parser = XMLReaderFactory.createXMLReader();
 
            //Register handler with parser
            parser.setContentHandler(handler);
 
            //Create an input source from the XML input stream
            InputSource source = new InputSource(fis);
 
            //parse the document
            parser.parse(source);
 
            //populate the parsed users list in above created empty list; You can return from here also.
            messages = handler.Messages();
 
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        	fis.close();
        }
		
		System.out.println("messages " + messages.size());
		
		String location;
		List<String> locations = new ArrayList<String>();
		ArrayList<String> sources = new ArrayList<String>();
		Iterator<Message> itm;
		Iterator<String> itl;
		String source;
		String translation;
		itm = messages.iterator();
		
		while (itm.hasNext()) {
			Message message = (Message) itm.next();
			locations = message.getLocations();
			source = message.getSource();
			translation = message.getTranslation();
			if (locations != null && source != null && translation != null) {
				itl = locations.iterator();
				while (itl.hasNext()) {
					String qmlfile = itl.next();
					if (qmlfile != null) {
						sources = filetranslations.get(qmlfile);
						if (sources == null) sources = new ArrayList<String>();
						sources.add(source);
						filetranslations.put(qmlfile, sources);
					}
					translations.put(source, translation);
				}
			}
		}
		System.out.println("==========================================");
		Set<String>	files = filetranslations.keySet();
		Iterator<String> itf = files.iterator();
		while (itf.hasNext()) { 
			String file = (String) itf.next();
			sources = filetranslations.get(file);
			translate(file, sources, translations);
		}	
		System.out.println("==========================================");
	}
}
  