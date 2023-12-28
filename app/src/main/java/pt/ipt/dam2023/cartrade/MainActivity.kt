package pt.ipt.dam2023.cartrade

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar a barra de ação (ou Toolbar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
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
}
