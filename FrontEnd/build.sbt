name := "SOCAssignment2"
 
version := "1.0" 
      
lazy val `socassignment2` = (project in file(".")).enablePlugins(PlayJava,PlayScala,PlayEbean)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      

resolvers ++= Seq("Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/")

scalaVersion := "2.12.2"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.11"
libraryDependencies += "org.json" % "json" % "20160810"
libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice , evolutions, javaWs, ws, "javax.json" % "javax.json-api" % "1.1.4", "mysql" % "mysql-connector-java" % "8.0.11")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      