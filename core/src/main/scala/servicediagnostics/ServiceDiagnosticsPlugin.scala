package servicediagnostics

trait ServiceDiagnosticsPlugin {
  /** 
   * returns a map of unresolved service name -&gt; list of missing deps 
   * leafs only: should not include intermediates
   * @return the unresolved service names
   */
  def getUnresolvedDependencies:Map[String, List[Dependency]] 

}

class Dependency(val name:String, val filter:String) {
  override def toString = name//+"("+filter+")" FIXME
}
