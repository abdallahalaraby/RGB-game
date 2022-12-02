package com.abdallahalaraby.rgb

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.abdallahalaraby.rgb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val colors = listOf(
        ColorHolder("Red", Color.parseColor("#ff0000")),
        ColorHolder("Green", Color.parseColor("#00ff00")),
        ColorHolder("Blue", Color.parseColor("#0000ff"))
    )
    private lateinit var binding: ActivityMainBinding
    private var score = 0
    private var currentColor: ColorHolder = colors[0] // temp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.startGame.setOnClickListener {
            start()
        }
        val colorsOnClickListeners = OnClickListener {
            checkAnswer(it)
            shuffle()
        }
        binding.button1.setOnClickListener(colorsOnClickListeners)
        binding.button2.setOnClickListener(colorsOnClickListeners)
        binding.button3.setOnClickListener(colorsOnClickListeners)
    }

    private fun start() {
        binding.startView.isVisible = false
        binding.gameView.isVisible = true
        score = 0
        startTimer()
        shuffle()
    }

    private fun startTimer() {

    }

    private fun checkAnswer(button: View) {
        val buttonColor = (button.background as ColorDrawable).color
        if (buttonColor == currentColor.hexValue) {
            score++
        }
    }

    private fun shuffle() {
        currentColor = getRandomColor()
        binding.colorLabel.setTextColor(getRandomColor().hexValue)
        binding.colorLabel.text = currentColor.name

        val buttonBackgroundColors = colors.toMutableList().apply { shuffle() }
        val buttonTextColors = colors.toMutableList().apply { shuffle() }

        binding.button1.text = buttonTextColors[0].name
        binding.button1.setBackgroundColor(buttonBackgroundColors[0].hexValue)
        binding.button2.text = buttonTextColors[1].name
        binding.button2.setBackgroundColor(buttonBackgroundColors[1].hexValue)
        binding.button3.text = buttonTextColors[2].name
        binding.button3.setBackgroundColor(buttonBackgroundColors[2].hexValue)

        updateScore()
    }

    private fun updateScore() {
        binding.score.text = getString(R.string.score, score)
    }

    private fun getRandomColor(): ColorHolder = colors.random()
}