package cellsociety_team06;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadXMLFile {

	static Map<String, List<List<Character>>> inputs = new HashMap<String, List<List<Character>>>();
	
	public static void main(String argv[]){
		returnMap();
	}

	public static Map<String, List<List<Character>>> returnMap() {

		try {

			File fXmlFile = new File("/Users/AlisonHuang/Documents/workspace/cellsociety_team06/inputs.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("model");
			
			Element e = (Element) nList.item(0);
			
			System.out.println("Node list: " + e.getAttribute("id"));

			for (int i = 0; i < nList.getLength(); i++) {
				
				List<List<Character>> tempGrid = new ArrayList<List<Character>>();
				
				Element tempModel = (Element) nList.item(i); //one of the 4 models
				
				NodeList rowList = tempModel.getElementsByTagName("Row");
				
				for (int j=0; j<rowList.getLength(); j++){
					String s = rowList.item(j).getTextContent();
					List<Character> row = new ArrayList<Character>();
					
					for (int k=0; k<s.length(); k++){
						row.add(s.charAt(k));
					}
					tempGrid.add(row);
				}
				
				
				
				inputs.put(tempModel.getAttribute("id"), tempGrid);

			}
			
			for (String name : inputs.keySet()){
				List<List<Character>> grid = inputs.get(name);
				
				for (int i=0; i<grid.size(); i++){
					List<Character> row = grid.get(i);
					
					for (int j=0; j<row.size(); j++){
						System.out.print(row.get(j));
					}
					System.out.println();
				}
				
				System.out.println();
				System.out.println();
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return inputs;
	}

}
