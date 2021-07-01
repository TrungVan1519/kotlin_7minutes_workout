package com.example.seven_minutes_workout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: customize ActionBar
        setSupportActionBar(tlbMain)

        vStart.setOnClickListener{
            startActivity(Intent(this, ExerciseActivity::class.java))
        }

        vBMI.setOnClickListener{
            startActivity(Intent(this, BMIActivity::class.java))
        }

        vHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}