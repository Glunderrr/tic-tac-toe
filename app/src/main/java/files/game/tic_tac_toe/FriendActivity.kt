package files.game.tic_tac_toe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FriendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend)

        val completeButton = findViewById<Button>(R.id.complete_Button)
        val myName = findViewById<EditText>(R.id.my_name)
        val friendName = findViewById<EditText>(R.id.friend_name)
        val exit = findViewById<Button>(R.id.exit_button)

        exit.setOnClickListener { finish() }
        completeButton.setOnClickListener {
            val intent = Intent(this, FriendGame::class.java)
            val bundle = Bundle()
            if (myName.text.toString() == "" || friendName.text.toString() == "") {
                Toast.makeText(
                    this@FriendActivity,
                    getString(R.string.enter_name),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                bundle.putString("my_name_key", myName.text.toString())
                bundle.putString("friend_name_key", friendName.text.toString())
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}