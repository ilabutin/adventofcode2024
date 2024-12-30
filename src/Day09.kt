fun main() {
    data class Node(var next: Node? = null, var prev: Node? = null, var free: Boolean, var value: Int, var size: Int, val index: Int) {}

    fun findNextFreeSlotForward(node: Node) : Node? {
        var n = node.next
        while (n != null && !n.free) {
            n = n.next
        }
        return n
    }

    fun findPrevBusySlotBackward(node: Node) : Node {
        var n = node.prev!!
        while (n.free) {
            n = n.prev!!
        }
        return n
    }

    fun part1(input: List<String>): Long {
        var head : Node? = null
        var tail : Node? = null

        for (i in input[0].indices) {
            if (head == null) {
                head = Node(null, null, free = false, value = 0, size = (input[0][0] - '0'), index = 0)
                tail = head
                continue
            }
            var free = false
            var value = 0
            if (i % 2 == 1) {
                free = true
            } else {
                value = i / 2
            }
            val size = (input[0][i] - '0')
            if (size > 0) {
                val node = Node(null, tail, free, value, size, index = i)
                tail!!.next = node
                tail = node
            }
        }

        var freeSlot = findNextFreeSlotForward(head!!)!!
        var busySlot = tail!!
        while (busySlot.free) {
            busySlot = busySlot.prev!!
        }

        while (freeSlot.index < busySlot.index) {
            val capacity = freeSlot.size
            // Check if all data can exactly fit in free slot
            if (busySlot.size == capacity) {
                // Mark free slot as busy slot and reassign value
                freeSlot.free = false
                freeSlot.value = busySlot.value
                busySlot.free = true
                freeSlot = findNextFreeSlotForward(freeSlot)!!
                busySlot = findPrevBusySlotBackward(busySlot)
                continue
            }
            // If free slot is larger - split it into two
            if (busySlot.size < capacity) {
                freeSlot.value = busySlot.value
                freeSlot.free = false
                freeSlot.size = busySlot.size
                val newFreeSlot = Node(freeSlot.next, freeSlot, free = true, 0, capacity - busySlot.size, freeSlot.index)
                freeSlot.next!!.prev = newFreeSlot
                freeSlot.next = newFreeSlot
                freeSlot = newFreeSlot
                busySlot.free = true
                busySlot = findPrevBusySlotBackward(busySlot)
                continue
            }
            // If free slot is smaller - copy as much as possible and split busy slot
            freeSlot.value = busySlot.value
            freeSlot.free = false
            freeSlot = findNextFreeSlotForward(freeSlot)!!

            busySlot.free = true
            val newBusySlot = Node(busySlot, busySlot.prev, free = false, busySlot.value, size = busySlot.size - capacity, busySlot.index)
            busySlot.prev!!.next = newBusySlot
            busySlot.prev = newBusySlot
            busySlot = newBusySlot
        }

        var sum : Long = 0
        var nodePositionStart = 0
        var node = head
        while (!node!!.free) {
            for (i in 0 until node.size) {
                sum += (nodePositionStart + i) * node.value
            }
            nodePositionStart += node.size
            node = node.next!!
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        var head : Node? = null
        var tail : Node? = null

        for (i in input[0].indices) {
            if (head == null) {
                head = Node(null, null, free = false, value = 0, size = (input[0][0] - '0'), index = 0)
                tail = head
                continue
            }
            var free = false
            var value = 0
            if (i % 2 == 1) {
                free = true
            } else {
                value = i / 2
            }
            val size = (input[0][i] - '0')
            if (size > 0) {
                val node = Node(null, tail, free, value, size, index = i)
                tail!!.next = node
                tail = node
            }
        }

        var busySlot = tail!!
        while (busySlot.free) {
            busySlot = busySlot.prev!!
        }

        while (busySlot.index > 0) {
            val requiredCapacity = busySlot.size
            // Find suitable free slot
            var freeSlot = findNextFreeSlotForward(head!!)
            var found = false
            while (freeSlot!!.index < busySlot.index) {
                if (freeSlot!!.size >= requiredCapacity) {
                    found = true
                    break
                }
                freeSlot = findNextFreeSlotForward(freeSlot!!)
            }
            if (!found) {
                busySlot = findPrevBusySlotBackward(busySlot)
                continue
            }

            val capacity = freeSlot!!.size
            // Check if all data can exactly fit in free slot
            if (busySlot.size == capacity) {
                // Mark free slot as busy slot and reassign value
                freeSlot!!.free = false
                freeSlot!!.value = busySlot.value
                busySlot.free = true
                busySlot = findPrevBusySlotBackward(busySlot)
                continue
            }
            // If free slot is larger - split it into two
            freeSlot!!.value = busySlot.value
            freeSlot!!.free = false
            freeSlot!!.size = busySlot.size
            val newFreeSlot = Node(freeSlot!!.next, freeSlot, free = true, 0, capacity - busySlot.size, freeSlot!!.index)
            freeSlot!!.next!!.prev = newFreeSlot
            freeSlot!!.next = newFreeSlot
            busySlot.free = true
            busySlot = findPrevBusySlotBackward(busySlot)
        }

        var sum : Long = 0
        var nodePositionStart = 0
        var node = head
        while (true) {
            if (node!!.free) {
                nodePositionStart += node!!.size
                if (node!!.next == null) {
                    break
                }
                node = node!!.next!!
                continue
            }
            for (i in 0 until node!!.size) {
                sum += (nodePositionStart + i) * node!!.value
            }
            nodePositionStart += node!!.size
            if (node!!.next == null) {
                break
            }
            node = node!!.next!!
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
