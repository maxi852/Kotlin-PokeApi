package com.example.pokedexapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    private lateinit var etUserName : EditText
    private lateinit var etPass : EditText
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUserName = findViewById(R.id.etUserNameReg)
        etPass = findViewById(R.id.etPassReg)
        btnRegister = findViewById(R.id.btnRegisterReg)

        btnRegister.setOnClickListener {
            if(etUserName.text.isNullOrEmpty() || etPass.text.isNullOrEmpty()){
                Toast.makeText(this,"Es obligatorio completar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val sharedPref = this.getSharedPreferences(
                    "credenciales", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("username",etUserName.text.toString())
                editor.putString("pass",etPass.text.toString())
                editor.apply()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}