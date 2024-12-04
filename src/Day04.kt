
fun main() {
    data class Point(val x: Int, val y: Int)
    val str = "XMAS"

    fun getDeltas(input: List<String>, position: Point): List<Point> {
        val w = input[0].length - 1
        val h = input.size - 1
        val directions = mutableListOf<Point>()
        if (position.y > 0 && position.x > 0) {
            directions.add(Point(-1, -1))
        }
        if (position.y > 0) {
            directions.add(Point(0, -1))
        }
        if (position.y > 0 && position.x < w) {
            directions.add(Point(1, -1))
        }
        if (position.x > 0) {
            directions.add(Point(-1, 0))
        }
        if (position.x < w) {
            directions.add(Point(1, 0))
        }
        if (position.y < h && position.x > 0) {
            directions.add(Point(-1, 1))
        }
        if (position.y < h) {
            directions.add(Point(0, 1))
        }
        if (position.y < h && position.x < w) {
            directions.add(Point(1, 1))
        }

        return directions
    }

    fun traverseDirection(input: List<String>, start: Point, direction: Point): Boolean {
        val w = input[0].length - 1
        val h = input.size - 1
        var position = start
        for (i in str.indices) {
            if (input[position.y][position.x] != str[i]) {
                return false
            }
            if (i == str.indices.last) {
                return true
            }
            position = Point(position.x + direction.x, position.y + direction.y)
            if (position.x < 0 || position.y < 0 || position.x > w || position.y > h) {
                return false
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (input[y][x] != str[0]) {
                    continue
                }
                val deltas = getDeltas(input, Point(x, y))
                for (delta in deltas) {
                    if (traverseDirection(input, Point(x, y), delta)) {
                        sum += 1
                        //println(Point(x, y))
                    }
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (y in 1 until input.size - 1) {
            for (x in 1 until input[y].length - 1) {
                if (input[y][x] != 'A') {
                    continue
                }

                var d1 = false
                if (input[y - 1][x - 1] == 'M' && input[y + 1][x + 1] == 'S') {
                    d1 = true
                }
                if (input[y - 1][x - 1] == 'S' && input[y + 1][x + 1] == 'M') {
                    d1 = true
                }
                var d2 = false
                if (input[y - 1][x + 1] == 'M' && input[y + 1][x - 1] == 'S') {
                    d2 = true
                }
                if (input[y - 1][x + 1] == 'S' && input[y + 1][x - 1] == 'M') {
                    d2 = true
                }

                if (d1 && d2) sum += 1
            }
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
