package pt.ipt.dam2023.cartrade.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pt.ipt.dam2023.cartrade.R
import pt.ipt.dam2023.cartrade.model.Car
import pt.ipt.dam2023.cartrade.model.CarPhotosResponse
import pt.ipt.dam2023.cartrade.retrofit.Retrofit
import pt.ipt.dam2023.cartrade.ui.activity.CarDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarListAdapter(private val cars: List<Car>, private val email:String?, private val context: Context) :
    RecyclerView.Adapter<CarListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = cars[position]
        holder.bindView(car)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CarDetails::class.java)
            intent.putExtra("carId", car.id)
            intent.putExtra("email", email)
            context.startActivity(intent)
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
        private val fotoPrincipal: ImageView = itemView.findViewById(R.id.fotoPrincipal)
        private val marcaModeloVersao: TextView = itemView.findViewById(R.id.marcaModeloVersao)
        private val infos: TextView = itemView.findViewById(R.id.infos)
        private val preco: TextView = itemView.findViewById(R.id.preco)

        fun bindView(car: Car) {
            marcaModeloVersao.text = "${car.marca} ${car.modelo} ${car.versao}"
            infos.text = "${car.ano} - ${car.kms}kms - ${car.combustivel} - ${car.potencia}cv"
            preco.text = "${car.preco}€"

            //Carrega as fotos do carro, dado o seu índice
            getListFotos(car.id)
        }

        private fun getListFotos(id: Int) {
            val call = Retrofit().getService().getCarPhotos(id)
            call!!.enqueue(object : Callback<CarPhotosResponse?> {
                override fun onResponse(
                    call: Call<CarPhotosResponse?>,
                    response: Response<CarPhotosResponse?>
                ) {
                    response?.body()?.let {
                        val photos = it.photos!!.toList()
                        //Atualiza a página principal
                        itemView.post {
                            updateImageViews(photos)
                        }
                    }
                }
                override fun onFailure(call: Call<CarPhotosResponse?>, t: Throwable) {
                    t?.message?.let { Log.e("onFailure error", it) }
                }
            })
        }

        private fun updateImageViews(photos: List<String>) {
            if (photos.isNotEmpty()) {
                //Apresenta a primeira imagem da lista de fotos
                val imageURL = photos[0]
                Picasso.get().load(imageURL).into(fotoPrincipal)
            } else {
                //Caso não existam fotos, apresenta uma imagem por defeito
                fotoPrincipal.setImageResource(R.drawable.car)
            }
        }
    }
}
