package pt.ipt.dam2023.cartrade.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.Car
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import pt.ipt.dam2023.cartrade.ui.adapter.CarListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var recyclerView: RecyclerView
private lateinit var btnBackTo: Button

class AdsCar: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads)
        recyclerView = findViewById(R.id.car_list_recyclerview)
        btnBackTo = findViewById(R.id.backTo)
        val email = intent.getStringExtra("email")
        listCars(email)

        btnBackTo.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun listCars(email: String?) {
        val call = Retrofit().getService().list_Cars()
        call.enqueue(object : Callback<List<Car>?> {
            override fun onResponse(call: Call<List<Car>?>?,
                                    response: Response<List<Car>?>?) {
                response?.body()?.let {
                    val cars: List<Car> = it
                    var myCars: MutableList<Car> = mutableListOf()
                    for (car in cars){
                        if(car.user == email){
                            myCars.add(car)
                        }
                    }
                    Log.e("onResponse", it.toString())
                    configureList(myCars, email)
                }
            }

            override fun onFailure(call: Call<List<Car>?>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }

    private fun configureList(cars: List<Car>, email: String?) {
        recyclerView.adapter = CarListAdapter(cars, email,this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
}