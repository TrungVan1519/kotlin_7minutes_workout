package com.example.seven_minutes_workout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seven_minutes_workout.models.Constants
import com.example.seven_minutes_workout.utils.ExerciseAdapter
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.util.*

class ExerciseActivity : AppCompatActivity() {

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration: Long = 10

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30

    private val exercises = Constants.defaultExerciseList()
    private var currentExercisePos = -1

    private var exerciseAdapter: ExerciseAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // TODO: customize ActionBar
        setSupportActionBar(tlbExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tlbExercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts!!.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "The language is not supported.", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Some errors happened when initialize.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    private fun setupRestView() {
        vRest.visibility = View.VISIBLE
        vExercise.visibility = View.GONE

        playSound()

        txtUpcomingExerciseName.text = exercises[currentExercisePos + 1].name

        restProgress = 0
        restTimer?.cancel()
        setRestProgressBar()
    }

    private fun playSound() {
        player = MediaPlayer.create(applicationContext, R.raw.press_start)
        player!!.isLooping = false
        player!!.start()
    }

    private fun setRestProgressBar() {
        progressBar.progress = restProgress
        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = (restTimerDuration - restProgress).toInt()
                txtTimer.text = (restTimerDuration - restProgress).toString()
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onFinish() {
                currentExercisePos++
                exercises[currentExercisePos].isSelected = true
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }

        (restTimer as CountDownTimer).start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupExerciseView() {
        vRest.visibility = View.GONE
        vExercise.visibility = View.VISIBLE

        imgImage.setImageResource(exercises[currentExercisePos].image)
        txtExerciseName.text = exercises[currentExercisePos].name

        speakOut(exercises[currentExercisePos].name)

        exerciseProgress = 0
        exerciseTimer?.cancel()
        setExerciseProgressBar()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setExerciseProgressBar() {
        progressBarExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = (exerciseTimerDuration - exerciseProgress).toInt()
                txtExerciseTimer.text = (exerciseTimerDuration - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePos < exercises.size - 1) {
                    exercises[currentExercisePos].isSelected = false
                    exercises[currentExercisePos].isCompleted = true
                    exerciseAdapter!!.notifyDataSetChanged()

                    setupRestView()
                } else {
                    startActivity(Intent(this@ExerciseActivity, FinishActivity::class.java))
                    finish()
                }
            }
        }

        (exerciseTimer as CountDownTimer).start()
    }

    override fun onDestroy() {
        super.onDestroy()

        restProgress = 0
        restTimer?.cancel()

        exerciseProgress = 0
        exerciseTimer?.cancel()

        tts?.stop()
        tts?.shutdown()

        player?.stop()
    }

    private fun setupExerciseStatusRecyclerView() {
        exerciseAdapter = ExerciseAdapter(this, exercises)
        rcvExerciseStatus.adapter = exerciseAdapter
        rcvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.btnYes.setOnClickListener {
            onBackPressed()
            customDialog.dismiss()
        }
        customDialog.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }
}