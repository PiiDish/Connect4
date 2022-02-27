// Comp2240 Assignment
// Node Class
// Author: Jason Disher
// Student No.: c3185333
// Last Modified 14/10/2019



public class Board //implements Cloneable
{
	private final int ROWS=6;
	private final int COLS=7;
	private final int player1=1;
	private final int player2=2;
	private int[][] board;
	private int[] used;
	private int piece_count;


		//Constructors
	public Board()
	{
		board = new int[ROWS][COLS];
		used = new int[]{0,0,0,0,0,0,0};

		//for(int i: board.length)
		for(int i=0; i<ROWS; i++)
		{
			for(int j=0; j<COLS; j++)
				board[i][j]=0;
		}
		piece_count=0;
	}
	public Board(int[][] b)
	{
		board = new int[ROWS][COLS];
		used = new int[]{0,0,0,0,0,0,0};

		for(int i=0; i<ROWS; i++)
		{
			for(int j=0; j<COLS; j++)
				board[i][j]=b[i][j];
		}
		piece_count=0;
	}
	public Board(Board b)
	{
		board = new int[ROWS][COLS];
		used = new int[]{0,0,0,0,0,0,0};
		
		for(int i=0; i<ROWS; i++)
		{
			for(int j=0; j<COLS; j++)
				board[i][j]=b.getBoard()[i][j];
		}
		for(int i=0; i<b.getUsedCols().length; i++)
		{
			used[i]=b.getUsedCols()[i];
		}
		piece_count=b.getPieceCount();
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public Board(String moves)
	{
		board = new int[ROWS][COLS];
		used = new int[]{0,0,0,0,0,0,0};
		int index=0;
		piece_count=0;
		//player=0;
		

		for(int i=0; i<ROWS; i++)
		{
			for(int j=0; j<COLS; j++)
				board[i][j]=0;
		}

		for(int i=0; i<moves.length(); i++)
		{
			index=Character.getNumericValue(moves.charAt(i));
			board[used[index]][index]=player1;
			used[index]++;
			i++;
			piece_count++;
			if(i!=moves.length())
			{
				index=Character.getNumericValue(moves.charAt(i));
				board[used[index]][index]=player2;
				used[index]++;
				piece_count++;
			}
		}
	}
	public int getPieceCount()
	{
		return piece_count;
	}
	public boolean isFull()
	{
		return piece_count>41;
	}
	public int[][] getBoard()
	{
		return board;
	}
	public void move(int col, boolean p)
	{
		int plr=0;

		if(p)
			plr=player1;
		else
			plr=player2;

		board[used[col]][col]=plr;
		used[col]++;
	}
	public boolean isLegalMove(int col)
	{
		if (col >=0 && col < COLS)
		{
		    if (used[col]<=ROWS-1)
				return true;
			else
				return false;
		}

		return false;
    }
    public int getPossMoves()
    {
    	int count=0;
    	for(int i=0; i<COLS; i++)
    		if(isLegalMove(i))
    			count++;

    	return count;
    }
    public int[] getUsedCols()
    {
    	return used;
    }
    public int getPlayer()
    {
    	return player1;
    }
	public String toString()
	{
		String out="";
		for(int i=ROWS-1; i>=0; i--)
		{
			for(int j=0; j<COLS; j++)
				out+=board[i][j]+" ";

			out+="\n";
		}

		return out;
	}
	/**
     * this method returns the score for the player given as an argument.
     * it checks horizontally, vertically, and each direction diagonally.
     * currently, it uses for loops, but i'm sure that it can be made
     * more efficient.
     *
     * The player whose score is being requested.  valid
     *               values are 1 or 2
     */
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
    public int getScore(int player)
    {
        //reset the scores
        int player_score = 0;

        //check horizontally
        for (int i=0; i<6; i++)
        {
            for (int j=0; j<4; j++)
            {
                if ((board[i][j] == player) &&
                        (board[i][j+1] == player) &&
                        	(board[i][j+2] == player) &&
                        (board[i][j+3] == player))
                {
                    player_score++;
                }
            }
        } // end horizontal

        //check vertically
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<7; j++)
            {
                if ((board[i][j] == player) &&
                        (board[i+ 1][j] == player) &&
                        	(board[i+2][j] == player) &&
                        (board[i+3][j] == player))
                {
                    player_score++;
                }
            }
        } // end verticle

        //check diagonally - backs lash ->	\
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<4; j++)
            {
                if ((board[i][j] == player) &&
                        (board[i+1][j+1] == player) &&
                        	(board[i+2][j+2] == player) &&
                        (board[i+3][j+3] == player))
                {
                    player_score++;
                }
            }
        }

        //check diagonally - forward slash -> /
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<4; j++)
            {
                if ((board[i+3][j] == player) &&
                        (board[i+2][j+1] == player) &&
                        	(board[i+1][j+2] == player) &&
                        (board[i][j+3] == player))
                {
                    player_score++;
                }
            }
        }

        return player_score;
    }

    /**
     * this method returns the possible triplets which result in a win for the player given as an argument.
     * Eg: 1110 will be considered, as 1111 is possible.
     * 1112 will not be considered, as 1111 can never be formed.
     * it checks horizontally, vertically, and each direction diagonally.
     *
     * The player whose score is being requested.  valid
     *               values are 1 or 2
     */
    //---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
    public int getThreeCount(int player)
    {
        //reset the scores
        int player_score = 0;

        //check horizontally
        for(int i=0; i<6; i++)
        {
            for(int j=0; j<4; j++)
            {
                if(((board[i][j] == player) &&
                        (board[i][j+1] == player) &&
                        (board[i][j+2] == player) &&
                        (board[i][j+3] == 0)) ||
                        ((board[i][j] == 0) &&
                                (board[i][j+1] == player) &&
                                (board[i][j+2] == player) &&
                                (board[i][j+3] == player)) ||
                        ((board[i][j] == player) &&
                                (board[i][j+1] == 0) &&
                                (board[i][j+2] == player) &&
                                (board[i][j+3] == player)) ||
                        ((board[i][j] == player) &&
                                (board[i][j+1] == player) &&
                                (board[i][j+2] == 0) &&
                                (board[i][j+3] == player)))
                {
                    player_score++;
                }
            }
        }

        //check vertically
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<7; j++)
            {
                if((board[i][j] == player) &&
                        (board[i+1][j] == player) &&
                       		(board[i+2][j] == player) &&
                        (board[i+3][j] == 0))
                {
                    player_score++;
                }
            }
        }

        //check diagonally - backs lash ->	\
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<4; j++)
            {
                if((board[i][j] == player) &&
                        (board[i+1][j+1] == player) &&
                        (board[i+2][j+2] == player) &&
                        (board[i+3][j+3] == 0) ||
                        ((board[i][j] == player) &&
                                (board[i+1][j+1] == 0) &&
                                (board[i+2][j+2] == player) &&
                                (board[i+3][j+3] == player)) ||
                        ((board[i][j] == 0) &&
                                (board[i+1][j+1] == player) &&
                                (board[i+2][j+2] == player) &&
                                (board[i+3][j+3] == player)) ||
                        ((board[i][j] == player) &&
                                (board[i+1][j+1] == player) &&
                                (board[i+2][j+2] == 0) &&
                                (board[i+3][j+3] == player))) 
                {
                    player_score++;
                }
            }
        }

        //check diagonally - forward slash -> /
        for(int i=0; i<3; i++)
        {
            for(int j=0; j<4; j++)
            {
                if(((board[i+3][j] == 0) &&
                        (board[i+2][j+1] == player) &&
                        (board[i+1][j+2] == player) &&
                        (board[i][j +3] == player)) ||
                        ((board[i+3][j] == player) &&
                                (board[i+2][j+1] == player) &&
                                (board[i+1][j+2] == player) &&
                                (board[i][j+3] == 0)) ||
                        ((board[i+3][j] == player) &&
                                (board[i+2][j+1] == 0) &&
                                (board[i+1][j+2] == player) &&
                                (board[i][j+3] == player)) ||
                        ((board[i+3][j] == player) &&
                                (board[i+2][j+1] == player) &&
                                (board[i+1][j+2] == 0) &&
                                (board[i][j+3] == player)))
                {
                    player_score++;
                }
            }
        }

        return player_score;
    }

    /**
     * this method returns the possible doubles which result in a win for the player given as an argument.
     * Eg: 1010 will be counted.
     * 1210 will not be counted, as win is not possible.
     * it checks horizontally, vertically, and each direction diagonally.
     *
     * The player whose score is being requested.  valid
     *               values are 1 or 2
     */
    //---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
    public int getTwoCount(int player) {
        //reset the scores
        int player_score = 0;

        //check horizontally
        for (int i = 0; i < 6; i++) {
            for (int j=0; j<4; j++)
            {
                int sum = 0;
                if(board[i][j] == player)
                {
                    sum++;
                }
                else if(board[i][j] == 0)
                {

                }
                else
                {
                    sum--;
                }
                if(board[i][j+1] == player)
                {
                    sum++;
                }
                else if(board[i][j+1] == 0)
                {

                }
                else
                {
                    sum--;
                }
                if(board[i][j+2] == player)
                {
                    sum++;
                }
                else if(board[i][j+2] == 0)
                {

                }
                else
                {
                    sum--;
                }
                if(board[i][j+3] == player)
                {
                    sum++;
                } 
                else if(board[i][j+3] == 0)
                {

                }
                else
                {
                    sum--;
                }

                if(sum == 2)
                {
                    player_score++;
                }
            }
        }

        //check vertically
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if ((board[i][j] == player) &&
                        (board[i+1][j] == player) &&
                        	(board[i+2][j] == 0) &&
                        (board[i+3][j] == 0))
                {
                    player_score++;
                }
            }
        }

        //check diagonally - backs lash ->	\
        for (int i=0; i<3; i++)
        {
            for (int j=0; j<4; j++)
            {
                int sum = 0;
                if(board[i][j] == player)
                {
                    sum++;
                }
                else if(board[i][j] == 0)
                {

                }
                else
                {
                    sum--;
                }
                if(board[i+1][j+1] == player)
                {
                    sum++;
                }
                else if(board[i+1][j+1] == 0)
                {

                }
                else
                {
                    sum--;
                }
                if(board[i+2][j+2] == player)
                {
                    sum++;
                } 
                else if(board[i+2][j+2] == 0)
                {

                }
                else
                {
                    sum--;
                }
                if(board[i+3][j+3] == player)
                {
                    sum++;
                }
                else if(board[i+3][j+3] == 0)
                {

                }
                else
                {
                    sum--;
                }

                if(sum == 2)
                {
                    player_score++;
                }
            }
        }

        //check diagonally - forward slash -> /
        for (int i=0; i<3; i++) {
            for (int j=0; j<4; j++)
            {
                int sum = 0;
                if(board[i+3][j] == player)
                {
                    sum++;
                }
                else if(board[i+3][j] == 0)
                {

                }
                else
                {
                    sum--;
                }
                if(board[i+2][j+1] == player)
                {
                    sum++;
                }
                else if(board[i+2][j+1] == 0)
                {

                }
                else
                {
                    sum--;
                }
                if(board[i+1][j+2] == player)
                {
                    sum++;
                }
                else if(board[i+1][j+2] == 0)
                {

                } else
                {
                    sum--;
                }
                if(board[i][j+3] == player)
                {
                    sum++;
                } 
                else if(board[i][j+3] == 0) 
                {

                }
                else
                {
                    sum--;
                }

                if(sum == 2*player)
                {
                    player_score++;
                }
            }
        }

        return player_score;
    }
    //---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
    public int getSingleCount(int player) 
    {
        //reset the scores
        int player_score = 0;

        //check horizontally
        for(int i=0; i<6; i++)
        {
            for(int j=0; j<7; j++)
            {
                if(board[i][j] == player)
                {
                    player_score++;
                }
            }
        }

        return player_score;
    }

    /**
     * the method prints the current score
     */
    public void printScore()
    {
        System.out.println("Score: Player1 - " + getScore(1) + " || Player2 - " + getScore(2));
    }
}