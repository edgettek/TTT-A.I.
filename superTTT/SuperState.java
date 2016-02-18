package superTTT;

import gameplay.Test;

import java.util.ArrayList;
import java.util.Random;
import basic.Player;
import basic.State;

public class SuperState {
	
	SimpleBoard[][] board;
	Player currentPlayer;
	
	//CONSTRUCTOR
	public SuperState() {
		
		SimpleBoard[][] newBoard = new SimpleBoard[3][3];
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				SimpleBoard s = new SimpleBoard(3);
				
				newBoard[i][j] = s;
			}
		}
		
		board = newBoard;
		
	}
	
	//USED TO INITIATE ALPHA BETA MINIMAX
	public SuperState makeMoveAlphaBeta(int board, int depth) {
		
		if(board == 0) {
			
			Random random = new Random();
			
			int newBoard = random.nextInt(9);
			newBoard++;
			
			int loc = random.nextInt(9);
			loc++;
			
			SuperAction a = new SuperAction(newBoard, loc, getCurrentPlayer());
			
			System.out.println(String.valueOf(newBoard) + String.valueOf(loc));
			
			Test.setBoardLocation(a.getLoc());
			
			return SuperNode.result(this, a).getState();
		}
		
		SuperNode n = new SuperNode();
		
		n.setState(this);
		
		SuperAction a = n.alphaBetaSearch(board, depth);
		
		
		Test.setBoardLocation(a.getLoc());
		
		SuperState s = n.getState();
		
		System.out.println(String.valueOf(a.getBoard()) +String.valueOf( a.getLoc()));
		
		s.applyAction(a);
		
		return s;
	}
	
	
	//CHECK IF A MOVE IS VALID
	public boolean valid(SuperAction a) {
		
		boolean check = false;
		
		int[] indices = getIndices(a.getBoard());
		
		SimpleBoard s = board[indices[0]][indices[1]];
		
		int [] dimens = getIndices(a.getLoc());
		
		if(s.getBoard()[dimens[0]][dimens[1]] != null) {
			check = false;
		}
		else {
			check = true;
		}
		
		return check;
		
	}
	
	//APPLY A MOVE TO THIS STATE
	public void applyAction(SuperAction a) {
		
		int[] indices = getIndices(a.getBoard());
		
		SimpleBoard s = board[indices[0]][indices[1]];
		
		int [] dimens = getIndices(a.getLoc());
		
		s.getBoard()[dimens[0]][dimens[1]] = a.getCurrentPlayer();
		
		if(isTerminal() == false) {
		
			if(currentPlayer == Player.X) {
				currentPlayer = Player.O;
			}
			else {
				currentPlayer = Player.X;
			}
		}
		
	}
	
	//GET LIST OF POSSIBLE ACTIONS FOR A GIVEN BOARD OF SIMPLETTT INSIDE SUPER BOARD
	public ArrayList<SuperAction> getApplicableActions(int boardNum) {
		
		ArrayList<SuperAction> actions = new ArrayList<SuperAction>();
		
		if(boardNum == 0) {
			
			//ignore this... sorry!
			//taken care of elsewhere
			
		}
		else {
		
			int[] indices = getIndices(boardNum);
			
			SimpleBoard currentBoard = board[indices[0]][indices[1]];
			
			Player[][] boardP = currentBoard.getBoard();
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					
					if(boardP[i][j] == null) {
						actions.add(new SuperAction(boardNum, getNumber(i,j), getCurrentPlayer()));
					}
				}
			}
		}
		
		return actions;
		
	}
	
	//FIND OUT WHO WON THE GAME
	public String whoWon() {
		
		Player player = Player.X;

		
		SimpleBoard b;
		
		
		for(int k = 0; k < 3; k++) {
			for(int l = 0; l < 3; l++) {
				
				
				
				b = this.getBoard()[k][l];
				
				for(int z = 0; z < 2; z++) {
				
					//Horizontal Win Conditions
					for(int i = 0; i < 3; i++) {
					
						if(b.getBoard()[i][0] == player && b.getBoard()[i][1] == player && b.getBoard()[i][2] == player) {
							return State.playerToString(player);
							
						}
					
					}
					
					
					//Vertical Win Conditions
					for(int j = 0; j < 3; j++) {
						
						if(b.getBoard()[0][j] == player && b.getBoard()[1][j] == player && b.getBoard()[2][j] == player) {
							return State.playerToString(player);
						}
					
					}
					
					//Diagonal Win Conditions
					if(b.getBoard()[0][0] == player && b.getBoard()[1][1] == player && b.getBoard()[2][2] == player) {
						return State.playerToString(player);
					}
					
					if(b.getBoard()[0][2] == player && b.getBoard()[1][1] == player && b.getBoard()[2][0] == player) {
						return State.playerToString(player);
					}
					
					player = Player.O;
				}
				
				boolean full = true;
		
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						
						if(b.getBoard()[i][j] == null) {
							full = false;
						}
						
					}
				}
				
				if(full == true) {
					return "tie";
				}
		
			}
		}
		
		
		return "fail";
		
	}
	
	public Player[][] getPlayerBoard(int boardNum) {
		int[] indices = getIndices(boardNum);
		
		SimpleBoard currentBoard = board[indices[0]][indices[1]];
		
		return currentBoard.getBoard();
	}
	
	//NOT USED ANYMORE 
	public SuperAction findMove(int board) {
	
		ArrayList<SuperAction> actions = new ArrayList<SuperAction>();
		
		SuperAction toReturn = new SuperAction(0, 0, Player.X);
		
		if(isTerminal() == false) {
			
			actions = getApplicableActions(board);
			
		}
			
		int upperLimit = actions.size();
		
		Random random = new Random();
		
		int pick = random.nextInt(upperLimit);
			
		toReturn = actions.get(pick);
		
		Test.setBoardLocation(toReturn.getLoc());
		
		return toReturn;
	}
	
	//TERMINAL TEST!
	public boolean isTerminal() {
		boolean gameOver = false;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				gameOver = gameOver || board[i][j].isTerminal();
				
			}
		}
		
		return gameOver;
		
	}
	
	public SimpleBoard[][] copyBoard() {
		
		SimpleBoard[][] newBoard = new SimpleBoard[3][3];
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				SimpleBoard s = new SimpleBoard(3);
				
				SimpleBoard orig = getBoard()[i][j];
				
				for(int k = 0; k < 3; k++) {
					for(int l = 0; l < 3; l++) {
						
						s.getBoard()[k][l] = orig.getBoard()[k][l];
						
						
						
					}
				}
				
				newBoard[i][j] = s;
				
			}
		}
		
		return newBoard;
	
	}
	
	public int findChange(SuperState s, int board) {
		
		int[] indices = getIndices(board);
		
		System.err.println("Indices: " + indices[0] + ", " + indices[1]);
		
		SimpleBoard simpleBoard = this.board[indices[0]][indices[1]];
		
		SimpleBoard orig = s.getBoard()[indices[0]][indices[1]];
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if(simpleBoard.getBoard()[i][j] != orig.getBoard()[i][j]){
					return getNumber(i, j);
				}
				
			}
		}
		
		return -1;
		
	}
	
	
	public SuperState(SimpleBoard[][] board, Player currentPlayer) {
		this.board = board;
		this.currentPlayer = currentPlayer;
	}
	public SimpleBoard[][] getBoard() {
		return board;
	}
	public void setBoard(SimpleBoard[][] board) {
		this.board = board;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public static int getNumber(int i, int j) {
		
		int [][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		
		return a[i][j];
		
	}
	
	public static int[] getIndices(int x) {
		
		int [][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		
		int c = -1;
		int d = -1;

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(a[i][j] == x) {
					c = i;
					d = j;
					break;
				}		
			}
		}
		
		int[] temp = {c,d};
		
		return temp;
		
	}
	
	
	
	public String toString() {
		
		String toPrint = "";
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
				
					toPrint += board[i][k].rowToString(j) + "\t\t";
				}
				toPrint += "\n";
			}
			toPrint += "\n";
			
		}
		
		return toPrint;
	}
	

}
