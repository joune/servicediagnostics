package servicediagnostics.impl

import servicediagnostics._
import org.apache.felix.scr.Component
import org.apache.felix.scr.Reference
import org.apache.felix.scr.ScrService
import scala.collection.JavaConversions._

class DSNotAvail extends ServiceDiagnosticsPlugin {

  var scrService:ScrService = _ //injected

  // same algo as DMNotAvail.. see comments there!
  override def getUnresolvedDependencies:Map[String, List[Dependency]] = {
    val comps:Map[String, Component] = 
      (for (c <- Option(scrService.getComponents).flatten;
            s <- Option(c.getServices).flatten)
          yield (s, c)).toMap

    val compNames = comps.keySet
    
    (for (kv <- comps.filter(kv => kv._2.getState == Component.STATE_UNSATISFIED);
          unavail = kv._2.getReferences.view
                              .filterNot(_ isSatisfied)
                              .filterNot(_ isOptional)
                              .filterNot(ref => compNames.contains(ref.getName))
                              .map(ref => new Dependency(ref.getServiceName, ref.getTarget)).force.toList;
          if (unavail nonEmpty))
        yield (kv._1, unavail)).toMap
  }
}

