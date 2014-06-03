package com.typesafe.sbt.stylus

import sbt._
import sbt.Keys._
import com.typesafe.sbt.web._
import com.typesafe.sbt.jse.SbtJsTask
import spray.json._

object Import {

  object StylusKeys {
    val stylus = TaskKey[Seq[File]]("stylus", "Invoke the stylus compiler.")

    val compress = SettingKey[Boolean]("stylus-compress", "Compress output by removing some whitespaces.")
    val useNib = SettingKey[Boolean]("stylus-nib", "Use stylus nib.")
  }

}

object SbtStylus extends AutoPlugin {

  override def requires = SbtJsTask

  override def trigger = AllRequirements

  val autoImport = Import

  import SbtWeb.autoImport._
  import WebKeys._
  import SbtJsTask.autoImport.JsTaskKeys._
  import autoImport.StylusKeys._

  val stylusUnscopedSettings = Seq(

    includeFilter := GlobFilter("main.styl"),

    jsOptions := JsObject(
      "compress" -> JsBoolean(compress.value),
      "useNib" -> JsBoolean(useNib.value)
    ).toString()
  )

  override def projectSettings = Seq(
    compress := false,
    useNib := false

  ) ++ inTask(stylus)(
    SbtJsTask.jsTaskSpecificUnscopedSettings ++
      inConfig(Assets)(stylusUnscopedSettings) ++
      inConfig(TestAssets)(stylusUnscopedSettings) ++
      Seq(
        moduleName := "stylus",
        shellFile := getClass.getClassLoader.getResource("stylus-shell.js"),

        taskMessage in Assets := "Stylus compiling",
        taskMessage in TestAssets := "Stylus test compiling"
      )
  ) ++ SbtJsTask.addJsSourceFileTasks(stylus) ++ Seq(
    stylus in Assets := (stylus in Assets).dependsOn(webModules in Assets).value,
    stylus in TestAssets := (stylus in TestAssets).dependsOn(webModules in TestAssets).value
  )

}