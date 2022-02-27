// Comp2240 Assignment
// Connect 4 game Play Class
// Author: Jason Disher
// Student No.: c3185333
// Last Modified 20/10/2019

import java.util.LinkedList;


public class Connect4
{
	private Board current_board;
	private Node current_root;
	private boolean isPlayer1;
	private int max_depth;
	private int index;

		//Constructors
	public Connect4()
	{
		current_board = new Board();
		current_root = new Node(current_board, true);
		isPlayer1=true;
		index=0;
	}
	public Connect4(Board b, boolean player1)
	{
		current_board=b;
		current_root = new Node(current_board, player1);
		isPlayer1=player1;
		index=0;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public void setCurrentGame(Board b, boolean player1)
	{
		current_board = new Board(b);
		current_root = new Node(current_board, player1);
		isPlayer1=player1;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public int nextMove(int d, boolean player1)
	{
		max_depth=d;
		
		return MiniMax.minimaxStart(current_root, max_depth, true);
		//return index;
	}
}