package pt.ipt.dam2023.cartrade.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.User
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var users: List<User>
private lateinit var user: User
private lateinit var txtNomeApelido: TextView
private lateinit var txtEmail: TextView
class Perfil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        txtNomeApelido.findViewById<TextView>(R.id.txtNomeApelido)
        txtEmail.findViewById<TextView>(R.id.email)

        val id = intent.getIntExtra("Id", -1)
        getUser(id)
        txtNomeApelido.text = "${user.nome} ${user.apelido}"
        txtEmail.text = "${user.email}"
    }

    private fun getUser(id: Int) {
        val call = Retrofit().getService().list_Users()
        call.enqueue(object : Callback<List<User>?> {
            override fun onResponse(call: Call<List<User>?>?,
                                    response: Response<List<User>?>?) {
                response?.body()?.let {
                    users = it
                    user = users[id - 1]
                    Log.e("onResponse", it.toString())
                }
            }

            override fun onFailure(call: Call<List<User>?>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }
}