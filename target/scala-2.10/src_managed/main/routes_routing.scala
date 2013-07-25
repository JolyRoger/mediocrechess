// @SOURCE:/home/torquemada/SD/workspace/ChessPlay/conf/routes
// @HASH:8ee6ff1e5906ad89ad8254104c7980deabd45698
// @DATE:Wed Jul 10 23:03:39 MSK 2013


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix  
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" } 


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:7
private[this] lazy val controllers_Application_init1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("init/"),DynamicPart("time", """[^/]+"""))))
        

// @LINE:8
private[this] lazy val controllers_Application_deleteID2 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("deleteID/"),DynamicPart("id", """[^/]+"""))))
        

// @LINE:9
private[this] lazy val controllers_Application_game3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("game"))))
        

// @LINE:10
private[this] lazy val controllers_Application_newPosition4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("new"))))
        

// @LINE:11
private[this] lazy val controllers_Application_update5 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("update/"))))
        

// @LINE:12
private[this] lazy val controllers_Application_update6 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("update/"),DynamicPart("move", """[^/]+"""))))
        

// @LINE:13
private[this] lazy val controllers_Application_stopPonder7 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("stopPonder"))))
        

// @LINE:14
private[this] lazy val controllers_Application_start8 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("start"))))
        

// @LINE:15
private[this] lazy val controllers_Application_next9 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("next/"),DynamicPart("playWithFen", """[^/]+"""))))
        

// @LINE:16
private[this] lazy val controllers_Application_rate10 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("rate"))))
        

// @LINE:17
private[this] lazy val controllers_Application_loadGame11 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("loadGame"))))
        

// @LINE:18
private[this] lazy val controllers_Application_saveGame12 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("saveGame"))))
        

// @LINE:19
private[this] lazy val controllers_Settings_settings13 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("settings"))))
        

// @LINE:20
private[this] lazy val controllers_Settings_getLegalMoves14 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("getLegalMoves/"),DynamicPart("playWithFen", """[^/]+"""))))
        

// @LINE:21
private[this] lazy val controllers_Settings_setPonderTime15 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("setPonderTime/"),DynamicPart("time", """[^/]+"""))))
        

// @LINE:26
private[this] lazy val controllers_Assets_at16 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+"""))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """init/$time<[^/]+>""","""controllers.Application.init(time:Int)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """deleteID/$id<[^/]+>""","""controllers.Application.deleteID(id:Int)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """game""","""controllers.Application.game"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """new""","""controllers.Application.newPosition()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """update/""","""controllers.Application.update(move:String = "")"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """update/$move<[^/]+>""","""controllers.Application.update(move:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """stopPonder""","""controllers.Application.stopPonder"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """start""","""controllers.Application.start"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """next/$playWithFen<[^/]+>""","""controllers.Application.next(playWithFen:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """rate""","""controllers.Application.rate"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """loadGame""","""controllers.Application.loadGame"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """saveGame""","""controllers.Application.saveGame"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """settings""","""controllers.Settings.settings"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """getLegalMoves/$playWithFen<[^/]+>""","""controllers.Settings.getLegalMoves(playWithFen:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """setPonderTime/$time<[^/]+>""","""controllers.Settings.setPonderTime(time:Int)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
       
    
def routes:PartialFunction[RequestHeader,Handler] = {        

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index, HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:7
case controllers_Application_init1(params) => {
   call(params.fromPath[Int]("time", None)) { (time) =>
        invokeHandler(controllers.Application.init(time), HandlerDef(this, "controllers.Application", "init", Seq(classOf[Int]),"GET", """""", Routes.prefix + """init/$time<[^/]+>"""))
   }
}
        

// @LINE:8
case controllers_Application_deleteID2(params) => {
   call(params.fromPath[Int]("id", None)) { (id) =>
        invokeHandler(controllers.Application.deleteID(id), HandlerDef(this, "controllers.Application", "deleteID", Seq(classOf[Int]),"GET", """""", Routes.prefix + """deleteID/$id<[^/]+>"""))
   }
}
        

// @LINE:9
case controllers_Application_game3(params) => {
   call { 
        invokeHandler(controllers.Application.game, HandlerDef(this, "controllers.Application", "game", Nil,"GET", """""", Routes.prefix + """game"""))
   }
}
        

// @LINE:10
case controllers_Application_newPosition4(params) => {
   call { 
        invokeHandler(controllers.Application.newPosition(), HandlerDef(this, "controllers.Application", "newPosition", Nil,"GET", """""", Routes.prefix + """new"""))
   }
}
        

// @LINE:11
case controllers_Application_update5(params) => {
   call(Param[String]("move", Right(""))) { (move) =>
        invokeHandler(controllers.Application.update(move), HandlerDef(this, "controllers.Application", "update", Seq(classOf[String]),"POST", """""", Routes.prefix + """update/"""))
   }
}
        

// @LINE:12
case controllers_Application_update6(params) => {
   call(params.fromPath[String]("move", None)) { (move) =>
        invokeHandler(controllers.Application.update(move), HandlerDef(this, "controllers.Application", "update", Seq(classOf[String]),"POST", """""", Routes.prefix + """update/$move<[^/]+>"""))
   }
}
        

// @LINE:13
case controllers_Application_stopPonder7(params) => {
   call { 
        invokeHandler(controllers.Application.stopPonder, HandlerDef(this, "controllers.Application", "stopPonder", Nil,"POST", """""", Routes.prefix + """stopPonder"""))
   }
}
        

// @LINE:14
case controllers_Application_start8(params) => {
   call { 
        invokeHandler(controllers.Application.start, HandlerDef(this, "controllers.Application", "start", Nil,"GET", """""", Routes.prefix + """start"""))
   }
}
        

// @LINE:15
case controllers_Application_next9(params) => {
   call(params.fromPath[String]("playWithFen", None)) { (playWithFen) =>
        invokeHandler(controllers.Application.next(playWithFen), HandlerDef(this, "controllers.Application", "next", Seq(classOf[String]),"POST", """""", Routes.prefix + """next/$playWithFen<[^/]+>"""))
   }
}
        

// @LINE:16
case controllers_Application_rate10(params) => {
   call { 
        invokeHandler(controllers.Application.rate, HandlerDef(this, "controllers.Application", "rate", Nil,"GET", """""", Routes.prefix + """rate"""))
   }
}
        

// @LINE:17
case controllers_Application_loadGame11(params) => {
   call { 
        invokeHandler(controllers.Application.loadGame, HandlerDef(this, "controllers.Application", "loadGame", Nil,"POST", """""", Routes.prefix + """loadGame"""))
   }
}
        

// @LINE:18
case controllers_Application_saveGame12(params) => {
   call { 
        invokeHandler(controllers.Application.saveGame, HandlerDef(this, "controllers.Application", "saveGame", Nil,"POST", """""", Routes.prefix + """saveGame"""))
   }
}
        

// @LINE:19
case controllers_Settings_settings13(params) => {
   call { 
        invokeHandler(controllers.Settings.settings, HandlerDef(this, "controllers.Settings", "settings", Nil,"GET", """""", Routes.prefix + """settings"""))
   }
}
        

// @LINE:20
case controllers_Settings_getLegalMoves14(params) => {
   call(params.fromPath[String]("playWithFen", None)) { (playWithFen) =>
        invokeHandler(controllers.Settings.getLegalMoves(playWithFen), HandlerDef(this, "controllers.Settings", "getLegalMoves", Seq(classOf[String]),"POST", """""", Routes.prefix + """getLegalMoves/$playWithFen<[^/]+>"""))
   }
}
        

// @LINE:21
case controllers_Settings_setPonderTime15(params) => {
   call(params.fromPath[Int]("time", None)) { (time) =>
        invokeHandler(controllers.Settings.setPonderTime(time), HandlerDef(this, "controllers.Settings", "setPonderTime", Seq(classOf[Int]),"GET", """""", Routes.prefix + """setPonderTime/$time<[^/]+>"""))
   }
}
        

// @LINE:26
case controllers_Assets_at16(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path
 GET     /assets/saves/*file               		controllers.Application.at(path="/public/saves", file)""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        
}
    
}
        