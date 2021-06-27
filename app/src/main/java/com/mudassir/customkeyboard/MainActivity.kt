package com.mudassir.customkeyboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.mudassir.customkeyboard.keyboard.NumberKeyboard
import com.mudassir.customkeyboard.keyboard.NumberKeyboardListener

class MainActivity : AppCompatActivity(), NumberKeyboardListener {

    private lateinit var amountEditText: TextView
    private var amountText: String = ""
    private var amount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        title = "Keyboard decimal"
        amountEditText = findViewById(R.id.amount)
        val numberKeyboard = findViewById<NumberKeyboard>(R.id.numberKeyboard)
        numberKeyboard.setListener(this)
    }

    override fun onNumberClicked(number: Int) {
        if (amountText.isEmpty() && number == 0) {
            return
        }
        updateAmount(amountText + number)
    }

    override fun onLeftAuxButtonClicked() {
        // dot button
        if (!hasDot(amountText)) {
            amountText = if (amountText.isEmpty()) "0." else "$amountText."
            showAmount(amountText)
        }
    }

    override fun onRightAuxButtonClicked() {
        // Delete button
        if (amountText.isEmpty()) {
            return
        }
        var newAmountText: String
        if (amountText.length <= 1) {
            newAmountText = ""
        } else {
            newAmountText = amountText.substring(0, amountText.length - 1)
            if (newAmountText[newAmountText.length - 1] == ',') {
                newAmountText = newAmountText.substring(0, newAmountText.length - 1)
            }
            if ("0" == newAmountText) {
                newAmountText = ""
            }
        }
        updateAmount(newAmountText)
    }

    /**
     * Update new entered amount if it is valid.
     */
    private fun updateAmount(newAmountText: String) {
//        val newAmount = if (newAmountText.isEmpty()) 0.0 else java.lang.Double.parseDouble(newAmountText.replace(",".toRegex(), "."))
        val newAmount = if (newAmountText.isEmpty()) 0.0 else java.lang.Double.parseDouble(newAmountText)

        Log.d("showAmount update","$newAmount   $newAmountText")
        if (newAmount in 0.0..MAX_ALLOWED_AMOUNT
            && getNumDecimals(newAmountText) <= MAX_ALLOWED_DECIMALS) {
            amountText = newAmountText
            amount = newAmount
            showAmount(amountText)
        }
    }

    /**
     * Add . every thousand.
     */
    private fun addThousandSeparator(amount: String): String {
        var integer: String
        val decimal: String
        if (amount.indexOf('.') >= 0) {
            integer = amount.substring(0, amount.indexOf('.'))
            decimal = amount.substring(amount.indexOf('.'), amount.length)
        } else {
            integer = amount
            decimal = ""
        }
        if (integer.length > 3) {
            val tmp = StringBuilder(integer)
            var i = integer.length - 3
            while (i > 0) {
                tmp.insert(i, ",")
                i = i - 3
            }
            integer = tmp.toString()
        }
        return integer + decimal
    }

    /**
     * Shows amount in UI.
     */
    private fun showAmount(amount: String) {
        Log.d("showAmount","$amount")
        amountEditText.text = "AED " + if (amount.isEmpty()) "0" else addThousandSeparator(amount)
    }

    /**
     * Checks whether the string has a comma.
     */
    private fun hasDot(text: String): Boolean {
        for (i in 0 until text.length) {
            if (text[i] == '.') {
                return true
            }
        }
        return false
    }

    /**
     * Calculate the number of decimals of the string.
     */
    private fun getNumDecimals(num: String): Int {
        return if (!hasDot(num)) {
            0
        } else num.substring(num.indexOf('.') + 1, num.length).length
    }

    companion object {
        //maximum amount here set only for testing 99999.99 can be changed
        private const val MAX_ALLOWED_AMOUNT = 99999.99
        private const val MAX_ALLOWED_DECIMALS = 2
    }
}
