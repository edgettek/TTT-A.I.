package basic;

import java.util.ArrayList;

public class Node {
	
	static Player computerToken;
	
	int utility;
	State state;
	ArrayList<Node> children;
	
	public Node() {
		children = new ArrayList<Node>();
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
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public ArrayList<Node> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
	
	public boolean terminalTest() {
		
		return state.isTerminal();
		
	}
	
	//UTILITY FUNCTION
	public int utility(Player p) {
		
		int result = 10;
		
		String winner = state.whoWon();
		
		if(winner.equalsIgnoreCase(p.toString())){
			result = 1;
		}
		
		if(winner.equalsIgnoreCase(State.oppositePlayer(p).toString())){
			result = -1;
		}
		
		if(winner.equals("tie")) {
			result = 0;
		}
		
		return result;
		
	}
	
	//result function
	public static Node result(State s, Action a) {
		
		State state = new State(s.copyBoard(), s.getCurrentPlayer());
		
		state.applyAction(a);
		
		Node n = new Node();
		
		n.setState(state);
		
		return n;
	}
	
	//ORIGINAL NON PRUNING MINIMAX - NOT USED ANYMORE
	public void miniMax() {
		
		if(terminalTest() == true) {
			
			utility = this.utility(computerToken);
		
			
		}
		else
		{
			
			if(state.getCurrentPlayer() == computerToken) {
				
				ArrayList<Action> actions = state.getApplicableActions();
				
				int max = Integer.MIN_VALUE;
				
				ArrayList<Node> successors = new ArrayList<Node>();
				
				for(Action a : actions) {
					
					successors.add(result(state,a));
				}
				
				children = successors;
				
				for(Node n : children) {

					n.miniMax();
					
					if(max < n.getUtility()) {
						max = n.getUtility();
					}
					
				}
				
				utility = max;
				
			}
			
			if(state.getCurrentPlayer() != computerToken) {
				
				
				ArrayList<Action> actions = state.getApplicableActions();
				
				int min = Integer.MAX_VALUE;
				
				ArrayList<Node> successors = new ArrayList<Node>();
				
				for(Action a : actions) {
					
					successors.add(result(state,a));

				}
				
				children = successors;
				
				for(Node n : children) {
					
					n.miniMax();

					if(min > n.getUtility()) {
						min = n.getUtility();
					}
					
				}
				
				utility = min;
			}
		}
		
		
	}
	
	//ALPHA BETA SEARCH
	public Action alphaBetaSearch() {
		
		utility = maxValue(Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		State s = this.getState();

		
		
		for(Node n : children) {
			if(utility == n.getUtility()) {
				s = n.getState();
				break;
			}
		}
		
		return new Action(state.findChange(s), state.getCurrentPlayer());
		
	}
	
	public int maxValue(int alpha, int beta) {
		
		if(terminalTest() == true) {
			
			
			int x = utility(computerToken);
			
			utility = x;
			
			return x;
		}
		
		utility = Integer.MIN_VALUE;
		
		ArrayList<Action> actions = state.getApplicableActions();
		
		
		for(Action a : actions) {
			Node n = result(this.state, a);
			
			children.add(n);

			utility = Math.max(utility, n.minValue(alpha, beta));
			
			if(utility >= beta) {
				return utility;
			}
			
			alpha = Math.max(alpha, utility);
		}
		
		return utility;
		
	}
	
	public int minValue(int alpha, int beta) {
		
		if(terminalTest() == true) {

			int x = utility(computerToken);
			
			utility = x;
			
			return x;
		}
		
		utility = Integer.MAX_VALUE;
		
		ArrayList<Action> actions = state.getApplicableActions();
		
		
		
		for(Action a : actions) {
			Node n = result(this.state, a);
			
			children.add(n);
			
			utility = Math.min(utility, n.maxValue(alpha, beta));
			
			if(utility <= alpha) {
				return utility;
			}
			
			beta = Math.min(beta, utility);
		}
		
		return utility;
		
	}
	
	//ALPHA BETA PRUNING MINIMAX!
	public int alphaBetaSearch(int depth, int alpha, int beta, boolean maximizer) {
		
		if(terminalTest() == true) {
			
			utility = this.utility(computerToken);
			return utility;
			
		}
		
		if(maximizer == true) {
			utility = Integer.MIN_VALUE;
			
			ArrayList<Action> actions = state.getApplicableActions();
			
			for(Action a : actions) {
				Node n = result(this.state, a);
				getChildren().add(n);
				
				utility = Math.max(utility, n.alphaBetaSearch(depth-1, alpha, beta, false));
				alpha = Math.max(alpha, utility);
				
				if(beta <= alpha) {
					break;
				}
			}
			
			return utility;
			
		}
		else {
			
			utility = Integer.MAX_VALUE;
			
			ArrayList<Action> actions = state.getApplicableActions();
			
			for(Action a : actions) {
				Node n = result(this.state, a);
				getChildren().add(n);
				
				utility = Math.min(utility, n.alphaBetaSearch(depth-1, alpha, beta, true));
				
				beta = Math.min(beta,  utility);
				
				if(beta <= alpha) {
					break;
				}
			}
			
			return 	utility;		
		}	
	}
		
		
	
	public String toString() {
		return state.toString();
	}
	
	
}
