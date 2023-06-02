package com.example.myapplicationkt

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode


class ScannerPage : AppCompatActivity() {
    private lateinit var codescanner: CodeScanner
    var txt_datascanner: TextView? = null
    private var CAMERA_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner_page)

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                123
            )
        } else {
            startScanning()
        }
    }

    private fun startScanning() {
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        txt_datascanner = findViewById<TextView>(R.id.tv_datascanner)
        codescanner = CodeScanner(this, scannerView)
        codescanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.CONTINUOUS
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    txt_datascanner!!.text = it.text

                }
            }
            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Scanner", "Camera error: " + "${it.message}")
                }
            }

            scannerView.setOnClickListener {
                codescanner.startPreview()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResult: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult)
        if (requestCode == 123) {
            if (grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                startScanning()
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codescanner.isInitialized) {
            codescanner?.startPreview()
        }
    }
}