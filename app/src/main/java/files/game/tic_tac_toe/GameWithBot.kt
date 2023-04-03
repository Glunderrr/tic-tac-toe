package files.game.tic_tac_toe

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
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

    private fun setOnClickListeners() {
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
        if (check(list, flag)) {
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