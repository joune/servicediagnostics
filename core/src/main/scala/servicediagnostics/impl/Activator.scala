package servicediagnostics.impl

import servicediagnostics._
import org.osgi.framework.BundleContext
import org.osgi.service.http.HttpService

import org.apache.felix.dm.DependencyActivatorBase
import org.apache.felix.dm.DependencyManager

import servicediagnostics.webconsole.Servlet
import javax.servlet.http.HttpServlet

import scala.collection.mutable.Map
import scala.collection.JavaConversions._

class Activator extends DependencyActivatorBase
{
  override def init(bc:BundleContext, dm:DependencyManager) 
  {
    // register our diagnostics service and its plugins
    dm.add(createComponent
      .setInterface(classOf[ServiceDiagnosticsPlugin].getName, null)
      .setImplementation(new servicediagnostics.impl.DMNotAvail(bc)))

    dm.add(createComponent
      .setInterface(classOf[ServiceDiagnosticsPlugin].getName, null)
      .setImplementation(classOf[servicediagnostics.impl.DSNotAvail])
      .add(createServiceDependency
        .setService(classOf[org.apache.felix.scr.ScrService])
        .setAutoConfig("scrService")
        setRequired(true)))

    dm.add(createComponent
      .setInterface(classOf[ServiceDiagnostics].getName, null)
      .setImplementation(new servicediagnostics.impl.ServiceDiagnosticsImpl(bc))
      .add(createServiceDependency
        .setService(classOf[ServiceDiagnosticsPlugin])
        .setCallbacks("addPlugin", null, null)
        .setRequired(false)))

    // register the servlet used by webconsole plugin
    dm.add(createComponent
        .setInterface(classOf[HttpServlet].getName, null)
        .setImplementation(classOf[Servlet])
        .add(createServiceDependency
          .setService(classOf[ServiceDiagnostics])
          .setRequired(true)
          .setAutoConfig("engine"))
        .add(createServiceDependency
          .setService(classOf[HttpService])
          .setRequired(true)
          .setCallbacks("setHttpService", null, null)))

    // and the webconsole plugin itself
    dm.add(createComponent
        .setInterface(classOf[javax.servlet.Servlet].getName, Map("felix.webconsole.label" -> "servicegraph"))
        .setImplementation(classOf[servicediagnostics.webconsole.WebConsolePlugin]))

  }

  override def destroy(bc:BundleContext, dm:DependencyManager) = {}
}
