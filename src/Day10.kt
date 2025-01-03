fun main() {

    data class Point(val x: Int, val y: Int)

    fun getNeighbours(p: Point, w: Int, h: Int): List<Point> {
        val neighbours = mutableListOf<Point>()
        if (p.x > 0) {
            neighbours.add(Point(p.x - 1, p.y))
        }
        if (p.y > 0) {
            neighbours.add(Point(p.x, p.y - 1))
        }
        if (p.x < w - 1) {
            neighbours.add(Point(p.x + 1, p.y))
        }
        if (p.y < h - 1) {
            neighbours.add(Point(p.x, p.y + 1))
        }
        return neighbours
    }

    fun calculateEnds(input: List<String>, x : Int, y : Int, ends: MutableSet<Point>) {
        val w = input[0].length
        val h = input.size
        val current = input[y][x]
        if (current == '9') {
            ends.add(Point(x, y))
            return
        }

        val next = current + 1
        val neighbours = getNeighbours(Point(x, y), w, h)
        for (neighbour in neighbours) {
            if (input[neighbour.y][neighbour.x] == next) {
                calculateEnds(input, neighbour.x, neighbour.y, ends)
            }
        }
    }

    fun calculateRating(input: List<String>, x : Int, y : Int, ends: MutableList<Point>) {
        val w = input[0].length
        val h = input.size
        val current = input[y][x]
        if (current == '9') {
            ends.add(Point(x, y))
            return
        }

        val next = current + 1
        val neighbours = getNeighbours(Point(x, y), w, h)
        for (neighbour in neighbours) {
            if (input[neighbour.y][neighbour.x] == next) {
                calculateRating(input, neighbour.x, neighbour.y, ends)
            }
        }
    }

    fun part1(input: List<String>): Long {
        var sum = 0L
        for (y in input.indices) {
            for (x in input[0].indices) {
                if (input[y][x] == '0') {
                    val ends = mutableSetOf<Point>()
                    calculateEnds(input, x, y, ends)
                    sum += ends.size
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        for (y in input.indices) {
            for (x in input[0].indices) {
                if (input[y][x] == '0') {
                    val ends = mutableListOf<Point>()
                    calculateRating(input, x, y, ends)
                    sum += ends.size
                }
            }
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36L)
    check(part2(testInput) == 81L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
