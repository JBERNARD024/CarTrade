package pt.ipt.dam2023.cartrade

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtenha as referências dos elementos no contêiner
        val itemImage: ImageView = findViewById(R.id.itemImage)
        val itemTitle: TextView = findViewById(R.id.itemTitle)
        val itemPrice: TextView = findViewById(R.id.itemPrice)

        // Substitua os valores de exemplo pelos seus dados reais
        itemImage.setImageResource(R.drawable.carro)
        itemTitle.text = "Audi R8 Coupé 5.2 FSI V10 quattro R tronic"
        itemPrice.text = "77 500 €"

        setupToolbar()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar o menu na barra de ação
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Manipular os itens do menu aqui
        when (item.itemId) {
            R.id.action_login -> {
                // Exemplo: Iniciar uma nova atividade quando o item Settings é clicado
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_registar -> {
                // Exemplo: Iniciar uma nova atividade quando o item Settings é clicado
                val intent = Intent(this, SignIn::class.java)
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar() {
        // Configurar a barra de ação (ou Toolbar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

}
