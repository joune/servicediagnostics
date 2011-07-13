STLIB=$INSTALL_DIR/autotest/lib
#scala \
java \
  -classpath $INSTALL_DIR/bundles/felix/felix.jar:$STLIB/scalatest-helpers.jar:$STLIB/scala-library-osgi.jar \
  com.alcatel.scala.test.FelixLauncher \
    servicediagnostics.jar \
    $INSTALL_DIR/bundles/felix/felix.jar\
    $INSTALL_DIR/bundles/repository/core/json.jar\
    $INSTALL_DIR/bundles/repository/core/org.apache.felix.dependencymanager.jar\
    $INSTALL_DIR/bundles/repository/core/org.apache.felix.scr.jar\
    $INSTALL_DIR/bundles/repository/core/org.osgi.compendium.jar\
    $INSTALL_DIR/bundles/felix/org.apache.felix.http.jetty.jar\
    $INSTALL_DIR/bundles/felix/org.apache.felix.webconsole.jar\
    $INSTALL_DIR/bundles/repository/core/org.apache.felix.shell.jar\
    $INSTALL_DIR/bundles/felix/org.apache.felix.dependencymanager.shell.jar\
    $STLIB/scala-library-osgi.jar\
    $STLIB/scalatest-helpers.jar\
    $STLIB/scalatest-1.3-osgi.jar |egrep -v "TestFailedException| at |\.\.\."
 
