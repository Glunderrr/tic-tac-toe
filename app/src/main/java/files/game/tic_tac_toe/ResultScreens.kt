package files.game.tic_tac_toe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class DrawActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.draw_screen)
        findViewById<Button>(R.id.play_more_button).setOnClickListener { finish() }
    }
}

class WinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.win_screen)
        findViewById<Button>(R.id.play_more_button).setOnClickListener { finish() }
        findViewById<TextView>(R.id.win_name).text =
            intent.extras?.getString("winner_name") ?: getString(R.string.is_win)
    }
}

class WinBot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.win_screen)
        findViewById<Button>(R.id.play_more_button).setOnClickListener { finish() }
        findViewById<TextView>(R.id.win_name).text = getString(R.string.is_win)
    }
}

class LoseBot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lose_screen)
        findViewById<Button>(R.id.play_more_button).setOnClickListener { finish() }
    }
}

