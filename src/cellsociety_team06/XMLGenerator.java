/*
 * Made by Gideon Pfeffer
 * Used for generating new XML grids
 */

package cellsociety_team06;

import java.util.Random;

public class XMLGenerator {
	
	public static void main(String[] args){
		int rows = 20; // change the number of rows and columns
		int cols = 20;
		Random rand = new Random();
		populate(rows, cols, rand);
	}
	
	private static void populate(int rows, int cols, Random rand){
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