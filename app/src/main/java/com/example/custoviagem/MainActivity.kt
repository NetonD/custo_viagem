package com.example.custoviagem

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.custoviagem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCalculate.setOnClickListener(calculate())
    }

    private fun calculate(): (View) -> Unit {
        return { view: View ->
            val invalidField = invalidField()
            if (invalidField == InvalidField.ALL_VALID) {
                updateResultValue(view)
            } else {
                showToastError(invalidField)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateResultValue(view: View) {

        val autonomy = binding.editAutonomy.text.toString().toFloat()
        val distance = binding.editDistance.text.toString().toFloat()
        val price = binding.editPrice.text.toString().toFloat()
        binding.textTotalValue.text = "R$ ${(distance * price) / autonomy}"
    }

    private fun invalidField(): InvalidField {
        val autonomyText = binding.editAutonomy.text.toString()
        val distanceText = binding.editDistance.text.toString()
        val priceText = binding.editPrice.text.toString()

        if (autonomyText == "" || autonomyText.toFloat() == 0f) return InvalidField.AUTONOMY
        if (distanceText == "") return InvalidField.DISTANCE
        if (priceText == "") return InvalidField.PRICE

        return InvalidField.ALL_VALID
    }

    private fun showToastError(field: InvalidField) {
        val message = when (field) {
            InvalidField.DISTANCE -> R.string.invalid_distance
            InvalidField.PRICE -> R.string.invalid_price
            InvalidField.AUTONOMY -> R.string.invalid_autonomy
            else -> R.string.unexpected_error
        }

        Toast
            .makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }
}

enum class InvalidField {
    DISTANCE, AUTONOMY, PRICE, ALL_VALID
}