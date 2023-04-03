package files.game.tic_tac_toe

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import files.game.tic_tac_toe.databinding.ActivityGameBinding

class FriendGame : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var drawCount = 0
    private var flag = true
    private val left = 0
    private val right = 1
    private val playerRating = arrayOf(0, 0)
    private val playerStatus = arrayOf(true, false)
    private val playerName = Array(2) { "" }
    private lateinit var buttons: Array<ImageView>
    private val list: Array<Boolean?> = Array(9) { null }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializing()
        clickListeners()
    }

    private fun clickListeners() {
        binding.exitButton.setOnClickListener { finish() }
        buttons.forEachIndexed { index, element -> element.setOnClickListener { clickRoutine(index) } }
    }

    private fun clickRoutine(number: Int) {
        list[number] = flag
        if (list[number] == true) buttons[number].setImageResource(R.drawable.cros)
        if (list[number] == false) buttons[number].setImageResource(R.drawable.zero)
        buttons[number].isClickable = false
        winCheck(flag)
        flag = !flag
    }

    private fun initializing() = with(binding) {
        leftRating.text = getString(R.string.win) + "${playerRating[left]}"
        rightRating.text = getString(R.string.win) + "${playerRating[right]}"
        leftName.text = intent.extras?.getString("my_name_key")
        rightName.text = intent.extras?.getString("friend_name_key")
        playerName[left] = leftName.text.toString();playerName[right] = rightName.text.toString()
        buttons = arrayOf(
            zeroButton, oneButton, twoButton, threeButton, fourButton, 
            fifeButton, sixButton, sevenButton, eightButton
        )
    }

    private fun updateScreenData() = with(binding) {
        buttons.forEachIndexed { index, button ->
            button.setImageResource(R.drawable.white);button.isClickable = true;list[index] = null
        }
        playerStatus[left] = playerStatus[right].also { playerStatus[right] = playerStatus[left] }
        leftStatus.text = rightStatus.text.also { rightStatus.text = leftStatus.text }
        leftStatus.setTextColor(getColor(if (leftStatus.text == "X") R.color.red else R.color.blue))
        rightStatus.setTextColor(getColor(if (leftStatus.text == "X") R.color.blue else R.color.red))
        leftRating.text = getString(R.string.win) + "${playerRating[left]}"
        rightRating.text = getString(R.string.win) + "${playerRating[right]}"
        flag = false
        drawCount = 0
    }

    private fun winCheck(value: Boolean) {
        drawCount++
        if (check(list,flag)) {
            val intent = Intent(this, WinActivity::class.java)
            for (index in playerStatus.indices) {
                if (playerStatus[index] == value) {
                    playerRating[index]++
                    intent.putExtra(
                        "winner_name",
                        "${playerName[index]}" + getString(R.string.winner)
                    )
                }
            }
            startActivity(intent)
            updateScreenData()
        } else if (drawCount == 9) {
            val intent = Intent(this, DrawActivity::class.java)
            startActivity(intent)
            updateScreenData()
        }
    }
}

class WinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.win_screen)
        findViewById<Button>(R.id.play_more_button).setOnClickListener { finish() }
        findViewById<TextView>(R.id.win_name).text = intent.extras?.getString("winner_name")
    }
}

class DrawActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.draw_screen)
        findViewById<Button>(R.id.play_more_button).setOnClickListener { finish() }
    }
}

fun check(list: Array<Boolean?>, flag: Boolean): Boolean {
    return list[0] == flag && list[4] == flag && list[8] == flag ||
            list[2] == flag && list[4] == flag && list[6] == flag ||
            list[0] == flag && list[1] == flag && list[2] == flag ||
            list[3] == flag && list[4] == flag && list[5] == flag ||
            list[6] == flag && list[7] == flag && list[8] == flag ||
            list[0] == flag && list[3] == flag && list[6] == flag ||
            list[1] == flag && list[4] == flag && list[7] == flag ||
            list[2] == flag && list[5] == flag && list[8] == flag
}