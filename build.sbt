name := "onlineMealOrdering"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.10.0"

resolvers ++= Seq(
  "Sunlights 3rd party" at "http://192.168.0.97:8081/nexus/content/repositories/thirdparty",
  "Sunlights snapshots" at "http://192.168.0.97:8081/nexus/content/repositories/snapshots/",
  "Sunlights releases" at "http://192.168.0.97:8081/nexus/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  javaCore,
  javaJdbc,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  cache,
  javaWs,
  filters,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "commons-beanutils" % "commons-beanutils" % "1.9.2",
  "commons-io" % "commons-io" % "2.4",
  "commons-net" % "commons-net" % "1.4.1",
  "com.google.guava" % "guava" % "18.0",
  "rapid" % "xsqlbuider" % "1.0.4",
  "org.apache.poi" % "poi" % "3.10.1",
  "org.apache.poi" % "poi-ooxml" % "3.10.1",
  "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.6",
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.webjars" % "jquery" % "1.11.1",
  "org.webjars" % "bootstrap" % "3.1.1-2" exclude("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.2.18" exclude("org.webjars", "jquery"),
  "org.webjars" % "angular-ui-router" % "0.2.11-1",
  "org.webjars" % "nervgh-angular-file-upload" % "1.1.5" ,
  "org.webjars" % "angular-ui" % "0.4.0-3",
  "org.webjars" % "ng-grid" % "2.0.14",
  "org.webjars" % "angular-ui-bootstrap" % "0.11.2",
  "org.webjars" % "angular-ui-date" % "0.0.5",
  "ch.ethz.ganymed" % "ganymed-ssh2" % "build210",
  "net.sourceforge.jexcelapi" % "jxl" % "2.6.10"
)

//sources in (Compile,doc) := Seq.empty
//
//publishArtifact in (Compile, packageDoc) := false

// Configure the steps of the asset pipeline (used in stage and dist tasks)
// rjs = RequireJS, uglifies, shrinks to one file, replaces WebJars with CDN
// digest = Adds hash to filename
// gzip = Zips all assets, Asset controller serves them automatically when client accepts them

//pipelineStages := Seq(rjs, digest)




