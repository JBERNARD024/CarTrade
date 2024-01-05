package pt.ipt.dam2023.cartrade

import android.content.Intent
import android.net.http.HeaderBlock
import android.os.Bundle
import android.text.Layout
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout);

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val headerView = navigationView.getHeaderView(0)

        headerView.setOnClickListener { val intent = Intent(this, Login::class.java)
            startActivity(intent) }


        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if(savedInstanceState == null){
            navigationView.setCheckedItem(R.id.nav_view)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                val intent = Intent(this, Favorites::class.java)
                startActivity(intent)
                return true
            }
            R.id.info -> {val intent = Intent(this, Favorites::class.java)
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}
