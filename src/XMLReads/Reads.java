package XMLReads;

import cellsociety_team06.ReadXMLFile;

public class Reads {
	private ReadXMLFile files;
	private String type;
	private int size;

	public Reads(String type, int s){
		this.type = type;
		files = new ReadXMLFile();
		size = s;
	}

	public Character get(int i, int j){
		return files.returnMap(size).get(type).get(i).get(j);
	}

	public int size(){
		return size;
	}
	
	public int height(){
		return files.returnMap(size).get(type).size();
	}

	public int width(){
		return files.returnMap(size).get(type).get(0).size();
	}

}
