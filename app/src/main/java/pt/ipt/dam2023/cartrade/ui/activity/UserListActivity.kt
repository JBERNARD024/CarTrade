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
import pt.ipt.dam2023.cartrade.model.User
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import pt.ipt.dam2023.cartrade.ui.adapter.UserListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private var sizeUsers: Int = 0
class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        listUsers()
        
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            addDummyUser()
        }
    }
    private fun listUsers() {
        val call = Retrofit().getService().list()
        call.enqueue(object : Callback<List<User>?> {
            override fun onResponse(call: Call<List<User>?>?,
                                    response: Response<List<User>?>?) {
                response?.body()?.let {
                    val users: List<User> = it
                    sizeUsers = users.size
                    Log.e("onResponse", it.toString())
                    configureList(users)
                }
            }
            override fun onFailure(call: Call<List<User>?>?, t: Throwable?) {
                t?.message?.let { Log.e("onFailure error", it) }
            }
        })
    }

    private fun configureList(users: List<User>) {
        val recyclerView: RecyclerView = findViewById(R.id.user_list_recyclerview)
        recyclerView.adapter = UserListAdapter(users, this)
        val layoutManager = StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }
    private fun addDummyUser() {
        val id = sizeUsers+1
        val user = User(id,"teste", "etset, ","teste@gmail.com", "123qwe#")
        addUser(user) {
            Toast.makeText(this,"Add " + it?.description,Toast.LENGTH_SHORT).show()
            listUsers()
        }
    }
    private fun addUser(user: User, onResult: (APIResult?) -> Unit){
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
    }
}


