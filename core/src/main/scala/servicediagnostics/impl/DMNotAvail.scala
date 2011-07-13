package servicediagnostics.impl

import servicediagnostics._
import org.apache.felix.dm.DependencyManager
import org.apache.felix.dm.ComponentDeclaration
import org.apache.felix.dm.ComponentDeclaration._
import org.apache.felix.dm.ComponentDependencyDeclaration
import org.apache.felix.dm.ComponentDependencyDeclaration._
import org.osgi.framework.BundleContext
import org.osgi.framework.ServiceReference
import scala.collection.JavaConversions._

class DMNotAvail(val bc:BundleContext) extends ServiceDiagnosticsPlugin {

  override def getUnresolvedDependencies:Map[String, List[Dependency]] = {
    // build a map(name -> comp) of all ComponentDeclarations known to DM, from each DependencyManager instance
    val comps:Map[String, ComponentDeclaration] = 
      (for(dm <- DependencyManager.getDependencyManagers;
          comp <- dm.asInstanceOf[DependencyManager].getComponents; 
          cd = comp.asInstanceOf[ComponentDeclaration]) 
        // yield (k,v) builds a list of (k,v), the result is then turned into a map
        yield (cd.getName.takeWhile(_ != '('), cd)) toMap
    
    val compNames = comps.keySet

    // build and return a map(name -> List(unavail)) of unavailable dependencies:
    // filter out registered services from the known ComponentDeclarations
    (for(kv <- comps.filter(kv => kv._2.getState == STATE_UNREGISTERED);
        // kv._1 is the name, kv._2 is the component
        // here, we lookup the component's list of dependencies
        // filtering out the ones available and the ones already known to DM, to keep only leafs
        // (view/force added for performance)
        unavail = kv._2.getComponentDependencies.view
                      .filter(dep => dep.getState == STATE_UNAVAILABLE_REQUIRED)
                      .filterNot(dep => compNames.contains(dep.getName))
                      .map(dep => new Dependency(dep.getName, ""/*dep.getFilter*/)).force.toList;
        if (unavail nonEmpty))
      yield (kv._1, unavail)) toMap
  }
}
