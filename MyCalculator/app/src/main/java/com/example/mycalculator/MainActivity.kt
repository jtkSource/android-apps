package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    private var lastNumeric: Boolean = false
    private var hasDot: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) { // view is the component that calls this method
        if (view is Button) {
            tvInput?.append(view.text)
            lastNumeric = true
        }
    }

    fun onClear(view: View) {
        if (view is Button) {
            tvInput?.text = ""
            hasDot = false
            lastNumeric = false
        }
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !hasDot) {
            tvInput?.append(".")
            lastNumeric = false
            hasDot = true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false
                hasDot = false
            }
        }

    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
                    value.contains("*")
            value.contains("+") ||
                    value.contains("-")
        }
    }

    fun onEqual(view: View) {
        tvInput?.text?.let {
            if (lastNumeric) {
                var tvValue = it.toString()
                var prefix = ""
                try {
                    if (tvValue.startsWith("-")) {
                        prefix = "-"
                        tvValue = tvValue.substring(1)
                    }
                    when {
                        tvValue.contains("-") -> performOperation("-", tvValue, prefix)
                        tvValue.contains("+") -> performOperation("+", tvValue, prefix)
                        tvValue.contains("*") -> performOperation("*", tvValue, prefix)
                        tvValue.contains("/") -> performOperation("/", tvValue, prefix)
                        else -> throw IllegalArgumentException("Unsupported operator!")
                    }
                } catch (e: ArithmeticException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun performOperation(operator: String, tvValue: String, prefix: String) {
        val splitValue = tvValue.split(operator)
        var one = splitValue[0]
        var two = splitValue[1]
        if (prefix.isNotEmpty()) {
            one = prefix + one
        }
        when (operator) {
            "-" -> tvInput?.text = (one.toDouble() - two.toDouble()).toString()
            "+" -> tvInput?.text = (one.toDouble() + two.toDouble()).toString()
            "*" -> tvInput?.text = (one.toDouble() * two.toDouble()).toString()
            "/" -> tvInput?.text = (one.toDouble() / two.toDouble()).toString()
            else -> throw IllegalArgumentException("Unsupported Operator: $operator")
        }
        removeTrailingZero(tvInput)
    }

    private fun removeTrailingZero(value: TextView?) {
        value?.let {
            val stringValue = it.text.toString()
            if (stringValue.endsWith(".0")) {
                it.text = stringValue.substring(0, stringValue.length - 2)
            }
        }
    }
}