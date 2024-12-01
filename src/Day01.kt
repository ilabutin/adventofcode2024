import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()
        for (line in input) {
            val split: List<String> = line.split(regex = "\\s+".toRegex())
            leftList.add(split[0].toInt())
            rightList.add(split[1].toInt())
        }
        leftList.sort()
        rightList.sort()

        var sum = 0
        for (i in leftList.indices) {
            sum += abs(leftList[i] - rightList[i])
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val leftList = mutableListOf<Int>()
        val rightMap = mutableMapOf<Int, Int>()
        for (line in input) {
            val split: List<String> = line.split(regex = "\\s+".toRegex())
            leftList.add(split[0].toInt())
            val r = split[1].toInt()
            if (!rightMap.containsKey(r)) {
                rightMap[r] = 0
            }
            rightMap[r] = rightMap[r]!! + 1
        }

        var sum = 0
        for (l in leftList) {
            if (!rightMap.containsKey(l)) {
                continue
            }
            sum += rightMap[l]!! * l
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
