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
import pt.ipt.dam2023.cartrade.model.Car
import pt.ipt.dam2023.cartrade.model.User
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCar: AppCompatActivity() {

    private lateinit var marcaTxt: TextInputEditText
    private lateinit var modeloTxt: TextInputEditText
    private lateinit var versaoTxt: TextInputEditText
    private lateinit var combustivelTxt: TextInputEditText
    private lateinit var anoTxt: TextInputEditText
    private lateinit var kmsTxt: TextInputEditText
    private lateinit var potenciaTxt: TextInputEditText
    private lateinit var cilindradaTxt: TextInputEditText
    private lateinit var corTxt: TextInputEditText
    private lateinit var tipoCaixaTxt: TextInputEditText
    private lateinit var numMudancasTxt: TextInputEditText
    private lateinit var numPortasTxt: TextInputEditText
    private lateinit var lotacaoTxt: TextInputEditText
    private lateinit var condicaoTxt: TextInputEditText
    private lateinit var precoTxt: TextInputEditText

    private lateinit var marcaTil: TextInputLayout
    private lateinit var modeloTil: TextInputLayout
    private lateinit var versaoTil: TextInputLayout
    private lateinit var combustivelTil: TextInputLayout
    private lateinit var anoTil: TextInputLayout
    private lateinit var kmsTil: TextInputLayout
    private lateinit var potenciaTil: TextInputLayout
    private lateinit var cilindradaTil: TextInputLayout
    private lateinit var corTil: TextInputLayout
    private lateinit var tipoCaixaTil: TextInputLayout
    private lateinit var numMudancasTil: TextInputLayout
    private lateinit var numPortasTil: TextInputLayout
    private lateinit var lotacaoTil: TextInputLayout
    private lateinit var condicaoTil: TextInputLayout
    private lateinit var precoTil: TextInputLayout
    private lateinit var btnAdicionarCarro: Button
    private lateinit var cars: List<Car>
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcar)

        email = intent.getStringExtra("email").toString()

        // Inicializar componentes
        marcaTxt = findViewById(R.id.txtMarca)
        modeloTxt = findViewById(R.id.txtModelo)
        versaoTxt = findViewById(R.id.txtVersao)
        combustivelTxt = findViewById(R.id.txtCombustivel)
        anoTxt = findViewById(R.id.txtAno)
        kmsTxt = findViewById(R.id.txtKms)
        potenciaTxt = findViewById(R.id.txtPotencia)
        cilindradaTxt = findViewById(R.id.txtCilindrada)
        corTxt = findViewById(R.id.txtCor)
        tipoCaixaTxt = findViewById(R.id.txtTipoCaixa)
        numMudancasTxt = findViewById(R.id.txtNumMudancas)
        numPortasTxt = findViewById(R.id.txtNumPortas)
        lotacaoTxt = findViewById(R.id.txtLotacao)
        condicaoTxt = findViewById(R.id.txtCondicao)
        precoTxt = findViewById(R.id.txtPreco)

        marcaTil = findViewById(R.id.txtInputMarca)
        modeloTil = findViewById(R.id.txtInputModelo)
        versaoTil = findViewById(R.id.txtInputVersao)
        combustivelTil = findViewById(R.id.txtInputCombustivel)
        anoTil = findViewById(R.id.txtInputAno)
        kmsTil = findViewById(R.id.txtInputKms)
        potenciaTil = findViewById(R.id.txtInputPotencia)
        cilindradaTil = findViewById(R.id.txtInputCilindrada)
        corTil = findViewById(R.id.txtInputCor)
        tipoCaixaTil = findViewById(R.id.txtInputTipoCaixa)
        numMudancasTil = findViewById(R.id.txtInputNumMudancas)
        numPortasTil = findViewById(R.id.txtInputNumPortas)
        lotacaoTil = findViewById(R.id.txtInputLotacao)
        condicaoTil = findViewById(R.id.txtInputCondicao)
        precoTil = findViewById(R.id.txtInputPreco)
        btnAdicionarCarro = findViewById(R.id.btnRegistarCar)
        listCars()
        btnAdicionarCarro.setOnClickListener {
            val marca = marcaTxt.text.toString()
            val modelo = modeloTxt.text.toString()
            val versao = versaoTxt.text.toString()
            val combustivel = combustivelTxt.text.toString()
            val ano = anoTxt.text.toString().toIntOrNull()
            val kms = kmsTxt.text.toString().toIntOrNull()
            val potencia = potenciaTxt.text.toString().toIntOrNull()
            val cilindrada = cilindradaTxt.text.toString().toIntOrNull()
            val cor = corTxt.text.toString()
            val tipoCaixa = tipoCaixaTxt.text.toString()
            val numMudancas = numMudancasTxt.text.toString().toIntOrNull()
            val numPortas = numPortasTxt.text.toString().toIntOrNull()
            val lotacao = lotacaoTxt.text.toString().toIntOrNull()
            val condicao = condicaoTxt.text.toString()
            val preco = precoTxt.text.toString().toIntOrNull()
            if(verificaCampos()){
                val id = cars.size+1
                val car = Car(id,marca, modelo, versao, combustivel, ano, kms, potencia, cilindrada, cor, tipoCaixa, numMudancas, numPortas, lotacao, condicao, preco, email)
                addCar(car) {
                    Toast.makeText(this,"Registo bem sucedido", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Campos não preenchidos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun listCars() {
        val call = Retrofit().getService().list_Cars()
        call.enqueue(object : Callback<List<Car>?> {
            override fun onResponse(call: Call<List<Car>?>?,
                                    response: Response<List<Car>?>?) {
                response?.body()?.let {
                    cars = it
                    Log.e("onResponse", it.toString())
                }
            }

            override fun onFailure(call: Call<List<Car>?>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }
    private fun validaMarca(): Boolean {
        if (marcaTxt.text!!.isEmpty()) {
            marcaTil.isErrorEnabled = true
            marcaTil.error = "Marca é um campo de preenchimento obrigatório"
            Log.d("Marca", "Fui eu")
            return false
        }
        return true
    }

    private fun validaModelo(): Boolean {
        if (modeloTxt.text!!.isEmpty()) {
            modeloTil.isErrorEnabled = true
            modeloTil.error = "Modelo é um campo de preenchimento obrigatório"
            Log.d("Modelo", "Fui eu")
            return false
        }
        return true
    }

    private fun validaVersao(): Boolean {
        if (versaoTxt.text!!.isEmpty()) {
            versaoTil.isErrorEnabled = true
            versaoTil.error = "Versão é um campo de preenchimento obrigatório"
            Log.d("Versao", "Fui eu")
            return false
        }
        return true
    }

    private fun validaCombustivel(): Boolean {
        if (combustivelTxt.text!!.isEmpty()) {
            combustivelTil.isErrorEnabled = true
            combustivelTil.error = "Combustível é um campo de preenchimento obrigatório"
            Log.d("Combustivel", "Fui eu")
            return false
        }
        return true
    }

    private fun validaAno(): Boolean {
        if (anoTxt.text!!.isEmpty()) {
            anoTil.isErrorEnabled = true
            anoTil.error = "Ano é um campo de preenchimento obrigatório"
            Log.d("Ano", "Fui eu")
            return false
        }
        return true
    }

    private fun validaKms(): Boolean {
        if (kmsTxt.text!!.isEmpty()) {
            kmsTil.isErrorEnabled = true
            kmsTil.error = "Quilómetros é um campo de preenchimento obrigatório"
            Log.d("Kms", "Fui eu")
            return false
        }
        return true
    }

    private fun validaPotencia(): Boolean {
        if (potenciaTxt.text!!.isEmpty()) {
            potenciaTil.isErrorEnabled = true
            potenciaTil.error = "Potência é um campo de preenchimento obrigatório"
            Log.d("Potencia", "Fui eu")
            return false
        }
        return true
    }

    private fun validaCilindrada(): Boolean {
        if (cilindradaTxt.text!!.isEmpty()) {
            cilindradaTil.isErrorEnabled = true
            cilindradaTil.error = "Cilindrada é um campo de preenchimento obrigatório"
            Log.d("Cilindrada", "Fui eu")
            return false
        }
        return true
    }

    private fun validaCor(): Boolean {
        if (corTxt.text!!.isEmpty()) {
            corTil.isErrorEnabled = true
            corTil.error = "Cor é um campo de preenchimento obrigatório"
            Log.d("Cor", "Fui eu")
            return false
        }
        return true
    }

    private fun validaTipoCaixa(): Boolean {
        if (tipoCaixaTxt.text!!.isEmpty()) {
            tipoCaixaTil.isErrorEnabled = true
            tipoCaixaTil.error = "Tipo de caixa é um campo de preenchimento obrigatório"
            Log.d("TipoCaixa", "Fui eu")
            return false
        }
        return true
    }

    private fun validaNumMudancas(): Boolean {
        if (numMudancasTxt.text!!.isEmpty()) {
            numMudancasTil.isErrorEnabled = true
            numMudancasTil.error = "Número de mudanças é um campo de preenchimento obrigatório"
            Log.d("NumMudancas", "Fui eu")
            return false
        }
        return true
    }

    private fun validaNumPortas(): Boolean {
        if (numPortasTxt.text!!.isEmpty()) {
            numPortasTil.isErrorEnabled = true
            numPortasTil.error = "Número de portas é um campo de preenchimento obrigatório"
            Log.d("NumPortas", "Fui eu")
            return false
        }
        return true
    }

    private fun validaLotacao(): Boolean {
        if (lotacaoTxt.text!!.isEmpty()) {
            lotacaoTil.isErrorEnabled = true
            lotacaoTil.error = "Lotação é um campo de preenchimento obrigatório"
            Log.d("Lotacao", "Fui eu")
            return false
        }
        return true
    }

    private fun validaCondicao(): Boolean {
        if (condicaoTxt.text!!.isEmpty()) {
            condicaoTil.isErrorEnabled = true
            condicaoTil.error = "Condição é um campo de preenchimento obrigatório"
            Log.d("Lotacao", "Fui eu")
            return false
        }
        return true
    }

    private fun validaPreco(): Boolean {
        if (precoTxt.text!!.isEmpty()) {
            precoTil.isErrorEnabled = true
            precoTil.error = "Preço é um campo de preenchimento obrigatório"
            Log.d("Preco", "Fui eu")
            return false
        }
        return true
    }

    private fun addCar(car: Car, onResult: (APIResult?) -> Unit){
        val call = Retrofit().getService().addCar(car.id, car.marca, car.modelo, car.versao, car.combustivel,
            car.ano, car.kms, car.potencia, car.cilindrada, car.cor, car.tipoCaixa, car.numMudancas,
            car.numPortas, car.lotacao, car.condicao, car.preco, email)
        call.enqueue(
            object : Callback<APIResult> {
                override fun onFailure(call: Call<APIResult>, t: Throwable) {
                    t.printStackTrace()
                    onResult(null)
                }
                override fun onResponse(call: Call<APIResult>, response: Response<APIResult>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

    private fun verificaCampos(): Boolean {
        if(validaMarca() && validaModelo() && validaVersao() && validaCombustivel() &&
            validaAno() && validaKms() && validaPotencia() && validaCilindrada() && validaCor() &&
            validaTipoCaixa() && validaNumMudancas() && validaNumPortas() && validaNumPortas() && validaLotacao() &&
            validaCondicao() && validaPreco()){
            return true
        }else{
            return false
        }
    }
}