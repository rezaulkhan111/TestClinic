package com.example.testclinic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.testclinic.databinding.FragmentCreateBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateBinding
    private lateinit var navController: NavController
    private val viewModel: CommonVM by viewModels()
    private val sharedCommonVM: SharedSVM by activityViewModels()

    private var itemGender = ArrayList<String>()
    private var globalGenderSelected = 0

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
        viewModel.getGender()

        binding.ivBack.setOnClickListener {
            navController.popBackStack()
        }

        when (sharedCommonVM.mUser?.viewStatus) {
            "Edit" -> {
                if (sharedCommonVM.mUser!!.id > 0) {
                    binding.apply {
                        tvToolBarTitle.text = "Update"
                        sharedCommonVM.mUser?.apply {
                            tietName.setText(name.toString())
                            tietEmail.setText(email.toString())
                            smStatus.isChecked = status == "active"
                        }

                        btnCreateOrUpdate.text = "Update"
                        updateUser()
                    }
                }
            }

            "Create" -> {
                binding.apply {
                    tvToolBarTitle.text = "Create"
                    btnCreateOrUpdate.text = "Create"
                    sharedCommonVM.mUser?.status = "inactive"
                    createUser()
                }
            }

            else -> {
                binding.apply {
                    tvToolBarTitle.text = "Details"
                    sharedCommonVM.mUser?.apply {
                        tietName.apply {
                            setText(name.toString())
                            isEnabled = false
                        }
                        tietEmail.apply {
                            setText(name.toString())
                            isEnabled = false
                        }
                        spGender.apply {
                            setSelection(globalGenderSelected)
                            isEnabled = false
                        }
                        smStatus.apply {
                            isChecked = status == "active"
                            isEnabled = false
                        }

                        btnCreateOrUpdate.apply {
                            text = ""
                            isEnabled = false
                        }
                    }
                }
            }
        }
    }

    private fun updateUser() {
        binding.spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (!itemGender.isNullOrEmpty()) {
                    if (position != 0) {
                        sharedCommonVM.mUser?.gender = itemGender[position]
                    }
                }
            }
        }

        binding.smStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedCommonVM.mUser?.status = if (isChecked) {
                "active"
            } else {
                "inactive"
            }
        }

        binding.btnCreateOrUpdate.setOnClickListener {
            sharedCommonVM.mUser?.apply {
                name = binding.tietName.text.toString()
                email = binding.tietEmail.text.toString()
            }
            viewModel.updateUser(sharedCommonVM.mUser!!)
        }
    }

    private fun createUser() {
        binding.spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (!itemGender.isNullOrEmpty()) {
                    if (position != 0) {
                        sharedCommonVM.mUser?.gender = itemGender[position]
                    }
                }
            }
        }

        binding.smStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedCommonVM.mUser?.status = if (isChecked) {
                "active"
            } else {
                "inactive"
            }
        }

        binding.btnCreateOrUpdate.setOnClickListener {
            sharedCommonVM.mUser?.apply {
                name = binding.tietName.text.toString()
                email = binding.tietEmail.text.toString()
            }
            viewModel.createUser(sharedCommonVM.mUser!!)
        }
    }

    override fun subscribeObservers() {
        viewModel.genderLD.observe(viewLifecycleOwner) { itlG ->
            itlG.forEach { itG ->
                itemGender.add(itG.gender.toString())
            }
            binding.spGender.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                itemGender
            )
        }

        viewModel.successUserLD.observe(viewLifecycleOwner) { itUs ->
            if (itUs != null) {
                Toast.makeText(requireContext(), "" + Gson().toJson(itUs), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.errorBodyCommonLD.observe(viewLifecycleOwner) {
            Log.e("CF", "subscribeObservers: " + Gson().toJson(it.toString()))
        }

        viewModel.fieldCommonLD.observe(viewLifecycleOwner) { itGr ->
            Toast.makeText(requireContext(), "" + Gson().toJson(itGr), Toast.LENGTH_SHORT)
                .show()
        }
    }
}