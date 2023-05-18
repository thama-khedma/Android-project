package com.example.khedma.view.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.khedma.R
import com.example.khedma.model.entities.Offre


class OffreRecyclerView: RecyclerView.Adapter<OffreRecyclerView.OffreViewHolder>() {
    var offreList:List<Offre> = emptyList()
    var onListItemClick: OnListItemClick? = null

    fun setList(offreList: List<Offre>) {
        this.offreList = offreList
        notifyDataSetChanged()
    }

    inner class OffreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.itemImage)
        var itemName: TextView = itemView.findViewById(R.id.itemName)
        var itemDescription: TextView = itemView.findViewById(R.id.itemDescription)

        fun bind(offre: Offre) {
            val sharedPreferences = itemView.context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

            itemImage.setImageResource(R.drawable.off)
            itemName.text = offre.name
            itemDescription.text = offre.description

            itemView.setOnClickListener {
                // Store offre.name in SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("offre", offre.name)
                editor.apply()

                onListItemClick?.OnListItemClickOffre(offre)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffreViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.offre, parent, false)
        return OffreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return offreList.size
    }

    override fun onBindViewHolder(holder: OffreViewHolder, position: Int) {
        var offre: Offre = offreList.get(position)
        holder.bind(offre)
    }

}