// Author: Jason Disher
// Student No.: c3185333


#include <cctype>
#include <cstdlib>
#include <iostream>
#include <string>
#include "connect4.h"
using namespace std;
using namespace piidish_14;

int main( )
{
    connect4 instance;
    connect4::who winner;
    char answer;
    string restline;

    do
    {
	winner = instance.play( );
	switch (winner)
	{
	case connect4::HUMAN:    cout << "You win" << endl; break;
	case connect4::COMPUTER: cout << "I win"   << endl; break;
	case connect4::NEUTRAL:  cout << "A draw"  << endl; break;
	}
	cout << "Do you want to play again? [Y/N] ";
	cin >> answer;
	getline(cin, restline);
    }   while (toupper(answer) == 'Y');

    return EXIT_SUCCESS;
}