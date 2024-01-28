package pt.ipt.dam2023.cartrade.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.AuthenticationRequest
import pt.ipt.dam2023.cartrade.model.AuthenticationResponse
import pt.ipt.dam2023.cartrade.model.User
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var emailTxt: TextInputEditText
    private lateinit var emailTil: TextInputLayout
    private lateinit var passwordTil: TextInputLayout
    private lateinit var passwordTxt: TextInputEditText
    private lateinit var backToHome: Button
    private lateinit var txtToRegisto: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        emailTxt = findViewById(R.id.txtEmail)
        emailTil = findViewById(R.id.txtInputEmail)
        passwordTxt = findViewById(R.id.txtPassword)
        passwordTil = findViewById(R.id.txtInputPassword)
        backToHome = findViewById(R.id.backToHome)
        txtToRegisto = findViewById(R.id.txtToRegisto)
        btnLogin = findViewById(R.id.btnLogin)
        txtToRegisto.setOnClickListener {
            val intent = Intent(this, Registar::class.java)
            startActivity(intent)
        }

        backToHome.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }

        btnLogin.setOnClickListener {
            val email = emailTxt.text.toString()
            val password = passwordTxt.text.toString()
            val user = AuthenticationRequest(email, password)
            autenticacao(user){result ->
                if(result != null){
                        if(result.code == 200){
                            passwordTil.isErrorEnabled = false
                            Toast.makeText(this, "Bem-vindo, ${result.nome} ${result.apelido}", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("Login", true)
                            intent.putExtra("Id", result.id)
                            startActivity(intent)
                        }else if(result.code == 401){
                            passwordTil.isErrorEnabled = true
                            passwordTil.error = "Password incorreta"
                        }else if(result.code == 404){
                            passwordTil.isErrorEnabled = true
                            passwordTil.error = "Utilizador não registado"
                        }
                    }
                }
            }
        }
    }
    private fun autenticacao(user: AuthenticationRequest, onResult: (AuthenticationResponse?) -> Unit){
        val call = Retrofit().getService().authentication(user!!.email, user!!.password)
        call.enqueue(
            object : Callback<AuthenticationResponse> {
                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }
                override fun onResponse(call: Call<AuthenticationResponse>, response: Response<AuthenticationResponse>) {
                    var result = response.body()
                    if(result == null){
                        result = AuthenticationResponse(null,null,null,null, null, response.code(), null)
                    }else{
                        result!!.code = response.code()
                    }

                    onResult(result)
                }
            }
        )
    }