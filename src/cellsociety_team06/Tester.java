package cellsociety_team06;

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import subUnits.Blank;
import subUnits.Burning;

public class Tester extends Application{

	private Stage window;
	private Unit[][] grid = new Unit[10][10];
	Random rand = new Random();
	private int width = 500;
	private int height = 500;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setResizable(false);
		window.setScene(getScene());
		window.show();
	}
	
	private Scene getScene(){
		Group root = new Group();
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				int r = rand.nextInt(5);
				if(r < 2){
					grid[i][j] = new Blank((width * i)/10, (height * j)/10, width/10, height/10);
				}
				else{
					grid[i][j] = new Burning((width * i)/10, (height * j)/10, width/10, height/10);
				}
				root.getChildren().add(grid[i][j]);
			}
		}
		Scene scene = new Scene(root, width, height);
		return scene;
	}

}
