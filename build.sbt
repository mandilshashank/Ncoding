import play.sbt.PlayImport._

name := "NCoding"

version := "1.0.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

autoScalaLibrary := false