package application;


import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class Map {
	
	//TREE MAP 
	TreeMap< String, Integer> buildings = new TreeMap< String, Integer>();
	
	
	
	// Created Variables	
	int tiles = 64;
	int MAP_SIDE = 8;
	int tm = 0;
	int turnCntr = 0;
	
	
	//create images for map
	Image plain = new Image("plains.jpg");
	ImagePattern plainPtr = new ImagePattern(plain);
	Image forest = new Image("forestPic.jpg");
	ImagePattern forestPtr = new ImagePattern(forest);
	Image hills = new Image("hillsPic.jpg");
	ImagePattern hillsPtr = new ImagePattern(hills);
	Image plainB = new Image("plainBPic.jpg");
	ImagePattern plainPtrB = new ImagePattern(plainB);
	Image forestB = new Image("forestBPic.jpg");
	ImagePattern forestPtrB = new ImagePattern(forestB);
	Image hillsB = new Image("hillsBPic.jpg");
	ImagePattern hillsPtrB = new ImagePattern(hillsB);
	Image castle = new Image("CastlePic.jpg");
	ImagePattern castlePtr = new ImagePattern(castle);

	
	
	public abstract String getInfo();
	
	public abstract void resetInfo();
	
	
//========================================================================================= buildMap
	public void buildMap(GridPane mapGrid, HBox buildButtons, VBox userInfo, HBox ImpInfo) {
		
		
		
		//create Player
		User player = new User();
		
		
		//create UserEvents priority queue
		UserEvents eventQ = new UserEvents();
		eventQ.createQueue(player);
		
		//create user buildings
		Building bld = new Building();
		
		LinkedList<Tile> contTl = new LinkedList<Tile>();
		//array of control tile
		Tile[] uncontTl = new Tile[64];
		//Tile[] cntrTl = new Tile[64];
		
		Random rand = new Random();

//Top Info Bubble
		Text turns = new Text("\t\t  TURN: " + turnCntr + "\t\t");
		Text eText = new Text("");
		eText.autosize();
		TextField srchTle = new TextField();
		srchTle.setPromptText("Enter Tile #:");
		Button srch = new Button();
		srch.setPrefWidth(52);
		srch.setText("Enter");
		
		ImpInfo.getChildren().addAll(turns, eText, srchTle, srch);
		ImpInfo.setPrefSize(530, 30);
		ImpInfo.setMaxWidth(530);
		
		
//First Build Button
		Button build1 = new Button("Build Castle");
		//text
		Text  build1res = new Text("wd: 2 fd: 2 or: 5\n(continuous: -1 fd & or)");
		build1res.setStyle("-fx-padding: 0.333333em 0 0.333333em 0;");
		//vBox
		VBox build1Box = new VBox(build1, build1res);
		build1Box.setPrefSize(100, 100);
		
//second Build Button
		Button build2 = new Button("Build farm");
		build2.setPrefWidth(132);
		//Text
		Text  build2res = new Text("wd: 2 fd: 2 or: 5");
		build2res.setStyle("-fx-padding: 0.333333em 0 0.333333em 0;");
		//VBox
		VBox build2Box = new VBox(build2, build2res);
		build2Box.setPrefSize(132, 100);
		
//Bottom Middle Section
		Text tileType = new Text("Type: Plain");
		Text tileCtrl = new Text("Control: 100");
		Text tileNum = new Text();
		Text errText = new Text();
		errText.setStroke(Color.RED);
		VBox middleBox = new VBox(tileType, tileCtrl, errText);
		middleBox.setPrefSize(200f, 100);
		
		
//User Side Panel
		Text usrInfo = new Text(player.getResStg() + "\n");
		Text scoreNum = new Text("Score: 0");
		userInfo.getChildren().addAll(usrInfo, scoreNum);	
		userInfo.setPrefWidth(80);
//End Turn Button
		Button endTrn = new Button("End Turn");
		endTrn.setStyle("-fx-padding: 0 1em 1em 0;");
		endTrn.setStyle("-fx-font-size:16");
		endTrn.setPrefSize(100, 50);
		
		
		buildButtons.getChildren().addAll(build1Box, middleBox, build2Box, endTrn);
		
		
//create score thread to get score updates
		Thread score = new Thread(()-> {
			while (true) {
				//calculate score
				int scoreB = (player.food) + (player.wood)+ (player.ore) + (player.amtArmies);
				if (scoreB != 0) {
					//if scored not / 0: then create score & add to label
					int scoreI = ((bld.getFarm() * 5) + (bld.getMill() * 5) +(bld.getMine() * 5)+
							(bld.getCastle() * 5)) + scoreB / ((turnCntr + 1) * 5);
					scoreNum.setText("Score: " + scoreI);
				}
				else {
					scoreNum.setText("Score: ??");
				}
				
				try {
					//wait every 2 seconds
					Thread.sleep(2000);
				}
				catch(Exception e) {
					
				}
			}
		});
		
		
		//start score thread
		score.start();
		
//Button Events
//********************************************************************************
		
//Button: Search Bar===================================================================
		srch.setOnAction(event ->{
			String tileInfo;

			try {
				int numbr = Integer.parseInt(srchTle.getText());
				
				int low = 0;
				int size = uncontTl.length;
				tileInfo = binarySearch(uncontTl, low, size, (numbr - 1));
			}
			catch(NumberFormatException e) {
				tileInfo = "Error! Please Enter a Number!\t";
			}

			srchTle.setPromptText("Enter Tile #:");
			eText.setText(tileInfo);
		});
//BUTTON End Turn===================================================================
		endTrn.setOnAction(event ->{
			
			
			//get building end turns
			bld.equals(player);
			bld.endOfTurn();
			player.equals(bld);
			
			//get Event
			String desc = eventQ.getItem(player, contTl, bld);
			eText.setText(desc);
			
			//reset player resource info
			usrInfo.setText(player.getResStg());
			
			//add to turn counter
			turnCntr++;
			turns.setText("\t\t\t\tTURN: " + turnCntr + "\t\t");
			
		});
		
		
		
//BUTTON 1: Build Castle=========================================================
			build1.setOnAction(event ->{
				try{
					//if build is done = print error
					if (uncontTl[Integer.parseInt(tileNum.getText())].getBuilt()) {
						errText.setText("Building Already Here");
					}
					else {
						
						//if player has resources
						if (player.getResInt()[0] >= uncontTl[Integer.parseInt(tileNum.getText())].getRes1()[0]
								&& player.getResInt()[1] >= uncontTl[Integer.parseInt(tileNum.getText())].getRes1()[1]
								&& player.getResInt()[2] >= uncontTl[Integer.parseInt(tileNum.getText())].getRes1()[2]) {
							
							//set objects parts
							contTl.addFirst(uncontTl[Integer.parseInt(tileNum.getText())]);
							contTl.getFirst().setBuilding1(bld);
							//build1.setText(uncontTl[tm].getBuilding1());
							
							//change recourse values
							player.bldResChng(uncontTl[Integer.parseInt(tileNum.getText())].getRes1());
							player.addAArmy();
							usrInfo.setText(player.getResStg());
							tm++;	
						}
						else {
							errText.setText("Not enough Resources!");
						}
					}
				}
				catch(NumberFormatException e) {
					errText.setText("Error! Please select a Tile first.");
				}
				
			});
			
//BUTTON 2: Build Other====================================================================
			build2.setOnAction(event ->{
				try {
					//if built, then give error
					if (uncontTl[Integer.parseInt(tileNum.getText())].getBuilt()) {
						errText.setText("Building already built in location!");
					}
					else {
						
						//if all resources available
						if (player.getResInt()[0] >= uncontTl[Integer.parseInt(tileNum.getText())].getRes2()[0]
								&& player.getResInt()[1] >= uncontTl[Integer.parseInt(tileNum.getText())].getRes2()[1]
								&& player.getResInt()[2] >= uncontTl[Integer.parseInt(tileNum.getText())].getRes2()[2]) {
							
							//add to control list
							contTl.addFirst(uncontTl[Integer.parseInt(tileNum.getText())]);
							contTl.getFirst().setBuilding2(bld);
							//build2.setText(uncontTl[tm].getBuilding2());
							
							
							//change resource values
							player.bldResChng(uncontTl[Integer.parseInt(tileNum.getText())].getRes2());
							usrInfo.setText(player.getResStg());
							tm++;	
						}
						else {
							errText.setText("Not enough Resources!");
						}
					}
				}
				catch(NumberFormatException e) {
					errText.setText("Error! Please select a Tile First.");
				}
				
			});
				

		
		
//*******************************************************************************************
//===========================================================================================
//*******************************************************************************************
		
		
		int matPos = 0;

//============================================================ For loop: Create Tiles
		for (int i = 0; i < MAP_SIDE; i++) {
            for (int j = 0; j < MAP_SIDE; j++) {
                int number = rand.nextInt(3);
                
//---------------------------------------Plains
                if (number == 1) {
                	//create Plain Tile
                	PlainTile newTile = new PlainTile();
                	uncontTl[matPos] = newTile;
                	newTile.setMatNum(matPos);
                	
                	//gridpane
                    GridPane.setRowIndex(newTile.getRectangle(), i);
                    GridPane.setColumnIndex(newTile.getRectangle(), j);
                    mapGrid.getChildren().addAll(newTile.getRectangle());
                    
         //ON CLICK====================================
                    newTile.getRectangle().setOnMouseClicked(event ->{
                    	//get building info
                    	build1.setText("Build: " + newTile.getBuilding1());
                    	build2.setText("Build: " + newTile.getBuilding2());
                    	build2res.setText("wd: " + newTile.getRes2()[0] + " fd: " + newTile.getRes2()[1] + " or: " + 
                            	newTile.getRes2()[2]);
                    	
                    	//set text: Type, Control, matrix number, error
                    	tileType.setText("Type: Plain");
                    	tileCtrl.setText("Control: " + newTile.getControl());
                    	tileNum.setText(""+ newTile.getMatNum());
                    	errText.setText("");
                    	
                    });
                }
//----------------------------------------Forest
                else if (number == 2) {
                	
                	//create Forest Tile
                	ForestTile newTile = new ForestTile();
                	uncontTl[matPos] = newTile;
                	newTile.setMatNum(matPos);
                	
                	//gridpane
                    GridPane.setRowIndex(newTile.getRectangle(), i);
                    GridPane.setColumnIndex(newTile.getRectangle(), j);
                    mapGrid.getChildren().addAll(newTile.getRectangle());
                    
             //ON CLICK=====================================
                    newTile.getRectangle().setOnMouseClicked(event ->{
                    	//get building 2 info
                    	build1.setText("Build: " + newTile.getBuilding1());
                    	build2.setText("Build:" + newTile.getBuilding2());
                    	build2res.setText("wd: " + newTile.getRes2()[0] + " fd: " + newTile.getRes2()[1] + " or: " + 
                            	newTile.getRes2()[2]);
                    	
                    	//set other text: Type, Control, matrix number, error
                    	tileType.setText("Type: Forest");
                    	tileCtrl.setText("Control: " + newTile.getControl());
                    	tileNum.setText(""+ newTile.getMatNum());
                    	errText.setText("");
                    });
                }
                
//-----------------------------------------------------------Hills
                else {
                	//create hills tile
                	HillsTile newTile = new HillsTile();
                	uncontTl[matPos] = newTile;
                	newTile.setMatNum(matPos);
                	
                	//gridpane
                    GridPane.setRowIndex(newTile.getRectangle(), i);
                    GridPane.setColumnIndex(newTile.getRectangle(), j);
                    mapGrid.getChildren().addAll(newTile.getRectangle());
                    
       //ON CLICK====================================
                    newTile.getRectangle().setOnMouseClicked(event ->{
                    	//get building 2 info
                    	build1.setText("Build: " + newTile.getBuilding1());
                    	build2.setText("Build: " + newTile.getBuilding2());
                    	build2res.setText("wd: " + newTile.getRes2()[0] + " fd: " + newTile.getRes2()[1] + " or: " + 
                            	newTile.getRes2()[2]);
                    	//get text: type, Control, matrix number, error
                    	tileType.setText("Type: Hills");
                    	tileCtrl.setText("Control: " + newTile.getControl());
                    	tileNum.setText(""+ newTile.getMatNum());
                    	errText.setText("");
                    });
                }

                matPos++;
            }// second for loop
		}// first for loop
		
		tm++;
		
		}//build map
	
	
	
//Binary Search for Tiles===============================================================
	public String binarySearch(Tile[] arr, int low, int roof, int tile) {
		
		//as long as roof higher or equal to low
		if (roof >= low) { 

			//get mid
			int mid = low + (roof - low) / 2;

			// If the element is present: return Info
			if (arr[mid].getMatNum() == tile) {
				return arr[mid].getInfo();
			}
			
			// If element is smaller than mid: then slice off roof
			if (arr[mid].getMatNum() > tile) {
				return binarySearch(arr, low, mid - 1, tile);
			}
			
			// If element is larger than mid: then slice off low
			else {
				return binarySearch(arr, mid + 1, roof, tile);
			}
		}

		// Element not present
		return "Error! Please enter a valid number (1 - 64)";
	}
	

	}

