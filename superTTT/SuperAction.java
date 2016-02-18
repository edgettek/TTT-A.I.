package superTTT;

import basic.Player;

public class SuperAction {
	
	int board, loc;
	Player currentPlayer;
	
	
	public SuperAction(int board, int loc, Player currentPlayer) {
		this.board = board;
		this.loc = loc;
		this.currentPlayer = currentPlayer;
	}
	public int getBoard() {
		return board;
	}
	public void setBoard(int board) {
		this.board = board;
	}
	public int getLoc() {
		return loc;
	}
	public void setLoc(int loc) {
		this.loc = loc;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public String toString(){
		return currentPlayer + " at location " + loc + " in board " + board;
	}

}
