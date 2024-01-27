package pt.ipt.dam2023.cartrade.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.APIResult
import pt.ipt.dam2023.cartrade.model.Car
import pt.ipt.dam2023.cartrade.model.User
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import pt.ipt.dam2023.cartrade.ui.adapter.CarListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var sizeCars: Int = 0
class CarListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_list)

        listCars()
    }
    private fun listCars() {
        val call = Retrofit().getService().list_Cars()
        call.enqueue(object : Callback<List<Car>?> {
            override fun onResponse(call: Call<List<Car>?>?,
                                    response: Response<List<Car>?>?) {
                response?.body()?.let {
                    val cars: List<Car> = it
                    sizeCars = cars.size
                    Log.e("onResponse", it.toString())
                    configureList(cars)
                }
            }
            override fun onFailure(call: Call<List<Car>?>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }

    private fun configureList(cars: List<Car>) {
        val recyclerView: RecyclerView = findViewById(R.id.car_list_recyclerview)
        recyclerView.adapter = CarListAdapter(cars, this)
        val layoutManager = StaggeredGridLayoutManager( 1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
    /*private fun addDummyUser() {
        val id = sizeCars+1
        val user = User(id,"teste", "etset, ","teste@gmail.com", "123qwe#")
        addUser(user) {
            Toast.makeText(this,"Add " + it?.description,Toast.LENGTH_SHORT).show()
            listCars()
        }
    }*/
    /*private fun addUser(car: Car, onResult: (APIResult?) -> Unit){
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
    }*/
}


