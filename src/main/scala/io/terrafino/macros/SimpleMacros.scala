package io.terrafino.macros

import java.util.Date

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.util.matching._

object SimpleMacros {
  def printTree(title: String)(expr: Any): Unit = macro printTreeMacro
  def tree[T](id: String)(x: => T): Unit = macro treeImpl
  def debug[T](x: => T): T = macro debugImpl

  def printTreeMacro(c: Context)(title: c.Tree)(expr: c.Tree) = {
    import c.universe._
    import PrettyPrint._

    val code: String = showCode(expr)
    val tree: String = prettify(showRaw(expr))

    q"""
    println(
      $title.toUpperCase + "\n\n" +
      "Desugared code:\n"  + $code + "\n\n" +
      "Underlying tree:\n" + $tree + "\n\n"
    )
    """
  }

  
  def treeImpl(c: Context)(id: c.Tree)(x: c.Tree) = { import c.universe._
    val q"..$stats" = x
    val loggedStats = stats.flatMap { stat =>
      val msg = "executing " + showCode(stat)
      val treename = showCode(id)
      //val x = q"""$treename.set($stat)"""
      val x = q"""$id.set($stat)"""
      val xstr = showCode(x)
      println(xstr)
      //List(q"println($xstr)", x ) 
      List( x ) 
    }
    q"..$loggedStats"
  }

  def debugImpl(c: Context)(x: c.Tree) = { import c.universe._
    val q"..$stats" = x
    val loggedStats = stats.flatMap { stat =>
      val msg = "executing " + showCode(stat)
      List(q"println($msg)", stat)
    }
    q"..$loggedStats"
  }
}