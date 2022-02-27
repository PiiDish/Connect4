// Author: Jason Disher
// Student No.: c3185333

#include <algorithm>  // Provides fill_n
#include <cassert>    // Provides assert macro
#include <cctype>     // Provides isdigit
#include <iomanip>    // Provides setw
#include <iostream>   // Provides cin, cout
#include <queue>      // Provides queue<string> class
#include <string>     // Provides string
#include "connect4.h" // Provides definition of connect4 class (derived from game)
using namespace std;

namespace piidish_14
{
    // Public static member constants. These were defined in connect.h:
    const int connect4::ROWS;
    const int connect4::COLUMNS;

    // Private static member constants, defined here. The COLUMN_DISPLAY_WIDTH
    // is the number of characters to use in the display( ) function for each
    // column in the output display. The FOUR_VALUE is the value returned by
    // the value function when it finds four-in-a-row. For the current
    // implementation of evaluate to work, the FOUR_VALUE should be at least
    // 24 times as large as the total number of spots on the board.
    // MOVE_STRINGS is an array of all possible moves, which must be strings
    // corresponding to the integers 0 through COLUMNS-1.
    const int connect4::COLUMN_DISPLAY_WIDTH = 3;
    const int connect4::FOUR_VALUE = 24*ROWS*COLUMNS;
    const string connect4::MOVE_STRINGS[COLUMNS] = {"0","1","2","3","4","5","6"};

    connect4::connect4( )
    : game( )
    {
	restart( );
    }
    
    game* connect4::clone( ) const
    {
	// Return a pointer to a copy of myself, made with the automatic copy
	// constructor. If I used dynamic memory, I would have to alter this
	// copy so that it used its own dynamic memory instead of mine. But
	// the connect4 class does not use any dynamic memory, so this is ok:
	return new connect4(*this);
    }
    
    void connect4::compute_moves(queue<string>& moves) const
    {
	int i;
	
	for (i = 0; i < COLUMNS; i++)
	{
	    if (many_used[i] < ROWS)
		moves.push(MOVE_STRINGS[i]);
	}
    }
    
    void connect4::display_status( ) const
    {
	int row, column;

	cout << "\nCurrent game status\n(HUMAN = #  and  COMPUTER = @):\n";
	if (moves_completed( ) != 0)
	    cout << "Most recent move in column " << most_recent_column << endl;
	for (row = ROWS-1; row >= 0; --row)
	{
	    for (column = 0; column < COLUMNS; ++column)
	    {
		switch (data[row][column])
		{
		case HUMAN:    cout << setw(COLUMN_DISPLAY_WIDTH) << "#"; break;
		case COMPUTER: cout << setw(COLUMN_DISPLAY_WIDTH) << "@"; break;
		case NEUTRAL:  cout << setw(COLUMN_DISPLAY_WIDTH) << "."; break;
		}
	    }
	    cout << endl;
	}
	for (column = 0; column < COLUMNS; ++column)
	    cout << setw(COLUMN_DISPLAY_WIDTH) << column;
	if (is_game_over( ))
		cout << "\nGame over." << endl;
	else if (last_mover( ) == COMPUTER)
	    cout << "\nHuman's turn to move (by typing a column number)" << endl;
	else
	    cout << "\nComputer's turn to move (please wait)..." << endl;
    }
						
    int connect4::evaluate( ) const
    {
	// NOTE: Positive answer is good for the computer.    
	int row, column;
	int answer = 0;

        // For each possible starting spot, calculate the value of the spot for
	// a potential four-in-a-row, heading down, left, and to the lower-left.
	// Normally, this value is in the range [-3...+3], but if a
	// four-in-a-row is found for the player, the result is FOUR_VALUE which
	// is large enough to make the total answer larger than any evaluation
	// that occurs with no four-in-a-row.

	// Value moving down from each spot:
	for (row = 3; row < ROWS; ++row)
	    for (column = 0; column < COLUMNS; ++column)
		answer += value(row, column, -1, 0);

	// Value moving left from each spot:
	for (row = 0; row < ROWS; ++row)
	    for (column = 3; column < COLUMNS; ++column)
		answer += value(row, column, 0, -1);

	// Value heading diagonal (lower-left) from each spot:
	for (row = 3; row < ROWS; ++row)
	    for (column = 3; column < COLUMNS; ++column)
		answer += value(row, column, -1, -1);

	// Value heading diagonal (lower-right) from each spot:
	for (row = 3; row < ROWS; ++row)
	    for (column = 0; column <= COLUMNS-4; ++column)
		answer += value(row, column, -1, +1);

	return answer;
    }
    
    bool connect4::is_game_over( ) const
    {
	    
	int row, column;
	int i;
	
	// Two simple cases:    
	if (moves_completed( ) == 0)
	    return false;
	if (moves_completed( ) == ROWS*COLUMNS)
	    return true;

	// Check whether most recent move is part of a four-in-a-row
	// for the player who just moved.
	column = most_recent_column;
	row = many_used[column] - 1;
	// Vertical:
	if (abs(value(row, column, -1, 0)) == FOUR_VALUE) return true;
	for (i = 0; i <= 3; i++)
	{
	    // Diagonal to the upper-right:
	    if (abs(value(row-i, column-i, 1,  1)) == FOUR_VALUE) return true;
	    // Diagonal to the lower-right:
  	    if (abs(value(row-i, column+i, 1, -1)) == FOUR_VALUE) return true;
	    // Horizontal:
	    if (abs(value(row,   column-i, 0,  1)) == FOUR_VALUE) return true;
	}

	return false;
    }
    
    bool connect4::is_legal(const string& move) const
    {
	int column = atoi(move.c_str( ));

	return
	    (!is_game_over( ))
	    &&
	    (move.length( ) > 0)
	    &&
	    (isdigit(move[0]))
	    &&
	    (column < COLUMNS)
	    &&
	    (many_used[column] < ROWS);
    }
    
    void connect4::make_move(const string& move)
    {
	int row, column;
	
	assert(is_legal(move));
	column = atoi(move.c_str( ));
	row = many_used[column]++;
	data[row][column] = next_mover( );
	most_recent_column = column;
	game::make_move(move);
    }

    void connect4::restart( )
    {
	fill_n(&(data[0][0]), ROWS*COLUMNS, NEUTRAL);
	fill_n(&(many_used[0]), COLUMNS, 0);
	game::restart( );
    }

    int connect4::value(int row, int column, int delta_r, int delta_c) const
    {
	// NOTE: Positive return value is good for the computer.    
	int i;
	int end_row = row + 3*delta_r;
	int end_column = column + 3*delta_c;
	int player_count= 0;
	int opponent_count = 0;

	if (
	    (row < 0) || (column < 0) || (end_row < 0) || (end_column < 0)
	    ||
	    (row >= ROWS) || (end_row >= ROWS)
	    ||	 
	    (column >= COLUMNS) || (end_column >= COLUMNS)
	   )
	    return 0;

	for (i = 1; i <= 4; ++i)
	{
	    if (data[row][column] == COMPUTER)
		++player_count;
	    else if (data[row][column] != NEUTRAL)
		++opponent_count;
	    row += delta_r;
	    column += delta_c;
	}

	if ((player_count > 0) && (opponent_count > 0))
	    return 0; // Neither player can get four-in-a-row here.
	else if (player_count == 4)
	    return FOUR_VALUE;
	else if (opponent_count == 4)
	    return -FOUR_VALUE;
	else
	    return player_count - opponent_count;
    }
}