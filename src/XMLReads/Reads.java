package XMLReads;

import cellsociety_team06.ReadXMLFile;

public class Reads {
	private ReadXMLFile files;
	private String type;
	
	public Reads(String type){
		this.type = type;
		files = new ReadXMLFile();
	}
	
	public Character get(int i, int j){
		return files.returnMap().get(type).get(i).get(j);
	}
	
	public int height(){
		return files.returnMap().get(type).size();
	}
	
	public int width(){
		return files.returnMap().get(type).get(0).size();
	}

}
