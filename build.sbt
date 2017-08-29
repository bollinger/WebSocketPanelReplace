import _root_.sbt.Keys._

name := "Websocket"

organization := "com.starjar"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.3"

seq(webSettings :_*)


// see http://comments.gmane.org/gmane.comp.lang.scala.simple-build-tool/1609
unmanagedResourceDirectories in Compile <++= baseDirectory { base =>
 (base / "src" / "main" / "scala") ::
 (base / "src" / "main" / "resources") ::
 Nil
}









libraryDependencies ++= {
 val wicketV = "7.8.0"
 Seq(
  "org.apache.wicket" % "wicket-core" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-extensions" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-util" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-request" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-datetime" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-ioc" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-guice" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-devutils" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-native-websocket-core" % wicketV withSources() withJavadoc(),
  "org.apache.wicket" % "wicket-native-websocket-javax" % wicketV
 )
}



libraryDependencies += "org.eclipse.jetty" % "jetty-webapp" % "9.1.0.v20131115" % "container"

libraryDependencies += "org.eclipse.jetty" % "jetty-plus" % "9.1.0.v20131115" % "container"

libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"





//
// Bootstrap to make things less ugly.
//

libraryDependencies += "de.agilecoders.wicket" % "wicket-bootstrap-core" % "0.10.6"

libraryDependencies += "de.agilecoders.wicket" % "wicket-bootstrap-extensions" % "0.10.6"

libraryDependencies += "de.agilecoders.wicket" % "wicket-bootstrap-themes" % "0.10.6"



libraryDependencies += "com.googlecode.wicket-jquery-ui" % "wicket-jquery-ui" % "7.2.0"

libraryDependencies += "com.googlecode.wicket-jquery-ui" % "wicket-jquery-ui-calendar" % "7.2.0"

libraryDependencies += "com.googlecode.wicket-jquery-ui" % "wicket-jquery-ui-theme-cupertino" % "7.2.0"



libraryDependencies ++= {
 val akkaV = "2.4.14"
 Seq(

 "com.typesafe.akka" %% "akka-actor" % akkaV,
// "com.typesafe.akka" %% "akka-agent" % akkaV,
///  "com.typesafe.akka" %% "akka-camel" % akkaV,
// "com.typesafe.akka" %% "akka-cluster" % akkaV,
// "com.typesafe.akka" %% "akka-cluster-metrics" % akkaV,
// "com.typesafe.akka" %% "akka-cluster-sharding" % akkaV,
// "com.typesafe.akka" %% "akka-cluster-tools" % akkaV,
// "com.typesafe.akka" %% "akka-contrib" % akkaV,
// "com.typesafe.akka" %% "akka-http-core" % akkaV,
// "com.typesafe.akka" %% "akka-http-testkit" % akkaV,
// "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaV,
// "com.typesafe.akka" %% "akka-osgi" % akkaV,
 "com.typesafe.akka" %% "akka-persistence" % akkaV,
// "com.typesafe.akka" %% "akka-persistence-tck" % akkaV,
// "com.typesafe.akka" %% "akka-remote" % akkaV,
// "com.typesafe.akka" %% "akka-slf4j" % akkaV,
 "com.typesafe.akka" %% "akka-stream" % akkaV
// "com.typesafe.akka" %% "akka-stream-testkit" % akkaV,
// "com.typesafe.akka" %% "akka-testkit" % akkaV,
// "com.typesafe.akka" %% "akka-distributed-data-experimental" % akkaV,
// "com.typesafe.akka" %% "akka-typed-experimental" % akkaV,
// "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
// "com.typesafe.akka" %% "akka-http-jackson-experimental" % akkaV,
// "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV,
// "com.typesafe.akka" %% "akka-http-xml-experimental" % akkaV,
// "com.typesafe.akka" %% "akka-persistence-query-experimental" % akkaV,
// "com.typesafe.akka" %% "akka-typed-experimental" % akkaV
 )
}











libraryDependencies += "com.google.inject" % "guice" % "4.1.0"

libraryDependencies += "com.google.inject.extensions" % "guice-servlet" % "4.1.0"

libraryDependencies += "org.log4s" %% "log4s" % "1.3.5"

libraryDependencies += "org.slf4j" % "slf4j-jdk14" % "1.7.12"

libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"



