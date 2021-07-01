package com.example.seven_minutes_workout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.seven_minutes_workout.utils.DBHandler
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        // TODO: customize ActionBar
        setSupportActionBar(tlbFinish)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tlbFinish.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish()
        }

        addDateToDatabase()
    }

    private fun addDateToDatabase() {
        val dateTime = Calendar.getInstance().time // Current Date and Time of the system.
        val date = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()).format(dateTime)
        DBHandler(this, null).addDate(date)
    }
}