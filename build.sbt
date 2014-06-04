sbtPlugin := true

organization := "com.typesafe.sbt"

name := "sbt-stylus"

version := "1.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.typesafe" % "jstranspiler" % "1.0.0",
  "org.webjars" % "mkdirp" % "0.3.5",
  "org.webjars" % "stylus" % "0.45.1",
  "org.webjars" % "stylus-nib" % "1.0.2-1",
  "org.webjars" % "when-node" % "3.2.2"
)

resolvers ++= Seq(
  "Typesafe Releases Repository" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.url("sbt snapshot plugins", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots"))(Resolver.ivyStylePatterns),
  Resolver.sonatypeRepo("snapshots"),
  "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
  Resolver.mavenLocal
)

addSbtPlugin("com.typesafe.sbt" %% "sbt-js-engine" % "1.0.0")

publishMavenStyle := false

publishTo := {
  if (isSnapshot.value) Some(Classpaths.sbtPluginSnapshots)
  else Some(Classpaths.sbtPluginReleases)
}

scriptedSettings

scriptedLaunchOpts <+= version apply { v => s"-Dproject.version=$v" }
