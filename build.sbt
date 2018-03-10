name := "dws-email"

version := "0.1"

scalaVersion := "2.12.4"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.simplejavamail" % "simple-java-mail" % "5.0.0.rc2-SNAPSHOT",
  "com.github.tototoshi" %% "scala-csv" % "1.3.5",
  "net.markenwerk" % "utils-mail-dkim" % "1.1.11"
)
