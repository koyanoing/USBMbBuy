package com.example.usbmbbuy

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.usbmbbuy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val REQUEST_CALL_PERMISSION = 101
    private val USSD_CODE = "*966*4*5*1#"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()

        binding.btnBuyMB.setOnClickListener {
            sendUSSDCode()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL_PERMISSION
            )
        }
    }

    private fun sendUSSDCode() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$USSD_CODE")
        
        try {
            startActivity(intent)
            Toast.makeText(this, "MB ဝယ်ယူခြင်း စတင်သည်...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "အမှားတစ်ခု ဖြစ်ပေါ်နေပါသည်: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied - App မလည်နိုင်ပါ", Toast.LENGTH_LONG).show()
            }
        }
    }
}
