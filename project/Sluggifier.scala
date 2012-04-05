import sbt._
import Keys._

import Helpers._
import Dependencies._

object Sluggifier extends Build{
  lazy val sluggifier = project(
    "Sluggifier",
    "0.0.1",
    Seq(
     icu4j
    ),
    Seq(
    )
  )
}

