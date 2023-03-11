package com.example.barcode

import android.Manifest
import android.app.Instrumentation.ActivityResult
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.barcode.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var barcodeView: DecoratedBarcodeView? = null
    private var beepManager: BeepManager? = null
    private companion object{
        private const val TAG = "PERMISOS_TAG"
    }
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()
        barLaunch()
    }

    private fun barLaunch() {
        val formats: Collection<BarcodeFormat> =
            listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        barcodeView?.barcodeView?.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView?.initializeFromIntent(this.intent)
        //barcodeView!!.decodeContinuous(callback)
        beepManager = BeepManager(this)
    }


    override fun onResume() {
        super.onResume()
        barcodeView?.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView?.pause()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeView?.onKeyDown(keyCode, event) ?: super.onKeyDown(keyCode, event)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
            Log.d(TAG, "PermisoLauncher: isGranted: $isGranted")
            if (isGranted){
                //singlePermissionGranted()
                Toast.makeText(this,"Permisos permitido",Toast.LENGTH_SHORT).show()
                //barLaunch()
            }else{
                Toast.makeText(this,"Permisos Denegados",Toast.LENGTH_SHORT).show()

            }
        }
    private fun requestPermission(){
        when{
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // permisos otorgados
                Toast.makeText(this,"PERMITIDO",Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
                ) -> {
                    // additiona rationale be displayed
                    Toast.makeText(this,"PERMITIDO",Toast.LENGTH_SHORT).show();
                run {
                    requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
                }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
                // Permission hos not been asked yet

            }
        }
    }


}