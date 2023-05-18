package com.example.khedma.view.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.khedma.R
import com.example.khedma.model.entities.Entreprise


class EntrepriseRecyclerView: RecyclerView.Adapter<EntrepriseRecyclerView.RestaurantViewHolder>() {
    var restaurantsList:List<Entreprise> = emptyList()
    var onListItemClick: OnListItemClick? = null

    fun setList(restaurantsList: List<Entreprise>) {
        this.restaurantsList = restaurantsList
        notifyDataSetChanged()
    }

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        var itemName: TextView = itemView.findViewById(R.id.itemName)
        var itemDescription: TextView = itemView.findViewById(R.id.itemDescription)

        fun bind(entreprise: Entreprise){



            //itemImage.setImageResource(R.drawable.restaurant_item)
            itemName.text = entreprise.name
            itemDescription.text = entreprise.description

            itemView.setOnClickListener{
                onListItemClick?.onItemClick(entreprise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.entreprise, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantsList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        var entreprise: Entreprise = restaurantsList.get(position)
        holder.bind(entreprise)
    }

}