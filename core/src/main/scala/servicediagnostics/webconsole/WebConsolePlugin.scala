package servicediagnostics.webconsole

import org.apache.felix.webconsole.SimpleWebConsolePlugin
import javax.servlet.http._

class WebConsolePlugin extends SimpleWebConsolePlugin("servicegraph", "Service Graph", Array[String]()) {
  val TEMPLATE = readTemplateFile("/html/index.html")

  override def renderContent(req:HttpServletRequest, resp:HttpServletResponse) = resp.getWriter().print( TEMPLATE )
}
