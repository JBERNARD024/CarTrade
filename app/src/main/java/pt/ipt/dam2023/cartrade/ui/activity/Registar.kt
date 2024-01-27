package pt.ipt.dam2023.cartrade.ui.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.APIResult
import pt.ipt.dam2023.cartrade.model.User
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registar : AppCompatActivity(){

    private lateinit var users: List<User>

    private lateinit var nomeTxt: TextInputEditText
    private lateinit var apelidoTxt: TextInputEditText
    private lateinit var emailTxt: TextInputEditText
    private lateinit var passwordTxt: TextInputEditText
    private lateinit var confPasswordTxt: TextInputEditText

    private lateinit var nomeTil: TextInputLayout
    private lateinit var apelidoTil: TextInputLayout
    private lateinit var emailTil: TextInputLayout
    private lateinit var passwordTil: TextInputLayout
    private lateinit var confPasswordTil: TextInputLayout
    private lateinit var btnCriarConta: Button
    private lateinit var backToLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registar)
        nomeTxt = findViewById(R.id.txtNome)
        apelidoTxt = findViewById(R.id.txtApelido)
        emailTxt = findViewById(R.id.txtEmail)
        passwordTxt = findViewById(R.id.txtPassword)
        confPasswordTxt = findViewById(R.id.txtConfirmarPassword)
        btnCriarConta = findViewById(R.id.btnCriarConta)
        nomeTil = findViewById(R.id.txtInputNome)
        apelidoTil = findViewById(R.id.txtInputApelido)
        emailTil = findViewById(R.id.txtInputEmail)
        passwordTil = findViewById(R.id.txtInputPassword)
        confPasswordTil = findViewById(R.id.txtInputConfPassword)
        backToLogin = findViewById(R.id.backToLogin)

        getListUsersSize()

        backToLogin.setOnClickListener {
           super.onBackPressedDispatcher.onBackPressed()
        }

        btnCriarConta.setOnClickListener {
            val nome = nomeTxt.text.toString()
            val apelido = apelidoTxt.text.toString()
            val email = emailTxt.text.toString()
            val password = passwordTxt.text.toString()

            if(verificaCampos()){
                val id = getListUsersSize()+1
                val user = User(id,nome, apelido, email, password)
                addUser(user) {
                    Toast.makeText(this,"Registo bem sucedido",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun verificaCampos(): Boolean {
        if(verificaNome() && verificaApelido() && verificaEmail() && verificaPasswords()){
            passwordTil.setStartIconDrawable(R.drawable.check_circle_24)
            passwordTil.setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
            confPasswordTil.setStartIconDrawable(R.drawable.check_circle_24)
            confPasswordTil.setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
            return true
        }else{
            return false
        }
    }

    private fun getListUsersSize(): Int {
        var size = 0
        val call = Retrofit().getService().list()
        call.enqueue(object : Callback<List<User>?> {
            override fun onResponse(call: Call<List<User>?>?,
                                    response: Response<List<User>?>?) {
                response?.body()?.let {
                    users =  it
                    size =  users.size
                    Log.e("onResponse", it.toString())
                }
            }
            override fun onFailure(call: Call<List<User>?>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
        return size
    }

    private fun addUser(user: User, onResult: (APIResult?) -> Unit){
        val call = Retrofit().getService().addUser(user?.id!!, user?.nome!!, user?.apelido!!, user?.email!!, user?.password!!)
        call.enqueue(
            object : Callback<APIResult> {
                override fun onFailure(call: Call<APIResult>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }
                override fun onResponse( call: Call<APIResult>, response: Response<APIResult>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

    private fun verificaNome():Boolean{
        if(nomeTxt.text!!.isEmpty()){
            nomeTil.isErrorEnabled = true
            nomeTil.error = "Nome é um campo de preenchimento obrigatório"
            return false
        }else{
            nomeTil.isErrorEnabled = false
            return true
        }
    }

    private fun verificaApelido():Boolean{
        if(apelidoTxt.text!!.isEmpty()){
            apelidoTil.isErrorEnabled = true
            apelidoTil.error = "Apelido é um campo de preenchimento obrigatório"
            return false
        }else{
            apelidoTil.isErrorEnabled = false
            return true
        }
    }

    private fun verificaPassword(): Boolean {
        if(passwordTxt.text!!.isEmpty()){
            passwordTil.isErrorEnabled = true
            passwordTil.error = "Password é um campo de preenchimento obrigatório"
            return false
        }else{
            if(passwordTxt.text.toString().length < 8){
                passwordTil.isErrorEnabled = true
                passwordTil.error = "Password deve conter pelo menos 8 caracteres"
                return false
            }else{
                passwordTil.isErrorEnabled = false
                return true
            }
        }
        return false
    }

    private fun verificaConfPassword():Boolean {
        if(confPasswordTxt.text!!.isEmpty()){
            confPasswordTil.isErrorEnabled = true
            confPasswordTil.error = "Password é um campo de preenchimento obrigatório"
            return false
        }else{
            if(confPasswordTxt.text.toString().length < 8){
                confPasswordTil.isErrorEnabled = true
                confPasswordTil.error = "Password deve conter pelo menos 8 caracteres"
                return false
            }else{
                confPasswordTil.isErrorEnabled = false
                return true
            }
        }
        return false
    }

    private fun verificaPasswords(): Boolean{
        if(verificaPassword() && verificaConfPassword()){
            if(passwordTxt.text.toString().equals(confPasswordTxt.text.toString())){
                confPasswordTil.isErrorEnabled = false
                return true
            }else{
                confPasswordTil.isErrorEnabled = true
                confPasswordTil.error = "Passwords não coincidem"
                return false
            }
        }else{
            return false
        }
    }

    private fun verificaEmail():Boolean{
        if(emailTxt.text!!.isNotEmpty()){
            var email = emailTxt.text.toString()
            if(email.contains('@') && email.contains('.')){
                for (user in users){
                    if(email.equals(user.email)){
                        emailTil.isErrorEnabled = true
                        emailTil.error = "Email  já está registado"
                        return false
                    }else{
                        emailTil.isErrorEnabled = false
                        emailTil.setStartIconDrawable(R.drawable.check_circle_24)
                        emailTil.setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                        return true
                    }
                }
            }else{
                emailTil.isErrorEnabled = true
                emailTil.error = "Email  introduzido não preenche os requisitos"
                return false
            }
        }else{
            emailTil.isErrorEnabled = true
            emailTil.error = "Email é um campo de preenchimento obrigatório "
            return false
        }
        return false
    }
}

