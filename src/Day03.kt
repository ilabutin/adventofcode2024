
fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        val r = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")

        val s = input.joinToString("")

        val items = r.findAll(s)

        for (match in items) {
            val x = match.groupValues[1].toInt()
            val y = match.groupValues[2].toInt()
            sum += x * y
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val r = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val enable = Regex("do\\(\\)")
        val disable = Regex("don't\\(\\)")

        val s = input.joinToString("")
        val e = enable.findAll(s).map { it.range.first }.toList()
        val d = disable.findAll(s).map { it.range.first }.toList()

        val items = r.findAll(s)

        for (match in items) {
            val x = match.groupValues[1].toInt()
            val y = match.groupValues[2].toInt()
            val p = match.range.first
            val e_index = e.lastOrNull { it < p }
            val d_index = d.lastOrNull { it < p }
            if (e_index == null && d_index == null) {
                sum += x * y
            } else if (e_index != null && d_index == null) {
                sum += x * y
            } else if (e_index != null && d_index != null && e_index > d_index) {
                sum += x * y
            }
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    val testInput2 = readInput("Day03_test2")
    check(part2(testInput2) == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
