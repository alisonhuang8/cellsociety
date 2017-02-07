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

	public static Map<String, List<List<Character>>> returnMap(int size) {

		try {

			File fXmlFile = new File("inputs3.xml");
			
			if (size == 1){
				fXmlFile = new File("inputs1.xml");
			} else if (size == 2){
				fXmlFile = new File("inputs2.xml");
			} else {
				fXmlFile = new File("inputs3.xml");
			}

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("model");

			Element e = (Element) nList.item(0);

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

			return inputs;
		} catch (Exception e) {
			e.printStackTrace();
		}



		return inputs;
	}

}