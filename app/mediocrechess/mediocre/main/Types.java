package mediocrechess.mediocre.main;

public class Types {
	public enum EventID {
		CELL_CLICKED, MOVE_DONE, PROMOTING, INFORM, 
		EMPTY, PROMOTION, PROMOTED_CHOOSED;
	}
	
	public enum GameStates {
		WAIT_FIRST_CLICK, WAIT_SECOND_CLICK, MOVE_DOING,
		THINKING, PAUSE, NONE, PROMOTION, MOVE_BACK, CHECK_FOR_MATE, MATE;
	}
	
	public enum Color {
		BLACK(0,0,0), RED(1,0,0), GREEN(0,1,0), BLUE(0,0,1), WHITE(1,1,1);
		
		float r, g, b;
		
		Color(float r, float g, float b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}
	}
	
	public static enum Type {
		KING('k', 'k', 'к'), QUEEN('q', 'q', 'ф'), ROOK('r', 'r', 'л'), BISHOP('b', 'b', 'c'), KNIGHT('n', 'n', 'к'), PAWN('p', '\0', '\0');
		char en, ru, fen;
		
		Type(char fen, char en, char ru) {
			this.fen = fen; this.en = en; this.ru = ru;
		}
		
		public char getFenChar() {
			return fen;
		}

		public char toLetter() {
			return en;
		}

		public char toRusLetter() {
			return ru;
		};
		
		public static Type getType(char c) {
			for (Type type : Type.values()) 
				if (type.fen == c) return type;
			return null;
		}
		
		public static Type[] getPromoteTypes() {
			return new Type[] {QUEEN, ROOK, BISHOP, KNIGHT};
		}
	}
	
	public static enum GameEnds {
		WHITE_MATE, BLACK_MATE, STALEMATE, PROCESS
	}
	/*public static enum Type {
		KING('k', 'к'), QUEEN('q', 'ф'), ROOK('r', 'л'), BISHOP('b', 'c'), KNIGHT('n', 'к'), PAWN('\0', '\0');
		char en, ru;
		
		Type(char en, char ru) {
			this.en = en; this.ru = ru;
		}
		
		public char toLetter() {
			return en;
		}

		public char toRusLetter() {
			return ru;
		};
		
		public static Type[] getPromoteTypes() {
			return new Type[] {QUEEN, ROOK, BISHOP, KNIGHT};
		}
	}
*/
	public static enum SquareName {
		a1,a2,a3,a4,a5,a6,a7,a8,
		b1,b2,b3,b4,b5,b6,b7,b8,
		c1,c2,c3,c4,c5,c6,c7,c8,
		d1,d2,d3,d4,d5,d6,d7,d8,
		e1,e2,e3,e4,e5,e6,e7,e8,
		f1,f2,f3,f4,f5,f6,f7,f8,
		g1,g2,g3,g4,g5,g6,g7,g8,
		h1,h2,h3,h4,h5,h6,h7,h8;
		
		static SquareName[][] array = {		{a1,a2,a3,a4,a5,a6,a7,a8},
											{b1,b2,b3,b4,b5,b6,b7,b8},
											{c1,c2,c3,c4,c5,c6,c7,c8},
											{d1,d2,d3,d4,d5,d6,d7,d8},
											{e1,e2,e3,e4,e5,e6,e7,e8},
											{f1,f2,f3,f4,f5,f6,f7,f8},
											{g1,g2,g3,g4,g5,g6,g7,g8},
											{h1,h2,h3,h4,h5,h6,h7,h8}
		};
/*
 * 		Initial position cell names =================================================
 */
		public static SquareName getKingName(boolean isWhite) {
			return isWhite ? e1 : e8;
		}
		public static SquareName getQueenName(boolean isWhite) {
			return isWhite ? d1 : d8;
		}
		public static SquareName[] getRooksNames(boolean isWhite) {
			return isWhite ? new SquareName[] {a1, h1} :
				new SquareName[] {a8, h8};
		}
		public static SquareName[] getBishopsNames(boolean isWhite) {
			return isWhite ? new SquareName[] {c1, f1} :
				new SquareName[] {c8, f8};
		}
		public static SquareName[] getKnightsNames(boolean isWhite) {
			return isWhite ? new SquareName[] {b1, g1} :
				new SquareName[] {b8, g8};
		}
		public static SquareName[] getPawnsNames(boolean isWhite) {
			return isWhite ? new SquareName[] {a2, b2, c2, d2, e2, f2, g2, h2} :
			new SquareName[] {a7, b7, c7, d7, e7, f7, g7, h7};
		}
		public static SquareName[][] asTwoDimensionalArray() {
			return array;
		}
		public static SquareName getNameBy0x88Code(int code) {
			for (SquareName sName : SquareName.values()) {
				if (code == sName.get0x88Code()) return sName; 
			}
			return null;
		}
		
		public int getHor() {
			return this.ordinal() % 8;
		}
		public int getVer() {
			return this.ordinal() / 8;
		}
		
		public int get0x88Code() {
			return getHor() * 8 * 2 + getVer();
		}
		
		
/*
 * Initial position cell names =================================================
 */
//		public static CellName getCellName(String nameStr) {
//			for (CellName cellName : values()) {
//				if (cellName.toString().equals(nameStr))
//					return cellName;
//			}
//			return null;
//		}
/*		public char getHor() {
			return 
		}
		public char getVer() {
			
		}*/
	}     
}
