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

class LoginActivity : AppCompatActivity() {
    private lateinit var etUserName : EditText
    private lateinit var etPass : EditText
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUserName = findViewById(R.id.etUserName)
        etPass = findViewById(R.id.etPass)
        btnLogin = findViewById(R.id.btnLogin)


        val sharedPref = this.getSharedPreferences(
            "credenciales", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("username","sinRegistrar")
        val password = sharedPref.getString("pass","sinRegistrar")

        if(userName.equals("sinRegistrar"))
            startActivity(Intent(this, RegisterActivity::class.java))


        btnLogin.setOnClickListener {
            if(etUserName.text.isNullOrEmpty() || etPass.text.isNullOrEmpty()){
                Toast.makeText(this,"Es obligatorio completar todos los campos", Toast.LENGTH_SHORT).show()
            } else {


                if(userName.equals(etUserName.text.toString()) && password.equals(etPass.text.toString())){
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this,userName + etUserName.text.toString(), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}