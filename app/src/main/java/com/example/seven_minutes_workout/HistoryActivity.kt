package com.example.seven_minutes_workout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seven_minutes_workout.utils.DBHandler
import com.example.seven_minutes_workout.utils.HistoryAdapter
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // TODO: customize ActionBar
        setSupportActionBar(tlbHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tlbHistory.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompletedDates()
    }

    private fun getAllCompletedDates() {
        val allCompletedDatesList = DBHandler(this, null).getAllCompletedDatesList()

        if (allCompletedDatesList.size > 0) {
            txtHistory.visibility = View.VISIBLE
            rcvHistory.visibility = View.VISIBLE
            txtNoDataAvailable.visibility = View.GONE

            rcvHistory.adapter = HistoryAdapter(this, allCompletedDatesList)
            rcvHistory.layoutManager = LinearLayoutManager(this)
        } else {
            txtHistory.visibility = View.GONE
            rcvHistory.visibility = View.GONE
            txtNoDataAvailable.visibility = View.VISIBLE
        }
    }
}