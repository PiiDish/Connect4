// Comp2240 Assignment
// Node Class
// Author: Jason Disher
// Student No.: c3185333
// Last Modified 14/10/2019

import java.util.LinkedList;


public class Node
{
	private boolean player1;
	private Board game_board;
	private Node[] children;	


	public Node()
	{
		player1=true;
		game_board=null;
		/*children = new Node[7];
		for(int i=0; i<children.length; i++)
			children[i]=null;*/
	}
	public Node(Node n)
	{
		player1=true;
		game_board = new Board(n.getBoard());
		children=n.getChildren();
		/*children = new Node[7];
		for(int i=0; i<children.length; i++)
			children[i]=null;*/
	}
	public Node(Board b)
	{
		player1=true;
		game_board = new Board(b);

		/*int avail=0;
		for(int i=0; i<7; i++)
			if(b.isLegalMove(i))
				avail++;

		children = new Node[avail];
		for(int i=0; i<children.length; i++)
			children[i]=null;*/
	}
	public Node(Board b, boolean player1)
	{
		this.player1=player1;
		game_board=b;

		//children=null;

		LinkedList<Node> child_list = new LinkedList<Node>();
		int avail=0;
		for(int i=0; i<7; i++)
		{
			Board next_move_board = new Board(b);
			if(next_move_board.isLegalMove(i))
				{
					next_move_board.move(i, player1);
					Node n = new Node(next_move_board);
					child_list.add(n);
					//System.out.print(n.getBoard()+"\n");
				}
		}
		this.setChildren(child_list);
	}
	public void setgame_board(Board b)
	{
		game_board=b;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	private void setChildren(LinkedList<Node> child_nodes)
	{
		children = new Node[child_nodes.size()];
		for(int i=0; i<children.length; i++)
			children[i]=child_nodes.remove();
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public Node getChild(int i)
	{
		return children[i];
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public Board getBoard()
	{
		return game_board;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public Node[] getChildren()
	{
		return children;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public void makeChildren(int size)
	{
		children = new Node[size];
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public int childrenSize()
	{
		if(children==null)
			return 0;
		else
			return children.length;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public boolean isPlayer1()
	{
		return player1;
	}
}