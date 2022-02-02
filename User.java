package application;


import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Random;

import java.util.TreeMap;

import com.sun.prism.paint.Color;

import javafx.scene.paint.ImagePattern;

public class User extends Map{
	
	//create initial starting amount
	int wood = 10;
	int food = 10;
	int ore = 10;
	int control = 50;
	
	//inital army amounts
	int amtArmies = 1;
	double armyStr = 1.5;
	
	
//Abstract Methods of Map=========================================	
	//get Info class
	public String getInfo() {
		String statement = "Wood: " + wood + "Food: " + food +"Ore: "+ ore + "Armys: " + amtArmies + "Army Strength: " + armyStr;
		return statement;
	}
	
	//reset User to new User
	public void resetInfo() {
		User reUser = new User();
		this.equals(reUser);
		reUser = null;
	}
	
	//equals all user values to each other
	public void equals(User other) {
		this.amtArmies = other.amtArmies;
		this.armyStr = other.armyStr;
		this.control = other.control;
		this.food = other.food;
		this.wood = other.wood;
		this.ore = other.ore;
		
	}

//Abstract Methods of Map=========================================	
	
	
	
	public User() {
		// Resources: Armies: Buildings: EventChance:
	}
	
	//Add resources: wood, food, ore, army========================================
	public void addAWood() {
		wood++;
	}
	public void addAFood() {
		food++;
	}
	public void addAOre() {
		ore++;
	}
	public void addAArmy() {
		amtArmies++;
		armyStr++;
	}
	
	//remove resources: army=============================
	public void removeAArmy() {
		amtArmies--;
		armyStr--;
	}
	//remove resources
	public void bldResChng(Integer[] minusVal) {
		wood -= minusVal[0];
		food -= minusVal[1];
		ore -= minusVal[2];
	}
	
	
	//get resource integers
	public Integer[] getResInt() {
		
		Integer[] res = {wood, food, ore};
		return res;
	}
	
	//string of values
	public String getResStg() {
		String res = ("\n\nWood:  " + wood + "\n\nFood:  " + food + "\n\nOre:  " + ore + 
				"\n\nArmies: " + amtArmies);
		return res;
	}
	

}

//==================================================================================================
//===================================================================================Building Class
class Building extends User{
	
	
	//building
	public Building() {
		
	}
	
	//End turn function
	public void endOfTurn() {
		
		
		// if contains Castle, end turn that many times
		if (buildings.containsKey("Castle")) {
			for (int i = 0; i < buildings.get("Castle"); i++) {
				castleEndTurn();
			}
		}
		// if contains Farm, end turn that many times
		if (buildings.containsKey("Farm")) {
			for (int i = 0; i < buildings.get("Farm"); i++) {
				farmEndTurn();
			}
		}
		// if contains Mine, end turn that many times
		if (buildings.containsKey("Mine")) {
			for (int i = 0; i < buildings.get("Mine"); i++) {
				mineEndTurn();
			}
		}
		// if contains Mill, end turn that many times
		if (buildings.containsKey("Mill")) {
			for (int i = 0; i < buildings.get("Mill"); i++) {
				millEndTurn();
			}
		}
		
	}
	
//--------------------------------------------------------------------	
//========Castle: push, end turn
	public void pushCastle() {
		//build castle if 
		if (buildings.containsKey("Castle")) {
			buildings.put("Castle", buildings.get("Castle") + 1);
		}
		else {
			buildings.put("Castle", 1);
		}
	}
	// pop castle
	public void popCastle() {
		//remove castle if contains 
		if (buildings.containsKey("Castle")) {
			buildings.put("Castle", buildings.get("Castle") - 1);
			removeAArmy();
		}
		else {
			buildings.put("Castle", 0);
		}
	}
	//get Castle
	public Integer getCastle() {
		if (buildings.containsKey("Castle")) {
			return buildings.get("Castle");		
		}
		else {
			return 0;
		}
	}
	//castle end turn
	public void castleEndTurn() {
		control += 3;
		ore--;
		food--;
	}
	
//=========Farm: push, end turn
	public void pushFarm() {
		if (buildings.containsKey("Farm")) {
			buildings.put("Farm", buildings.get("Farm") + 1);
		}
		else {
			buildings.put("Farm", 1);
		}	
	}
	//pop farm
	public void popfarm() {
		if (buildings.containsKey("Farm")) {
			buildings.put("Farm", buildings.get("Farm") - 1);
		}
		else {
			buildings.put("Farm", 0);
		}
	}	
	//get Farm
	public Integer getFarm() {
		if (buildings.containsKey("Farm")) {
			return buildings.get("Farm");		
		}
		else {
			return 0;
		}
	}
	// end turn
	public void farmEndTurn() {
		control -= 1;
		addAFood();
	}
	
//=========Mine: push, end turn
	public void pushMine() {
		if (buildings.containsKey("Mine")) {
			buildings.put("Mine", buildings.get("Mine") + 1);
		}
		else {
			buildings.put("Mine", 1);
		}
	}
	//pop mine
	public void popMine() {
		if (buildings.containsKey("Mine")) {
			buildings.put("Mine", buildings.get("Mine") - 1);
		}
		else {
			buildings.put("Mine", 0);
		}
		
	}
	//get Mine value
	public Integer getMine() {
		if (buildings.containsKey("Mine")) {
			return buildings.get("Mine");		
		}
		else {
			return 0;
		}
	}
	//end turn
	public void mineEndTurn() {
		control -= 2;
		addAOre();
	}

//=========Mill: push, pop, end turn
	public void pushLumbermill() {
		if (buildings.containsKey("Mill")) {
			buildings.put("Mill", buildings.get("Mill") + 1);
		}
		else {
			buildings.put("Mill", 1);
		}	
	}
	
	//pop Mill
	public void popLumbermill() {
		if (buildings.containsKey("Mill")) {
			buildings.put("Mill", buildings.get("Mill") - 1);
		}
		else {
			buildings.put("Mill", 0);
		}	
	}
	public Integer getMill() {
		if (buildings.containsKey("Mill")) {
			return buildings.get("Mill");		
		}
		else {
			return 0;
		}
	}
	//end Turn
	public void millEndTurn() {
		control -= 1;
		addAWood();
	}
	

	
}
//=====================================================================================================
//****************************************************************************************************
//============================================================================================EVENT CLASS
class Event implements Comparable<Event>{
	private int level;
	private String title;
	private int function;
	
	//event class
	public Event( int levelInp, String TitleInp, int functionInp) {
		//Title
		title = TitleInp;
		//Level
		level = levelInp;
		//Function Type(army attacked, resources taken, area destroyed)
		function = functionInp;
	}
	
	//get functions
	public int getLevel() {
		return level;
	}
	
	public String getTitle() {
		return title;
	}
	public int getFunction() {
		return function;
	}
	
	//equals function
	public boolean equals(Event other) {
		if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Event event = (Event) other;
        return Integer.compare(event.level, level) == 0 &&
                Objects.equals(title, event.title);
	}
	
	//hash code
	@Override
	public int hashCode() {
		return Objects.hash(level, title);
	}
	
	//compare
	@Override
	public int compareTo(Event other) {
		if(this.equals(other)) {
			return 0;
		}
		else if (getLevel() > other.getLevel()) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	//tostring
	public String toString() {
		String printOut = "Title: " + title + "  lvl" + level + " function " + function;
		return (printOut );
	}
}

//===============================================================================================
//************************************************************************************************
//=======================================================================================USER EVENTS
//holds all user Events, and gets when they happen
class UserEvents extends User{
	PriorityQueue<Event> pQueue = new PriorityQueue<Event>();
	Random rand = new Random();
	public void createQueue(User player){
		
		
		
		//create events
		Event ev1 = new Event(1, "Goblins!", 1);
		Event ev2 = new Event(2, "Bandits!", 2);
		Event ev3 = new Event(2, "Orcs!", 1);
		Event ev4 = new Event(1, "Jaguars!", 2);
		Event ev5 = new Event(5, "Skeletons!", 1);
		Event ev6 = new Event(2, "Meteor!", 3);
		Event ev7 = new Event(3, "Cult!", 2);
		Event ev8 = new Event(10, "Giant!", 3);
		
		//add to queue
		pQueue.add(ev1);
		pQueue.add(ev2);
		pQueue.add(ev3);
		pQueue.add(ev4);
		pQueue.add(ev5);
		pQueue.add(ev6);
		pQueue.add(ev7);
		pQueue.add(ev8);
		
	}
	
	//get Item
	public String getItem(User player, LinkedList<Tile> contTl, Building bld) {
		
		try {
			String desc;
			int rnd;
			//if rand = 5
			if (player.control <= 10) {
				rnd = rand.nextInt(2);
			}
			else {
				rnd =rand.nextInt(4);
			}
			if (rnd == 1) {
				
				//get event
				Event currEvent = pQueue.poll();
				
				//army attack
				if (currEvent.getFunction() == 1) {
					desc = armyAttacked(player, currEvent);
				}
				//resource Taken
				else if (currEvent.getFunction() == 2) {
					desc = resourceTaken(player, currEvent);
				}
				//place attacked
				else{
					desc = destroyTile(player, currEvent,contTl, bld);
				}
				
				
			}
			else {
				desc = "NO EVENT THIS TURN";
			}
			
			return desc;
		}catch (NullPointerException e) {
			String desc = "You have completed all Events! Congrats";
			return desc;
		}
		
		
	}
	
//Tile Destroyed
	public String destroyTile(User player, Event cEvent, LinkedList<Tile> contTl, Building bld) {
		//get String
		String nm = contTl.getFirst().getBilNm();
		String desc = "You were hit by a " + cEvent.getTitle() + "!\nAnd a " + nm + " was destroyed!";
		
		bld.equals(player);
		//pop nessacary item from set
		if (nm == "Castle") {
			bld.popCastle();
		}else if (nm == "Farm"){
			bld.popfarm();
		}else if (nm == "Mill") {
			bld.popLumbermill();
		}else if (nm == "Mine") {
			bld.popMine();
		}else {
			desc = "Error";
		}
		
		player.equals(bld);
		
		//reset the Tile of Linked List: remove item
		contTl.getFirst().resetInfo();
		//contTl.getFirst().clr.setFill();
		contTl.removeFirst();
			

			
		return desc;
	}
	
	
	
//army is attacked
	public String armyAttacked(User player, Event cEvent) {
		double attack = player.armyStr - cEvent.getLevel() ;
		String desc;
		//if army str < level: lose an army
		if (attack <= 0) {
			player.removeAArmy();
			desc = "You were Attacked by " + cEvent.getTitle() + "!\nAnd lost a Unit!" ;
		}
		else {
			desc = "You were Attacked by " + cEvent.getTitle() + "!\nBut, you drove them back!" ;
		}
		return desc;
	}
	
//Resources Taken
	public String resourceTaken(User player, Event cEvent) {
		//get random source
		int source =rand.nextInt(3); 
		String desc = "";
		//take level of wood
		if (source == 0) {
			for (int i = 0; i < cEvent.getLevel(); i++) {
				player.wood--;
				desc = "You were ransacked by " + cEvent.getTitle() +
						"!\nAnd they took " + cEvent.getLevel() + " Wood!";
			}
		}
		//take level of food
		else if (source == 1) {
			for (int i = 0; i < cEvent.getLevel(); i++) {
				player.food--;
				desc = "You were ransacked by " + cEvent.getTitle() + 
						"!\nAnd they took " + cEvent.getLevel() + " Food!";
			}
		}
		//take level of ore
		else {
			for (int i = 0; i < cEvent.getLevel(); i++) {
				player.ore--;
				desc = "You were ransacked by " + cEvent.getTitle() +
						"!\nAnd they took " + cEvent.getLevel() + " Ore!";
			}
		}
		
		return desc;
		
	}

	
}



