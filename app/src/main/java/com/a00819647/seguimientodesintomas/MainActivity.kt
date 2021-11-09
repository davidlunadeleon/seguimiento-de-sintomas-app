package com.a00819647.seguimientodesintomas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a00819647.seguimientodesintomas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}