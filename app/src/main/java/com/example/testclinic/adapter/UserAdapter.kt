package com.example.testclinic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testclinic.databinding.ListItemClinicBinding
import com.example.testclinic.data.model.User

class UserAdapter :
    RecyclerView.Adapter<UserAdapter.ClinicVH>() {

    private var listUser: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicVH {
        return ClinicVH(
            ListItemClinicBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return if (listUser.size > 0) {
            listUser.size
        } else {
            0
        }
    }

    fun setUsers(listRepository: MutableList<User>) {
        listUser = listRepository
    }

    override fun onBindViewHolder(holder: ClinicVH, position: Int) {
        try {
            holder.onBind(position)
//            holder.itemView.setOnClickListener {
//                val accountInformation = listRepositoryDet[position]
//                repositoryCallback.repositoryItemClick(accountInformation, position)
//            }
        } catch (_: Exception) {
        }
    }

    inner class ClinicVH(val binding: ListItemClinicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val repoModel = listUser[position]

        }
    }
}