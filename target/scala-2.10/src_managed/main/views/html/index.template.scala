
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.Html] {

    /**/
    def apply():play.api.templates.Html = {
        _display_ {import tags._


Seq[Any](_display_(Seq[Any](/*2.2*/main(title = "Mediocre Chess")/*2.32*/ {_display_(Seq[Any](format.raw/*2.34*/("""
	<form id="loadgame" accept="application/octet-stream">
		<input id="gamefile" type="file" size="45" name="gamefile" class="input">
	</form>

	<div class="board">
		<img src=""""),_display_(Seq[Any](/*8.14*/routes/*8.20*/.Assets.at("images/empty_board448.png"))),format.raw/*8.59*/("""">
		<div class="promotion">
			<img src=""""),_display_(Seq[Any](/*10.15*/routes/*10.21*/.Assets.at("images/promotion.png"))),format.raw/*10.55*/("""">
			<div id="queen" class="promoted piece"></div>
			<div id="rook" class="promoted piece"></div>
			<div id="bishop" class="promoted piece"></div>
			<div id="knight" class="promoted piece"></div>
		</div>
		<script type="text/javascript">init()</script>
	</div>
	
	<div id="notation"> </div>
	<div id="btn-group" class="btn-group">
        <div style="height: 100%;">
            <button name="newgame" value="new" class="btn handlebtn" onclick="newPosition('8/8/4P3/8/8/k5K1/8 w - - 10 40')">New game</button>
            <button name="savegame" value="save" class="btn handlebtn" onclick="saveGame()">Save game</button>
            <button name="loadgame" value="load" class="btn handlebtn" onclick="
                $(':file').click()">Load game</button>
    """),format.raw/*26.107*/("""
            <button name="moveback" value="moveback" class="btn handlebtn" onclick="doMoveBack()">Move back</button>
            <button name="turnsides" value="turnsides" class="btn handlebtn" onclick="turnSide()">Turn sides</button>
        </div>
        <div style="height: 40px; margin-top: -77px;">
            <button id="movebtn" name="move" value="move" class="btn handlebtn" onclick="updatePosition()">MOVE</button>
        </div>
        <div id="showMoves">

"""),format.raw/*42.3*/("""
            <button id="movedown" name="movedown" value="movedown" class="btn handlebtn" onclick="moveDownShow()">&lt;&lt;</button>
            <button id="moveup" name="moveup" value="moveup" class="btn handlebtn" onclick="moveUpShow()">&gt;&gt;</button>
        </div>
    </div>
	
	<img src=""""),_display_(Seq[Any](/*48.13*/routes/*48.19*/.Assets.at("images/loading.gif"))),format.raw/*48.51*/("""">
	
	<div id="winpanel"></div>
	<div id="wintitlepanel">
		<span id="wintitle">Unknown</span>
	</div>
	"""),_display_(Seq[Any](/*54.3*/settings("aaa"))),format.raw/*54.18*/("""
    """),_display_(Seq[Any](/*55.6*/about("bbb"))),format.raw/*55.18*/("""
	<output></output>
""")))})))}
    }
    
    def render(): play.api.templates.Html = apply()
    
    def f:(() => play.api.templates.Html) = () => apply()
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Jul 10 23:03:42 MSK 2013
                    SOURCE: /home/torquemada/SD/workspace/ChessPlay/app/views/index.scala.html
                    HASH: 27404b7c0ec4b359d851cd39f13c5ba5d42e0100
                    MATRIX: 592->16|630->46|669->48|881->225|895->231|955->270|1034->313|1049->319|1105->353|1900->1221|2399->2201|2732->2498|2747->2504|2801->2536|2941->2641|2978->2656|3019->2662|3053->2674
                    LINES: 23->2|23->2|23->2|29->8|29->8|29->8|31->10|31->10|31->10|47->26|56->42|62->48|62->48|62->48|68->54|68->54|69->55|69->55
                    -- GENERATED --
                */
            