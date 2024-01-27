package pt.ipt.dam2023.cartrade.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.navigation.NavigationView
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.Car
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import pt.ipt.dam2023.cartrade.ui.adapter.CarListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var sizeCars: Int = 0

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listCars()

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout);

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.home)
        val headerView = navigationView.getHeaderView(0)

        headerView.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }


        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_view)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this, Favorites::class.java)
                startActivity(intent)
                return true
            }

            R.id.info -> {
                val intent = Intent(this, Favorites::class.java)
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
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
}
