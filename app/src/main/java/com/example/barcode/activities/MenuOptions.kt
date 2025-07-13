package com.example.barcode.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.replace
import com.example.barcode.R
import com.example.barcode.databinding.ActivityMenuOptionsBinding
import com.example.barcode.fragments.MainMenuFragment
import com.example.barcode.fragments.ScanContinuosFragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
class MenuOptions : AppCompatActivity() {
    private lateinit var binding: ActivityMenuOptionsBinding
    val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun goToOtherActivity(view: View) {

        Toast.makeText(this,"clic", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, CaptureContinuosActivity::class.java)
        //startActivity(intent)
    }
    fun switchToScannerBar(view: View){
        //Toast.makeText(this,"llamar fragmento bar", Toast.LENGTH_SHORT).show()
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentoMenu) as? MainMenuFragment
        /*fragment?.funcionDesdeFragment()*/
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView2, ScanContinuosFragment())
            .addToBackStack(null)
            .commit()
    }
}