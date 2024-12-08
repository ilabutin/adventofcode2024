enum class Op(val order: Int) { ADD(0), MUL(1), CONCAT(2) }

fun main() {

    fun calculate(numbers: List<Long>, ops: Long) : Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            if ((ops and (1L.shl(i - 1))) == 0L) {
                result += numbers[i]
            } else {
                result *= numbers[i]
            }
        }

        return result
    }

    fun verifyOp(numbers: List<Long>, requiredResult: Long): Boolean {
        val numOps = numbers.size - 1
        val maxOpMask = 1L.shl(numOps)
        for (i in 0L until maxOpMask) {
            if (requiredResult == calculate(numbers, i)) {
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Long {
        var sum = 0L
        for (line in input) {
            val parts = line.split(":")
            val result = parts[0].trim().toLong()
            val numbers = parts[1].trim().split(" ").map { it.toLong() }

            if (verifyOp(numbers, result)) sum += result
        }

        return sum
    }

    fun nextOpCombination(ops: MutableList<Op>): Boolean {
        var index = 0
        while (true) {
            if (index >= ops.size) {
                break
            }
            if (ops[index].order < 2) {
                ops[index] = Op.entries.toTypedArray()[ops[index].order + 1]
                return true
            }
            ops[index] = Op.ADD
            index++
        }
        return false
    }

    fun calculateTriOps(numbers: List<Long>, ops: List<Op>) : Long {
        var result = numbers[0]
        for (i in 1 until numbers.size) {
            if (ops[i - 1] == Op.ADD) {
                result += numbers[i]
            } else if (ops[i - 1] == Op.MUL) {
                result *= numbers[i]
            } else if (ops[i - 1] == Op.CONCAT) {
                val dig = numbers[i].toString().length
                result = result * (Math.pow(10.0, dig.toDouble()).toLong()) + numbers[i]
            }
        }

        return result
    }

    fun verifyTriOps(numbers: List<Long>, requiredResult: Long): Boolean {
        val numOps = numbers.size - 1
        val ops = mutableListOf<Op>()
        for (i in 0L until numOps) {
            ops.add(Op.ADD)
        }

        do {
            if (requiredResult == calculateTriOps(numbers, ops)) {
                return true
            }
            if (!nextOpCombination(ops))
                break
        } while (true)
        return false
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        for (line in input) {
            val parts = line.split(":")
            val result = parts[0].trim().toLong()
            val numbers = parts[1].trim().split(" ").map { it.toLong() }

            if (verifyTriOps(numbers, result)) sum += result
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
