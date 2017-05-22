lazy val root = (project in file("."))
  .settings(
    name         := "valet-gen-slick-tables",
    scalaVersion := "2.11.8",
    libraryDependencies ++= List(
      "com.typesafe.slick" %% "slick" % "3.1.1",
      "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
      "org.slf4j" % "slf4j-nop" % "1.7.19",
      "mysql" % "mysql-connector-java" % "5.1.40"
    ),
    sourceGenerators in Compile <+= (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
      val outputDir = file(".").getCanonicalPath() + "/app"
      val url = "jdbc:mysql://127.0.0.1/test?characsbt ruterEncoding=UTF8&autoReconnect=true&useSSL=false"
      val jdbcDriver = "com.mysql.jdbc.Driver"
      val slickDriver = "slick.driver.MySQLDriver"
      val pkg = "models.entity"
      val user = "test"
      val password = "test"
      toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password), s.log))
      val fname = outputDir + "/models/autogen/tables/Tables.scala"
      Seq(file(fname))
    }
  )

//import slick.{ model => m }
//
//lazy val root = (project in file("."))
//  .settings(slickCodegenSettings:_*)
//  .settings(
//    name         := "valet-gen-slick-tables",
//    version      := "0.0.1",
//    scalaVersion := "2.11.8",
//    description  := "see https://github.com/tototoshi/sbt-slick-codegen",
//    libraryDependencies ++= Seq(
//      "joda-time" % "joda-time" % "2.7",
//      "org.joda" % "joda-convert" % "1.7",
//      "com.typesafe.slick" %% "slick" % "3.1.0",
//      "com.github.tototoshi" %% "slick-joda-mapper" % "2.1.0"
//    ),
//    slickCodegenDatabaseUrl := "jdbc:mysql://127.0.0.1/test?characterEncoding=UTF8&autoReconnect=true&useSSL=false",
//    slickCodegenDatabaseUser := "test",
//    slickCodegenDatabasePassword := "test",
//    slickCodegenDriver := slick.driver.MySQLDriver,
//    slickCodegenJdbcDriver := "com.mysql.jdbc.Driver",
//    slickCodegenOutputPackage := "models",
//    slickCodegenOutputDir := file( (file(".").getCanonicalPath() + "/app") ),
//    slickCodegenExcludedTables := Seq("schema_version"),
//    slickCodegenCodeGenerator := { (model:  m.Model) =>
//      new slick.codegen.SourceCodeGenerator(model) {
//        override def code =
//          "import com.github.tototoshi.slick.MySQLJodaSupport._\n" + "import org.joda.time.DateTime\n" + super.code
//        override def Table = new Table(_) {
//          override def Column = new Column(_) {
//            override def rawType = model.tpe match {
//              case "java.sql.Timestamp" => "DateTime" // kill j.s.Timestamp
//              case _ =>
//                super.rawType
//            }
//          }
//        }
//      }
//    },
//    sourceGenerators in Compile <+= slickCodegen
//  )
