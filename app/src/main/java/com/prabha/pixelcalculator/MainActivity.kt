package com.prabha.pixelcalculator

import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by prabhakaranpanjalingam on 24,July,2021
 */
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val BASE_DPI = 160
    private var selectedType: String = ""
    var selectedBin: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val type: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            getDensityType()
        )
        type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dpTypeSpinner.adapter = type
        val bin: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            getDpValues()
        )
        bin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dpBinsSpinner.adapter = bin
        dpBinsSpinner.onItemSelectedListener = this
        dpTypeSpinner.onItemSelectedListener = this
        submit.setOnClickListener {
            val selectedValue = etValue.text.toString().toInt()
            for (dpdetails: DpDetails in getDpValues()) {
                runOnUiThread{
                    when (dpdetails.dpType) {
                        "LDPI" -> {
                            getValues(ldpiValue, selectedType, dpdetails.dpValue, selectedValue)
                        }
                        "MDPI" -> {
                            getValues(mdpiValue, selectedType, dpdetails.dpValue, selectedValue)
                        }
                        "TVDPI" -> {
                            getValues(tvdpiValue, selectedType, dpdetails.dpValue, selectedValue)
                        }
                        "HDPI" -> {
                            getValues(hdpiValue, selectedType, dpdetails.dpValue, selectedValue)
                        }
                        "XHDPI" -> {
                            getValues(xHdpiValue, selectedType, dpdetails.dpValue, selectedValue)
                        }
                        "XXHDPI" -> {
                            getValues(xxHdpiValue, selectedType, dpdetails.dpValue, selectedValue)
                        }
                        "XXXHDPI" -> {
                            getValues(xxxHdpiValue, selectedType, dpdetails.dpValue, selectedValue)
                        }
                    }
                }

            }

        }

    }

    fun getValues(textView: TextView, selectedType: String, dpValue: Int, selectedValue: Int) {
        Log.e("Selected :", dpValue.toString() + "---  " + selectedType)
        when (selectedType) {
            "Px" -> {
                tvSizeTitle.text = "Px"
                val dp = calculateDpValue(selectedValue, selectedBin)
                Log.e("Dp :", dp.toString())
                setPxValues(textView, calculatePxValue(dp, dpValue))
            }
        }
    }


    fun getDpValues(): List<DpDetails> {
        val list = arrayListOf<DpDetails>()
        list.add(DpDetails("LDPI", 120))
        list.add(DpDetails("MDPI", 160))
        list.add(DpDetails("TVDPI", 213))
        list.add(DpDetails("HDPI", 240))
        list.add(DpDetails("XHDPI", 320))
        list.add(DpDetails("XXHDPI", 480))
        list.add(DpDetails("XXXHDPI", 640))
        return list
    }

    fun getDensityType(): List<String> {
        val list = arrayListOf<String>()
        list.add("Px")
        return list
    }

    fun calculateDpValue(value: Int, selectedDp: Int): Double {
        val dpValue:Double = (selectedDp.toDouble()/BASE_DPI.toDouble())
        val result = (value.toDouble() /dpValue)
        return result
    }

    fun calculatePxValue(respectiveDpValue: Double, selectedDp: Int): Double {
        val value = selectedDp.toDouble()/BASE_DPI.toDouble()
        val result = (respectiveDpValue*value).toDouble()
        Log.e("Pxvalues :", value.toString() + " --- " + result.toString())
        return result
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {
            R.id.dpBinsSpinner -> {
                selectedBin = getDpValues()[position].dpValue

            }
            R.id.dpTypeSpinner -> {
                selectedType = getDensityType()[position]

            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    fun setDpValues(textView: TextView, value: Int) {
        textView.text = value.toString()
    }

    private fun setPxValues(textView: TextView, value: Double) {
        textView.text = "%.2f".format(value).toString()
    }
}