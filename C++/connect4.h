// Author: Jason Disher
// Student No.: c3185333


#ifndef MAIN_CONNECT4
#define MAIN_CONNECT4
#include <queue>    // Provides queue<string>
#include <string>   // Provides string
#include "game.h"   // Provides the game base class

namespace piidish_14
{
    class connect4 : public game
    {
    public:
	// STATIC CONSTANTS
	static const int ROWS = 6;
	static const int COLUMNS = 7;

	// CONSTRUCTOR
	connect4( );

    protected:
	// *******************************************************************
	// VIRTUAL FUNCTIONS (these are overridden from the game base class)
	// *******************************************************************
	// Return a pointer to a copy of myself:
	virtual game* clone( ) const;
        // Compute all the moves that the next player can make:
	virtual void compute_moves(std::queue<std::string>& moves) const;
	// Display the status of the current game:
	virtual void display_status( ) const;
	// Evaluate the current board position.
	// NOTE: Positive values are good for the computer.
	virtual int evaluate( ) const;
	// Return true if the current game is finished:
	virtual bool is_game_over( ) const;
	// Return true if the given move is legal for the next player:
	virtual bool is_legal(const std::string& move) const;
	// Have the next player make a specified move:
	virtual void make_move(const std::string& move);
	// Restart the game from the beginning:
	virtual void restart( );

    private:
	// HELPER FUNCTIONS
	int value(int row, int column, int delta_r, int delta_c) const;

	// HELPER CONSTANTS. See connect4.cxx for their values.
	static const int COLUMN_DISPLAY_WIDTH;
	static const int FOUR_VALUE;
	static const std::string MOVE_STRINGS[COLUMNS];

        // MEMBER VARIABLES TO TRACK THE STATE OF THE GAME
	who data[ROWS][COLUMNS];
	int many_used[COLUMNS];
	int most_recent_column;
    };
}

#endif