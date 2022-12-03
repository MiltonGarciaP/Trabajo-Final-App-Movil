package com.milton.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var  authStateListener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btningresar : Button = findViewById(R.id.btningresar)
        val txtemail : TextView = findViewById(R.id.txtemail)
        val txtcontra : TextView = findViewById(R.id.txtcontraseña)
        val crearC : Button = findViewById(R.id.registrar)
        val ocontraseña : Button = findViewById(R.id.ocontraseña)


        ocontraseña.setOnClickListener()
        {
            val i = Intent(this , MainActivity4::class.java)
            startActivity(i)
        }

        crearC.setOnClickListener()
        {
            val i = Intent(this ,MainActivity3::class.java )
            startActivity(i)

        }
        firebaseAuth = Firebase.auth
        btningresar.setOnClickListener()
        {


               if(txtcontra.length() == 0 )
               {
                  Toast.makeText(baseContext, "Ha dejado la contraseña vacia",Toast.LENGTH_SHORT).show()
               }else if (txtemail.length() == 0)
               {
                   Toast.makeText(baseContext, "Ha dejado el email vacio ",Toast.LENGTH_SHORT).show()
               }else if (txtemail.length() == 0 or txtcontra.length())
               {
                   Toast.makeText(baseContext, "Ha dejado el email vacio o contraseña vacio",Toast.LENGTH_SHORT).show()
               }
            else {

                   signIn(txtemail.text.toString(), txtcontra.text.toString())

               }





        }
    }

    private fun signIn(email: String, password: String)
    {


        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful)
                {
                    val user = firebaseAuth.currentUser
                    val verificar = user?.isEmailVerified

                    if(verificar == true)
                    {
                        Toast.makeText(baseContext, "Ha ingresado correctamente", Toast.LENGTH_SHORT).show()
                        val i = Intent(this ,MainActivity2::class.java )
                        startActivity(i)
                    } else
                    {
                        Toast.makeText(baseContext,"No ha verificado su correo", Toast.LENGTH_SHORT).show()
                    }

                }else
                {
                    Toast.makeText(baseContext,"Error de Email y/o contraseña", Toast.LENGTH_SHORT).show()
                }

            }
    }
}