package com.example.testclinic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.testclinic.data.model.User
import com.example.testclinic.databinding.FragmentCreateBinding
import com.example.testclinic.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateBinding
    private lateinit var navController: NavController
    private val viewModel: CommonVM by viewModels()
    private val sharedCommonVM: SharedSVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)

        initView()
        subscribeObservers()
    }

    override fun initView() {
        binding.tvToolBarTitle.text = "Details"
        if (sharedCommonVM.mUserId != 0) {
            binding.tvToolBarTitle.text = "Update"
        }

        binding.apply {

        }
    }

    override fun subscribeObservers() {
        viewModel.genderLD.observe(viewLifecycleOwner) { itlG ->

        }
    }

    fun setPreviewsData(lUser: User) {
        binding.apply {
            tietName.setText(lUser.name.toString())
            tietEmail.setText(lUser.name.toString())
//            spGender.setText(lUser.name.toString())
            tietName.setText(lUser.name.toString())
            smStatus.isActivated = lUser.status == "active"
        }
    }
}