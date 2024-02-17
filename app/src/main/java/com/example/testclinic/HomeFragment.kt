package com.example.testclinic

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testclinic.adapter.UserAdapter
import com.example.testclinic.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private val viewModel: CommonVM by viewModels()

    private lateinit var adapterUser: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)

        initView()
        subscribeObservers()
    }

    override fun initView() {
        viewModel.fetchAllUsers()

        binding.apply {

            rvUserList.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapterUser =
                    UserAdapter()
                adapter = adapterUser
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribeObservers() {
        viewModel.successUsersLD.observe(viewLifecycleOwner) { itlU ->
            if (itlU != null) {
                adapterUser.setUsers(itlU.toMutableList())
                adapterUser.notifyDataSetChanged()
            }
        }

        viewModel.fieldCommonLD.observe(viewLifecycleOwner) { itGR ->

        }
    }
}