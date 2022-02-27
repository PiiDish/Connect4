// Comp2240 Assignment
// Interface Class
// Author: Jason Disher
// Student No.: c3185333
// Last Modified 3/9/2019

import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;


public class Interface
{
	private String engine_name;
	private Node current_state;
	private Connect4 game;
	//private Board current_board;
	private boolean p1;

		// Constructors
	public Interface(Connect4 g)
	{
		engine_name="PiiDish-C3185333";//+" C3185333_MiniMax";
		current_state=null;
		game=g;
		p1=true;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public void run()
	{
		Stack<String> previous_moves = new Stack<String>();
		
		try
		{
			Scanner input = new Scanner(System.in);
			

			String[] str;
			boolean input_parsed=false;
			int best_move;

			int[] count = new int[7];
			String moves="";

			while(!input_parsed)
			{
				str=input.nextLine().split(" ");

				switch(str[0])
				{
					case "name":
						System.out.println(engine_name);
						break;

					case "position":
						Board current_board=null;
						if(str[1].equalsIgnoreCase("startpos"))
						{
							if(str.length>2)
							{
								moves=str[2];
								previous_moves.push(moves);

								if(moves.length()<1)
									current_board = new Board();
								else
									current_board = new Board(moves);

								p1=true;
								if(moves.length()%2==0)
									p1=false;

								if(current_board==null||game==null)
								while(p1);//current_board==null);
								game.setCurrentGame(current_board, p1);
								//game = new Connect4(current_board, p1);
								
								//while(game==null){}
								/*for(int i=0; i<str[2].length(); i++)//stringToCharArray.length; i++)
								{
									int index=Character.getNumericValue(str[2].charAt(i));
									//int index=str[2].charAt(i)-48; //Integer.parseInt(stringToCharArray[i].toString());
									
									count[index]++;
								}*/
							}
						}
						else
							System.out.println("Ahhhhhhhhh");

						break;

					case "isready":
						//if(current_board!=null)
						System.out.println("readyok");
						break;

					case "go":
						int depth=7;
						int m=game.nextMove(depth, p1);
				
						System.out.println("Info depth is: "+depth);
						System.out.println("bestmove "+m);//tmp.length()%2+" 0");
						break;


					case "perfet":
						System.out.println("7");
						break;

					case "quit":
						System.out.println("Quiting...");
						input_parsed=true;
						break;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public static void main(String[] args)
	{
		Connect4 g = new Connect4();
		Interface machine = new Interface(g);
		machine.run();
		
	}
}