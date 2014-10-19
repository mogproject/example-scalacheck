package com.github.mogproject.example

case class Coord(x: Double, y: Double) {
  def distance(c: Coord) = math.sqrt(math.pow(c.x - x, 2) + math.pow(c.y - y, 2))
}
