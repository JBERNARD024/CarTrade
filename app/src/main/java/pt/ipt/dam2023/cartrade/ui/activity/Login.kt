package pt.ipt.dam2023.cartrade.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pt.ipt.dam2023.cartrade.R


class Login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var email: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        findViewById<TextView>(R.id.txtToSignIn).setOnClickListener {
            val intent = Intent(this, Registar::class.java)
            startActivity(intent)
        }
    }
}