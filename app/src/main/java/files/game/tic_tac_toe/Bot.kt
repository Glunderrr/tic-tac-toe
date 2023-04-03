package files.game.tic_tac_toe

class Bot(private val list: Array<Boolean?>) {
    private val combination = arrayOf(
        arrayOf(0, 4, 8), arrayOf(2, 4, 6), arrayOf(3, 4, 5), arrayOf(1, 4, 7),
        arrayOf(0, 1, 2), arrayOf(0, 3, 6), arrayOf(6, 7, 8), arrayOf(2, 5, 8),
    )

    fun answer(botStatus: Boolean): Int {
        combination.shuffle()
        val variant = (1..3).random()
        for (index in combination.indices) {
            when {
                list[combination[index][0]] == botStatus && list[combination[index][1]] == botStatus && list[combination[index][2]] == null -> {
                    list[combination[index][2]] = botStatus; return combination[index][2]
                }
                list[combination[index][0]] == botStatus && list[combination[index][2]] == botStatus && list[combination[index][1]] == null -> {
                    list[combination[index][1]] = botStatus; return combination[index][1]
                }
                list[combination[index][1]] == botStatus && list[combination[index][2]] == botStatus && list[combination[index][0]] == null -> {
                    list[combination[index][0]] = botStatus; return combination[index][0]
                }
            }
        }
        for (index in combination.indices) {
            when {
                list[combination[index][0]] == !botStatus && list[combination[index][1]] == !botStatus && list[combination[index][2]] == null -> {
                    list[combination[index][2]] = botStatus; return combination[index][2]
                }
                list[combination[index][0]] == !botStatus && list[combination[index][2]] == !botStatus && list[combination[index][1]] == null -> {
                    list[combination[index][1]] = botStatus; return combination[index][1]
                }
                list[combination[index][1]] == !botStatus && list[combination[index][2]] == !botStatus && list[combination[index][0]] == null -> {
                    list[combination[index][0]] = botStatus; return combination[index][0]
                }
            }
        }
        for (index in combination.indices) {
            when {
                list[combination[index][0]] == botStatus && list[combination[index][1]] == null && list[combination[index][2]] == null -> for (i in 0..2) if (list[combination[index][i]] == null) {
                    list[combination[index][i]] = botStatus;return combination[index][i]
                }
                list[combination[index][1]] == botStatus && list[combination[index][2]] == null && list[combination[index][0]] == null -> for (i in 0..2) if (list[combination[index][i]] == null) {
                    list[combination[index][i]] = botStatus;return combination[index][i]
                }
                list[combination[index][2]] == botStatus && list[combination[index][0]] == null && list[combination[index][1]] == null -> for (i in 0..2) if (list[combination[index][i]] == null) {
                    list[combination[index][i]] = botStatus;return combination[index][i]
                }
            }
        }
        if (list[4] == null && variant != 1) {
            list[4] = botStatus; return 4
        }
        val numberRow = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        numberRow.shuffle()
        for (element in numberRow) {
            if (list[element] == null) {
                list[element] = botStatus
                return element
            }
        }
        return -1
    }
}