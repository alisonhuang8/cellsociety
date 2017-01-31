package cellsociety_team06;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CellSociety extends Application {
	public static final int SIZE = 500;
	public static final Paint BACKGROUND = Color.WHITE;
	
	public Stage myStage;
	
	private Scene myHomeScene;
	private Scene myInfoScene;
	private Button myLifeButton;
	private Button myFireButton;
	private Button myWatorButton;
	private Button mySegregationButton;
	private Pane layout;
	private String fileName = "a";
	private String lifeFile = "lifeinfo.txt";
	
	
	public void start(Stage s) throws Exception {
		myStage = s;
		myHomeScene = homeScene(SIZE,SIZE,BACKGROUND);
		myStage.setScene(myHomeScene);
        myStage.setTitle("Home Screen");
        myStage.show();
	}
	
	private Scene homeScene(int width, int height, Paint background) {
		layout = new StackPane();
		
		myLifeButton = createSimButton("Game of Life Simulation", lifeFile);
		myLifeButton.setTranslateY(-SIZE/4);
		
		myFireButton = createSimButton("Fire Simulation", lifeFile);
		myFireButton.setTranslateY(-SIZE/8);
		
		myWatorButton = createSimButton("Predator vs. Prey Simulation", lifeFile);
		
		mySegregationButton = createSimButton("Segregation Simulation", lifeFile);
		mySegregationButton.setTranslateY(SIZE/8);
        
		myHomeScene = new Scene(layout, width, height, background);
		return myHomeScene;
	}
	
	private Scene infoScene(int width, int height, Paint background) {
		Pane root = new Pane();
		Text simulationInfo = new Text(SIZE,SIZE,"");
		Scanner scanner = new Scanner(fileName);
		while (scanner.hasNextLine()) {
			System.out.println(fileName);
			String line = scanner.nextLine();
			System.out.println(line);
			simulationInfo.setText(line);
		}
		root.getChildren().add(simulationInfo);
		return myInfoScene;
	}
	private void setInfoScene() {
		myInfoScene = infoScene(SIZE,SIZE, BACKGROUND);
		myStage.setScene(myInfoScene);
		myStage.setTitle("Info Screen");
		myStage.show();
	}
	
	private Button createSimButton(String label, String nextFile) {
		Button simButton = new Button(label); 
    	simButton.setOnAction(e-> ButtonClicked(e, nextFile));
    	layout.getChildren().add(simButton);
    	return simButton;
	}
	
    public void ButtonClicked(ActionEvent e, String nextFile) {
        if (e.getSource() == myLifeButton || e.getSource() == myFireButton || 
        	e.getSource() == myWatorButton || e.getSource() == mySegregationButton) {
    		fileName = nextFile;
        	setInfoScene();
        }
    }
    
	public static void main (String[] args) {
	        launch(args);
	    }
}
