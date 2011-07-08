package servicediagnostics.impl

import servicediagnostics._
import org.osgi.framework.BundleContext
import org.osgi.framework.ServiceReference
import org.osgi.framework.Constants.OBJECTCLASS
import scala.collection.mutable.Buffer

class ServiceDiagnosticsImpl(val bc:BundleContext) extends ServiceDiagnostics {
  val plugins:Buffer[ServiceDiagnosticsPlugin] = Buffer()

  def addPlugin(p:ServiceDiagnosticsPlugin) = plugins += p

  override def notavail = {
    // merge all notavails from plugins 
    // (kv stands for each key/value pair in a map)
    val merged = (for(p <- plugins; kv <- p.getUnresolvedDependencies) yield kv) toMap

    // remove remaining intermediates. ex: unresolved in DS -> unavailable in DM
    // and return the resulting map
    (for(kv <- merged; dep <- kv._2; if (merged.get(dep.name) isEmpty)) yield kv) toMap
  }

  override def allServices:Map[String,List[String]] = {
    val allrefs = bc.getAllServiceReferences(null, null)
    if (allrefs == null) return Map()

    def names(ref:ServiceReference):Array[String] = {
      val n = ref.getProperty(OBJECTCLASS)
      if (n != null) n.asInstanceOf[Array[String]] else Array()
    }
    def using(ref:ServiceReference):List[String] = {
      val u = ref.getUsingBundles
      if (u != null) u.toList.map(_ toString) else List()
    }

    (for(ref <- bc.getAllServiceReferences(null, null);
         name <- names(ref);
         u = using(ref);
         if (u.nonEmpty))
       yield (name, u)) toMap
  }
  
}
