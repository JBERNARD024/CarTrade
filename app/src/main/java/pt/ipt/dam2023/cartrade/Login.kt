package pt.ipt.dam2023.cartrade

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Login: AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        findViewById<TextView>(R.id.txtToSignIn).setOnClickListener {
            val intent = Intent(this, Registar::class.java)
            startActivity(intent)
        }

        email = findViewById(R.id.txtEmail)
        password = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        db = DBHelper(this)
        
        btnLogin.setOnClickListener { 
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if(TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }else{
                val verificaUser = db.verificaUtilizador(emailText, passwordText)
                if(verificaUser==true){
                    Toast.makeText(this, "Autenticação bem sucedida", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Dados Inválidos", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }
}