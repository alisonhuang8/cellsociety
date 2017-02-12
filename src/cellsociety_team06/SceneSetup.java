/**
 * Written by Faith Rodriguez
 * Creates and implements all GUI features of the setup scenes
 */

package cellsociety_team06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneSetup {
	
	private int width;
	private int height;
	private ResourceBundle resources;
	private Stage s;
	private String[] shapes = {"Square", "Triangle", "Hexagon"};
	private String[] boundaryTypes = {"Normal","Toroidal"};
	private int tempChoice;
	private int unitShape;
	
	public SceneSetup(int gridWidth, int gridHeight, ResourceBundle resourcesPackage, Stage stage) {
		width = gridWidth;
		height = gridHeight;
		resources = resourcesPackage;
		s = stage;
	}
	public Button createSimButton(Pane root, String label) {
		FlowPane buttons = (FlowPane) root;
		buttons.setVgap(50);
		buttons.setLayoutX(width/3);
		buttons.setLayoutY(width/5);
		buttons.setBackground(null);
		Button simButton = new Button();
		simButton.setText(label);
		buttons.getChildren().add(simButton);
		return simButton;
	}
	
	public Text readFile(Pane infoRoot, String fileName) {
		Text simulationInfo = new Text(width / 20, height / 20, "");
		simulationInfo.setWrappingWidth(width * 9 / 10);
		try (Scanner scanner = new Scanner(new File(fileName))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine() + "\n";
				simulationInfo.setText(simulationInfo.getText() + line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return simulationInfo;
	}
	
	public Button buttonPlay(Pane infoRoot, Scene nextScene) {
		Button btn_play = new Button("Let's Play");
		btn_play.setLayoutX(width / 2);
		btn_play.setLayoutY(4 * height / 5);
		infoRoot.getChildren().add(btn_play);
		btn_play.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg) {
				s.setScene(nextScene);
				s.show();
				s.setTitle(resources.getString("Shape"));
			}
		});
		return btn_play;
	}
	
	public Button buttonBack(Pane infoRoot, Scene nextScene) {
		Button btn_back = new Button(resources.getString("BackToHome"));
		btn_back.setLayoutX(3 * width / 10);
		btn_back.setLayoutY(4 * height / 5);
		infoRoot.getChildren().add(btn_back);	
		btn_back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg) {
				s.setScene(nextScene);
			}
		});
		return btn_back;
	}
	
	public int getChoice() {
		return tempChoice;
	}
	
	public int createButtons(Group root, Scene nextScene, String[] options) {
		VBox shapeButtons = createBox(root, width, height);
		shapeButtons.setTranslateX(width/5 - 10);
		for (String option:options) {
			Button btn = new Button(option);
			shapeButtons.getChildren().add(btn);
			btn.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg) {
					tempChoice = Arrays.asList(options).indexOf(option) + 1;
					System.out.println(tempChoice);
					s.setScene(nextScene);
				}
			});
		}
		return tempChoice;
	}
	

//	public String[] createNeighborButtons(Group root, Scene nextScene, Model model) {
//		String[] ret = new String[4];
//		
//		
//		return ret;
//	}
//	
//	public void createBoundaryBtn(Group root, Scene nextScene) {
//		
//	}
	
	public Label createLabel(Group root, ResourceBundle r, int width, int height, String message) {
		Label lb_size = new Label(r.getString(message));
		lb_size.setTranslateX(width/9*2);
		lb_size.setTranslateY(height/7);
		root.getChildren().add(lb_size);
		return lb_size;
	}
	
	public Button createSizeButton(Group sizeSceneRoot, String label){
		Button sizeButton = new Button(label);
		sizeSceneRoot.getChildren().add(sizeButton);
		return sizeButton;
	}
	
	public VBox createBox(Group root, int width, int height) {
		VBox sizes = new VBox();
		root.getChildren().add(sizes);
		sizes.setAlignment(Pos.CENTER);
		sizes.setTranslateX(width/7*5/2);
		sizes.setTranslateY(height/11*2);
		return sizes;
	}
}
