package controllers

import play.api.mvc._
import mediocrechess.GameEngine
import play.api.libs.json.Json

object Settings extends Controller { 
  implicit def string2Int(s: String): Int = augmentString(s).toInt
    def settings = Action {
    	Ok("")
    }

  def getLegalMoves(playWithFen: String) = Action { request =>
    request.session.get("ID").map { id =>
	    request.body.asJson.map { json =>
		  if (playWithFen == "false") {
			GameEngine.setFromMoves(id, (json \ "history").as[String])
		  } else {		// playWithFen == "true"
			GameEngine.setFromFen(id, (json \ "history").as[String], (json \ "fen").as[String])
		  }
		  
		   Ok(Json.toJson( Map( "fen" -> GameEngine.getFen(id),
				   				"legal" -> GameEngine.getLegalMoves(id) ) ) )
						  
//		  Ok(GameEngine.getLegalMoves(id))
	    }.getOrElse(BadRequest("Need the JSON data"))
    }.getOrElse(BadRequest("Wrong ID"))
  }
  
  def setPonderTime(time: Int) = Action { request =>
    request.session.get("ID").map { id =>
      GameEngine.setPonderTime(id, time)
	    Ok("aa")
    }.getOrElse(BadRequest("Wrong ID"))
  }
}