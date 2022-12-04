package com.milton.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity3 : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val txtnombre : TextView = findViewById(R.id.editnombre)
        val txtemail : TextView = findViewById(R.id.editcorreo)
        val txtcontraseña : TextView = findViewById(R.id.editcontraseña)
        val txtcontra : TextView = findViewById(R.id.editrepe)
        val btncrear : Button = findViewById(R.id.crearU)
        val btnvolver : Button = findViewById(R.id.volver)

        btnvolver.setOnClickListener()
        {
            val i = Intent(this ,MainActivity::class.java )
            startActivity(i)
        }

        btncrear.setOnClickListener()
        {
            var pass1 = txtcontraseña.text.toString()
            var pass2 = txtcontra.text.toString()

            if (txtnombre.length() == 0)
            {
                Toast.makeText(baseContext, "Debe introducir un nombre", Toast.LENGTH_SHORT).show()
                txtnombre.requestFocus()

            }else if (txtemail.length() == 0)
            {
                Toast.makeText(baseContext, "Debe introducir un email", Toast.LENGTH_SHORT).show()
                txtemail.requestFocus()

            }else if (txtcontraseña.length() == 0)
            {
                Toast.makeText(baseContext, "debe introducir una contraseña", Toast.LENGTH_SHORT).show()
                txtcontraseña.requestFocus()

            }else if (txtcontra.length() == 0)
            {
                Toast.makeText(baseContext, "debe repetir la contraseña", Toast.LENGTH_SHORT).show()
                txtcontra.requestFocus()
            }

            else if (pass1.equals(pass2))
            {

                createAccounnt(txtemail.text.toString() , txtcontraseña.text.toString())


            }else
            {
                Toast.makeText(baseContext, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show()
                txtcontraseña.requestFocus()
            }

        }

        firebaseAuth = Firebase.auth
    }

    private fun createAccounnt(email: String, password: String )
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful)
                {
                    SendConfirm()
                    Toast.makeText(baseContext, "Cuenta creada correctamente , se ha enviado un correo para verificar", Toast.LENGTH_SHORT).show()
                    val i = Intent(this ,MainActivity::class.java )
                    startActivity(i)
                }else
                {
                    Toast.makeText(baseContext, "Ha ocurrido un error" + task.exception, Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun SendConfirm ()
    {
      val user = firebaseAuth.currentUser!!
              user.sendEmailVerification()
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful)
                {

                }else
                {

                }
            }

    }


}
