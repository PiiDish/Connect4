// Comp2240 Assignment
// MiniMax Class
// Author: Jason Disher
// Student No.: c3185333
// Last Modified 14/10/2019



public class MiniMax
{
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public static int minimax(Node node, int depth, boolean maximisingPlayer)
	{
		if(depth==0)
			return evaluate(node);
		if(maximisingPlayer)
		{
			//Set the current evaluation to be as small as possile if we wish to maximise it
			int value=Integer.MIN_VALUE;
			//Geberate childs
			for(int i=0; i<node.childrenSize(); i++)
			{
				//If we can do better, then set the value to the better evaluation
				value=Math.max(value, minimax(new Node(node.getChild(i).getBoard(), !node.isPlayer1()), depth-1, false));
			}
			return value;
		}
		else
		{
			//We are trying to minimise . Set the current evaluation to be as large as possible if we wish to minimise it
			int value=Integer.MAX_VALUE;
			for(int i=0; i<node.childrenSize(); i++)
			{
				value=Math.min(value, minimax(new Node(node.getChild(i).getBoard(), !node.isPlayer1()), depth-1, true));
			}

			return value;
		}
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public static int minimaxStart(Node node, int depth, boolean maximisingPlayer)
	{
		int best_index=0;
		
		if(maximisingPlayer)
		{
			int best_value=Integer.MIN_VALUE;
			for(int i=0; i<node.childrenSize(); i++)
			{
				int temp_value=minimax(new Node(node.getChild(i).getBoard(), !node.isPlayer1()), depth-1, false); //node.getChild(i)
				if(temp_value>best_value && node.getBoard().isLegalMove(i))
				{
					//We are trying to maximise, so if we get a bigger value update.
					best_value=temp_value;
					best_index=i;
				}
			}
		}
		else
		{
				//mimimising player
			int best_value=Integer.MAX_VALUE;

			for(int i=0; i<node.childrenSize(); i++)
			{
				//System.out.println(node.getBoard());
				int temp_value=minimax(new Node(node.getChild(i).getBoard(), !node.isPlayer1()), depth-1, true);
				if(temp_value<best_value && node.getBoard().isLegalMove(i))
				{
					//Here we are trying to minimise so we get a smaller value
					if(temp_value<best_value)
					{
						best_value=temp_value;
						best_index=i;
					}
					
					return best_index;
				}
			}
		}
		
		return best_index;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public static int evaluate(Node node)
	{
		int result = 0;
		int this_player=0, that_player=0;
		Board current_board=node.getBoard();

		if(node.isPlayer1())
		{
			this_player=1;
			that_player=2;
		}
		else
		{
			this_player=2;
			that_player=1;
		}


        if (current_board.isFull())
        {
            if((current_board.getScore(this_player)-current_board.getScore(that_player))>0)
                return Integer.MAX_VALUE;
            else if((current_board.getScore(this_player)-current_board.getScore(that_player)) == 0)
                return 0;
            else
                return Integer.MIN_VALUE;
        }
        else 
        {

            result = (current_board.getScore(this_player)*1000)+(current_board.getThreeCount(this_player)*500)+(current_board.getTwoCount(this_player)*10)+(current_board.getSingleCount(this_player)*10)
                    - (current_board.getScore(that_player)*1000)-(current_board.getThreeCount(that_player)*500)-(current_board.getTwoCount(that_player)*10)-(current_board.getSingleCount(that_player)*10);
            //System.out.println("Eval: " + -result);
        }
        return (-result);
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public static int dummyevaluate(Node node)
	{
		if(node==null && node.getBoard()==null)
			return 0;
		Board b=node.getBoard();
		int this_player=0, that_player=0;
		//System.out.println("player1= "+node.isPlayer1());
		if(node.isPlayer1())
		{
			this_player=1;
			that_player=2;
		}
		else
		{
			this_player=2;
			that_player=1;
		}
		//System.out.println("player1= "+player);
		//System.out.println(b);
		int aiScore=1;
        int score=0;
        int blanks = 0;
        int k=0, moreMoves=0;
        for(int i=0;i<6;++i)
        {
            for(int j=0;j<7;++j)
            {   
                if(b.getBoard()[i][j]==0 || b.getBoard()[i][j]==this_player) //change for approprate player
                	continue; 
                
                if(j<=3)
                { 
                    for(k=1; k<4; ++k)
                    {
                        if(b.getBoard()[i][j+k]==1)
                        	aiScore++;
                        else if(b.getBoard()[i][j+k]==2)
                        {
                        	aiScore=0;
                        	blanks=0;
                        	break;
                        }
                        else 
                        	blanks++;
                    }
                     
                    moreMoves=0; 
                    if(blanks>0) 
                        for(int c=1 ;c<4 ; ++c)
                        {
                            int col=j+c;
                            for(int m=i; m<=5; m++)
                            {
                             	if(b.getBoard()[m][col]==0)
                             		moreMoves++;
                            	else break;
                            } 
                        } 
                    
                    if(moreMoves!=0) 
                    	score+=calculateScore(aiScore, moreMoves);
                    //System.out.println(score);
                    aiScore=1;   
                    blanks = 0;
                } 
                
                if(i>=3)
                {
                    for(k=1; k<4; ++k)
                    {
                        if(b.getBoard()[i-k][j]==this_player)//1)
                        	aiScore++;
                        else if(b.getBoard()[i-k][j]==that_player)//2)
                        {
                        	aiScore=0;
                        	break;
                        } 
                    } 
                    moreMoves = 0; 
                    
                    if(aiScore>0)
                    {
                        int col = j;
                        for(int m=i-k+1; m<=i-1; m++)
                        {
                         	if(b.getBoard()[m][col]==0)
                         		moreMoves++;
                        	else break;
                        }  
                    }
                    if(moreMoves!=0) 
                    	score+=calculateScore(aiScore, moreMoves);
                    //System.out.println(score);
                    aiScore=1;  
                    blanks=0;
                }
                 
                if(j>=3)
                {
                    for(k=1; k<4; ++k)
                    {
                        if(b.getBoard()[i][j-k]==this_player)//1)
                        	aiScore++;
                        else if(b.getBoard()[i][j-k]==that_player)//2)
                        {
                        	aiScore=0; 
                        	blanks=0;
                        	break;
                        }
                        else blanks++;
                    }
                    moreMoves=0;
                    if(blanks>0) 
                        for(int c=1; c<4; ++c)
                        {
                            int col=j-c;
                            for(int m=i; m<=5; m++)
                            {
                             	if(b.getBoard()[m][col]==0)
                             		moreMoves++;
                                else 
                                	break;
                            } 
                        } 
                    
                    if(moreMoves!=0) 
                    	score+=calculateScore(aiScore, moreMoves);
                    //System.out.println(score);
                    aiScore=1; 
                    blanks=0;
                }
                 
                if(j<=3 && i>=3)
                {
                    for(k=1; k<4; ++k)
                    {
                        if(b.getBoard()[i-k][j+k]==this_player)//1)
                        	aiScore++;
                        else if(b.getBoard()[i-k][j+k]==that_player)//2)
                        	{
                        		aiScore=0;
                        		blanks=0;
                        		break;
                        	}
                        else 
                        	blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0)
                    {
                        for(int c=1; c<4; ++c)
                        {
                            int col=j+c, row=i-c;
                            for(int m=row; m<=5; ++m)
                            {
                                if(b.getBoard()[m][col]==0)
                                	moreMoves++;
                                else if(b.getBoard()[m][col]==this_player);//1);
                                else 
                                	break;
                            }
                        } 
                        if(moreMoves!=0) 
                        	score+=calculateScore(aiScore, moreMoves);
                        //System.out.println(score);
                        aiScore=1;
                        blanks=0;
                    }
                }
                 
                if(i>=3 && j>=3)
                {
                    for(k=1; k<4; ++k)
                    {
                        if(b.getBoard()[i-k][j-k]==this_player)//1)
                        	aiScore++;
                        else if(b.getBoard()[i-k][j-k]==that_player)//2)
                        {
                        	aiScore=0;
                        	blanks=0;
                        	break;
                        }
                        else 
                        	blanks++;                        
                    }

                    moreMoves=0;
                    if(blanks>0)
                    {
                        for(int c=1; c<4; ++c)
                        {
                            int col=j-c, row=i-c;
                            for(int m=row; m<=5; ++m)
                            {
                                if(b.getBoard()[m][col]==0)
                                	moreMoves++;
                                else if(b.getBoard()[m][col]==this_player);//1);
                                else 
                                	break;
                            }
                        } 
                        if(moreMoves!=0) 
                        	score+=calculateScore(aiScore, moreMoves);
                        //System.out.println(score);
                        aiScore=1;
                        blanks=0;
                    }
                } 
            }
        }
        //System.out.println(score);
        return score;
    }
    //---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
    private static int calculateScore(int aiScore, int moreMoves)
    {   
        //System.out.println("AI Score: "+aiScore);//+" More Moves: "+moreMoves);
        int moveScore=4-moreMoves;
        if(aiScore==0)
        	return 0;
        else if(aiScore==1)
        	return 1*moveScore;
        else if(aiScore==2)
        	return 50*moveScore;
        else if(aiScore==3)
        	return 1000*moveScore;
        else return 100000000;
    }
}