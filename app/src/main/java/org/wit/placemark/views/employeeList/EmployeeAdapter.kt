package org.wit.placemark.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.placemark.databinding.CardEmployeeBinding
import org.wit.placemark.models.EmployeeModel

interface PlacemarkListener {
    fun onPlacemarkClick(placemark: EmployeeModel, position : Int)
}

class PlacemarkAdapter constructor(private var placemarks: List<EmployeeModel>,
                                   private val listener: PlacemarkListener) :
        RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardEmployeeBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder(private val binding : CardEmployeeBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(placemark: EmployeeModel, listener: PlacemarkListener) {
            binding.employeeCardName.text = placemark.name
            binding.employeeCardBio.text = placemark.bio
            binding.employeeCardEmail.text = placemark.email
            binding.employeeCardPhoneNum.text = placemark.phone
            Picasso.get().load(placemark.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onPlacemarkClick(placemark,adapterPosition) }
        }
    }
}