fun main() {

    fun part1(input: List<String>): Long {
        var stones = input[0].split(" ").map { it.toLong() }
        var blinks = 25

        while (blinks > 0) {
            // replace 0 --> 1
            // mmmnnn -> mmm nnn
            // else -> value * 2024
            val newStones = mutableListOf<Long>()
            for (stone in stones) {
                if (stone == 0L) {
                    newStones.add(1L)
                } else {
                    val s = stone.toString()
                    if (s.length % 2 == 0) {
                        val leftStone = s.substring(0, s.length / 2).toLong()
                        val rightStone = s.substring(s.length / 2, s.length).toLong()
                        newStones.add(leftStone)
                        newStones.add(rightStone)
                    } else {
                        newStones.add(stone * 2024)
                    }
                }
            }
            stones = newStones

            blinks--
        }

        return stones.size.toLong()
    }

    fun part2(input: List<String>): Long {
        val stones = input[0].split(" ").map { it.toLong() }
        var blinks = 75

        val stonesDict = mutableMapOf<Long, Long>()
        for (stone in stones) {
            if (!stonesDict.containsKey(stone)) {
                stonesDict[stone] = 0
            }
            stonesDict[stone] = stonesDict[stone]!! + 1
        }

        while (blinks > 0) {
            // replace 0 --> 1
            // mmmnnn -> mmm nnn
            // else -> value * 2024
            val snapshot = stonesDict.toMap()
            for (stone in snapshot.keys) {
                val count = snapshot[stone]!!
                if (count == 0L) {
                    continue
                }
                if (stone == 0L) {
                    stonesDict[stone] = stonesDict[stone]!! - count
                    stonesDict[1] = (stonesDict.getOrDefault(1L, 0L)) + count
                } else {
                    val s = stone.toString()
                    if (s.length % 2 == 0) {
                        val leftStone = s.substring(0, s.length / 2).toLong()
                        val rightStone = s.substring(s.length / 2, s.length).toLong()
                        stonesDict[leftStone] = (stonesDict.getOrDefault(leftStone, 0L)) + count
                        stonesDict[rightStone] = stonesDict.getOrDefault(rightStone, 0L) + count
                        stonesDict[stone] = stonesDict[stone]!! - count
                    } else {
                        stonesDict[stone * 2024] = (stonesDict.getOrDefault(stone * 2024, 0L)) + count
                        stonesDict[stone] = stonesDict[stone]!! - count
                    }
                }
            }

            blinks--
        }

        return stonesDict.values.sum()
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("125 17")) == 55312L)
//    check(part2(listOf("125 17")) == 55312L)

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInput("Day10_test")
//    check(part1(testInput) == 36L)
//    check(part2(testInput) == 81L)

    // Read the input from the `src/Day01.txt` file.
//    val input = readInput("Day10")
    part1(listOf("5910927 0 1 47 261223 94788 545 7771")).println()
    part2(listOf("5910927 0 1 47 261223 94788 545 7771")).println()
}
