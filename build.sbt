name := """bookstore"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += javaJdbc
libraryDependencies += ehcache
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.1"
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.2"
