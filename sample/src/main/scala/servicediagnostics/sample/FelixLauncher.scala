package servicediagnostics.sample

import java.util.HashMap
import java.util.ServiceLoader
import scala.io.Source


import org.osgi.framework.launch.FrameworkFactory
import org.apache.felix.main.AutoProcessor

object FelixLauncher
{
  def main(bundles:Array[String]) = 
  {
    // launch felix
    val factory:FrameworkFactory = ServiceLoader.load(classOf[FrameworkFactory],
      getClass.getClassLoader).iterator.next
    val felixProps = new HashMap[String, String]
    felixProps.put("org.osgi.framework.storage", ".cache")
    felixProps.put("org.osgi.framework.storage.clean", "onFirstInit")
    felixProps.put("felix.auto.install", bundles.map("file:"+_).mkString(" "))
    felixProps.put("felix.auto.start", bundles.map("file:"+_).mkString(" "))
    felixProps.put("org.osgi.framework.bootdelegation", "sun.*,com.sun.*")

    //println("Launch Felix using "+factory+" and "+felixProps)
    val felix = factory.newFramework(felixProps)
    felix.init
    AutoProcessor.process(felixProps, felix.getBundleContext)
    felix.start
    //println("Felix started")
  }
}
