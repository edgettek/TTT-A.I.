package basic;

import java.util.ArrayList;
import java.util.Random;

public class State {
	
	Player [][] board;
	Player currentPlayer;
	
	public Player[][] getBoard() {
		return board;
	}
	
	public ArrayList<Action> getApplicableActions() {
		
		ArrayList<Action> actions = new ArrayList<Action>();
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if(board[i][j] == null) {
					actions.add(new Action(getNumber(i,j), getCurrentPlayer()));
				}
			}
		}
		
		return actions;
	}
	
	//NOT USED ANYMORE - STOCHASTIC SEARCH
	public Action findMove() {
		
		ArrayList<Action> actions = new ArrayList<Action>();
		
		Action toReturn = new Action(-1, Player.X);
		
		if(isTerminal() == false) {
			
			actions = getApplicableActions();
			
		}
			
		int upperLimit = actions.size();
		
		Random random = new Random();
		
		int pick = random.nextInt(upperLimit);
			
		toReturn = actions.get(pick);
		
		return toReturn;
			
	}
	
	//NOT USED ANYMORE
	public State makeMove() {
		Node n = new Node();
		n.setState(this);
		
		n.miniMax();
		
		int utilityAtRoot = n.getUtility();
		
		System.err.println("Utility at root is: " + utilityAtRoot);
		
		ArrayList<Node> children = n.getChildren();
		
		State s = n.getState();
		
		for(Node node : children) {
			if(node.getUtility() == utilityAtRoot) {
				s = node.getState();
				
				
			}
		}
		
		System.out.println(findChange(s));
		
		return s;
		
	}
	
	//USED TO INITIATE ALPHA BETA
	public State makeMoveAlphaBeta() {
		Node n = new Node();
		
		n.setState(this);
		
		Action a = n.alphaBetaSearch();
		
		State s = n.getState();
		
		System.out.println(a.getLocation());
		
		s.applyAction(a);
		
		return s;
	}
	
	public State makeMoveAlphaBeta(int depth) {
		Node n = new Node();
		
		n.setState(this);
		
		int desiredUtility = n.alphaBetaSearch(depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		
		State s = this;
		
		for(Node node : n.getChildren()) {
			if(node.getUtility() == desiredUtility) {
				s = node.getState();
				
				break;
			}
		}
		
		System.out.println(findChange(s));
		
		return s;
	}
	
	
	
	public int findChange(State s) {
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if(board[i][j] != s.getBoard()[i][j]){
					return getNumber(i, j);
				}
				
			}
		}
		
		return -1;
		
	}
	
	public Player[][] copyBoard() {
		
		Player[][] newBoard = new Player[3][3];
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				newBoard[i][j] = board[i][j];
				
			}
		}
		
		return newBoard;
	}
	
	public void applyAction(Action a) {
		
		int x = a.getLocation();

		
		int [] array = getLocation(x);
		
		board[array[0]][array[1]] = a.getCurrentPlayer();
		
		if(isTerminal() == false) {
		
			if(currentPlayer == Player.X) {
				currentPlayer = Player.O;
			}
			else {
				currentPlayer = Player.X;
			}
		}
		
	}
	
	public boolean valid(Action a) {
		
		boolean check = false;
		
		int [] dimens = getLocation(a.getLocation());
		
		if(board[dimens[0]][dimens[1]] != null) {
			check = false;
		}
		else {
			check = true;
		}
		
		return check;
		
	}
	
	public static Player getPlayer(String s) {
		if(s.equalsIgnoreCase("x")) {
			return Player.X;
		}
		else {
			return Player.O;
		}	
	}
	
	public static int getNumber(int i, int j) {
		
		int [][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		
		return a[i][j];
		
	}
	
	public static int[] getLocation(int x) {
		
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
	
	public static String playerToString(Player p) {
		
		String result = "";
		
		if(p == Player.O) {
			result = "o";
		}
		
		if(p == Player.X) {
			result = "x";
		}
		
		return result;
		
	}
	
	public static Player oppositePlayer(Player p) {
		if(p == Player.X) {
			return Player.O;
		}
		else {
			return Player.X;
		}
	}
	
	public String whoWon() {
		
		Player player = Player.X;
		
		
		for(int z = 0; z < 2; z++) {
		
			//Horizontal Win Conditions
			for(int i = 0; i < 3; i++) {
			
				if(board[i][0] == player && board[i][1] == player && board[i][2] == player) {
					return playerToString(player);
					
				}
			
			}
			
			
			//Vertical Win Conditions
			for(int j = 0; j < 3; j++) {
				
				if(board[0][j] == player && board[1][j] == player && board[2][j] == player) {
					return playerToString(player);
				}
			
			}
			
			//Diagonal Win Conditions
			if(board[0][0] == player && board[1][1] == player && board[2][2] == player) {
				return playerToString(player);
			}
			
			if(board[0][2] == player && board[1][1] == player && board[2][0] == player) {
				return playerToString(player);
			}
			
			player = Player.O;
		}
		
		boolean full = true;

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if(board[i][j] == null) {
					full = false;
				}
				
			}
		}
		
		if(full == true) {
			return "tie";
		}
		
		
		return "fail";
		
	}
	
	

	public void setBoard(Player[][] board) {
		this.board = board;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public State(Player [][] nBoard, Player nPlayer) {
		
		board = nBoard;
		currentPlayer = nPlayer;
		
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
