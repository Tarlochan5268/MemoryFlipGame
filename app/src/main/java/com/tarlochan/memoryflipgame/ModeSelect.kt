package com.tarlochan.memoryflipgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ModeSelect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_select)
    }

    fun startEasyLevel(view: View) {
        startActivity(Intent(this, EasyLevel::class.java))
        //finish this activity
        finish()
    }

    fun startHardLevel(view: View) {
        startActivity(Intent(this, HardLevel::class.java))
        //finish this activity
        finish()
    }
}
