
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
object main extends BaseScalaTemplate[play.api.templates.Html,Format[play.api.templates.Html]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Html,play.api.templates.Html] {

    /**/
    def apply/*1.2*/(title: String)(content: Html):play.api.templates.Html = {
        _display_ {

Seq[Any](format.raw/*1.32*/("""

<!DOCTYPE html>
<html>
<head>
        <title>"""),_display_(Seq[Any](/*6.17*/title)),format.raw/*6.22*/("""</title>
        <meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
"""),format.raw/*8.126*/("""
        <link rel="stylesheet" type="text/css" media="screen" href=""""),_display_(Seq[Any](/*9.70*/routes/*9.76*/.Assets.at("stylesheets/chess.css"))),format.raw/*9.111*/("""">
        <link rel="stylesheet" type="text/css" media="screen" href=""""),_display_(Seq[Any](/*10.70*/routes/*10.76*/.Assets.at("stylesheets/settings.css"))),format.raw/*10.114*/("""">
        <link rel="stylesheet" type="text/css" media="screen" href=""""),_display_(Seq[Any](/*11.70*/routes/*11.76*/.Assets.at("stylesheets/bootstrap.css"))),format.raw/*11.115*/("""">
        <link rel="stylesheet" type="text/css" media="screen" href=""""),_display_(Seq[Any](/*12.70*/routes/*12.76*/.Assets.at("stylesheets/bootstrap-responsive.css"))),format.raw/*12.126*/("""">
        <link rel="stylesheet" type="text/css" media="screen" href=""""),_display_(Seq[Any](/*13.70*/routes/*13.76*/.Assets.at("stylesheets/main.css"))),format.raw/*13.110*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*14.59*/routes/*14.65*/.Assets.at("images/favicon.png"))),format.raw/*14.97*/("""">
"""),format.raw/*17.125*/("""
        <script type="text/javascript" src=""""),_display_(Seq[Any](/*18.46*/routes/*18.52*/.Assets.at("javascripts/jquery-1.7.1.min.js"))),format.raw/*18.97*/("""" charset="UTF-8"></script>
		<script type="text/javascript" src=""""),_display_(Seq[Any](/*19.40*/routes/*19.46*/.Assets.at("javascripts/jquery.blockUI.js"))),format.raw/*19.89*/(""""></script>
        <script type="text/javascript" src=""""),_display_(Seq[Any](/*20.46*/routes/*20.52*/.Assets.at("javascripts/bootstrap.js"))),format.raw/*20.90*/("""" charset="UTF-8"></script>
        <script type="text/javascript" src=""""),_display_(Seq[Any](/*21.46*/routes/*21.52*/.Assets.at("javascripts/jscripts.js"))),format.raw/*21.89*/("""" charset="UTF-8"></script>
        <script type="text/javascript" src=""""),_display_(Seq[Any](/*22.46*/routes/*22.52*/.Assets.at("javascripts/webkit.js"))),format.raw/*22.87*/("""" charset="UTF-8"></script>
        <script type="text/javascript" src=""""),_display_(Seq[Any](/*23.46*/routes/*23.52*/.Assets.at("javascripts/chess.js"))),format.raw/*23.86*/("""" charset="UTF-8"></script>
</head>
    <body onunload="unload()">
     <div id="header">
     	<h1 id="welcome"></h1>
                <div id="options">
"""),format.raw/*35.3*/("""
                    |
                    <span title="Settings" class="jslink" id="fb-trigger-settings">Settings</span>
                    |
                    <span title="About" class="jslink" id="fb-trigger-about">About</span>
                </div>
        </div>
        
        <script type="text/javascript" charset="utf-8">
</script>
   <div id="content">
        """),_display_(Seq[Any](/*46.10*/content)),format.raw/*46.17*/("""
      </div>
      <div id="footer">The chess uses <a href="http://mediocrechess.sourceforge.net/">Mediocre</a> engine by 
      <a href="mailto:mediocrechess@gmail.com">Jonatan Pettersson</a>. Design and web programming by 
      <a href="mailto:mjelnr@gmail.com">Daniil Monakhov</a>.<br>Gens una sumus</div>
    </body>
</html>
"""))}
    }
    
    def render(title:String,content:Html): play.api.templates.Html = apply(title)(content)
    
    def f:((String) => (Html) => play.api.templates.Html) = (title) => (content) => apply(title)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Wed Jul 10 23:03:42 MSK 2013
                    SOURCE: /home/torquemada/SD/workspace/ChessPlay/app/views/main.scala.html
                    HASH: 8d2eca5f292afe0170ac46e92746ff7a9df6daa6
                    MATRIX: 509->1|616->31|699->79|725->84|839->295|944->365|958->371|1015->406|1123->478|1138->484|1199->522|1307->594|1322->600|1384->639|1492->711|1507->717|1580->767|1688->839|1703->845|1760->879|1857->940|1872->946|1926->978|1958->1327|2040->1373|2055->1379|2122->1424|2225->1491|2240->1497|2305->1540|2398->1597|2413->1603|2473->1641|2582->1714|2597->1720|2656->1757|2765->1830|2780->1836|2837->1871|2946->1944|2961->1950|3017->1984|3198->2413|3612->2791|3641->2798
                    LINES: 19->1|22->1|27->6|27->6|29->8|30->9|30->9|30->9|31->10|31->10|31->10|32->11|32->11|32->11|33->12|33->12|33->12|34->13|34->13|34->13|35->14|35->14|35->14|36->17|37->18|37->18|37->18|38->19|38->19|38->19|39->20|39->20|39->20|40->21|40->21|40->21|41->22|41->22|41->22|42->23|42->23|42->23|48->35|59->46|59->46
                    -- GENERATED --
                */
            