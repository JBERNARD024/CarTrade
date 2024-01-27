package pt.ipt.dam2023.cartrade.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.Car

class CarListAdapter(private val cars: List<Car>, private val context: Context) :
    RecyclerView.Adapter<CarListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = cars[position]
        holder?.let {
            it.bindView(car)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return cars.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(car: Car) {
            val fotoPrincipal:TextView = itemView.findViewById(R.id.fotoPrincipal)
            val marcaModeloVersao:TextView = itemView.findViewById(R.id.marcaModeloVersao)
            val infos:TextView = itemView.findViewById(R.id.infos)
            val preco:TextView = itemView.findViewById(R.id.preco)

            marcaModeloVersao.text = car.marca + " " + car.modelo + " " + car.versao
            infos.text = car.ano.toString() + " - " + car.kms.toString() + "kms - " + car.combustivel + " - " + car.potencia + "cv"
            preco.text = car.preco.toString() + "€"
        }
    }
}
