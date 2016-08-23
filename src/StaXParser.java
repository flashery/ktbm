
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import model.Config;

public class StaXParser {
  static final String DATABASE = "database";
  static final String NAME = "name";
  static final String USERNAME = "username";
  static final String PASSWORD = "password";
  static final String CONNECTION_URL = "connectionurl";
  
  @SuppressWarnings({ "unchecked", "null" })
  
  public List<Config> readConfig(String configFile) {
	  
    List<Config> items = new ArrayList<Config>();
    
    try {
    	
      // First, create a new XMLInputFactory
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      
      // Setup a new eventReader
      InputStream in = new FileInputStream(configFile);
      
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
      
      // read the XML document
      Config item = null;

      while (eventReader.hasNext()) {
        XMLEvent event = eventReader.nextEvent();

        if (event.isStartElement()) {
        	
          StartElement startElement = event.asStartElement();
          
          // If we have an item element, we create a new item
          if (startElement.getName().getLocalPart() == (DATABASE)) {
        	  
            item = new Config();
            
            // We read the attributes from this tag and add the date
            // attribute to our object
            Iterator<Attribute> attributes = startElement
                .getAttributes();
            
            while (attributes.hasNext()) {
            	
              Attribute attribute = attributes.next();
              
              if (attribute.getName().toString().equals(NAME)) {
            	  
                item.setDatabaseName(attribute.getValue());
                
              }

            }
          }

          if (event.isStartElement()) {
            if (event.asStartElement().getName().getLocalPart()
                .equals(USERNAME)) {
              event = eventReader.nextEvent();
              item.setUsername(event.asCharacters().getData());
              continue;
            }
          }
          if (event.asStartElement().getName().getLocalPart()
              .equals(PASSWORD)) {
            event = eventReader.nextEvent();
            item.setPassword(event.asCharacters().getData());
            continue;
          }
          if (event.asStartElement().getName().getLocalPart()
               .equals(CONNECTION_URL)) {
              event = eventReader.nextEvent();
              item.setConnectionUrl(event.asCharacters().getData());
              continue;
          }

        }
        // If we reach the end of an item element, we add it to the list
        if (event.isEndElement()) {
        	
          EndElement endElement = event.asEndElement();
          
          if (endElement.getName().getLocalPart() == (DATABASE)) {
        	  
        	  items.add(item);
        	  
          }
        }

      }
    } catch (FileNotFoundException e) {
    	
      e.printStackTrace();
      
    } catch (XMLStreamException e) {
    	
      e.printStackTrace();
      
    }
	return items;
  }

} 