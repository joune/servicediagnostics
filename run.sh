REPO=$HOME/.m2/repository
CLASSPATH=lib/scala-library-osgi.jar:$REPO/org/apache/felix/org.apache.felix.main/3.2.2/org.apache.felix.main-3.2.2.jar:sample/target/servicediagnostics.sample-1.0.0-SNAPSHOT.jar
#scala 
java -classpath $CLASSPATH servicediagnostics.sample.FelixLauncher \
  core/target/servicediagnostics.core-1.0.0-SNAPSHOT.jar\
  sample/target/servicediagnostics.sample-1.0.0-SNAPSHOT.jar\
  $REPO/org/apache/felix/org.apache.felix.main/3.2.2/org.apache.felix.main-3.2.2.jar\
  $REPO/org/apache/felix/org.apache.felix.dependencymanager/3.0.0/org.apache.felix.dependencymanager-3.0.0.jar\
  $REPO/org/apache/felix/org.apache.felix.scr/1.6.0/org.apache.felix.scr-1.6.0.jar\
  $REPO/org/osgi/org.osgi.compendium/4.2.0/org.osgi.compendium-4.2.0.jar\
  $REPO/org/apache/felix/org.apache.felix.http.jetty/2.2.0/org.apache.felix.http.jetty-2.2.0.jar\
  $REPO/org/apache/felix/org.apache.felix.webconsole/3.1.8/org.apache.felix.webconsole-3.1.8.jar\
  $REPO/org/apache/felix/org.apache.felix.shell/1.4.2/org.apache.felix.shell-1.4.2.jar\
  lib/json.jar\
  lib/scala-library-osgi.jar
 
