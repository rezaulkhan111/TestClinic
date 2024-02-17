package com.example.testclinic.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Filter
import com.example.testclinic.data.model.User
import java.util.Locale

class UserFilter(
    private val listUser: ArrayList<User>,
    private val adapter: UserAdapter
) : Filter() {
    override fun performFiltering(constraint: CharSequence): FilterResults {
        var mConstraint: CharSequence? = constraint
        val results = FilterResults()
        if (!mConstraint.isNullOrEmpty()) {
            Log.e("UF", "performFiltering: " + mConstraint)
            mConstraint = mConstraint.toString().uppercase(Locale.getDefault())
            val filteredPlayers = ArrayList<User>()
            for (i in listUser.indices) {
                if (listUser[i].status?.uppercase(Locale.getDefault())!!
                        .contains(mConstraint)
                ) {
                    filteredPlayers.add(listUser[i])
                }
            }
            results.count = filteredPlayers.size
            results.values = filteredPlayers
        } else {
            results.count = listUser.size
            results.values = listUser
        }
        return results
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        adapter.mainListUser = results.values as ArrayList<User>
        adapter.notifyDataSetChanged()
    }
}