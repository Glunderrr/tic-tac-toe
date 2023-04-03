package files.game.tic_tac_toe

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val friendButton = findViewById<Button>(R.id.friend_Button)
        val botButton = findViewById<Button>(R.id.bot_Button)

        botButton.setOnClickListener{
            val intent = Intent(this, GameWithBot::class.java)
            startActivity(intent)
        }

        friendButton.setOnClickListener {
            val intent = Intent(this, FriendActivity::class.java)
            startActivity(intent)
        }
    }
}