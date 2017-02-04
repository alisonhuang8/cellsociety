import cellsociety_team06.ReadXMLFile;

public class XMLTest {
	private static ReadXMLFile files = new ReadXMLFile();
	
	public static void main(String[] args){
		if(files.returnMap().get("Segregation") == null) System.out.println("was null");
		System.out.println(files.returnMap().get("Segregation").size());
		System.out.println(files.returnMap().size());
		
		System.out.println(files.returnMap().get("Segregation").get(0).get(0).getClass().getName());
		
		if(files.returnMap().get("Segregation").get(0).get(0) == 'A') System.out.println("here");

	}
}
