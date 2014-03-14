name := "gitstat"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.kohsuke" % "github-api" % "1.47",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.2.3",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.2.3",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.2.3"
)     

play.Project.playJavaSettings


