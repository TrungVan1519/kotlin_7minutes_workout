package com.example.seven_minutes_workout

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private val METRIC_UNITS = "METRIC_UNIT"
    private val US_UNITS = "US_UNIT"
    private var currentVisibleView = METRIC_UNITS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        // TODO: customize ActionBar
        setSupportActionBar(tlbBMI)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Calculate BMI"
        tlbBMI.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if (currentVisibleView == METRIC_UNITS) {
                if (validateMetricUnits()) {
                    val height = txtMetricUnitHeight.text.toString().toFloat() / 100
                    val weight = txtMetricUnitWeight.text.toString().toFloat()
                    val bmi = weight / (height * height)

                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this, "Please enter valid values.", Toast.LENGTH_SHORT).show()
                }
            } else { // The values are validated.
                if (validateUSUnits()) {
                    val usUnitHeightValueFeet = txtUsUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch = txtUsUnitHeightInch.text.toString()
                    val usUnitWeightValue = txtUsUnitWeight.text.toString().toFloat()

                    // Here the Height Feet and Inch values are merged and multiplied by 12 for converting it to inches.
                    val heightValue =
                        usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                    // This is the Formula for US UNITS result.
                    // Reference Link : https://www.cdc.gov/healthyweight/assessing/bmi/childrens_bmi/childrens_bmi_formula.html
                    val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(this, "Please enter valid values.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleMetricUnitsView()

        val e = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (buttonView.id == radMetricUnits.id) {
                    makeVisibleMetricUnitsView()
                } else {
                    makeVisibleUsUnitsView()
                }
            }
        }
        radMetricUnits.setOnCheckedChangeListener(e)
        radUsUnits.setOnCheckedChangeListener(e)
    }

    private fun validateMetricUnits(): Boolean {
        return txtMetricUnitWeight.text.toString().isNotEmpty()
                && txtMetricUnitHeight.text.toString().isNotEmpty()
    }

    private fun validateUSUnits(): Boolean {
        return txtUsUnitWeight.text.toString().isNotEmpty()
                && txtUsUnitHeightFeet.text.toString().isNotEmpty()
                && txtUsUnitHeightInch.text.toString().isNotEmpty()
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        // round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        txtYourBMI.visibility = View.VISIBLE
        txtBMIValue.visibility = View.VISIBLE
        txtBMIType.visibility = View.VISIBLE
        txtBMIDescription.visibility = View.VISIBLE

        txtBMIValue.text = bmiValue
        txtBMIType.text = bmiLabel
        txtBMIDescription.text = bmiDescription
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS
        vMetricUnits.visibility = View.VISIBLE
        vUsUnits.visibility = View.GONE

        txtMetricUnitHeight.text!!.clear()
        txtMetricUnitWeight.text!!.clear()

        txtYourBMI.visibility = View.INVISIBLE
        txtBMIValue.visibility = View.INVISIBLE
        txtBMIType.visibility = View.INVISIBLE
        txtBMIDescription.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS
        vMetricUnits.visibility = View.GONE
        vUsUnits.visibility = View.VISIBLE

        txtUsUnitWeight.text!!.clear()
        txtUsUnitHeightFeet.text!!.clear()
        txtUsUnitHeightInch.text!!.clear()

        txtYourBMI.visibility = View.INVISIBLE
        txtBMIValue.visibility = View.INVISIBLE
        txtBMIType.visibility = View.INVISIBLE
        txtBMIDescription.visibility = View.INVISIBLE
    }
}