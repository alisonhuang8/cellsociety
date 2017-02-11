/*
 * Made by Gideon Pfeffer
 * Used for generating new XML grids
 */

package cellsociety_team06;

import java.util.Random;

public class XMLGenerator {
	static int rows;
	static int cols;
	static Random rand;
	
	public static void main(String[] args){
		rows = 20; // change the number of rows and columns
		cols = 20;
		rand = new Random();
		populate();
	}
	
	private static void populate(){
		for(int i = 0; i < rows; i++){
			System.out.print("<Row>");
			for(int j = 0; j < cols; j++){
				if(rand.nextInt(100) < 30){ //percent chance of this block
					System.out.print("L"); //block ID
				}
				else if(rand.nextInt(100) < 200){ //percent chance of this block
					System.out.print("0"); //block ID
				}
				else{
					System.out.print("0"); //block ID
				}
			}
			System.out.println("</Row>");
		}
	}
}