package com.example.zadaniekalkulator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import kotlin.math.log
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var inputText = "0"
        var stackText = ""
        var lastOp = ""
        val  inputTextView: TextView = findViewById(R.id.text_view_id)
        val  inputTextViewOld: TextView = findViewById(R.id.text_view_old_id)
        inputTextView.setText(inputText)
        inputTextViewOld.setText(stackText)

        val button0: Button = findViewById(R.id.button_0)
        val button1: Button = findViewById(R.id.button_1)
        val button2: Button = findViewById(R.id.button_2)
        val button3: Button = findViewById(R.id.button_3)
        val button4: Button = findViewById(R.id.button_4)
        val button5: Button = findViewById(R.id.button_5)
        val button6: Button = findViewById(R.id.button_6)
        val button7: Button = findViewById(R.id.button_7)
        val button8: Button = findViewById(R.id.button_8)
        val button9: Button = findViewById(R.id.button_9)


        val buttonPlus: Button = findViewById(R.id.button_plus)
        val buttonMinus: Button = findViewById(R.id.button_minus)
        val buttonStar: Button = findViewById(R.id.button_star)
        val buttonDivide: Button = findViewById(R.id.button_slash)
        val buttonEqual: Button = findViewById(R.id.button_equal)

        val buttonPower: Button = findViewById(R.id.button_power)
        val buttonLog: Button = findViewById(R.id.button_log)
        val buttonPro: Button = findViewById(R.id.button_modulo)
        val buttonInvert: Button = findViewById(R.id.button_invert)

        val buttonClean: Button = findViewById(R.id.button_C)

        button0.setOnClickListener {
            if(inputText == "0") {
                inputText = "0"
            }
            else {
                inputText += "0"
            }
            inputTextView.setText(inputText)
        }
        button1.setOnClickListener {
            if(inputText == "0") {
                inputText = "1"
            }
            else {
                inputText += "1"
            }
            inputTextView.setText(inputText)
        }
        button2.setOnClickListener {
            if(inputText == "0") {
                inputText = "2"
            }
            else {
                inputText += "2"
            }
            inputTextView.setText(inputText)
        }
        button3.setOnClickListener {
            if(inputText == "0") {
                inputText = "3"
            }
            else {
                inputText += "3"
            }
            inputTextView.setText(inputText)
        }
        button4.setOnClickListener {
            if(inputText == "0") {
                inputText = "4"
            }
            else {
                inputText += "4"
            }
            inputTextView.setText(inputText)
        }
        button5.setOnClickListener {
            if(inputText == "0") {
                inputText = "5"
            }
            else {
                inputText += "5"
            }
            inputTextView.setText(inputText)
        }
        button6.setOnClickListener {
            if(inputText == "0") {
                inputText = "6"
            }
            else {
                inputText += "6"
            }
            inputTextView.setText(inputText)
        }
        button7.setOnClickListener {
            if(inputText == "0") {
                inputText = "7"
            }
            else {
                inputText += "7"
            }
            inputTextView.setText(inputText)
        }
        button8.setOnClickListener {
            if(inputText == "0") {
                inputText = "8"
            }
            else {
                inputText += "8"
            }
            inputTextView.setText(inputText)
        }
        button9.setOnClickListener {
            if(inputText == "0") {
                inputText = "9"
            }
            else {
                inputText += "9"
            }
            inputTextView.setText(inputText)
        }

        buttonPlus.setOnClickListener {
            var nDouble = calculate(inputText, stackText, lastOp)
            var nInt = nDouble.toInt()
            if(nInt - nDouble == 0.0) {
                stackText = nInt.toString()
            }
            else {
                stackText = nDouble.toString()
            }
            inputText = "0"
            lastOp = "+"
            inputTextView.setText(inputText)
            inputTextViewOld.setText(stackText+lastOp)
        }

        buttonMinus.setOnClickListener {
            var nDouble = calculate(inputText, stackText, lastOp)
            var nInt = nDouble.toInt()
            if(nInt - nDouble == 0.0) {
                stackText = nInt.toString()
            }
            else {
                stackText = nDouble.toString()
            }
            inputText = "0"
            lastOp = "-"
            inputTextView.setText(inputText)
            inputTextViewOld.setText(stackText+lastOp)
        }

        buttonStar.setOnClickListener {
            var nDouble = calculate(inputText, stackText, lastOp)
            var nInt = nDouble.toInt()
            if(nInt - nDouble == 0.0) {
                stackText = nInt.toString()
            }
            else {
                stackText = nDouble.toString()
            }
            inputText = "0"
            lastOp = "*"
            inputTextView.setText(inputText)
            inputTextViewOld.setText(stackText+lastOp)
        }

        buttonDivide.setOnClickListener {
            var nDouble = calculate(inputText, stackText, lastOp)
            var nInt = nDouble.toInt()
            if(nInt - nDouble == 0.0) {
                stackText = nInt.toString()
            }
            else {
                stackText = nDouble.toString()
            }
            inputText = "0"
            lastOp = "/"
            inputTextView.setText(inputText)
            inputTextViewOld.setText(stackText+lastOp)
        }

        buttonPower.setOnClickListener {
            var nDouble = calculate(inputText, stackText, "^")
            var nInt = nDouble.toInt()
            if(nInt - nDouble == 0.0) {
                inputText = nInt.toString()
            }
            else {
                inputText = nDouble.toString()
            }
            inputTextView.setText(inputText)
        }

        buttonLog.setOnClickListener {
            var nDouble = calculate(inputText, stackText, "log")
            var nInt = nDouble.toInt()
            if(nInt - nDouble == 0.0) {
                inputText = nInt.toString()
            }
            else {
                inputText = nDouble.toString()
            }
            inputTextView.setText(inputText)
        }

        buttonPro.setOnClickListener {
            var nDouble = calculate("100", inputText, "/")
            inputText = nDouble.toString()
            inputTextView.setText(inputText)
        }

        buttonInvert.setOnClickListener {
            var nDouble = calculate(inputText, "-1", "*")
            var nInt = nDouble.toInt()
            if(nInt - nDouble == 0.0) {
                inputText = nInt.toString()
            }
            else {
                inputText = nDouble.toString()
            }
            inputTextView.setText(inputText)
        }

        buttonEqual.setOnClickListener {
            var nDouble = calculate(inputText, stackText, lastOp)
            var nInt = nDouble.toInt()
            if(nInt - nDouble == 0.0) {
                inputText = nInt.toString()
            }
            else {
                inputText = nDouble.toString()
            }
            stackText = ""
            lastOp = ""
            inputTextView.setText(inputText)
            inputTextViewOld.setText(stackText)
        }


        buttonClean.setOnClickListener {
            inputText = "0"
            stackText = ""
            lastOp = ""
            inputTextView.setText(inputText)
            inputTextViewOld.setText(stackText)
        }
    }

    fun calculate(arg1: String, arg2: String, oper: String) :Double {
        val arg1Double: Double = arg1.toDouble()
        var arg2Double: Double = 0.0
        if(!arg2.isEmpty()) {
            arg2Double = arg2.toDouble()
        }
        when(oper) {
            "+" -> return arg2Double + arg1Double
            "-" -> return arg2Double - arg1Double
            "*" -> return arg2Double * arg1Double
            "/" -> return arg2Double / arg1Double
            "^" -> return arg1Double.pow(2)
            "log" -> return log(arg1Double, 2.0)
            else -> {return arg1Double}
        }
    }
}

