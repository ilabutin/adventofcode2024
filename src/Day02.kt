import kotlin.io.path.Path
import kotlin.io.path.appendText
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val reports =
            input.map { it.split(regex = "\\s+".toRegex()).map { x -> x.toInt() } }

        var safeCount: Int = 0
        for (r in reports) {
            val deltas = r.zipWithNext { a, b -> b - a }

            if (deltas.all { it >= 1 } && deltas.all { it <= 3 }) {
                safeCount++;
            }
            if (deltas.all { it >= -3 } && deltas.all { it <= -1 }) {
                safeCount++;
            }
        }

        return safeCount
    }

    fun checkSequence(s: List<Int>): Boolean {
        val deltas = s.zipWithNext { a, b -> b - a }

        if (deltas.all { it >= 1 } && deltas.all { it <= 3 }) {
            return true
        }
        else if (deltas.all { it >= -3 } && deltas.all { it <= -1 }) {
            return true
        }
        return false
    }

    fun part2(input: List<String>): Int {
        val reports =
            input.map { it.split(regex = "\\s+".toRegex()).map { x -> x.toInt() } }

        var safeCount: Int = 0
        for (r in reports) {
            if (checkSequence(r)) {
                Path("src/out.txt").appendText(r.toString() + "\n")
                safeCount++;
            }
            else {
                for (i in r.indices) {
                    val newS = r.filterIndexed { idx, _ -> idx != i }
                    if (checkSequence(newS)) {
                        safeCount++;
                        Path("src/out.txt").appendText(r.toString() + "\n")
                        break
                    }
                }
            }
        }

        return safeCount
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    //check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
