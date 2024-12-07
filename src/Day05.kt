
fun main() {

    fun part1(input: List<String>): Int {
        val before = mutableMapOf<Int, MutableList<Int>>()
        var sum = 0

        for (line in input) {
            if (line.contains('|')) {
                val parts = line.split('|')
                val left = parts[0].toInt()
                val right = parts[1].toInt()
                val map = before.getOrPut(right) { mutableListOf() }
                map.add(left)
            }
            if (line.contains(',')) {
                val values = line.split(',').map { it.toInt() }
                var good = true
                for (i in values.indices) {
                    val v = values[i]
                    for (j in 0 until  i) {
                        val t = values[j]
                        val b = before.getOrElse(t) { mutableListOf() }
                        if (b.contains(v)) {
                            good = false
                            break
                        }
                    }
                }
                if (good) {
                    sum += values[(values.size - 1) / 2]
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val before = mutableMapOf<Int, MutableList<Int>>()
        var sum = 0

        for (line in input) {
            if (line.contains('|')) {
                val parts = line.split('|')
                val left = parts[0].toInt()
                val right = parts[1].toInt()
                val map = before.getOrPut(right) { mutableListOf() }
                map.add(left)
            }
            if (line.contains(',')) {
                val values = line.split(',').map { it.toInt() }
                var good = true
                for (i in values.indices) {
                    val v = values[i]
                    for (j in 0 until  i) {
                        val t = values[j]
                        val b = before.getOrElse(t) { mutableListOf() }
                        if (b.contains(v)) {
                            good = false
                            break
                        }
                    }
                }
                if (!good) {
                    val sortedVals = values.sortedWith { o1, o2 ->
                        if (before.getOrElse(o2, { mutableListOf() }).contains(o1)) -1
                        else if (before.getOrElse(o1, { mutableListOf() }).contains(o2))  1
                        else 0
                    }
                    sum += sortedVals[(sortedVals.size - 1) / 2]
                }

            }
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
