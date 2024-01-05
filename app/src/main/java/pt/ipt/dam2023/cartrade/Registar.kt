package pt.ipt.dam2023.cartrade

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pt.ipt.dam2023.cartrade.databinding.RegistarBinding

class Registar: AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener{

    private lateinit var binding:RegistarBinding
    private lateinit var txtUsername:String
    private lateinit var txtEmail:String
    private lateinit var txtPassword:String
    private lateinit var txtConfPassword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegistarBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.txtUsername.onFocusChangeListener = this
        binding.txtEmail.onFocusChangeListener = this
        binding.txtPassword.onFocusChangeListener = this
        binding.txtConfPassword.onFocusChangeListener = this

    }

    //Função que verifica se o campo do Username foi preenchido
    private fun verificaUsername(): Boolean{
        var erro:String = ""
        var verifica: Boolean = false
        txtUsername = binding.txtUsername.text.toString()
        if(txtUsername.isEmpty()){
            erro = "O campo Username é de preenchimento obrigatório."
            verifica = false
        }else{
            verifica = true
        }
        if (verifica == false){
            binding.txtInputUsername.apply {
                isErrorEnabled = true
                error = erro
            }
        }
        return verifica
    }

    private fun verificaEmail(): Boolean{
        var erro:String = ""
        var verifica: Boolean = false
        txtEmail = binding.txtEmail.text.toString()
        if(txtEmail.isEmpty()){
            erro = "O campo Email é de preenchimento obrigatório."
            verifica = false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
            erro = "Email é inválido!"
            verifica = false
        }else{
            verifica = true
        }
        if (verifica == false){
            binding.txtInputEmail.apply {
                isErrorEnabled = true
                error = erro
            }
        }
        return verifica
    }

    private fun verificaPassword(): Boolean{
        var erro:String = ""
        var verifica: Boolean = false
        txtPassword = binding.txtPassword.text.toString()
        if(txtPassword.isEmpty()){
            erro = "O campo Password é de preenchimento obrigatório."
            verifica = false
        }else if(txtPassword.length < 8){
            erro = "Password deve conter pelo menos 8 caracteres"
            verifica = false
        }else{
            verifica = true
        }
        if (verifica == false){
            binding.txtInputPassword.apply {
                isErrorEnabled = true
                error = erro
            }
        }
        return verifica
    }

    private fun verificaConfPassword(): Boolean{
        var erro:String = ""
        var verifica: Boolean = false
        txtConfPassword = binding.txtConfPassword.text.toString()
        if(txtConfPassword.isEmpty()){
            erro = "O campo Confirmar Password é de preenchimento obrigatório."
            verifica = false
        }else if(txtConfPassword.length < 8){
            erro = "Password deve conter pelo menos 8 caracteres"
            verifica = false
        }else{
            verifica = true
        }
        if (verifica == false){
            binding.txtInputConfPassword.apply {
                isErrorEnabled = true
                error = erro
            }
        }
        return verifica
    }

    private fun verificaPasswords(): Boolean{
        var erro:String = ""
        var verifica: Boolean = false
        txtPassword = binding.txtPassword.text.toString()
        txtConfPassword = binding.txtConfPassword.text.toString()
        if(txtPassword != txtConfPassword){
            erro = "Passwords não coincidem"
            verifica = false
        }else{
            verifica = true
        }
        if (verifica == false){
            binding.txtInputConfPassword.apply {
                isErrorEnabled = true
                error = erro
            }
        }
        return verifica
    }

    override fun onClick(v: View?) {

    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(v != null){
            when(v.id){
                R.id.txtUsername -> {
                    if (hasFocus){
                        if(binding.txtInputUsername.isCounterEnabled){
                            binding.txtInputUsername.isErrorEnabled = false
                        }
                    }else{
                        verificaUsername()
                    }
                }

                R.id.txtEmail -> {
                    if (hasFocus){
                        if(binding.txtInputEmail.isCounterEnabled){
                            binding.txtInputEmail.isErrorEnabled = false
                        }
                    }else{
                        verificaEmail()
                    }
                }

                R.id.txtPassword -> {
                    if (hasFocus){
                        if(binding.txtInputPassword.isCounterEnabled){
                            binding.txtInputPassword.isErrorEnabled = false
                        }
                    }else{
                        verificaPassword()
                    }
                }

                R.id.txtConfPassword -> {
                    if (hasFocus){
                        if(binding.txtInputConfPassword.isCounterEnabled){
                            binding.txtInputConfPassword.isErrorEnabled = false
                        }
                    }else{
                        verificaConfPassword()
                    }
                }
            }
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }
}