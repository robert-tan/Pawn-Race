# Pawn Race
Java chess game where players race pawns to the end of the board, implemented with a minimax algorithm.

## How to Play
* Games may be played via Player vs. Player, Player vs. Computer, or Computer vs. Computer
* Each player starts with 7 pawns, with a gap you may specify in the args
* To choose the game mode and gap, run the executable with arguments "(P/C) (P/C) (a-h) (a-h)" to specify the white player, black player, white gap, and black gap respectively, where 'P' represents a Player playing the game, 'C' represents a Computer playing the game, and each of 'a-h' representing the gap file
* Your goal is to get a pawn to the opposite end of the board before your opponent, the computer
* The computer uses a minimax AI with alpha beta pruning
* Standard chess rules apply - moving forward, moving forward twice on the first move, sideward captures, and en passant captures
* To move, input the move using valid PGN notation into the terminal
* Once you move, you must wait for the computer to move, which can be quite slow for higher depths
* The game is over when either a player reaches the end of the board, a player has no more pieces, or a player has pieces but no more available moves (in which case it is a stalemate)

## Awards
Placed 3rd in the Imperial College London Accenture Java Pawn Race Tournament in December, 2017

## Credits
This game was made jointly with Jiayang (Emma) Cao, also from Imperial College London
  
