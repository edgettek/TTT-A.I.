package superTTT;

import basic.Player;

public class SimpleBoard {
	
	Player [][] board;
	
	
	public SimpleBoard(int n) {
		board = new Player[n][n];
	}
	
	public boolean isTerminal() {
		
		Player player = Player.X;	
					
			for(int z = 0; z < 2; z++) {
			
				//Horizontal Win Conditions
				for(int i = 0; i < 3; i++) {
				
					if(board[i][0] == player && board[i][1] == player && board[i][2] == player) {
						return true;
					}
				
				}
				
				
				//Vertical Win Conditions
				for(int j = 0; j < 3; j++) {
					
					if(board[0][j] == player && board[1][j] == player && board[2][j] == player) {
						return true;
					}
				
				}
				
				//Diagonal Win Conditions
				if(board[0][0] == player && board[1][1] == player && board[2][2] == player) {
					return true;
				}
				
				if(board[0][2] == player && board[1][1] == player && board[2][0] == player) {
					return true;
				}
				
				player = Player.O;
			}
		
		boolean fullBoard = true;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if(board[i][j] == null) {
					fullBoard = false;
				}
				
			}
		}
	
		return fullBoard;
		
	}

	public Player[][] getBoard() {
		return board;
	}

	public void setBoard(Player[][] board) {
		this.board = board;
	}
	
	public String rowToString(int n) {
		String temp = "";
		
		for(int j = 0; j < 3; j++) {
			if(board[n][j] != null) {
				temp += "|" + board[n][j];
			}
			else {
				temp += "| ";
			}
		}
		
		temp += "|";
		
		return temp;
		
	}
	
	public String toString() {
		
		String toPrint = "";
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if(board[i][j] == null) {
					toPrint +="| ";
				}
				else {
					toPrint +="|" + board[i][j];
				}
			}
			toPrint += "|" + "\n-------\n";
			
		}
		
		return toPrint;
	}

}
