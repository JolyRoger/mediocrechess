package mediocrechess.mediocre.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import mediocrechess.mediocre.board.Board;
import mediocrechess.mediocre.board.Move;
import mediocrechess.mediocre.engine.Engine;
import mediocrechess.mediocre.def.Definitions;

public class Uci implements Definitions {
	private static final String VERSION = "0.5";
	public BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Initialize the reader
	private Logger logger = (Logger)LoggerFactory.getLogger(Uci.class);
	private Board board = null;
	private Engine engine = null;
	private StringBuilder out = null;
	private boolean useBook;
	private int searchDepth; 
	private int movetime;
	@SuppressWarnings("unused")
	private String openingLine, command;
	private boolean free = true;
	private Move[] legalMoves;
	
	private int ponderTime = 0;
	
//	static {
//		System.setOut(null);
//	}
	
	public enum GameEnds {
		BLACK_MATE, WHITE_MATE, DRAWN, PROCESS
	}
	public String init() {
		logger.debug("Enter UCI protocol");
		out = new StringBuilder();
		// playing
		board = new Board(); // Create a board on which we will be
        if (engine == null) engine = new Engine(this);
		legalMoves = new Move[218];
		for (int i = 0; i < legalMoves.length; i++) {
			legalMoves[i] = new mediocrechess.mediocre.board.Move();
		}
		board.setupStart(); // Start position
		
		useBook = Settings.getInstance().isUseOwnBook(); // Initilize using the book to what is set in the settings file
		openingLine = ""; // Holds the opening line so far
		searchDepth = 0; // Initialize search depth
		movetime = 0; // Initialize fixed time per move
		out.append("");
		out.append("id name Mediocre " + VERSION);
		out.append("id author Jonatan Pettersson");
		out.append("option name Hash type spin default " + Settings.DEFAULT_HASH_SIZE + " min 1 max 512");
		out.append("option name EvalHash type spin default " + Settings.DEFAULT_EVAL_HASH_SIZE + " min 1 max 32");
		out.append("option name PawnHash type spin default " + Settings.DEFAULT_PAWN_HASH_SIZE + " min 1 max 32");
		out.append("option name Ponder type check default " + Settings.DEFAULT_PONDER);
		out.append("option name OwnBook type check default " + Settings.DEFAULT_USE_OWN_BOOK);		
		out.append("uciok");
		
		// This is the loop in which we look for incoming commands from Uci
//			String command = reader.readLine(); // Receive the input
		return out.toString();
	}

	/**
	 * Handles input from the UCI application and also sends moves and settings
	 * from the engine
	 * 
	 * I had some much appreciated help with this from Yves Catineau
	 */
	public String uci(String command) throws IOException {
		this.command = command;
//		System.out.println("hashCode=" + this.hashCode());
		free = false;
		out = new StringBuilder();
		// This is the loop in which we look for incoming commands from Uci
		System.out.println("Received command: >" + command + "<");
		logger.debug("Received command: >" + command + "<");

		if ("uci".equals(command)) {
			out.append("id name Mediocre " + VERSION);
			out.append("id author Jonatan Pettersson");
			out.append("uciok");
		}

		if ("isready".equals(command)) {
			out.append("readyok");
		}

		if ("quit".equals(command)) {
			logger.debug("Quit command received, exiting");
			System.exit(0);
		}

		if(command.startsWith("setoption")) {
			String[] commandSplit = command.split(" ");
			if(commandSplit.length == 5) {
				try {
					if(commandSplit[2].equals("Hash")) {
						int size = Integer.parseInt(commandSplit[4]);
						logger.debug("Setting TT size to " + size);
						Settings.getInstance().setTranspositionTableSize(size);
					} else if(commandSplit[2].equals("EvalHash")) {
						int size = Integer.parseInt(commandSplit[4]);
						logger.debug("Setting evalTT size to " + size);
						Settings.getInstance().setEvalTableSize(size);
					} else if(commandSplit[2].equals("PawnHash")) {
						int size = Integer.parseInt(commandSplit[4]);
						logger.debug("Setting pawn TT size to " + size);
						Settings.getInstance().setPawnTableSize(size);
					} else if(commandSplit[2].equals("Ponder")) {
						boolean isUse = Boolean.parseBoolean(commandSplit[4]);
						logger.debug("Setting use own book to " + isUse);
						Settings.getInstance().setPonder(isUse);
					} else if(commandSplit[2].equals("OwnBook")) {
						boolean isUse = Boolean.parseBoolean(commandSplit[4]);
						Settings.getInstance().setUseOwnBook(isUse);
						useBook = isUse;
					}	
				} catch (Exception e) {
					System.err.println("Failure when parsing set option: " + e.getMessage());
				}
			}
		}

		// A new game is starting, can be both from start and inserted
		// position
		if ("ucinewgame".equals(command)) {
			Settings.getInstance().getRepTable().clear(); // Reset the history
			Settings.getInstance().getTranspositionTable().clear(); // Reset transposition table
			useBook = Settings.getInstance().isUseOwnBook(); // We can potentially use the book in the new game (will be set to true again, if set in settings)
			searchDepth = 0;
			movetime = 0;
		}

		// Using the UCI protocol we receive the moves by the opponent
		// in a 'position' string, this string comes with a FEN-string (or
		// states "startpos")
		// followed by the moves played on the board.
		// 
		// The UCI protocol states that the position should be set on the
		// board
		// and all moves played
		if (command.startsWith("position")) {
			// Set the position on the board

			// Clear the rep table since all possible repetition
			// will be set by the position string 
			Settings.getInstance().getRepTable().clear();

			if (command.indexOf("startpos") != -1) // Start position
			{
				openingLine = ""; // Initialize opening line
				board.setupStart(); // Insert start position
			} else // Fen string
			{
				String fen = extractFEN(command);

				useBook = false; // The position was not played from the
				// start so don't attempt to use the
				// opening book
				openingLine = "none"; // Make sure we can't receive a book
				// move
				if (!"".equals(fen)) // If fen == "" there was an error
					// in the position-string
				{
					board.inputFen(fen); // Insert the FEN
				}
			}

			// Play moves if there are any

			String[] moves = extractMoves(command);

			if (moves != null) // There are moves to be played
			{
				openingLine = ""; // Get ready for new input
				for (int i = 0; i < moves.length; i++) {

					int moveToMake = receiveMove(moves[i], board);
					if (moveToMake == 0) {
						out.append("Error in position string. Move "
								+ moves[i] + " could not be found.");
					} else {
						board.makeMove(moveToMake); // Make the move on the
						// board
						Settings.getInstance().getRepTable().recordRep(board.zobristKey);
						if (useBook)
							openingLine += moves[i]; // Update opening
						// line
					}
				}
			}
		}

		// The GUI has told us to start calculating on the position, if the
		// opponent made a move this will have been caught in the 'position'
		// string
		if (command.startsWith("go")) {
			int wtime = 0; // Initialize the times
			int btime = 0;
			int winc = 0;
			int binc = 0;
			boolean ponder = false;

			// If infinite time, set the times to 'infinite'
			if ("infinite".equals(command.substring(3))) {
				System.out.println("INFINITE");
				wtime = 99990000;
				btime = 99990000;
				winc = 0;
				binc = 0;
			} else if ("depth".equals(command.substring(3, 8))) {
				try {
					searchDepth = Integer.parseInt(command.substring(9));
				} catch (NumberFormatException ex) {
					logger.error("Malformed search depth");
				}
			} else if ("movetime".equals(command.substring(3, 11))) {
				try {
					movetime = Integer.parseInt(command.substring(12));
				} catch (NumberFormatException ex) {
					logger.error("Malformed move time");
				}
			} else // Extract the times since it's not infinite time
				// controls
			{
				String[] splitGo = command.split(" ");
				for (int goindex = 1; goindex < splitGo.length-1; goindex++) {

					try {
						if ("wtime".equals(splitGo[goindex]))
							wtime = Integer.parseInt(splitGo[goindex + 1]);
						else if ("btime".equals(splitGo[goindex]))
							btime = Integer.parseInt(splitGo[goindex + 1]);
						else if ("winc".equals(splitGo[goindex]))
							winc = Integer.parseInt(splitGo[goindex + 1]);
						else if ("binc".equals(splitGo[goindex]))
							binc = Integer.parseInt(splitGo[goindex + 1]);
						else if("ponder".equals(splitGo[goindex + 1])) {
							ponder = true;
						}
					}

					// Catch possible errors so the engine doesn't crash
					// if the go command is flawed
					catch (ArrayIndexOutOfBoundsException ex) {
						logger.error("Malformed go command");
					} catch (NumberFormatException ex) {
						logger.error("Malformed go command");
					}
				}
			}

			// We now have the times so set the engine's time and increment
			// to whatever side he is playing (the side to move on the
			// board)

			int engineTime;
			int engineInc;
			if (board.toMove == 1) {
				engineTime = wtime;
				engineInc = winc;
			} else {
				engineTime = btime;
				engineInc = binc;
			}

			// The engine's turn to move, so find the best line
			
			Engine.LineEval bestLine = new Engine.LineEval();

			if (useBook) {
				String bookMove = Settings.getInstance().getBook().getMoveFromBoard(board);

				if (bookMove.equals("")) {
					useBook = false;
				} else {
					openingLine += bookMove;
					bestLine.line[0] = receiveMove(bookMove, board);
				}
			}
			if(!useBook) {
				try {
					if (engine == null) engine = new Engine(this);
					bestLine = engine.search(board, searchDepth, engineTime, engineInc, movetime, ponder);
				} catch (Exception e) {
					logger.error("Error while searching", e);
				}
			}
			if (bestLine.line[0] != 0) // We have found a move to make
			{
				board.makeMove(bestLine.line[0]); // Make best move on the
				// board

				Settings.getInstance().getRepTable().recordRep(board.zobristKey);

				if(Settings.getInstance().getPonder() & bestLine.line[1] != 0) {
					out.append("bestmove "	+ (Move.inputNotation(bestLine.line[0])) + " ponder " + (Move.inputNotation(bestLine.line[1])));
				} else {
					out.append("bestmove "	+ (Move.inputNotation(bestLine.line[0])));
				}
			}
		}
		free = true;
//		System.out.println('\t'+out.toString());
		return out.toString();
	} // END uci()

	/**
	 * Used by uci mode
	 * 
	 * Extracts the fen-string from a position-string, do not call if the
	 * position string has 'startpos' and not fen
	 * 
	 * Throws 'out of bounds' exception so faulty fen string won't crash the
	 * program
	 * 
	 * @param position
	 *            The position-string
	 * @return String Either the start position or the fen-string found
	 */
	public String extractFEN(String position)
			throws ArrayIndexOutOfBoundsException {

		String[] splitString = position.split(" "); // Splits the string at the
		// spaces

		String fen = "";
		if (splitString.length < 6) {
			System.out.println("Error: position fen command faulty");
		} else {
			fen += splitString[2] + " "; // Pieces on the board
			fen += splitString[3] + " "; // Side to move
			fen += splitString[4] + " "; // Castling rights
			fen += splitString[5] + " "; // En passant
			if (splitString.length >= 8) {
				fen += splitString[6] + " "; // Half moves
				fen += splitString[7]; // Full moves
			}
		}

		return fen;
	} // END extractFEN()

	/**
	 * Used by uci mode
	 * 
	 * Extracts the moves at the end of the 'position' string sent by the UCI
	 * interface
	 * 
	 * Originally written by Yves Catineau and modified by Jonatan Pettersson
	 * 
	 * @param position
	 *            The 'position' string
	 * @return moves The last part of 'position' that contains the moves
	 */
	private String[] extractMoves(String position) {
		String pattern = " moves ";
		int index = position.indexOf(pattern);
		if (index == -1)
			return null; // No moves found

		String movesString = position.substring(index + pattern.length());
		String[] moves = movesString.split(" "); // Create an array with the
		// moves
		return moves;
	} // END extractMoves()

	/**
	 * Takes an inputted move-string and matches it with a legal move generated
	 * from the board
	 * 
	 * @param move
	 *            The inputted move
	 * @param board
	 *            The board on which to find moves
	 * @return int The matched move
	 */
	public int receiveMove(String move, Board board) throws IOException {

		Move[] legalMoves = new Move[256];
		for(int i = 0; i < 256; i++) legalMoves[i] = new Move();
		int totalMoves = board.gen_allLegalMoves(legalMoves, 0); // All moves

		for (int i = 0; i < totalMoves; i++) {
			if (Move.inputNotation(legalMoves[i].move).equals(move)) {
				return legalMoves[i].move;
			}
		}

		// If no move was found return null
		return 0;
	} // END receiveMove

	public void setPonderTime(int time) {
		ponderTime = time;
	}
	public int getPonderTime() {
		return ponderTime;
	}

	public String getLegalMoves() {
		board.gen_allLegalMoves(legalMoves, 0);
		
		String out = "";
		for (Move legalMove : legalMoves) {
			int from = mediocrechess.mediocre.board.Move.fromIndex(legalMove.move);
			int to = mediocrechess.mediocre.board.Move.toIndex(legalMove.move);
			if (from != to) {
				out += Types.SquareName.getNameBy0x88Code(from) + "" + Types.SquareName.getNameBy0x88Code(to) + " ";
			}
		}
		return out.trim();
	}
	
	public Board getBoard() {
		if (board == null) board = new Board();
		return board;
	}

	public String getCurrentCommand() {
		return command;
	}
	
	public boolean isFree() {
		return free;
	}
	
	/**
	 * Checks the board and the repetition table if the game is over
	 * 
	 */
	public String isGameOver(Board board, int[] gameHistory,
			int gameHistoryIndex) {
		Move[] legalMoves = new Move[256];
		for(int i = 0; i < 256; i++) legalMoves[i] = new Move();
		if (board.gen_allLegalMoves(legalMoves, 0) == 0) {
			if (board.isInCheck()) {
				if (board.toMove == WHITE_TO_MOVE) {
					return "0-1 (Black mates)";
				} else {
					return "1-0 (White mates)";
				}
			} else {
				return "1/2-1/2 (Stalemate)";
			}
		}

		if (board.movesFifty >= 100) {
			return "1/2-1/2 (50 moves rule)";
		}

		for (int i = 0; i < gameHistoryIndex; i++) {
			int repetitions = 0;
			for (int j = i + 1; j < gameHistoryIndex; j++) {
				if (gameHistory[i] == gameHistory[j])
					repetitions++;
				if (repetitions == 2) {
					return "1/2-1/2 (Drawn by repetition)";
				}
			}
		}

		if (engine.getEvaluation().drawByMaterial(board, 0)) {
			return "1/2-1/2 (Drawn by material)";
		}

		return "no";
	} // END isGameOver

    public GameEnds isMate() {
        int allLegalMoves = board.gen_allLegalMoves(legalMoves, 0);
        if (allLegalMoves == 0) {
            if (Engine.isInCheck(board)) {
                if (board.toMove == WHITE_TO_MOVE) {
                    return GameEnds.BLACK_MATE;
                } else {
                    return GameEnds.WHITE_MATE;
                }
            } else {
                System.out.println("Stalemate");
                return GameEnds.DRAWN;
            }
        }
/*

        if (board.movesFifty >= 100) {
            System.out.println("50 moves rule");
            return GameEnds.DRAWN;  // 50 moves rule
        }

        HashSet<Integer> indexes = new HashSet<Integer>();
        indexes.clear();
//        for (int i=0; i<indexes.length; i++) { indexes[i] = -1; }

        System.out.println("board.historyIndex=" + board.historyIndex);
        System.out.print("history: ");
        print(board.history);
        for (int i = 0; i < board.historyIndex; i++) {
            int repetitions = 0;
            for (int j = i + 1; j < board.historyIndex; j++) {
                if (board.history[i] == board.history[j]) {
                    indexes.add(i);
                    repetitions++;
                    indexes.add(j);
                }
                if (repetitions == 2) {
                    System.out.println("Drawn by repetition");
                    Integer index = -1;
                    for (Iterator<Integer> it = indexes.iterator(); it.hasNext();) {
                        index = it.next();
                        System.out.println("\tindex: " + index);
                    }
                    return GameEnds.DRAWN;  // Drawn by repetition
                }
            }
        }

        if (engine.getEvaluation().drawByMaterial(board, 0)) {
            System.out.println("Drawn by material");
            return GameEnds.DRAWN;    // Drawn by material;
        }
*/

        return GameEnds.PROCESS;
    }

    private void print(int[] arr) {
        for (int a: arr) {
            if (a != 0)
                System.out.print(a + " ");
        }
        System.out.println();
    }
}
