package files.game.tic_tac_toe

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import files.game.tic_tac_toe.databinding.ActivityBotGameBinding
import files.game.tic_tac_toe.databinding.StatisticsBinding

class GameWithBot : AppCompatActivity() {
    private var myStatus = true
    private val list: Array<Boolean?> = Array(9) { null }
    private val bot = Bot(list)
    private lateinit var binding: ActivityBotGameBinding
    private lateinit var buttons: Array<ImageView>
    private lateinit var sharedPref: SharedPreferences
    private var drawCount = 0
    private var win = 0
    private var lose = 0
    private var draw = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBotGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttons = arrayOf(
            binding.zeroButton, binding.oneButton, binding.twoButton,
            binding.threeButton, binding.fourButton, binding.fifeButton,
            binding.sixButton, binding.sevenButton, binding.eightButton
        )
        sharedPref = getSharedPreferences("data_for_stat", MODE_PRIVATE)
        getStat()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.exitButton.setOnClickListener { finish() }
        buttons.forEachIndexed { index, button -> button.setOnClickListener { clickRoutine(index) } }
        binding.statisticButton.setOnClickListener {
            val intent = Intent(this@GameWithBot, Statistics::class.java)
            intent.putExtras(saveStatAndCreateBundleWithThis())
            startActivity(intent)
        }
    }

    private fun clickRoutine(number: Int) {
        list[number] = myStatus
        upDateBarView(number)
        if (!myWinCheck(myStatus)) {

            upDateBarView(bot.answer(!myStatus))
            botWinCheck(!myStatus)
        }
    }

    private fun upDateBarView(number: Int) {
        if (list[number] == true) buttons[number].setImageResource(R.drawable.cros)
        if (list[number] == false) buttons[number].setImageResource(R.drawable.zero)
        buttons[number].isClickable = false
    }

    private fun myWinCheck(flag: Boolean): Boolean {
        drawCount++
        return if (check(list, flag)) {
            win++
            val intent = Intent(this, WinActivity::class.java)
            startActivity(intent)
            clearScreen()
            true
        } else if (drawCount == 9) {
            draw++
            val intent = Intent(this, DrawActivity::class.java)
            startActivity(intent)
            clearScreen()
            true
        } else false
    }

    private fun botWinCheck(flag: Boolean) {
        drawCount++
        if (check(list,flag)) {
            lose++
            val intent = Intent(this, LoseBot::class.java)
            startActivity(intent)
            clearScreen()
        } else if (drawCount == 9) {
            draw++
            val intent = Intent(this, DrawActivity::class.java)
            startActivity(intent)
            clearScreen()
        }
    }

    private fun clearScreen() {
        drawCount = 0
        myStatus = !myStatus
        if (myStatus) {
            binding.yourSymbol.text = "X"
            binding.yourSymbol.setTextColor(getColor(R.color.red))
        } else {
            binding.yourSymbol.text = "O"
            binding.yourSymbol.setTextColor(getColor(R.color.blue))
        }
        for (index in buttons.indices) {
            list[index] = null
            buttons[index].setImageResource(R.drawable.white)
            buttons[index].isClickable = true
        }
        if (!myStatus) {
            upDateBarView(bot.answer(true))
            drawCount++
        }
    }

    private fun saveStatAndCreateBundleWithThis(): Bundle {
        val editor = sharedPref.edit()
        editor.putInt("win_key", win)
        editor.putInt("lose_key", lose)
        editor.putInt("draw_key", draw)
        editor.apply()
        val bundle = Bundle()
        bundle.putInt("win", win)
        bundle.putInt("lose", lose)
        bundle.putInt("draw", draw)
        return bundle
    }

    private fun getStat() {
        win = sharedPref.getInt("win_key", 0)
        lose = sharedPref.getInt("lose_key", 0)
        draw = sharedPref.getInt("draw_key", 0)
    }
}

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

class Statistics : AppCompatActivity() {
    private lateinit var binding: StatisticsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = StatisticsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.winText.text = "${getString(R.string.win)} ${intent.getIntExtra("win", 0)}"
        binding.loseText.text = "${getString(R.string.lose)} ${intent.getIntExtra("lose", 0)}"
        binding.drawText.text = "${getString(R.string.draw)} ${intent.getIntExtra("draw", 0)}"
        binding.exitButton.setOnClickListener { finish() }
    }
}