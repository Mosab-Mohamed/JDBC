package DBMS;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XMLParser implements XMLParserInterface {
	ArrayList<Column> LabelNames=new ArrayList<Column>();
	ArrayList<ArrayList<String>> dbTable=new ArrayList<ArrayList<String>>();
	
	private String fileName;
	int NumOfInsert ;
	
	// hady
	int updateCounter = 0;
	int deleteCounter = 0;
	// hady
	
	public XMLParser(String s) {
		fileName = s;
	}
	
	public void Create(String ident,ArrayList<Column> arr)
	{
		File xmlFile = new File(fileName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			Element newElement = doc.createElement(ident);	//
	        doc.appendChild(newElement);	//
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(new File(fileName));
            transformer.transform(source, file);
        } catch (ParserConfigurationException | TransformerException e1) {
            e1.printStackTrace();
        }
	}
	
	public void insertToXML(String ident,ArrayList<Column> arr) {
		NumOfInsert++;
		File xmlFile = new File(fileName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile);
			doc.getDocumentElement().normalize();
		
            addElement(doc, ident,arr);
             
            doc.getDocumentElement().normalize();
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
           
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");    
            DOMSource source = new DOMSource(doc);
            
            StreamResult file = new StreamResult(new File(fileName));
            transformer.transform(source, file);
            //System.out.println(doc.is);
                         
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        }
		
		
	}
	
    private void addElement(Document doc, String ident,ArrayList<Column> arr) {
       
        Node newElement = doc.getFirstChild().appendChild(doc.createElement("row"));
        
        for(Column c : arr){
            Node el = doc.createElement(c.name);
            el.appendChild(doc.createTextNode(c.DataType));
            newElement.appendChild(el);
        }
        doc.getFirstChild().appendChild(newElement);
    }
    
    //public String updateXML(String id, String column, String newValue){     //
    public boolean updateXML(String column,String oldvalue,ArrayList<Column> arr){   
    	boolean match = false ;
    	File xmlFile = new File(fileName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			NodeList n = doc.getElementsByTagName(column);
			for (int i = 0 ; i<n.getLength() ; i++)
			{
				if (n.item(i).getTextContent().equals(oldvalue))
				{
					Node d = n.item(i).getParentNode();
					NodeList e = d.getChildNodes();
					for (Column c : arr)
					{
						for (int j = 0 ; j<e.getLength() ; j++)
						{
							if (e.item(j).getNodeName().equals(c.name))
							{
								match=true;
								e.item(j).setTextContent(c.DataType);
							}
						}
					}
					//break;
				}
			}
		
            //updateElement(doc, id, column, newValue);           // <<<<<<<<<<
			//updateElement(doc, column,oldvalue, newValue);
             
            doc.getDocumentElement().normalize();
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
           
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");    
            DOMSource source = new DOMSource(doc);
            
            StreamResult file = new StreamResult(new File(fileName));
            transformer.transform(source, file);
                         
        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        }
		return match;
    }
    
    // updte inserted null values
    public void updateInsert(ArrayList<Column> arr,String table_name){
    	File xmlFile = new File(fileName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile);
			doc.getDocumentElement().normalize();
			Node n = doc.getFirstChild();          // table name tag
			NodeList list = n.getChildNodes();
			NodeList list2 = list.item(list.getLength()-2).getChildNodes();
			for (Column c : arr)
			{
				for (int j = 1 ; j<list2.getLength() ; j+=2)
				{
					if (list2.item(j).getNodeName().equals(c.name))
					{
						list2.item(j).setTextContent(c.DataType);
						// hady
						updateCounter++;
						// hady
					}
				}
			}
			doc.getDocumentElement().normalize();
	            
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	           
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");    
	        DOMSource source = new DOMSource(doc);
	            
	        StreamResult file = new StreamResult(new File(fileName));
	        transformer.transform(source, file);
		}
		catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        } 
    }
    public String selectElement(ArrayList<String> elementList,String selectedElement,char condition,String value){
		Document doc = getDocument(fileName);
    	//Document doc = getDocument("DB.xml");
		boolean happened = false ;
		String returnedWord = "" ;
		String collector ;
		
		if(elementList.get(0).toString().equals("*")){
			
			if(selectedElement==null ){
				NodeList selectedNode = doc.getDocumentElement().getChildNodes() ;

				for(int i=0 ; i<selectedNode.getLength() ; i++){
					
					NodeList siblingChilds = selectedNode.item(i).getChildNodes();
					collector = "";
					ArrayList<String> in=new ArrayList<String>();
					for(int j=0 ; j<siblingChilds.getLength() ; j++){
						if(siblingChilds.item(j).getTextContent()!=null && siblingChilds.item(j).getTextContent().matches("\n")==false){					
							
							happened = true ;
							Node n=siblingChilds.item(j);
							in.add(n.getTextContent().trim());
						}
					}
					if(in.size()!=0){
						dbTable.add(in);
					}
				}
			}
			
			else{
				
				NodeList matchNodes = doc.getElementsByTagName(selectedElement);
				for(int i=0 ; i<matchNodes.getLength() ; i++){
					
					Node selectedNode = matchNodes.item(i);
					collector = "";
					if(condition == '>' ){
						int intValue1 = Integer.parseInt(value);
						
						if(Integer.parseInt(selectedNode.getTextContent()) > intValue1){
							NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
							ArrayList<String> in=new ArrayList<String>();

							for(int j=0 ; j<siblingChilds.getLength() ; j++){
								
								if(siblingChilds.item(j).getTextContent()!=null && siblingChilds.item(j).getTextContent().matches("\n")==false){
									happened= true;
									Node n=siblingChilds.item(j);
									in.add(n.getTextContent().trim());
								}
							}
							if(in.size()!=0){
								dbTable.add(in);
							}

						}
					}
						
					else if(condition =='<'){
						int intValue2 = Integer.parseInt(value);
						
						if(Integer.parseInt(selectedNode.getTextContent()) < intValue2){
							NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
							ArrayList<String> in=new ArrayList<String>();

							for(int j=0 ; j<siblingChilds.getLength() ; j++){
								
								if(siblingChilds.item(j).getTextContent()!=null && siblingChilds.item(j).getTextContent().matches("\n")==false){
									happened= true;
									Node n=siblingChilds.item(j);
									in.add(n.getTextContent().trim());
								}
							}
							if(in.size()!=0){
								dbTable.add(in);
							}

						}
					}
					
					else if(condition == '='){
						if(selectedNode.getTextContent().equals(value)){
							
							NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
							ArrayList<String> in=new ArrayList<String>();

							for(int j=0 ; j<siblingChilds.getLength() ; j++){
								
								if(siblingChilds.item(j).getTextContent()!=null && siblingChilds.item(j).getTextContent().matches("\n")==false){
									happened= true;
									Node n=siblingChilds.item(j);
									in.add(n.getTextContent().trim());

								}
							}
							if(in.size()!=0){
								dbTable.add(in);
							}

							
						}
					}
				}
			}
			
		}
		
		else{
			
			if(selectedElement==null){
				
				NodeList rows = doc.getDocumentElement().getChildNodes();
				boolean done ;
				int indexOfListElement = 0;
				for(int i=0 ; i<rows.getLength() ; i++){
					
					NodeList currentRow = rows.item(i).getChildNodes();
					indexOfListElement = -1;
					ArrayList<String> in=new ArrayList<String>();
					for(int j=0 ; j<currentRow.getLength() ; j++){
						
						collector = "";
						done = false ;
						

						for(int k=0 ; k<elementList.size() ; k++){
							
							if(currentRow.item(j).getTextContent()!=null && currentRow.item(j).getNodeName().equals(elementList.get(k)) ){
								happened= true;
								Node n=currentRow.item(j);
								in.add(n.getTextContent().trim());
								done = true;
								indexOfListElement ++;
								break;
							}
						}
						
					}
					if(in.size()!=0){
							dbTable.add(in);
						}
				}
				
				
			}
			
			
			
			else{
					
				NodeList matchNodes = doc.getElementsByTagName(selectedElement);
				boolean done ;
				int indexOfListElement = 0;
					
				for(int i=0 ; i<matchNodes.getLength() ; i++){
					Node selectedNode = matchNodes.item(i);
					collector = "";
					
					if(condition == '>' ){
						int intValue1 = Integer.parseInt(value);
						
						if(Integer.parseInt(selectedNode.getTextContent()) > intValue1){
							NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
							indexOfListElement = -1;
							
								ArrayList<String> in=new ArrayList<String>();

							for(int j=0 ; j<siblingChilds.getLength() ; j++){
								collector = "";
								
								done = false ;
								for(int k=0 ; k<elementList.size() ; k++){
									if(siblingChilds.item(j).getNodeName().equals(elementList.get(k))){
										happened= true;
										Node n=siblingChilds.item(j);
										in.add(n.getTextContent().trim());
										done = true;
										indexOfListElement ++;
										break;
									}
								}
								
							}
							if(in.size()!=0){
									dbTable.add(in);
								}
							

						}
					}
						
					else if(condition =='<'){
						int intValue2 = Integer.parseInt(value);
						
						if(Integer.parseInt(selectedNode.getTextContent()) < intValue2){
							NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
							indexOfListElement = -1;
							
								ArrayList<String> in=new ArrayList<String>();

							for(int j=0 ; j<siblingChilds.getLength() ; j++){
								collector = "";
								done = false ;
								for(int k=0 ; k<elementList.size() ; k++){
									if(siblingChilds.item(j).getNodeName().equals(elementList.get(k))){
										happened= true;
										Node n=siblingChilds.item(j);
										Element name=(Element) n;
										in.add(name.getTextContent());
										done = true;
										indexOfListElement ++;
										break;
									}
								}
								
							}
							if(in.size()!=0){
									dbTable.add(in);
								}
						}
					}
					
					else if(condition == '='){
						if(selectedNode.getTextContent().equals(value)){
							
							NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
							indexOfListElement = -1;
							ArrayList<String> in=new ArrayList<String>();

							for(int j=0 ; j<siblingChilds.getLength() ; j++){
								collector = "";
								done = false ;

								for(int k=0 ; k<elementList.size() ; k++){
									if(siblingChilds.item(j).getNodeName().equals(elementList.get(k))){
										happened= true;
										Node n=siblingChilds.item(j);
										in.add(n.getTextContent());
										done = true;
										indexOfListElement ++;
										break;
									}
								}
														
								}if(in.size()!=0){
									dbTable.add(in);
								}
						}
					}
				}
			
		}
			
		}
		
		if(happened){
			return null ;
		}
		else 
			return NOT_MATCH_CRITERIA ;
	}
	
	public String deleteElement(String deletedElement,String elementName,char condition,String value) throws Throwable {
		
		
		//convert the xml file to Dom object to parse date
		Document doc = getDocument(fileName);
		
		NodeList matchNodes = doc.getElementsByTagName(elementName);
		/////
		Node root = doc.getFirstChild();
		/////
		boolean happened = false ;
		
		 //if the root is the deletedElement
		if(deletedElement.equals("*") && elementName==null ){
			//doc.removeChild(doc.getDocumentElement());
			////
			root.setTextContent(null);
			////
			happened=true;
		}
		
		else if(elementName != null){
			for(int i=matchNodes.getLength()-1 ; i>=0 ; i--){   
				Node deletedNode = matchNodes.item(i);
				
				if(condition == '>' ){
					int intValue1 = Integer.parseInt(value);
					
					if(Integer.parseInt(deletedNode.getTextContent()) > intValue1){
						Element parentNode = (Element)deletedNode.getParentNode() ;
						doc.getDocumentElement().removeChild(parentNode);
						happened = true;
						// hady
						deleteCounter++;
						// hady
					}
				}
					
				else if(condition =='<'){
					int intValue2 = Integer.parseInt(value);
					
					if(Integer.parseInt(deletedNode.getTextContent()) < intValue2){
						Node parentNode = deletedNode.getParentNode() ;
						doc.getDocumentElement().removeChild(parentNode);
						happened = true;
						// hady
						deleteCounter++;
						// hady
					}
				}
				
				else if(condition == '='){
					if(deletedNode.getTextContent().equals(value)){
						Node parentNode = deletedNode.getParentNode() ;
						doc.getDocumentElement().removeChild(parentNode);
						happened = true;
						// hady
						deleteCounter++;
						// hady
					}
				}
				
				
				
			}
		}
		/////
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
       
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");    
        DOMSource source = new DOMSource(doc);
        
        StreamResult file = new StreamResult(new File(fileName));
        transformer.transform(source, file);
        /////
		///
        
        ///
		if(happened)
			return Integer.toString(deleteCounter)+" "+Con_Delete;
		else
			return NOT_MATCH_CRITERIA;
		
	}

	private Document getDocument(String s) {
		
		try{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		//factory.setValidating(true);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		return builder.parse(new InputSource(s));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	// hady
	public int count_update() {
	
		int i = updateCounter;
		updateCounter = 0;
		return i;
	}
	
	public int count_delete() {
		
		int i = deleteCounter;
		deleteCounter = 0;
		return i;
	}
	// hady

	
}
