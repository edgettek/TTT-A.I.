package gameplay;

import java.util.Scanner;

import superTTT.SuperAction;
import superTTT.SuperNode;
import superTTT.SuperState;
import basic.Action;
import basic.Player;
import basic.State;
import basic.Node;

public class Test {
	
	static int boardLocation;
	
	public static void main(String [] args) {
		
		//BASIC GAME
		if(args[0].equals("basic")) {
			
			Scanner scan2 = new Scanner(System.in);
			
			String s = "";
			
			String winner = "z";
			
			//Keep playing until user quits
			while(true) {
				
				System.err.println("Please enter your desired token (either 'x' or 'o', or 'q' to quit): ");
				
				s = scan2.next();
				
				if(s.equalsIgnoreCase("q")) {
					break;
				}
				
				if(!s.equalsIgnoreCase("o") && !s.equalsIgnoreCase("x")) {
					
					while(!s.equalsIgnoreCase("o") && !s.equalsIgnoreCase("x")) {
						System.err.println("Please enter your desired token (either 'x' or 'o', or 'q' to quit): ");
						s = scan2.next();
					}
					
				}
				
				//start game!
				winner = basicGame(s);
				
				if(winner.equals("tie")) {
					System.err.println("That game ended in a tie!");
				}
				else {
					System.err.println("Yay! Player " + winner + " won that game!");
				}
				
			}
		}
		else {
			//SUPER TIC TAC TOE
			if(args[0].equals("super")) {
							
				Scanner scan2 = new Scanner(System.in);
				
				String s = "";
				
				String winner = "z";
				
				while(true) {
					
					System.err.println("Please enter your desired token (either 'x' or 'o', or 'q' to quit): ");
					
					s = scan2.next();
					
					if(s.equalsIgnoreCase("q")) {
						break;
					}
					
					if(!s.equalsIgnoreCase("o") && !s.equalsIgnoreCase("x")) {
						
						while(!s.equalsIgnoreCase("o") && !s.equalsIgnoreCase("x")) {
							System.err.println("Please enter your desired token (either 'x' or 'o', or 'q' to quit): ");
							s = scan2.next();
						}
						
					}
					
					//PLAY GAME
					winner = superGame(s);
					
					if(winner.equals("tie")) {
						System.err.println("That game ended in a tie!");
					}
					else {
						System.err.println("Yay! Player " + winner + " won that game!");
					}
					
				}
				
			}
		}
		

	}
	
	//ALPHA BETA VERSION - SUPER
	public static String superGame(String user) {
		
		Scanner scan = new Scanner(System.in);
		
		Player starter = State.getPlayer(user);
		
		SuperState initial = new SuperState();
		
		initial.setCurrentPlayer(starter);
		
		String nextSpot;
		
		boardLocation = 0;
		
		if(starter == Player.X) {
			
			SuperNode.setComputerToken(Player.O);
			
			System.err.println("Please enter a two digit integer to place your token:"
					+ "\n(Example: '19' places your token in the bottom right corner of the board located in the top left of the SuperTTT Board)");
			
			nextSpot = scan.next();
			
			int[] move = parseNumbers(nextSpot);
			
			System.err.println("Your move is in box: " + move[0] + " and in location: " + move[1]);
			
			SuperAction a = new SuperAction(move[0], move[1], initial.getCurrentPlayer());
			
			while(initial.valid(a) == false) {
				
				System.err.println("Please enter a VALID location to place your token:");
				
				nextSpot = scan.next();
				
				move = parseNumbers(nextSpot);
				
				a = new SuperAction(move[0], move[1], initial.getCurrentPlayer());
				
			}
		
			boardLocation = move[1];
			
			initial.applyAction(a);
			
			System.err.println(initial);
		}
		else {
			initial.setCurrentPlayer(Player.X);
			SuperNode.setComputerToken(Player.X);
		}
		
		while(initial.isTerminal() == false) {
			
			initial = initial.makeMoveAlphaBeta(boardLocation, 7);

			if(initial.isTerminal() == true) {
				System.err.println(initial);
				break;
			}
			
			System.err.println(initial);
			
			System.err.println("Please enter a location to place your token:");
			
			nextSpot = scan.next();
			
			int[] move = parseNumbers(nextSpot);
			SuperAction a = new SuperAction(move[0], move[1], initial.getCurrentPlayer());
			
			while(initial.valid(a) == false || move[0] != boardLocation) {
				
				System.err.println("Please enter a VALID location to place your token:");
				nextSpot = scan.next();
				
				move = parseNumbers(nextSpot);
				
				a = new SuperAction(move[0], move[1], initial.getCurrentPlayer());
				
			}
			initial.applyAction(a);
			
			if(initial.isTerminal() == true) {
				System.err.println(initial);
				break;
			}
			
			boardLocation = move[1];
			System.err.println(initial);
		}
		
		
		return initial.whoWon();
	}
	
	public static void setBoardLocation(int n) {
		boardLocation = n;
	}

	public static int[] parseNumbers(String s) {
		
		char[] c = s.toCharArray();
		
		int [] temp = new int[c.length];
		
		for(int i = 0; i < c.length; i++) {
			temp[i] = Integer.parseInt(String.valueOf(c[i]));
		}
		
		return temp;
	}
	
		
	//ALPHA BETA VERSION - BASIC
	public static String basicGame(String user) {
		
		Scanner scan = new Scanner(System.in);
		
		Player starter = State.getPlayer(user);
		
		State initial = new State(new Player[3][3], starter);
		
		int nextSpot;
		
		if(starter == Player.X) {
			
			Node.setComputerToken(Player.O);
			
			System.err.println("Please enter a location to place your token:");
			
			nextSpot = scan.nextInt();
			
			Action a = new Action(nextSpot, initial.getCurrentPlayer());
			
			while(initial.valid(a) == false) {
				
				System.err.println("Please enter a VALID location to place your token:");
				
				nextSpot = scan.nextInt();
				
				a = new Action(nextSpot, initial.getCurrentPlayer());
				
			}
		
			initial.applyAction(a);
			
			System.err.println(initial);
		}
		else {
			initial.setCurrentPlayer(Player.X);
			Node.setComputerToken(Player.X);
		}
		
		while(initial.isTerminal() == false) {
			
			initial = initial.makeMoveAlphaBeta(100);
			
			if(initial.isTerminal() == true) {
				System.err.println(initial);
				break;
			}
			
			System.err.println(initial);
			
			System.err.println("Please enter a location to place your token:");
			
			nextSpot = scan.nextInt();
			
			Action a = new Action(nextSpot, initial.getCurrentPlayer());
			
			while(initial.valid(a) == false) {
				
				System.err.println("Please enter a VALID location to place your token:");
				
				nextSpot = scan.nextInt();
				
				a = new Action(nextSpot, initial.getCurrentPlayer());
				
			}
		
			initial.applyAction(a);
			System.err.println(initial);
		}
		
		
		return initial.whoWon();

	}

}
