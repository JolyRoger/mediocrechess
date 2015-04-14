package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.iteratee.Iteratee
import play.api.libs.iteratee.Enumerator
import play.api.libs.concurrent.Akka
import play.api.Play.current
import play.api.http.Writeable
import java.text.SimpleDateFormat
import java.util.Calendar
import scalax.io.Output
import scalax.io.Resource
import java.io.File
import play.api.libs.concurrent.Promise
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._
import views.html.defaultpages.badRequest
import mediocrechess.GameEngine
import org.yaml.snakeyaml.Yaml

object Application extends Controller {
  
//  val = mediocre.sm.common.StateMachine.getInstance()
  implicit def string2Int(s: String): Int = augmentString(s).toInt
  def path = if (Play.isProd) "target/"+ new File("target").listFiles.filter(_.getName.startsWith("scala"))(0).getName +"/classes/"
  else { "" }
  
  def index = Action {
	Redirect(routes.Application.game) 
  }
  
  def init(time: Int) = Action {
    val id = GameEngine.createID.toString
    println("create ID: " + id)
    GameEngine.setPonderTime(id, time)
    Ok(id).withSession("ID" -> id)
  }
  
  def deleteID(id: Int) = Action {
	println("delete id=" + id)
    for {
      files <- Option(new File(path + "public/saves").listFiles)
      file <- files if file.getName.startsWith((id + "_").toString)
    } file.delete()
//    SimpleResult(
//    		header = ResponseHeader(200),
//    		body = Enumerator("Hello World")
//    )
    Ok(GameEngine.deleteID(id))
  }
  
  def newPosition = Action { request =>
//	run
//  	mediocre.main.Uci.getBoard().gen_allLegalMoves(mediocre.sm.common.StateBase.legalMovesArray(), 0);
//	println(legal)
  val status = ("status" -> "OK")
  val fen = ("fen" -> "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
  val whiteIsUp = ("whiteIsUp" -> "false")
  
  request.session.get("ID").map { id =>
  	GameEngine.newGame(id)
  	Ok(Json.toJson( Map(status, fen, whiteIsUp, "legal" -> GameEngine.getLegalMoves(id))))
  }.getOrElse {
	  val id = routes.Application.init(5000).toString
	  Ok(Json.toJson( Map(status, fen, whiteIsUp, 
	      "legal" -> GameEngine.getLegalMoves(id)))).withSession("ID" -> id)
  }
  }

  def start = Action.async {
    val promiseOfString: Future[String] = Future("success")
    promiseOfString.map( i => Ok("Got result: " + i))
  }

//  def start = Action {
//	val promiseOfString: Future[String] = Akka.future {
////	  GameEngine.newUci
//	  "success"
//  	}
//	AsyncResult {
//	  promiseOfString.map( i => Ok("Got result: " + i))
//	}
//  }
  def next(playWithFen: String) = Action.async { request =>
    request.session.get("ID") match {
      case Some(id) =>
        var oldlegal = ""
        var oldfen = ""
        request.body.asJson match {
          case Some(json) =>
            val promiseOfString: Future[String] = Future {
              if (playWithFen == "false") {
                GameEngine.setFromMoves(id, (json \ "history").as[String])
              } else {
                GameEngine.setFromFen(id, (json \ "history").as[String], (json \ "fen").as[String])
              }
              oldfen = GameEngine.getFen(id)
              oldlegal = GameEngine.getLegalMoves(id)

              val gamover = GameEngine.isGameOver(id)
              println("gamover: " + gamover)
              if (gamover != "PROCESS") {
                gamover
              } else {
                GameEngine.go(id)
              }
            }
            promiseOfString.map(res =>
              Ok(Json.toJson( Map("status" -> res,
                "newfen" -> GameEngine.getFen(id),
                "oldfen" -> oldfen,
                "bestmove" -> res,
                "oldlegal" -> oldlegal,
                "newlegal" -> GameEngine.getLegalMoves(id),
                "gamover" -> GameEngine.isGameOver(id)))))
          case None => Future(BadRequest("Unknown JSON"))
        }
      case None => Future(BadRequest("Unknown ID"))
    }
  }

  //  def next(playWithFen: String) = Action { request =>
  //	  request.session.get("ID").map { id =>
  //	    var oldlegal = ""
  //	    var oldfen = ""
  //	    request.body.asJson.map { json =>
  //			  val promiseOfString: Future[String] = Akka.future {
  //				  if (playWithFen == "false") {
  //				    GameEngine.setFromMoves(id, (json \ "history").as[String])
  //				  } else {
  //					  GameEngine.setFromFen(id, (json \ "history").as[String], (json \ "fen").as[String])
  //				  }
  //				  oldfen = GameEngine.getFen(id)
  //				  oldlegal = GameEngine.getLegalMoves(id)
  //
  //				  val gamover = GameEngine.isGameOver(id)
  //				  if (gamover != "PROCESS") {
  //					  gamover
  //				  } else {
  //					  GameEngine.go(id)
  //				  }
  //			  }
  //			  AsyncResult {
  //				  promiseOfString.map { res =>
  //            println("resgame: " + res)
  //				  Ok(Json.toJson( Map("status" -> res,
  //						  "newfen" -> GameEngine.getFen(id),
  //						  "oldfen" -> oldfen,
  //						  "bestmove" -> res,
  //						  "oldlegal" -> oldlegal,
  //						  "newlegal" -> GameEngine.getLegalMoves(id),
  //						  "gamover" -> GameEngine.isGameOver(id))))
  //				  }
  //			  }
  //		}.getOrElse { BadRequest("Expecting Json data") }
  //	  }.getOrElse { BadRequest("Unknown ID") }
  //  }

  def rate = Action {
    for {
      files <- Option(new File("public/saves").listFiles)
      file <- files if file.getName.startsWith("20_")
    } {
    	file.delete
    }
    Ok("")
  }

  def update(move: String) = Action { request =>
    {
      request.body.asJson.map { json =>
        (json \ "name").asOpt[String].map { name =>
          Ok("Hello " + name)
        }.getOrElse {
          BadRequest("Missing parameter [name]")
        }
      }.getOrElse {
        BadRequest("Expecting Json data")
      }
    }
  }

  def saveGame = Action(parse.json) { request =>
    (request.body \ "fen").asOpt[String].map { fen =>
	    (request.body \ "history").asOpt[String].map { history =>
		    (request.body \ "legal").asOpt[String].map { legal =>
			    (request.body \ "userid").asOpt[String].map { userid =>
            (request.body \ "moves").asOpt[JsArray].map { moves =>
//              println("moves: " + (moves/* \\ "note"*/))
              println("path: " + path)
			      val f = new File(path + "public/saves/" + userid + "_" + new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(Calendar.getInstance.getTime) +
			          ".chess")
              println("file: " + f.getAbsolutePath)
			      if (f.createNewFile()) {
			    	  val output = Resource fromFile f.getAbsolutePath

              val moveLst = for {
                move <- moves.value
              } yield "\r\t<move note=" + (move \ "note") + ">\n\t\t<fen>" + (move \ "fen").as[String] + "</fen>\n\t\t<legal>"  +
//                  (move \ "legal") +
                  (move \ "legal").as[JsArray].value.map(Json.stringify(_).replace("\"", "")).reduce((x,y)=>x+" "+y) +
                  "</legal>\n\t</move>"

              val gameLst = List("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n",
                "<game>\n",
                "<fen>" + fen + "</fen>\n",
                "<history>" + history + "</history>\n",
                "<legal>" + legal + "</legal>\n")

              output.writeStrings(gameLst:::"<moves>"::moveLst.toList:::List("\r</moves>\n"):::List("</game>"))(scalax.io.Codec.UTF8)

//			    	  output.writeStrings(List("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n",
//			    			  					"<game>\n",
//			    			  				   "<fen>" + fen + "</fen>\n",
//			    			  				   "<history>" + history + "</history>\n",
//			    			  				   "<legal>" + legal + "</legal>\n",
//			    			  				   "</game>" ))(scalax.io.Codec.UTF8)
			      }
//			      Ok.sendFile(f, fileName = (name) => "foo.xml").as("application/force-download")
//			      Ok.sendFile(f, true).withHeaders(CONTENT_TYPE -> "application/force-download",
//			      					  CONTENT_DISPOSITION -> ("attachment; filename="+f.getName))
//			      SimpleResult(
//			    		  header = ResponseHeader(200, Map(CONTENT_TYPE -> "application/force-download")),
//			    		  body = Enumerator(f.getName))
			      Ok(f.getName /*"foo.xml"*/)/*.as("application/force-download")*/
            }.getOrElse { BadRequest("Missing parameter [moves]") }
			    }.getOrElse { BadRequest("Missing parameter [userid]") }
		    }.getOrElse { BadRequest("Missing parameter [legal]") }
	    }.getOrElse { BadRequest("Missing parameter [history]") }
    }.getOrElse { BadRequest("Missing parameter [fen]") }
  }

  def loadGame = Action(parse.multipartFormData) { request =>
    request.body.file("gamefile").map { gamefile =>
    import java.io.File
    val filename = gamefile.filename 
    val contentType = gamefile.contentType
    val f = new File("/tmp/" + new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(Calendar.getInstance().getTime()) + ".xml") 
    gamefile.ref.moveTo(f)
    try {
    	val game = scala.xml.XML.load(scala.xml.Source.fromFile(f))
		val fen = (game \ "fen").text
		val history = (game \ "history").text
		val legal = (game \ "legal").text
    val moves = (game \ "moves" \\ "move")

//      println("======================================")
//      println(moves)
      println("======================================")

      moves.foreach((z)=>println(z + "\nELEMENT"))
      val moves2 = Json.arr(
        moves.map((move) => {
        Json.obj(
          "note" -> move.attribute("note").get.toString,
          "fen" -> (move \ "fen").text.toString,
          "legal" -> (move \ "legal").text.toString
        )
        }
        )
      )
    println(moves2.getClass + " :: " + moves2.value.length)

    val moves3 = Nil

    val notes = moves
//      println("======================================")
//      notes.foreach((z)=>println(z + "\nELEMENT"))
		request.session.get("ID").map { id =>
		GameEngine.newGame(id)
		f.delete
			Ok(Json.toJson(
				Map("status" -> "OK",
						"data" -> "File uploaded",
						"error" -> "", 
						"fen" -> fen,
						"history" -> history,
            "moves" -> Json.stringify(moves2),
						"newlegal" -> legal)))
    	}.getOrElse {
    		Ok(Json.toJson( Map("status" -> "Unsuccess",
				    "data" -> "Id not found",
					"error" -> "ID not found")))
    	}
	} catch {
      case e : Exception =>
        println("Exception: " + e.getMessage())
        Ok(Json.toJson(
          Map("status" -> "Unsuccess",
              "data" -> "File doesn't uploaded",
            "error" -> e.getMessage())))
      }
      }.getOrElse {
        Ok(Json.toJson(
            Map("status" -> "Unsuccess",
                "data" -> "File not found",
              "error" -> "File not found")))
      }
  }

  def game = Action {
    Ok(views.html.index())
  }

  def stopPonder = Action { request =>
    println("Stop ponder")
    request.session.get("ID").map { id =>
      GameEngine.send(id, "stop")
    }
  	println("Stop app")
    Ok("a")
  }
}
