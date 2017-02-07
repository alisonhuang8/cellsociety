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
		rows = 30; // change the number of rows and columns
		cols = 30;
		rand = new Random();
		populate();
	}
	
	private static void populate(){
		for(int i = 0; i < rows; i++){
			System.out.print("<Row>");
			for(int j = 0; j < cols; j++){
				if(rand.nextInt(100) < 20){ //percent chance of this block
					System.out.print("0"); //block ID
				}
				else if(rand.nextInt(100) < 40){ //percent chance of this block
					System.out.print("A"); //block ID
				}
				else{
					System.out.print("B"); //block ID
				}
			}
			System.out.println("</Row>");
		}
	}
}
