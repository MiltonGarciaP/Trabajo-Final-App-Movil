package com.milton.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity4 : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val txtemail : TextView = findViewById(R.id.txtC)
        val btnC : Button = findViewById(R.id.btnc)


        btnC.setOnClickListener()
        {
            if(txtemail.length() == 0)
            {
                Toast.makeText(baseContext, "Ha dejado el correo vacio"  , Toast.LENGTH_SHORT).show()

            }else
            {
                sendPass(txtemail.text.toString())
            }

        }


        firebaseAuth = Firebase.auth

    }

     private fun sendPass(email: String)
     {
         firebaseAuth.sendPasswordResetEmail(email)
             .addOnCompleteListener(){ task ->
                 if(task.isSuccessful)
                 {
                     Toast.makeText(baseContext, "Se ha enviado un correo al correo puesto", Toast.LENGTH_SHORT).show()
                     val i = Intent(this , MainActivity::class.java)
                     startActivity(i)

                 }else
                 {
                     Toast.makeText(baseContext, "Ha ocurrido un error" + task.exception, Toast.LENGTH_SHORT).show()
                 }

             }

     }



}