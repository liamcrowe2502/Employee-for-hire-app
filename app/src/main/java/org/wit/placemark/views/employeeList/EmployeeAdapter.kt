package org.wit.placemark.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.placemark.databinding.CardEmployeeBinding
import org.wit.placemark.models.EmployeeModel

interface PlacemarkListener {
    fun onEmployeeClick(employee: EmployeeModel, position : Int)
}

class EmployeeAdapter constructor(private var employees: List<EmployeeModel>,
                                   private val listener: PlacemarkListener) :
        RecyclerView.Adapter<EmployeeAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardEmployeeBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val employee = employees[holder.adapterPosition]
        holder.bind(employee, listener)
    }

    override fun getItemCount(): Int = employees.size

    class MainHolder(private val binding : CardEmployeeBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(employee: EmployeeModel, listener: PlacemarkListener) {
            binding.employeeCardName.text = employee.name
            binding.employeeCardBio.text = employee.bio
            binding.employeeCardEmail.text = employee.email
            binding.employeeCardPhoneNum.text = employee.phone
            Picasso.get().load(employee.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onEmployeeClick(employee,adapterPosition) }
        }
    }
}
