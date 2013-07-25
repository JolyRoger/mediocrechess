
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
object settings extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(value: String):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.17*/("""
<div class="generic_dialog" id="fb-modal-settings">
	<div class="generic_dialog_popup" style="top: 70px; ">
		<table class="pop_dialog_table" id="pop_dialog_table"
			style="width: 532px; height: 230px;" >
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
							<span>Settings</span>
						</h2>
						<div class="dialog_content">
							<div class="dialog_body">
								<div id="settingsL">
									<label for="showmoves">Show available moves</label>
									<input id="showmoves" type="checkbox" 
										onchange="showAvailableMoves(this)" checked/>
									<br><hr>
									<div id="players" onclick="setPlayers()">
										Select players<br><br>	
										<label for="ai_ai">Mediocre vs. Mediocre</label>
										<input id="ai_ai" type="radio" name="players" value="ai_ai"/><br> 
										<label for="human_human">Human vs. Human</label>
										<input id="human_human" type="radio" name="players" value="human_human"/><br> 
										<label for="ai_human">Mediocre vs. Human</label>
										<input id="ai_human" type="radio" name="players" value="ai_human" checked/><br> 
									</div>
								</div>
								<div id="settingsR">
									<label disabled="true" style="color: #a9a9a9;" for="showtimer">Show timer</label>
									<input disabled="true" id="showtimer" type="checkbox"/>
									<br><hr>
									Think time<br><br>
									<div id="ponderTime" onclick="setPonderTime()">
										<label for="time1">&nbsp;1 sec.</label> 
										<input id="time1" type="radio" name="pondertime" value="onesec"/><br>
										<label for="time5">&nbsp;5 sec.</label> 
										<input id="time5" type="radio" name="pondertime" value="fivesec" checked/><br>
										<label for="time10">&nbsp;10 sec.</label> 
										<input id="time10" type="radio" name="pondertime" value="tensec"/><br>
										<label disabled="true" for="timeinfinite" style="color: #a9a9a9">&nbsp;Infinite</label>
										<input disabled="true" id="timeinfinite" type="radio" name="pondertime" value="infinite"/><br>
																				 
									</div>
								</div>

								<div class="dialog_buttons">
"""),format.raw/*55.58*/("""
									<input type="button" value="Close" name="close"
										class="inputsubmit" id="fb-close-settings" />
								</div>
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
                    SOURCE: /home/torquemada/SD/workspace/ChessPlay/app/views/tags/settings.scala.html
                    HASH: 9d2124b904bc851d74f4a0e2d3a4ccf42d5c6006
                    MATRIX: 513->1|605->16|2960->2457
                    LINES: 19->1|22->1|75->55
                    -- GENERATED --
                */
            