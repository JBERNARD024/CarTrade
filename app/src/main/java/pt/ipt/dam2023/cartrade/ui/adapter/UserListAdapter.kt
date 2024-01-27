package pt.ipt.dam2023.cartrade.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.User

class UserListAdapter(private val users: List<User>, private val context: Context) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder?.let {
            it.bindView(user)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return users.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(user: User) {
            val nome:TextView = itemView.findViewById(R.id.user_item_nome)
            val apelido:TextView = itemView.findViewById(R.id.user_item_apelido)
            val email:TextView = itemView.findViewById(R.id.user_item_email)
            val password:TextView = itemView.findViewById(R.id.user_item_password)
            nome.text = user.nome
            apelido.text = user.apelido
            email.text = user.email
            password.text = user.password
        }
    }
}
