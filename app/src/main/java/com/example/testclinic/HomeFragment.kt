package com.example.testclinic

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testclinic.adapter.ICallBack
import com.example.testclinic.adapter.UserAdapter
import com.example.testclinic.data.model.User
import com.example.testclinic.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), ICallBack {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private val viewModel: CommonVM by viewModels()
    private val sharedCommonVM: SharedSVM by activityViewModels()
    private var adapterUser: UserAdapter? = null

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

            btnActive.setOnClickListener {
                if (adapterUser != null) {
                    adapterUser?.filter?.filter("active")
                }
            }

            btnDeactivate.setOnClickListener {
                if (adapterUser != null) {
                    adapterUser?.filter?.filter("inactive")
                }
            }

            fabtnAddUser.setOnClickListener {
                sharedCommonVM.mUser = User().apply {
                    id = 0
                }
                navController.navigate(R.id.action_home_to_create)
            }

            rvUserList.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = adapterUser
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribeObservers() {
        viewModel.successUsersLD.observe(viewLifecycleOwner) { itlU ->
            if (itlU != null) {
                adapterUser =
                    UserAdapter(itlU.toMutableList(), this@HomeFragment)
                binding.rvUserList.apply {
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    adapter = adapterUser
                }
            }
        }

        viewModel.fieldCommonLD.observe(viewLifecycleOwner) { itGR ->
            Toast.makeText(
                requireContext(),
                "Error: " + itGR.message.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    //Click for Details
    override fun onClickItem(mUser: User) {
        sharedCommonVM.mUser = mUser
        navController.navigate(R.id.action_home_to_create)
    }

    //Click for Edit
    override fun onClickItem(mUserId: Int) {
        sharedCommonVM.mUserId = mUserId
        navController.navigate(R.id.action_home_to_create)
    }
}