package com.android.liveconcerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.android.liveconcerts.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setup()
    }

    private fun setup(){
        title = "Autenticación"

        binding.btnSignIn.setOnClickListener{
            if (binding.fieldUser.text.isNotEmpty() && binding.fieldPassword.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(binding.fieldUser.toString(), binding.fieldPassword.toString()).addOnCompleteListener(){
                        if (it.isSuccessful){
                            showMain()
                        }else{
                            showAlert()
                        }
                    }
            }
        }
        binding.btnLogin.setOnClickListener {
            if (binding.fieldUser.text.isNotEmpty() && binding.fieldPassword.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(binding.fieldUser.toString(), binding.fieldPassword.toString()).addOnCompleteListener(){
                        if (it.isSuccessful){
                            showMain()
                        }else{
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showMain(){
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }
}