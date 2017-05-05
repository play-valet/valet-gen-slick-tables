package foo

object Launcher {

  def showUsage = {
    println(
      s"""
         | Usage:
         |        sbt "run hello world"
       """.stripMargin
    )
  }

  def main(args: Array[String]) {
    args.toList match {
      case name :: params if (name == "hello") => println(params.headOption.getOrElse(""))
      case _                                   => showUsage
    }
  }

}
