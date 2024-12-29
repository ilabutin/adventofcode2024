fun main() {

    data class Point(val x: Int, val y: Int)

    fun part1(input: List<String>): Long {
        val points = mutableMapOf<Char, MutableList<Point>>()

        for (y in input.indices) {
            for (x in 0 until input[0].length) {
                if (input[y][x] == '.') {
                    continue
                }
                val c = input[y][x]
                if (points[c] == null) {
                    points[c] = mutableListOf<Point>()
                }
                points[c]!!.add(Point(x, y))
            }
        }

        val w = input[0].length
        val h = input.size

        val result = mutableSetOf<Point>()
        for (c in points.keys) {
            val p = points[c]!!
            for (i in p.indices) {
                for (j in i + 1 until p.size) {
                    val p1 = p[i]
                    val p2 = p[j]

                    val pA = Point(p1.x - (p2.x - p1.x), p1.y - (p2.y - p1.y))
                    val pB = Point(p2.x + (p2.x - p1.x), p2.y + (p2.y - p1.y))

                    if (pA.x >= 0 && pA.y >= 0 && pA.x < w && pA.y < h) {
                        result.add(pA)
                    }
                    if (pB.x >= 0 && pB.y >= 0 && pB.x < w && pB.y < h) {
                        result.add(pB)
                    }
                }
            }
        }

        return result.size.toLong()
    }

    fun part2(input: List<String>): Long {
        val points = mutableMapOf<Char, MutableList<Point>>()

        for (y in input.indices) {
            for (x in 0 until input[0].length) {
                if (input[y][x] == '.') {
                    continue
                }
                val c = input[y][x]
                if (points[c] == null) {
                    points[c] = mutableListOf<Point>()
                }
                points[c]!!.add(Point(x, y))
            }
        }

        val w = input[0].length
        val h = input.size

        val result = mutableSetOf<Point>()
        for (c in points.keys) {
            val p = points[c]!!
            for (i in p.indices) {
                for (j in i + 1 until p.size) {
                    val p1 = p[i]
                    val p2 = p[j]

                    val dX = p2.x - p1.x
                    val dY = p2.y - p1.y

                    var d = 1
                    while (true) {
                        val pA = Point(p1.x - d * dX, p1.y - d * dY)

                        if (pA.x >= 0 && pA.y >= 0 && pA.x < w && pA.y < h) {
                            result.add(pA)
                        } else {
                            break
                        }
                        d++
                    }

                    d = 1
                    while (true) {
                        val pA = Point(p2.x + d * dX, p2.y + d * dY)

                        if (pA.x >= 0 && pA.y >= 0 && pA.x < w && pA.y < h) {
                            result.add(pA)
                        } else {
                            break
                        }
                        d++
                    }
                    result.add(p1)
                    result.add(p2)
                }
            }
        }

        return result.size.toLong()
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14L)
    check(part2(testInput) == 34L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
