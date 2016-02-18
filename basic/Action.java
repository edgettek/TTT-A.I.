package basic;

public class Action {
	
	Player currentPlayer;
	int location;
	
	//constructor
	public Action(int loc, Player player) {
		currentPlayer = player;
		location = loc;
	}
	
	public int getLocation() {
		return location;
	}


	public void setLocation(int location) {
		this.location = location;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	
	public String toString(){
		return currentPlayer + " at location " + location;
	}
	

}
