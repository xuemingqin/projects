package com.example.keystoresam

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val PREF_NAME = "sample_pref"
    private val SAVED_KEY = "encrypted_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        encryptButton.setOnClickListener {
            if (editText.text.isNullOrEmpty()) return@setOnClickListener


        }
    }
}
