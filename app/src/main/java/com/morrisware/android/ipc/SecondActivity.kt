package com.morrisware.android.ipc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        button.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java)
                .putExtra(SecondActivity.KEY, SecondActivity.data++.toString()))
        }
        Log.d(TAG, "${UserMessage.userId}")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent before:${parseIntent(intent)}")
        Log.d(TAG, "getIntent before:${parseIntent(getIntent())}")
        setIntent(intent)
        Log.d(TAG, "onNewIntent after:${parseIntent(intent)}")
        Log.d(TAG, "getIntent after:${parseIntent(getIntent())}")
    }

    private fun parseIntent(intent: Intent?): String =
        intent?.getStringExtra(KEY) ?: ""

    companion object {
        const val TAG = "SecondActivity"
        const val KEY = "key"
        var data = 1

    }


}
