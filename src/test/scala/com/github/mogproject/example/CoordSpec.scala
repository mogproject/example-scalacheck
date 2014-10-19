package com.github.mogproject.example

import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{MustMatchers, FlatSpec}

class CoordSpec extends FlatSpec with MustMatchers with GeneratorDrivenPropertyChecks {
  def genCoord: Gen[Coord] =
    for {
      x <- Gen.choose(-100.0, 100.0)
      y <- Gen.choose(-100.0, 100.0)
    } yield Coord(x, y)

  "Coord#distance" should "be norm when one side is origin" in forAll { (x: Double, y: Double) =>
    val norm = math.sqrt(x * x + y * y)
    Coord(x, y).distance(Coord(0, 0)) mustBe norm
    Coord(0, 0).distance(Coord(x, y)) mustBe norm
  }

  it should "be zero with same coordinates" in forAll(genCoord) { (a: Coord) =>
    a.distance(a) mustBe 0.0
  }
  it should "be positive or zero" in forAll(genCoord, genCoord) { (a: Coord, b: Coord) =>
    a.distance(b) must be >= 0.0
  }
  it should "be less than 300" in forAll(genCoord, genCoord) { (a: Coord, b: Coord) =>
    a.distance(b) must be < 300.0
  }
  it should "not change after swapping parameters" in forAll(genCoord, genCoord) { (a: Coord, b: Coord) =>
    a.distance(b) mustBe b.distance(a)
  }
  it should "not change after parallel shift" in forAll(
    genCoord, genCoord, Gen.choose(-100.0, 100.0), arbitrary[Int], minSuccessful(500), maxDiscarded(2000)) {

    (a: Coord, b: Coord, dx: Double, dy: Int) => whenever(-10000 <= dy && dy <= 10000) {
      a.distance(b) mustBe (Coord(a.x + dx, a.y + dy).distance(Coord(b.x + dx, b.y + dy)) +- 1.0E-8)
    }
  }
}
