package com.example.barcode.fragments

import android.Manifest
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.barcode.R as Rfrg
import com.example.barcode.databinding.ActivityCaptureContinuosBinding
import com.example.barcode.databinding.FragmentScanContinuosBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.*


// capture binding
public class ScanContinuosFragment : Fragment() {
    private lateinit var binding: FragmentScanContinuosBinding
    private var barcodeView: DecoratedBarcodeView? = null
    private var beepManager: BeepManager? = null
    private var tvTexto: TextView? = null
    private var lastText: String? = null
    val bundle = Bundle()

    private companion object{
        private const val TAG = "PERMISOS_TAG"
    }

    private val callback = BarcodeCallback { result ->
        if (result.text == null || result.text == lastText) {
            return@BarcodeCallback
        }
        lastText = result.text
        barcodeView!!.setStatusText("")
        beepManager!!.playBeepSoundAndVibrate()
//        val imageView = findViewById<ImageView>(R.id.barcodePreview)
//        imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW))
        // tvTexto = binding.tvLectura
        Toast.makeText(activity,this.lastText, Toast.LENGTH_SHORT).show()
        //tvTexto!!.text = this.lastText
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanContinuosBinding.inflate(inflater, container, false)
        return binding.root
        /*val view: View = inflater.inflate(Rfrg.layout.fragment_scan_continuos, container, false)
        return view*/
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        barLaunch()
    }

    /**** Bar Code code ***/
    private fun barLaunch() {
        // permite asignar el view al decorador
        barcodeView = binding.barcodeScanner
        val formats: Collection<BarcodeFormat> =
            listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        barcodeView?.setStatusText("FR: Pasa el codigo por favor")
        barcodeView?.barcodeView?.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView?.initializeFromIntent(Intent())
        barcodeView!!.decodeContinuous(callback)
        beepManager = BeepManager(requireActivity())
    }


    override fun onResume() {
        super.onResume()
        barcodeView?.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView?.pause()
    }

    fun Fragment.onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeView?.onKeyDown(keyCode, event) == true || onKeyDown(keyCode, event)
    }
    /* PERMISOS DE USO DE CAMARA */
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            Log.d(ScanContinuosFragment.TAG, "PermisoLauncher: isGranted: $isGranted")
            if (isGranted){
                //singlePermissionGranted()
                Toast.makeText(activity,"Permisos permitido", Toast.LENGTH_SHORT).show()
                //barLaunch()
            }else{
                Toast.makeText(activity,"Permisos Denegados", Toast.LENGTH_SHORT).show()

            }
        }
    private fun requestPermission(){
        when{
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // permisos otorgados
                Toast.makeText(activity,"PERMITIDO", Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            ) -> {
                // additiona rationale be displayed
                Toast.makeText(requireActivity(),"PERMITIDO", Toast.LENGTH_SHORT).show()
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