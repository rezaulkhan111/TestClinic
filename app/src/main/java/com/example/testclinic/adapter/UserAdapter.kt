package com.example.testclinic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.testclinic.databinding.ListItemClinicBinding
import com.example.testclinic.data.model.User

class UserAdapter :
    RecyclerView.Adapter<UserAdapter.ClinicVH>, Filterable {

    var mainListUser: MutableList<User> = mutableListOf()
    private var userFilter: UserFilter? = null
    private var filterUserList: ArrayList<User> = arrayListOf()
    private var localCallBack: ICallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicVH {
        return ClinicVH(
            ListItemClinicBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    constructor(
        listUsers: MutableList<User>,
        ibsCallBack: ICallBack
    ) {
        mainListUser = listUsers.toCollection(ArrayList())
        filterUserList = listUsers.toCollection(ArrayList())
        localCallBack = ibsCallBack
    }

    override fun getFilter(): Filter {
        if (userFilter == null) {
            userFilter = UserFilter(filterUserList, this)
        }
        return userFilter!!
    }

    override fun getItemCount(): Int {
        return if (mainListUser.size > 0) {
            mainListUser.size
        } else {
            0
        }
    }

    fun setUsers(listRepository: MutableList<User>) {
        mainListUser = listRepository
    }

    override fun onBindViewHolder(holder: ClinicVH, position: Int) {
        holder.onBind(position)

        val mUser = mainListUser[position]
        //Click for Details
        holder.itemView.setOnClickListener {
            localCallBack?.onClickDetailsItem(mUser)
        }

        //Click for Edit
        holder.binding.ivEditInfo.setOnClickListener {
            localCallBack?.onClickEditItem(mUser)
        }
    }

    inner class ClinicVH(val binding: ListItemClinicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val repoModel = mainListUser[position]
            if (repoModel.status == "active") {
                binding.apply {
                    tvName.apply {
                        text = repoModel.name.toString()
                        isEnabled = true
                    }
                    tvGender.apply {
                        text = repoModel.gender.toString()
                        isEnabled = true
                    }
                    tvEmailAddress.apply {
                        text = repoModel.email.toString()
                        isEnabled = true
                    }
                }
            } else {
                binding.apply {
                    tvName.apply {
                        text = repoModel.name.toString()
                        isEnabled = false
                    }
                    tvGender.apply {
                        text = repoModel.gender.toString()
                        isEnabled = false
                    }
                    tvEmailAddress.apply {
                        text = repoModel.email.toString()
                        isEnabled = false
                    }
                }
            }
        }
    }
}

interface ICallBack {
    //Click for Details
    fun onClickDetailsItem(mUser: User)

    //Click for Edit
    fun onClickEditItem(mUser: User)

}