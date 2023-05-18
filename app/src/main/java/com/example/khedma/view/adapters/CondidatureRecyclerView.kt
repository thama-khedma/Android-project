package com.example.khedma.view.adapters
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.khedma.viewmodel.repository.AcceptCondidature
import com.example.khedma.viewmodel.repository.RefuseCondidature
import com.example.khedma.R
import com.example.khedma.model.entities.Condidature
import com.example.khedma.model.entities.Offre
import com.example.khedma.view.activities.profileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter


class CondidatureRecyclerView: RecyclerView.Adapter<CondidatureRecyclerView.CondidatureViewHolder>() {
    var condidatureList:List<Condidature> = emptyList()
    var onListItemClick: OnListItemClick? = null

    fun setList(condidatureList: List<Condidature>) {
        this.condidatureList = condidatureList
        notifyDataSetChanged()
    }

    inner class CondidatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.qrcodeImage)
        var itemUser: TextView = itemView.findViewById(R.id.itemUser)
        var acceptButton: ImageButton = itemView.findViewById(R.id.acceptButton)
        var refuseButton: ImageButton = itemView.findViewById(R.id.refuseButton)
        var itemOffre: TextView = itemView.findViewById(R.id.itemOffre)
        var itemEntreprise: TextView = itemView.findViewById(R.id.itemEntreprise)

        fun bind(condidature: Condidature){

            itemImage.setImageResource(R.drawable.condidature)
            itemUser.text = condidature.user
            itemOffre.text = condidature.offre
            itemEntreprise.text = condidature.entreprise
            // Generate QR code
            val qrCodeContent = "${condidature.user} applied for the ${condidature.offre} position at ${condidature.entreprise}"

            val qrCodeSize = 300 // Adjust the size of the QR code image as needed
            val bitMatrix: BitMatrix = try {
                val writer = QRCodeWriter()
                writer.encode(qrCodeContent, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize)
            } catch (e: WriterException) {
                // Handle QR code generation error
                e.printStackTrace()
                return
            }

            // Convert BitMatrix to Bitmap
            val qrCodeBitmap = Bitmap.createBitmap(qrCodeSize, qrCodeSize, Bitmap.Config.RGB_565)
            for (x in 0 until qrCodeSize) {
                for (y in 0 until qrCodeSize) {
                    qrCodeBitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            // Set the QR code Bitmap to the ImageView
            itemImage.setImageBitmap(qrCodeBitmap)


            itemView.setOnClickListener{
                onListItemClick?.OnListItemClickCondidature(condidature)
            }
            acceptButton.setOnClickListener{
                CoroutineScope(Dispatchers.Main).launch {
                  AcceptCondidature(condidature.id)
                }
            }
            refuseButton.setOnClickListener{
                CoroutineScope(Dispatchers.Main).launch {
                RefuseCondidature(condidature.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CondidatureViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.condidature, parent, false)
        return CondidatureViewHolder(view)
    }

    override fun getItemCount(): Int {
        return condidatureList.size
    }

    override fun onBindViewHolder(holder: CondidatureViewHolder, position: Int) {
        var condidature: Condidature = condidatureList.get(position)
        holder.bind(condidature)
    }

}
