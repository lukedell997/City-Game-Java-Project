package application;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Tile extends Map{

	//universal values: Building Strings, built value, control, matNum, and Rectangle
	String bld1;
	String bld2;
	boolean built = false;
	int ctrl = 0;
	int matNum = 0;
	String bilNm = "";
	
	
	Rectangle clr = new Rectangle(50,50);
	
	//get Info for Tile
	public String getInfo() {
		String statement;

		if (bilNm == "") {
			statement = "Option1: " + getBuilding1() + ": Food- " + getRes1()[0] + 
					", Wood- " + getRes1()[1] + ", Ore- " + getRes1()[2] + ",\n" +
					"Option 2: "+ getBuilding2()+ ": Food- " + getRes2()[0] + ", Wood- "
					+ getRes2()[1] + ", Ore- "+ getRes2()[2];
						
		}
		else {
			statement = "Current Building: " + bilNm + " \nOption1: " + getBuilding1() + ": Food- " + getRes1()[0] + 
					", Wood- " + getRes1()[1] + ", Ore- " + getRes1()[2] + ",\t\n" +
					"Option 2: "+ getBuilding2()+ ": Food- " + getRes2()[0] + ", Wood- "
					+ getRes2()[1] + ", Ore- "+ getRes2()[2];
		}
		return statement;
	}
	
	//reset Tile
	public abstract void resetInfo();
	
	//equal Tiles
	public void equals(Tile other) {
		this.bld1 = other.bld1;
		this.bld2 = other.bld2;
		this.built = other.built;
		this.ctrl = other.ctrl;
		this.clr = other.clr;
		this.bilNm = other.bilNm;
		
	}

//Abstract Methods of Map==================================================
	
	

	
	//create rectangle color
	public void makeRectColor() {
		clr.setFill(Color.PALEGREEN);
        clr.setStroke(Color.BLACK);
	}
	
	//get rectangle
	public Rectangle getRectangle() {
		return clr;
	}
	
	//get building name
	public String getBilNm() {
		return bilNm;
	}
	
	//create Tile
	public Tile() {
		makeRectColor();
	}
	
	//Building 1: Castle Information
	public String getBuilding1() {
		bld1 = "Castle";
		return bld1;
	}
	
	public Integer[] getRes1() {
		Integer[] res1 = {2, 2, 5};
		return res1;
	}
	
	public void setBuilding1(Building bld) {
		clr.setFill(castlePtr);
		//clr.setFill(Color.GRAY);
		built = true;
		bilNm = "Castle";

		bld.pushCastle();

		ctrl = 3;
	}
	
	//Building 2
	public abstract String getBuilding2();
	
	public abstract Integer[] getRes2();
	
	public abstract void setBuilding2(Building bld);
	
	//Control for individual tile
	public int getControl() {
		return ctrl;
	}
	
	public void setControl(int newCtrl) {
		ctrl = newCtrl;
	}
	
	//built
	public boolean getBuilt() {
		return built;
	}

	
	//MatNum (for array tile is in)
	public int getMatNum() {
		return matNum;
		
	}
	
	public void setMatNum(int mat) {
		matNum = mat;
	}
	


}

//==================================================================Plain Subclass
class PlainTile extends Tile{
	
	//reset Tile to original
	public void resetInfo() {
		PlainTile newTl = new PlainTile();
		
		this.equals(newTl);
		clr.setFill(plainPtr);
		newTl = null;
	}
	
	
	//create Rectangle color: image of plain
	public void makeRectColor() {
		clr.setFill(plainPtr);
		//clr.setFill(Color.LIGHTGREEN);
		clr.setStroke(Color.BLACK);
	}
	
	//create plain tile
	public PlainTile() {
		
		makeRectColor();
		
	}
	
	//Building 2
	public String getBuilding2() {
		bld2 = "Farm";
		return bld2;
	}
	
	public void setBuilding2(Building bld) {
		built = true;
		clr.setFill(plainPtrB);
		
		bld.pushFarm();
		
		bilNm = "Farm";
		
		
		ctrl = -1;
	}
	
	//Building 2 Resources
	public Integer[] getRes2() {
		Integer[] res2 = {3, 1, 2};
		return res2;
	}

}

//=========================================================================Forest Subclass
class ForestTile extends Tile{
	
	//reset Tile to original
	public void resetInfo() {
		ForestTile newTl = new ForestTile();
		
		this.equals(newTl);
		clr.setFill(forestPtr);
		newTl = null;
	}
	
	//create Rectangle color: image of forest
	public void makeRectColor() {
		clr.setFill(forestPtr);
		//clr.setFill(Color.DARKGREEN);
		clr.setStroke(Color.BLACK);
	}
	//create Tile
	public ForestTile() {
		makeRectColor();
	}
	
	//Building 2
	public String getBuilding2() {
		bld2 = "LumberMill";
		return bld2;
	}
	public void setBuilding2(Building bld) {
		built = true;
		clr.setFill(forestPtrB);
		
		bilNm = "Mill";

		bld.pushLumbermill();
		ctrl = -1; 
	}
	
	//Building 2 Resources
	public Integer[] getRes2() {
		Integer[] res2 = {1, 3, 2};
		return res2;
	}

}

//==================================================================================Hills Subclass
class HillsTile extends Tile{
	
	//reset Tile to original
	public void resetInfo() {
		HillsTile newTl = new HillsTile();
		
		this.equals(newTl);
		newTl = null;
	}
	
	//create Rectangle color: image of hills
	public void makeRectColor() {
		clr.setFill(hillsPtr);
		//clr.setFill(Color.BEIGE);
		clr.setStroke(Color.BLACK);
	}
	
	//create Tile
	public HillsTile(){
		makeRectColor();
	}
	
	//Building 2
	public String getBuilding2() {
		bld2 = "Mine";
		return bld2;
	}
	public void setBuilding2(Building bld) {
		built = true;
		clr.setFill(hillsPtrB);
		bilNm = "Mine";
		bld.pushMine();
		ctrl = -2;
	}
	//Building 2 resources
	public Integer[] getRes2() {
		Integer[] res2 = {2, 3, 1};
		return res2;
	}
	//setters
	
}
