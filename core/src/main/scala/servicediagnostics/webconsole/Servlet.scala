package servicediagnostics.webconsole

import java.io.PrintStream

import org.osgi.service.http.HttpContext
import org.osgi.service.http.HttpService
import javax.servlet.http._

import scala.collection.JavaConversions._
import scala.collection.mutable.{Map => mMap}
import org.json.JSONObject

import servicediagnostics.ServiceDiagnostics

class Servlet extends HttpServlet {

  var engine:ServiceDiagnostics = _ //injected by DM

  def setHttpService(hs:HttpService) = {
    val hc = hs.createDefaultHttpContext
    hs.registerServlet("/servicegraph/json", this, null, hc)
    hs.registerResources("/servicegraph", "/html", hc)
  }

  override def service(req:HttpServletRequest, resp:HttpServletResponse) = {
      val cmd = req.getPathInfo
      if(cmd.endsWith("all")) reply(resp, engine.allServices)
      else if(cmd.endsWith("notavail")) reply(resp, engine.notavail)
      else println("Unrecognized cmd: "+cmd)
  }

  def reply(resp:HttpServletResponse, map:Map[String,List[AnyRef]]) = 
        new PrintStream(resp.getOutputStream, true).println(
          new JSONObject(asJavaMap(mMap() ++ map.map(kv => (kv._1, asJavaList(kv._2))))))
        //new PrintStream(resp.getOutputStream, true).println(render(decompose(map)))
}

