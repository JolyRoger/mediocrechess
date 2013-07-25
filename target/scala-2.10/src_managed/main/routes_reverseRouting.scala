// @SOURCE:/home/torquemada/SD/workspace/ChessPlay/conf/routes
// @HASH:8ee6ff1e5906ad89ad8254104c7980deabd45698
// @DATE:Wed Jul 10 23:03:39 MSK 2013

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString


// @LINE:26
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers {

// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:8
def deleteID(id:Int): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "deleteID/" + implicitly[PathBindable[Int]].unbind("id", id))
}
                                                

// @LINE:10
def newPosition(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "new")
}
                                                

// @LINE:17
def loadGame(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "loadGame")
}
                                                

// @LINE:15
def next(playWithFen:String): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "next/" + implicitly[PathBindable[String]].unbind("playWithFen", playWithFen))
}
                                                

// @LINE:7
def init(time:Int): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "init/" + implicitly[PathBindable[Int]].unbind("time", time))
}
                                                

// @LINE:16
def rate(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "rate")
}
                                                

// @LINE:13
def stopPonder(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "stopPonder")
}
                                                

// @LINE:12
// @LINE:11
def update(move:String): Call = {
   (move: @unchecked) match {
// @LINE:11
case (move) if move == "" => Call("POST", _prefix + { _defaultPrefix } + "update/")
                                                        
// @LINE:12
case (move) if true => Call("POST", _prefix + { _defaultPrefix } + "update/" + implicitly[PathBindable[String]].unbind("move", move))
                                                        
   }
}
                                                

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                

// @LINE:9
def game(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "game")
}
                                                

// @LINE:14
def start(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "start")
}
                                                

// @LINE:18
def saveGame(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "saveGame")
}
                                                
    
}
                          

// @LINE:26
class ReverseAssets {
    

// @LINE:26
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:21
// @LINE:20
// @LINE:19
class ReverseSettings {
    

// @LINE:20
def getLegalMoves(playWithFen:String): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "getLegalMoves/" + implicitly[PathBindable[String]].unbind("playWithFen", playWithFen))
}
                                                

// @LINE:19
def settings(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "settings")
}
                                                

// @LINE:21
def setPonderTime(time:Int): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "setPonderTime/" + implicitly[PathBindable[Int]].unbind("time", time))
}
                                                
    
}
                          
}
                  


// @LINE:26
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.javascript {

// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:8
def deleteID : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.deleteID",
   """
      function(id) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "deleteID/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("id", id)})
      }
   """
)
                        

// @LINE:10
def newPosition : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.newPosition",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "new"})
      }
   """
)
                        

// @LINE:17
def loadGame : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.loadGame",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "loadGame"})
      }
   """
)
                        

// @LINE:15
def next : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.next",
   """
      function(playWithFen) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "next/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("playWithFen", playWithFen)})
      }
   """
)
                        

// @LINE:7
def init : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.init",
   """
      function(time) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "init/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("time", time)})
      }
   """
)
                        

// @LINE:16
def rate : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.rate",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "rate"})
      }
   """
)
                        

// @LINE:13
def stopPonder : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.stopPonder",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "stopPonder"})
      }
   """
)
                        

// @LINE:12
// @LINE:11
def update : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.update",
   """
      function(move) {
      if (move == """ + implicitly[JavascriptLitteral[String]].to("") + """) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "update/"})
      }
      if (true) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "update/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("move", move)})
      }
      }
   """
)
                        

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

// @LINE:9
def game : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.game",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "game"})
      }
   """
)
                        

// @LINE:14
def start : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.start",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "start"})
      }
   """
)
                        

// @LINE:18
def saveGame : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.saveGame",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "saveGame"})
      }
   """
)
                        
    
}
              

// @LINE:26
class ReverseAssets {
    

// @LINE:26
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:21
// @LINE:20
// @LINE:19
class ReverseSettings {
    

// @LINE:20
def getLegalMoves : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Settings.getLegalMoves",
   """
      function(playWithFen) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "getLegalMoves/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("playWithFen", playWithFen)})
      }
   """
)
                        

// @LINE:19
def settings : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Settings.settings",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "settings"})
      }
   """
)
                        

// @LINE:21
def setPonderTime : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Settings.setPonderTime",
   """
      function(time) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "setPonderTime/" + (""" + implicitly[PathBindable[Int]].javascriptUnbind + """)("time", time)})
      }
   """
)
                        
    
}
              
}
        


// @LINE:26
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.ref {

// @LINE:18
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:8
def deleteID(id:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.deleteID(id), HandlerDef(this, "controllers.Application", "deleteID", Seq(classOf[Int]), "GET", """""", _prefix + """deleteID/$id<[^/]+>""")
)
                      

// @LINE:10
def newPosition(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.newPosition(), HandlerDef(this, "controllers.Application", "newPosition", Seq(), "GET", """""", _prefix + """new""")
)
                      

// @LINE:17
def loadGame(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.loadGame(), HandlerDef(this, "controllers.Application", "loadGame", Seq(), "POST", """""", _prefix + """loadGame""")
)
                      

// @LINE:15
def next(playWithFen:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.next(playWithFen), HandlerDef(this, "controllers.Application", "next", Seq(classOf[String]), "POST", """""", _prefix + """next/$playWithFen<[^/]+>""")
)
                      

// @LINE:7
def init(time:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.init(time), HandlerDef(this, "controllers.Application", "init", Seq(classOf[Int]), "GET", """""", _prefix + """init/$time<[^/]+>""")
)
                      

// @LINE:16
def rate(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.rate(), HandlerDef(this, "controllers.Application", "rate", Seq(), "GET", """""", _prefix + """rate""")
)
                      

// @LINE:13
def stopPonder(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.stopPonder(), HandlerDef(this, "controllers.Application", "stopPonder", Seq(), "POST", """""", _prefix + """stopPonder""")
)
                      

// @LINE:11
def update(move:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.update(move), HandlerDef(this, "controllers.Application", "update", Seq(classOf[String]), "POST", """""", _prefix + """update/""")
)
                      

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      

// @LINE:9
def game(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.game(), HandlerDef(this, "controllers.Application", "game", Seq(), "GET", """""", _prefix + """game""")
)
                      

// @LINE:14
def start(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.start(), HandlerDef(this, "controllers.Application", "start", Seq(), "GET", """""", _prefix + """start""")
)
                      

// @LINE:18
def saveGame(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.saveGame(), HandlerDef(this, "controllers.Application", "saveGame", Seq(), "POST", """""", _prefix + """saveGame""")
)
                      
    
}
                          

// @LINE:26
class ReverseAssets {
    

// @LINE:26
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path
 GET     /assets/saves/*file               		controllers.Application.at(path="/public/saves", file)""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:21
// @LINE:20
// @LINE:19
class ReverseSettings {
    

// @LINE:20
def getLegalMoves(playWithFen:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Settings.getLegalMoves(playWithFen), HandlerDef(this, "controllers.Settings", "getLegalMoves", Seq(classOf[String]), "POST", """""", _prefix + """getLegalMoves/$playWithFen<[^/]+>""")
)
                      

// @LINE:19
def settings(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Settings.settings(), HandlerDef(this, "controllers.Settings", "settings", Seq(), "GET", """""", _prefix + """settings""")
)
                      

// @LINE:21
def setPonderTime(time:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Settings.setPonderTime(time), HandlerDef(this, "controllers.Settings", "setPonderTime", Seq(classOf[Int]), "GET", """""", _prefix + """setPonderTime/$time<[^/]+>""")
)
                      
    
}
                          
}
                  
      