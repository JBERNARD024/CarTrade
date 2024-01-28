package pt.ipt.dam2023.cartrade.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.APIResult
import pt.ipt.dam2023.cartrade.model.Car
import pt.ipt.dam2023.cartrade.model.CarPhotosResponse
import pt.ipt.dam2023.cartrade.model.CarResponse
import pt.ipt.dam2023.cartrade.model.User
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import pt.ipt.dam2023.cartrade.ui.adapter.CarDetailsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarDetails : AppCompatActivity() {

    private lateinit var photosRecyclerView: RecyclerView
    private lateinit var CarDetailsAdapter: CarDetailsAdapter
    private lateinit var txtAno: TextView
    private lateinit var txtKms: TextView
    private lateinit var txtPot: TextView
    private lateinit var txtId: TextView
    private lateinit var txtMarca: TextView
    private lateinit var txtModelo: TextView
    private lateinit var txtVersao: TextView
    private lateinit var txtCombustivel: TextView
    private lateinit var txtCilindrada: TextView
    private lateinit var txtCor: TextView
    private lateinit var txtTipoCaixa: TextView
    private lateinit var txtNumMudancas: TextView
    private lateinit var txtNumPortas: TextView
    private lateinit var txtCondicao: TextView
    private lateinit var btnEditar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnBackTo: Button
    private lateinit var car: Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)

        val carId = intent.getIntExtra("carId", -1)
        val email = intent.getStringExtra("email")
        txtAno = findViewById(R.id.ano)
        txtKms = findViewById(R.id.quilometros)
        txtPot = findViewById(R.id.potencia)
        txtId = findViewById(R.id.idCarro)
        txtMarca = findViewById(R.id.marca)
        txtModelo = findViewById(R.id.modelo)
        txtVersao = findViewById(R.id.versao)
        txtCombustivel = findViewById(R.id.combustivel)
        txtCilindrada = findViewById(R.id.cilindrada)
        txtCor = findViewById(R.id.cor)
        txtTipoCaixa = findViewById(R.id.tipoCaixa)
        txtNumMudancas = findViewById(R.id.numMudancas)
        txtNumPortas = findViewById(R.id.numPortas)
        txtCondicao = findViewById(R.id.condicao)
        btnEditar = findViewById(R.id.btnEditar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnBackTo = findViewById(R.id.backTo)
        getCar(carId)
        getListFotos(carId)

        photosRecyclerView = findViewById(R.id.photosRecyclerView)
        photosRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        CarDetailsAdapter = CarDetailsAdapter(emptyList())
        photosRecyclerView.adapter = CarDetailsAdapter

        btnEditar.setOnClickListener {
            val intent = Intent(this, EditCar::class.java)
            intent.putExtra("carId", carId)
            startActivity(intent)
        }

        btnEliminar.setOnClickListener {
            eliminarCarro(car.id, this)
            val intent = Intent(this, AdsCar::class.java)
            startActivity(intent)
        }

        btnBackTo.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun eliminarCarro(id: Int, context: Context) {
        val call = Retrofit().getService().deletedCar(id)
        call!!.enqueue(object : Callback<APIResult> {
            override fun onResponse(call: Call<APIResult>?,
                                    response: Response<APIResult>?) {
                response?.body()?.let {
                    Log.e("onResponse", it.toString())
                    Toast.makeText(context,"Carro eliminado com sucesso", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<APIResult>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure", it) }
            }
        })
    }

    private fun getListFotos(id: Int) {
        val call = Retrofit().getService().getCarPhotos(id)
        call!!.enqueue(object : Callback<CarPhotosResponse?> {
            override fun onResponse(
                call: Call<CarPhotosResponse?>,
                response: Response<CarPhotosResponse?>
            ) {
                response?.body()?.let {
                    val photos = it.photos!!.toList()
                    CarDetailsAdapter.updateData(photos)
                }
            }

            override fun onFailure(call: Call<CarPhotosResponse?>, t: Throwable) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }

    private fun getCar(id:Int) {
        val call = Retrofit().getService().list_Cars()
        call.enqueue(object : Callback<List<Car>?> {
            override fun onResponse(call: Call<List<Car>?>?,
                                    response: Response<List<Car>?>?) {
                response?.body()?.let {
                    val cars: List<Car> = it
                    car = cars[id-1]
                    updateCarDetails()
                }
            }
            override fun onFailure(call: Call<List<Car>?>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }

    private fun updateCarDetails() {
        txtAno.text = "${car.ano}"
        txtKms.text = "${car.kms} km"
        txtPot.text = "${car.potencia} cv"
        txtId.text = "${car.id}"
        txtMarca.text = "${car.marca}"
        txtModelo.text = "${car.modelo}"
        txtVersao.text = "${car.versao}"
        txtCombustivel.text = "${car.combustivel}"
        txtCilindrada.text = "${car.cilindrada} cm3"
        txtCor.text = "${car.cor}"
        txtTipoCaixa.text = "${car.tipoCaixa}"
        txtNumMudancas.text = "${car.numMudancas}"
        txtNumPortas.text = "${car.numMudancas}"
        txtCondicao.text = "${car.condicao}"
    }
}
