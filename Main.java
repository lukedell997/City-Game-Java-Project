package application;
	

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.GroupLayout.Alignment;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {
	
	Scene mainScene, scene, infoScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("The City Game");


//================================================================================================
		//Main Screen
			BorderPane mainSRoot = new BorderPane();
			
		//title
			Label title= new Label("The City\n   Game");
			title.setFont(new Font("Times New Roman", 46f));
			title.setPrefSize(500, 100);
			title.setMinSize(500, 100);
			
		//play button
			Button playbutton= new Button("Play Game");
			playbutton.setPrefSize(200, 100);
			playbutton.setStyle("-fx-font-size:32");
			
			//button action
			playbutton.setOnAction(e -> primaryStage.setScene(scene));   
			
		//info button
			Button infoButton = new Button("  Info  ");
			infoButton.setPrefSize(200, 100);
			infoButton.setStyle("-fx-font-size:32");
			
			//button action
			infoButton.setOnAction(e -> primaryStage.setScene(infoScene));  
			
		//exit button
			Button exitButton = new Button ("Exit Game");
			exitButton.setPrefSize(200, 100);
			exitButton.setStyle("-fx-font-size:32");
			
			//button action
			exitButton.setOnAction(e -> Platform.exit());
			
		//new box
			VBox menu = new VBox();
			
			VBox blank = new VBox();
			Text blanktxt = new Text("\t\t\t\t\t");
			blank.getChildren().add(blanktxt);
			
			VBox blank2 = new VBox();
			Text blanktxt2 = new Text("\t\t\t\t\t");
			blank2.getChildren().add(blanktxt2);
			
			menu.getChildren().addAll(title, playbutton, infoButton, exitButton);
			
		//set scene
			mainSRoot.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
			mainSRoot.setCenter(menu);
			mainSRoot.setLeft(blank);
			mainSRoot.setRight(blank2);
			
			//create scene
			mainScene= new Scene(mainSRoot, 530, 550);

//=============================================================================================
//==================================================================================================			
			//Info Screen
			BorderPane infoRoot = new BorderPane();
			
		//Labels
			Label c1text= new Label("The City Game");
			c1text.setFont(new Font("Times New Roman", 22f));
			c1text.setPrefHeight(100);
			
			
			Label l1text= new Label("A game by:\n\t Luke Dell");
			l1text.setFont(new Font("Times New Roman", 14f));
			l1text.setPrefHeight(100);
			
			
			
			Label l2text= new Label("Objective:");
			l2text.setFont(new Font("Times New Roman", 22f));
			l2text.setPrefHeight(200);
			
			Label c2text= new Label("The objective is to have fun. \nThe game is a easy-going game that allows you to " +
					"\ncreate a city and expand.\n Develop a city and protect from events that happen.");
			c2text.setFont(new Font("Times New Roman", 16f));
			c2text.setPrefHeight(200);
			
			Label l3text= new Label("Tiles:");
			l3text.setFont(new Font("Times New Roman", 22f));
			l3text.setPrefHeight(300);
			
			Label c3text= new Label("City: give an Army. consumes 1 food and ore a turn\n provides "+
			"+3 control. cost(2 wood, 2 food, 5 ore)" +"\nFarm: give 1 food. provides -1 control. \ncost(3 wood, 1 food, 2 ore)" + 
					"\nMine: gives 1 ore. provides -2 control. \ncost(2 wood, 3 food, 1 ore)" + "\nMill: gives 1 wood. provides -1 control. \ncost(1 wood, 3 food, 2 ore)");
			
			c3text.setFont(new Font("Times New Roman", 16f));
			c3text.setPrefHeight(300);
			
			
		//play button
			Button playbuttonIn= new Button("Play Game");
			playbuttonIn.setPrefSize(100, 50);
			playbuttonIn.setStyle("-fx-font-size:16");
			
			//button action
			playbuttonIn.setOnAction(e -> primaryStage.setScene(scene));   
			

			
		//exit button
			Button exitButtonIn = new Button ("Exit Game");
			exitButtonIn.setPrefSize(100, 50);
			exitButtonIn.setStyle("-fx-font-size:16");
			
			
			//button action
			exitButtonIn.setOnAction(e -> Platform.exit());
			
			//filler label
			Label space = new Label("          ");
			
		//new box
			VBox middle = new VBox();
			middle.getChildren().addAll(c1text, c2text, c3text);
			middle.setPadding(new Insets(20));
			
			VBox left = new VBox();
			left.getChildren().addAll(l1text, l2text, l3text);
			left.setPadding(new Insets (5, 5, 0, 0));
			
			
			
			HBox bottom = new HBox();
			bottom.getChildren().addAll(exitButtonIn, space, playbuttonIn);
			
			
		//set scene
			infoRoot.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
			infoRoot.setCenter(middle);
			infoRoot.setLeft(left);
			
			infoRoot.setBottom(bottom);
			
			//create scene
			infoScene= new Scene(infoRoot, 530, 550);

			
			
//**************************Map Scene*********************************************************
//=============================================================================================
			//root border
			BorderPane root = new BorderPane();

			//map grid
			GridPane mapGrid = new GridPane();
			
			//lower buttons for build
			HBox buildButtons = new HBox();
			buildButtons.setPrefSize(600, 80);
			
			//side resources
			VBox userInfo = new VBox();
			


			
			//top information: event and turns
			HBox importantInfo = new HBox();
			
		//menu
			//menu option
			Menu menu1 = new Menu("Options");
			
			//back to main
			MenuItem backtoMain = new MenuItem("back to Home");
			backtoMain.setOnAction(event -> primaryStage.setScene(mainScene));
			
			//exit program
			MenuItem exitMIT = new MenuItem("exit");
			exitMIT.setOnAction(event -> Platform.exit());

			//add to menu
			menu1.getItems().add(backtoMain);
			menu1.getItems().add(exitMIT);
			
			//menu bar
			MenuBar menBr = new MenuBar();
			menBr.getMenus().add(menu1);
			
			//create top border for both items
			BorderPane topOp = new BorderPane();
			topOp.setTop(menBr);
			topOp.setBottom(importantInfo);
			
			//create newgame
			Map newgame = new User();
			newgame.buildMap(mapGrid, buildButtons, userInfo, importantInfo);
			
			
			root.setBackground(new Background(new BackgroundFill(Color.LEMONCHIFFON, null, null)));
			//root.setTop(menBr);
			root.setTop(topOp);
			root.setBottom(buildButtons);
			root.setLeft(mapGrid);
			root.setRight(userInfo);
			
			scene = new Scene(root,530,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(mainScene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}


