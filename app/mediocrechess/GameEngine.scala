package mediocrechess

import mediocrechess.uci.com.alonsoruibal.chess.uci.Uci

object GameEngine {
  	val UCI = "uci "; 
	val UCINEWGAME = "ucinewgame"; 
	val GO_INFINITE = "go infinite "; 
	val POSITION_STARTPOS = "position startpos"; 
	val MOVES = " moves "; 
	val POSITION_FEN = "position fen "; 
	val GO_MOVETIME = "go movetime "; 
	val BEST_MOVE = "bestmove"; 
	val STOP = "stop";

	var idMap = emptyMap
	var movetime = 5000; 
	
	def emptyMap = {
	  println("NEW EMPTY MAP!")
	  Map.empty[Int, Uci]
	}
	
	def createID = {
	  def correct(propID: Int): Int = 
	    if (!idMap.contains(propID)) {
	      idMap += (propID -> new Uci)
	      propID
	    } else correct(propID + 1)
	  correct(0)
	}
	
	def deleteID(id: Int) = {
		idMap -= id
		println("The map cleared from " + id)
		"success"
	}
	
	def clearMap {
		idMap = idMap.empty
	}
	
	def newGame(id: Int) = {
		idMap.get(id) match {
		  case Some(uci) => 
		    uci.init
		    uci.uci(UCINEWGAME)
		  case None => "Uci with id " + id + " not found"
		}
	}
	
	def setFromMoves(id: String, startpos: String/*, idMap: Map[Int, Uci]*/) {
//	  println("!!!setFromMoves: " + POSITION_STARTPOS + MOVES + startpos)
	  send(id, POSITION_STARTPOS + MOVES + startpos)
	}
	def setFromFen(id: String, history: String, fen: String/*, idMap: Map[Int, Uci]*/) {
//		println("setFromFen: " + POSITION_FEN + fen + MOVES + history/*.drop(history.lastIndexOf(' ')+1)*/)
		send(id, POSITION_FEN + fen + MOVES + history/*.drop(history.lastIndexOf(' ')+1)*/)
	}
	
	def send(id: String, command: String) {
	  println("go id=" + id);
	  idMap(id).uci(command)
	}

	def go(id: String/*, idMap: Map[Int, Uci]*/) = {
	  if (idMap(id).getPonderTime > 0)
		  idMap(id).uci(GO_MOVETIME + idMap(id).getPonderTime)
	  else {
	    idMap(id).uci(GO_INFINITE.trim())
	  }
	}
	
	def setPonderTime(id: String, time: Int) {
	  idMap(id).setPonderTime(time * 1000)
	}

	def getLegalMoves(id: Int) = {
		idMap.get(id) match {
		case Some(uci) => uci.getLegalMoves
		case None => "Uci with id " + id + " not found"
		}
	}
	def getFen(id: Int) = {
		idMap.get(id) match {
		case Some(uci) => uci.getBoard().getFen()
		case None => "Uci with id " + id + " not found"
		}
	}
	
	def isGameOver(id: Int) = {
		idMap.get(id) match {
		case Some(uci) => uci.isMate.toString
		case None => "Uci with id " + id + " not found"
		}
	}
	def tmpAddToMap {
	}
	def print {
	  println("idMap: " + idMap + " idMap.hash=" + idMap.hashCode)
	}
	implicit def string2Int(s: String): Int = augmentString(s).toInt
}
