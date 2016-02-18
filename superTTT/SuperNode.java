package superTTT;

import java.util.ArrayList;

import basic.Player;
import basic.State;


public class SuperNode {
	
	static Player computerToken;
	int utility;
	SuperState state;

	ArrayList<SuperNode> children;

	

	//constructor
	public SuperNode() {
		children = new ArrayList<SuperNode>();
	}
	
	//Terminal Test
	public boolean terminalTest() {
		
		return state.isTerminal();
		
	}
	
	//Alpha Beta Search Minimax
	public SuperAction alphaBetaSearch(int board, int depth) {
		
		utility = maxValue(Integer.MIN_VALUE, Integer.MAX_VALUE, board, depth);
		
		SuperState s = this.getState();
		
		
		for(SuperNode n : children) {

			if(utility == n.getUtility()) {
				s = n.getState();
				break;
			}
		}
		
		return new SuperAction(board, state.findChange(s, board), state.getCurrentPlayer());
		
	}
	
	public int maxValue(int alpha, int beta, int board, int depth) {

		
		if(terminalTest() == true) {
			
			int x = utility(computerToken);
			
			utility = x;
			
			return x;
		}
		
		if(depth <= 0) {
			
			int x = this.heuristic(computerToken, board);
			utility = x;
			
			return x;
			
		}
		
		utility = Integer.MIN_VALUE;
		
		ArrayList<SuperAction> actions = state.getApplicableActions(board);
		
		
		for(SuperAction a : actions) {
			SuperNode n = result(this.state, a);
			
			children.add(n);

			utility = Math.max(utility, n.minValue(alpha, beta, a.getLoc(), depth -1 ));
			
			if(beta <= alpha) {
				break;
			}
			alpha = Math.max(alpha, utility);
			
		}
		
		return utility;
		
	}
	
	public int minValue(int alpha, int beta, int board, int depth) {
		
		if(terminalTest() == true) {
			
			int x = utility(computerToken);
			
			utility = x;
			
			return x;
		}
		
		if(depth <= 0) {
			
			int x = this.heuristic(computerToken, board);
			
			utility = x;
			
			return x;
			
		}
		
		utility = Integer.MAX_VALUE;
		
		ArrayList<SuperAction> actions = state.getApplicableActions(board);
		
		
		
		for(SuperAction a : actions) {
			SuperNode n = result(this.state, a);
			
			children.add(n);
			
			utility = Math.min(utility, n.maxValue(alpha, beta, a.getLoc(), depth - 1));
			
			if(beta <= alpha) {
				break;
			}
			
			beta = Math.min(beta, utility);
		}
		
		return utility;
		
	}
	
	//HEURISTIC FUNCTION
	public int heuristic(Player p, int board) {
		
		Player opposite = State.oppositePlayer(p);
		
		SuperState state = this.getState(); 
		
		int[] indices = SuperState.getIndices(board);
		
		SimpleBoard b = state.getBoard()[indices[0]][indices[1]];
		
		int result = 8;
		
			//Horizontal Win Conditions
			for(int i = 0; i < 3; i++) {
			
				if(b.getBoard()[i][0] == opposite || b.getBoard()[i][1] == opposite || b.getBoard()[i][2] == opposite) {
					result--;
					//System.err.println("horizontal");
				}
			
			}
			
			
			//Vertical Win Conditions
			for(int j = 0; j < 3; j++) {
				
				if(b.getBoard()[0][j] == opposite || b.getBoard()[1][j] == opposite || b.getBoard()[2][j] == opposite) {
					result--;
					//System.err.println("vertical");

				}
			
			}
			
			//Diagonal Win Conditions
			if(b.getBoard()[0][0] == opposite || b.getBoard()[1][1] == opposite || b.getBoard()[2][2] == opposite) {
				result--;
				//System.err.println("diagonal 1");

			}
			
			if(b.getBoard()[0][2] == opposite || b.getBoard()[1][1] == opposite || b.getBoard()[2][0] == opposite) {
				result--;
				//System.err.println("diagonal 2");

			}
			
			return result;
				
	}
	
	//UTILITY FUNCTION
	public int utility(Player p) {
		
		int result = 100;
		
		String winner = state.whoWon();
		
		if(winner.equalsIgnoreCase(p.toString())){
			result = 9;
		}
		
		if(winner.equalsIgnoreCase(State.oppositePlayer(p).toString())){
			result = -9;
		}
		
		if(winner.equals("tie")) {
			result = 0;
		}
		
		return result;
		
	}
	
	
	
	public static SuperNode result(SuperState s, SuperAction a) {
		
		SuperState state = new SuperState(s.copyBoard(), s.getCurrentPlayer());
		
		state.applyAction(a);
		
		SuperNode n = new SuperNode();
		
		n.setState(state);
		
		return n;
		
		
	}
	


	public static void setComputerToken(Player p) {
		computerToken = p;
	}

	public int getUtility() {
		return utility;
	}

	public void setUtility(int utility) {
		this.utility = utility;
	}

	public SuperState getState() {
		return state;
	}

	public void setState(SuperState state) {
		this.state = state;
	}

	public ArrayList<SuperNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<SuperNode> children) {
		this.children = children;
	}

}
