package com.example.factorial

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.factorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.buttonCalculate.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(this) {
            binding.progressBarLoading.visibility = View.GONE
            binding.buttonCalculate.isEnabled = true

            when (it) {
                is Error -> {
                    Toast.makeText(this, "you did not entered value", Toast.LENGTH_SHORT).show()
                }

                is Progress -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.buttonCalculate.isEnabled = false
                }

                is Factorial -> {
                    binding.textViewFactorial.text = it.value
                }
            }
        }
    }
}