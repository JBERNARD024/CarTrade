package pt.ipt.dam2023.cartrade.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.navigation.NavigationView
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.Car
import pt.ipt.dam2023.cartrade.model.User
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import pt.ipt.dam2023.cartrade.ui.adapter.CarListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var users: List<User>
    private lateinit var user: User
    private var isLoggedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        recyclerView = findViewById(R.id.car_list_recyclerview)
        navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.home)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        listCars()
        setSupportActionBar(toolbar)

        val headerView = navigationView.getHeaderView(0)
        val id = intent.getIntExtra("Id", -1)
        isLoggedIn = intent.getBooleanExtra("Login", false)
        if (!isLoggedIn) {
            hideLogoutOption()
        } else {
            showLogoutOption(id) {
                val menuHeader = navigationView.getHeaderView(0)
                val nomeApelido = menuHeader.findViewById<TextView>(R.id.login)
                val email = menuHeader.findViewById<TextView>(R.id.email)
                nomeApelido.text = "Olá, ${user.nome} ${user.apelido}"
                email.visibility = View.VISIBLE
                email.text = user.email
            }
        }

        headerView.setOnClickListener {
            if(!isLoggedIn){
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, Perfil::class.java)
                startActivity(intent)
            }
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

    private fun hideLogoutOption() {
        val menuNavigation = navigationView.menu
        val logoutItem = menuNavigation.findItem(R.id.logout)
        logoutItem.isVisible = false
        val menuHeader = navigationView.getHeaderView(0)
        val login = menuHeader.findViewById<TextView>(R.id.login)
        val email = menuHeader.findViewById<TextView>(R.id.email)
        login.text = "Login"
        email.visibility = View.GONE
        email.text = ""
    }

    private fun showLogoutOption(id: Int, callback: () -> Unit) {
        val menuNavigation = navigationView.menu
        val logoutItem = menuNavigation.findItem(R.id.logout)
        logoutItem.isVisible = true
        getListUsersSize(id) {
            callback.invoke()
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

            R.id.logout -> {
                isLoggedIn = false
                Toast.makeText(this, "Adeus, ${user.nome} ${user.apelido}", Toast.LENGTH_LONG).show()
                handleLogout()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun handleLogout() {
        isLoggedIn = false
        hideLogoutOption()
        user = User(null, null, null, null, null)
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun listCars() {
        val call = Retrofit().getService().list_Cars()
        call.enqueue(object : Callback<List<Car>?> {
            override fun onResponse(call: Call<List<Car>?>?,
                                    response: Response<List<Car>?>?) {
                response?.body()?.let {
                    val cars: List<Car> = it
                    Log.e("onResponse", it.toString())
                    configureList(cars)
                }
            }

            override fun onFailure(call: Call<List<Car>?>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }

    private fun getListUsersSize(id: Int, callback: () -> Unit) {
        val call = Retrofit().getService().list_Users()
        call.enqueue(object : Callback<List<User>?> {
            override fun onResponse(call: Call<List<User>?>?,
                                    response: Response<List<User>?>?) {
                response?.body()?.let {
                    users = it
                    user = users[id - 1]
                    Log.e("onResponse", it.toString())
                    callback.invoke()
                }
            }

            override fun onFailure(call: Call<List<User>?>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }

    private fun configureList(cars: List<Car>) {
        recyclerView.adapter = CarListAdapter(cars, this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
}
