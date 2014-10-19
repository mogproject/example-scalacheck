package com.github.mogproject.example

import org.scalatest.prop.Checkers.check
import org.scalatest.{MustMatchers, FlatSpec}

class ExampleSpec extends FlatSpec with MustMatchers {
  "multiplying integer with two" should "be same as adding itself" in check { x: Int =>
    x * 2 == x + x
  }
}
