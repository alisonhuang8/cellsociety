/**
 * Made by Gideon Pfeffer
 * Used for generating new XML grids
 */

package cellsociety_team06;

import java.util.Random;

public class XMLGenerator {
	static int rows;
	static int cols;
	static Random rand;
	
	/**
	 * Select the number of rows and cols
	 */
	public static void main(String[] args){
		rows = 60; // change the number of rows and columns
		cols = 60;
		rand = new Random();
		populate();
	}

	/**
	 * populates based on characters given
	 */
	private static void populate(){
		for(int i = 0; i < rows; i++){
			System.out.print("<Row>");
			for(int j = 0; j < cols; j++){
				if(rand.nextInt(100) < 200){ //percent chance of this block
					System.out.print("0"); //block ID
				}
				else if(rand.nextInt(100) < 80){ //percent chance of this block
					System.out.print("B"); //block ID
				}
				else{
					System.out.print("0"); //block ID
				}
			}
			System.out.println("</Row>");
		}
	}
}