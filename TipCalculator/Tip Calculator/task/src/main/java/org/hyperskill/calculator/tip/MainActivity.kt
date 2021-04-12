package org.hyperskill.calculator.tip

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.slider.Slider
import java.math.BigDecimal
import java.math.RoundingMode

val MINUS_ONE: BigDecimal = BigDecimal.valueOf(-1.0)
val HUNDRED: BigDecimal = BigDecimal.valueOf(100)

class MainActivity : AppCompatActivity() {
    private lateinit var edit: EditText
    private lateinit var slider: Slider
    private lateinit var text: TextView

    private var number = MINUS_ONE
    private var percentage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edit = findViewById(R.id.edit_text)
        slider = findViewById(R.id.slider)
        text = findViewById(R.id.text_view)

        edit.doAfterTextChanged {
            number = if (edit.length() > 0) BigDecimal(edit.text.toString()) else MINUS_ONE
            updateTextVisible()
        }

        slider.addOnChangeListener { _, value, _ ->
            percentage = value.toInt()
            updateTextVisible()
        }
    }

    private fun updateTextVisible() {
        if (number != MINUS_ONE) {
            text.text = if (percentage != 0) {
                val amount = number.multiply(percentage.toBigDecimal()).divide(HUNDRED, 2, RoundingMode.HALF_UP)
                String.format(getString(R.string.text_view_text), amount)
            } else {
                "Tip amount: 0.00"
            }
        } else {
            text.text = ""
        }
    }
}