package com.morrisware.android.ipc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.morrisware.android.ipc.messenger.BookMessengerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        messenger.setOnClickListener {
            startActivity(Intent(this, BookMessengerActivity::class.java))
        }
        button.setOnClickListener {
            UserMessage.userId = 2
            startActivity(Intent(this, SecondActivity::class.java)
                .putExtra(SecondActivity.KEY, SecondActivity.data++.toString())
            )
        }
        textView.setOnClickListener {
            startActivity(Intent(this, BookActivity::class.java))
        }
    }

}
