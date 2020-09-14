name := """SOCAssignment2-Map"""
organization := "cmu.edu.soc"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.11"
libraryDependencies += "org.json" % "json" % "20160810"