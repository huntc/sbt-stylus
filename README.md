sbt-stylus
==========

[![Build Status](https://api.travis-ci.org/sbt/sbt-stylus.png?branch=master)](https://travis-ci.org/sbt/sbt-stylus)

> Note that this plugin is presently only working with an engineType set to Node e.g.:
> `set JsEngineKeys.engineType := JsEngineKeys.EngineType.Node`

Allows stylus to be used from within sbt. Builds on com.typesafe.sbt:js-engine in order to execute the stylus compiler along with
the scripts to verify. js-engine enables high performance linting given parallelism and native JS engine execution.

To use this plugin use the addSbtPlugin command within your project's plugins.sbt (or as a global setting) i.e.:

```scala
addSbtPlugin("com.typesafe.sbt" % "sbt-stylus" % "1.0.0")
```

Your project's build file also needs to enable sbt-web plugins. For example with build.sbt:

    lazy val root = (project in file(".")).enablePlugins(SbtWeb)

The compiler allows some of the same options to be specified as the (stylus CLI itself)[http://learnboost.github.io/stylus/docs/executable.html].
Here are the options:

Option              | Description
--------------------|------------
compress            | Compress output by removing some whitespaces.
    
The following sbt code illustrates how compression can be enabled:

```scala
StylusKeys.compress in Assets := true
```

By default only `main.styl` is looked for given that the Stylus compiler must be explicitly fed the files
that are required for compilation. Beyond just `main.styl`, you can use an expression in your `build.sbt` like the
following:

```scala
includeFilter in (Assets, StylusKeys.stylus) := "foo.styl" | "bar.styl"
```

...where both `foo.styl` and `bar.styl` will be considered for the Stylus compiler.

Alternatively you may want a more general expression to exclude Stylus files that are not considered targets
for the compiler. Quite commonly, Stylus files are divided up into those entry point files and other files, with the
latter set intended for importing into the entry point files. These other files tend not to be suitable for the
compiler in isolation as they can depend on the global declarations made by other non-imported Stylus files. For example,
you may have a convention where any Stylus file starting with an `_` should not be considered for direct compilation. To
include all `.styl` files but exclude any beginning with an `_` you can use the following declaration:

```scala
includeFilter in (Assets, StylusKeys.stylus) := "*.styl"

excludeFilter in (Assets, StylusKeys.stylus) := "_*.styl"
```

&copy; Typesafe Inc., 2014
