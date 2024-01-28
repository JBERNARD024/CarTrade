package pt.ipt.dam2023.cartrade.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pt.ipt.dam2023.cartrade.R

class CarDetailsAdapter(private var photos: List<String>) : RecyclerView.Adapter<CarDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoUrl = photos[position]
        holder.bindView(photoUrl, position + 1) // Position + 1 for 1-based numbering
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun updateData(newPhotos: List<String>) {
        photos = newPhotos
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val carPhotoImageView: ImageView = itemView.findViewById(R.id.carPhotoImageView)
        private val photoNumberTextView: TextView = itemView.findViewById(R.id.photoNumberTextView)

        fun bindView(photoUrl: String, photoNumber: Int) {
            Picasso.get().load(photoUrl).into(carPhotoImageView)
            photoNumberTextView.text = photoNumber.toString()
        }
    }
}
