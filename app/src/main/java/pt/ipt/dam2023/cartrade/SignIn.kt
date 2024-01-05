package pt.ipt.dam2023.cartrade

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignIn: AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confPassword: EditText
    private lateinit var btnCriarConta: Button
    private lateinit var db:DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)

        findViewById<Button>(R.id.btnToLogin).setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        username = findViewById(R.id.txtUsername)
        email = findViewById(R.id.txtEmail)
        password = findViewById(R.id.txtPassword)
        confPassword = findViewById(R.id.txtConfirmarPassword)
        btnCriarConta = findViewById(R.id.btnCriarConta)
        db = DBHelper(this)

        btnCriarConta.setOnClickListener {
            val userText = username.text.toString()
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            val confPasswordText = confPassword.text.toString()
            val guardarDados = db.inserirDados(userText, emailText, passwordText)

            if(TextUtils.isEmpty(userText) || TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(confPasswordText)){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }else{
                if(passwordText.equals(confPasswordText)){
                    if(guardarDados==true){
                        Toast.makeText(this, "Registo concluído com sucesso", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Utilizador já registado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(this, "Passwords não coincidem", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
    }

}