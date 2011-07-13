package servicediagnostics.sample

import aQute.bnd.annotation.component._

@Component(immediate=true, provide=Array(classOf[TestDS])) class TestDS 

@Component(provide=Array(classOf[DS1])) class DS1 {
  @Reference def setDS2(s:DS2) = {}
}

@Component(provide=Array(classOf[DS2])) class DS2 {
  @Reference def setDM3(s:DM3) = {}
}

@Component(provide=Array(classOf[DS3])) class DS3 {
  @Reference def setMap(s:java.util.Map[String,String]) = {} //not available
}
