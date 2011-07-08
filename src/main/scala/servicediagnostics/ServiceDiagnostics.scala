package servicediagnostics

trait ServiceDiagnostics {
  def notavail:Map[String, List[Dependency]]
  def allServices:Map[String, List[String]]
}
