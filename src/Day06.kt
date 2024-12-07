
fun main() {
    data class Pos(val x: Int, val y: Int)
    data class MoveResult(val visited: Int, val exited: Boolean, val endPos: Pos, val loop: Boolean = false)
    data class Dir(val x: Int, val y: Int)
    data class Cell(var f: Char, var visited: Boolean, var directions: MutableSet<Int>)

    val dirs = listOf(Dir(0, -1), Dir(1, 0), Dir(0, 1), Dir(-1, 0))

    var w = 0
    var h = 0
    var field = mutableListOf<MutableList<Cell>>()

    fun isPosInside(p: Pos): Boolean {
        return p.x in 0 until w && p.y in 0 until h
    }

    fun move(startPos: Pos, dirIndex: Int): MoveResult {
        var pos = startPos
        var visited = 0
        while (true) {
            // Check if we were here in the same direction
            if (field[pos.y][pos.x].directions.contains(dirIndex)) {
                return MoveResult(visited, false, pos, true)
            }
            field[pos.y][pos.x].directions.add(dirIndex)

            // if current position is not marked - mark it
            if (!field[pos.y][pos.x].visited) {
                field[pos.y][pos.x].visited = true
                visited++
            }
            // calculate next potential position
            val nextPos = Pos(pos.x + dirs[dirIndex].x, pos.y + dirs[dirIndex].y)

            // If we move outside - stop and return
            if (!isPosInside(nextPos)) {
                return MoveResult(visited, true, pos)
            }
            // If find obstacle - return
            if (field[nextPos.y][nextPos.x].f == '#') {
                return MoveResult(visited, false, pos)
            }
            // Else move forward
            pos = nextPos
        }
    }

    fun init(input: List<String>): Pos {
        field = mutableListOf()

        var pos = Pos(0, 0)
        // Find initial position
        for (y in input.indices) {
            val l = mutableListOf<Cell>()
            for (x in input[0].indices) {
                l.add(Cell(input[y][x], false, mutableSetOf()))
                if (input[y][x] == '^') {
                    pos = Pos(x, y)
                }
            }
            field.add(l)
        }

        return pos
    }

    fun part1(input: List<String>): Int {
        w = input[0].length
        h = input.size

        var pos = init(input)

        var dirIndex = 0
        var visited = 0
        while (true) {
            val moveResult = move(pos, dirIndex)
            visited += moveResult.visited
            if (moveResult.exited) {
                break
            }
            pos = moveResult.endPos
            dirIndex = (dirIndex + 1) % dirs.size
        }

        return visited
    }


    fun visitCheckLoop(startPos: Pos): Boolean {
        var dirIndex = 0
        var visited = 0
        var pos = startPos
        while (true) {
            val moveResult = move(pos, dirIndex)
            visited += moveResult.visited
            if (moveResult.exited) {
                return false
            }
            if (moveResult.loop) {
                return true
            }
            pos = moveResult.endPos
            dirIndex = (dirIndex + 1) % dirs.size
        }
    }

    fun part2(input: List<String>): Int {
        w = input[0].length
        h = input.size
        field = mutableListOf()

        var count = 0
        for (y in input.indices) {
            for (x in input[y].indices) {
                if (input[y][x] != '.') {
                    continue
                }
//                print("Checking ${y}, ${x}")
                val pos = init(input)
                field[y][x].f = '#'
                if (visitCheckLoop(pos)) {
//                    println("  -> true")
                    count++
                } else {
//                    println("  -> false")
                }

            }
        }

        return count
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
