package com.example.myapplicationkt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplicationkt.databinding.ActivityLoginBinding


class LoginPage : AppCompatActivity() {
    var requestPerCamera = requestPermissCamera()
    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }
    private lateinit var binding: ActivityLoginBinding

    var tv_regis: TextView? = null
    var bt_login_submit: Button? = null
    //val login_request=Volley.newRequestQueue(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set Content View(ui activity_login)
        //requestPerCamera.checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usernameFocusListener()
        passwordFocusListener()
        binding.btLogin.setOnClickListener { submitLoginForm() }

    }

    private fun submitLoginForm() {
        binding.containerLoginUser.helperText = validUsername()
        binding.containerLoginPass.helperText = validPassword()

        val validEmail = binding.containerLoginUser.helperText == null
        val validPassword = binding.containerLoginPass.helperText == null

        if (validEmail && validPassword)
            resetForm()
        else
            invalidForm()
    }

    private fun invalidForm()
    {
        var message = ""
        if(binding.containerLoginUser.helperText != null)
            message += "\n\nUsername: " + binding.containerLoginUser.helperText
        if(binding.containerLoginPass.helperText != null)
            message += "\n\nPassword: " + binding.containerLoginPass.helperText


        AlertDialog.Builder(this)
            .setTitle("เกิดข้อผิดพลาด")
            .setMessage(R.string.app_requiredVerify)
            .setPositiveButton(R.string.app_okey){ _,_ ->

            }

            .show()
    }

    private fun resetForm()
    {
        Toast.makeText(this,"user: "+binding.edLoginUsername.text +
            "\npasss: "+binding.edLoginPass.text,Toast.LENGTH_LONG).show()

        binding.edLoginUsername.setText(null)
        binding.edLoginPass.setText(null)
        binding.containerLoginUser.helperText.isNullOrEmpty()
        binding.containerLoginPass.helperText.isNullOrEmpty()

        val intent_home = Intent(this, ScannerPage::class.java)
        startActivity(intent_home)

    }


    private fun passwordFocusListener()
    {
        binding.edLoginPass.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.containerLoginPass.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String?
    {
        val passwordText = binding.edLoginPass.text.toString()
        if(passwordText.length < 8)
        {
            return getString(R.string.app_requiredPass)
        }
        /*if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Must Contain 1 Lower-case Character"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }*/

        return null
    }

    private fun usernameFocusListener() {
        binding.edLoginPass.setOnFocusChangeListener { _, focused ->
            if (!focused){
                binding.containerLoginUser.helperText = validUsername()
            }
        }
    }

    private fun validUsername(): String? {
        val str_username = binding.edLoginUsername.text.toString()
        if (str_username.equals("") || str_username==null){
            return getString(R.string.app_requiredUser)
        }
        return null
    }


}