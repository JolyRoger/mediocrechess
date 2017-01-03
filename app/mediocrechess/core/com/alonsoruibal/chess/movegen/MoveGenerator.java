package mediocrechess.core.com.alonsoruibal.chess.movegen;

import mediocrechess.core.com.alonsoruibal.chess.Board;

public interface MoveGenerator {

	int generateMoves(Board board, int moves[], int index);

}