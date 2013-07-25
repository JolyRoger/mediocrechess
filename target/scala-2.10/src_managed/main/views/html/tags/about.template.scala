
package views.html.tags

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
object about extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(value: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.17*/("""
<div class="generic_dialog" id="fb-modal-about">
	<div class="generic_dialog_popup" style="top: 70px; ">
		<table class="pop_dialog_table" id="pop_dialog_table"
               style="width: 532px;">
			<tbody>
				<tr>
					<td class="pop_topleft" />
					<td class="pop_border pop_top" />
					<td class="pop_topright" />
				</tr>
				<tr>
					<td class="pop_border pop_side" />
					<td id="pop_content" class="pop_content">
						<h2 class="dialog_title">
							<span>About</span>
						</h2>
						<div class="dialog_content">
							<div class="dialog_body" style="height: 400px;">
                                <div style="height: 100%; overflow-y: scroll;">
                                    This is the chess web application based on Mediocre chess engine by Jonatan Peterson and Play web framework. Here you can play chess, save current game into XML on your computer and load it from there. Also you can take bad move back and look at previous moves.
                                    <br><br>
                                    1. Board
                                    <br><br>
                                    Play by moving pieces on the chess board. By default your side is white located down the board. You can change this pressing "MOVE" button and/or "Turn sides" button. If click on a piece of your color the clicked square is emphasized by yellow border. Squares where piece from bordered square can move are ensquared by red border.
                                    <br><br>
                                    There is settings popup window where you can set ponder time for computer and assign players.<br>
                                    Three options are possible.
                                    <br><br>
                                    First is Mediocre vs Medoicre. It means your computer will play with itself. Choose this radiobutton and press MOVE button. You will see that computer moves step by step until game is finished or you change this setting.
                                    <br><br>
                                    Second is Human vs Human. The computer won't response for your move on the board.<br>
                                    And the last is Human vs Mediocre. A move will be thought and done by computer if only you make a new one the board. If you press MOVE button computer generates a new move and passes turn to you.<br><br>
                                </div>
							</div>
                            <div class="dialog_buttons" style="margin: 10px;">
"""),format.raw/*37.101*/("""
                                <input type="button" value="Close" name="close"
                                       class="inputsubmit" id="fb-close-about" />
                            </div>
                        </div>
					</td>
					<td class="pop_border pop_side" />
				</tr>
				<tr>
					<td class="pop_bottomleft" />
					<td class="pop_border pop_bottom" />
					<td class="pop_bottomright" />
				</tr>
			</tbody>
		</table>
	</div>
</div>"""))}
    }
    
    def render(value:String): play.api.templates.Html = apply(value)
    
    def f:((String) => play.api.templates.Html) = (value) => apply(value)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Jul 10 23:03:42 MSK 2013
                    SOURCE: /home/torquemada/SD/workspace/ChessPlay/app/views/tags/about.scala.html
                    HASH: d69ff9934cc14a1d11d0187922f476d9378dd3a9
                    MATRIX: 510->1|602->16|3186->2671
                    LINES: 19->1|22->1|58->37
                    -- GENERATED --
                */
            