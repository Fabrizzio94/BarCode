package com.example.barcode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.barcode.databinding.ActivityMenuOptionsBinding

class MenuOptions : AppCompatActivity() {
    private lateinit var binding: ActivityMenuOptionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun goToOtherActivity(view: View) {
        val intent = Intent(this, CaptureContinuosActivity::class.java)
        startActivity(intent)
    }
}