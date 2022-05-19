package com.example.skuska

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.skuska.databinding.FragmentAddBinding
import com.example.skuska.viewModels.AddMemoryViewModel
import cz.mendelu.tododolist.architecture.BaseFragment
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddMemoryFragment : BaseFragment<FragmentAddBinding, AddMemoryViewModel>(AddMemoryViewModel::class) {
    override val bindingInflater: (LayoutInflater) -> FragmentAddBinding
        get() = FragmentAddBinding::inflate

    override fun initViews() {
        var nameField: TextInputView = binding.memoryName
        var textField: TextInputView = binding.memoryText
        var placeField: TextInputView = binding.memoryPlace
        var spinner: Spinner = binding.memorySpinner
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.type_array)
        )

        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0 != null) {
                    if (p0.getItemAtPosition(p2).toString() == "Positive"){
                        viewModel.memory.type = true
                    }else{
                        viewModel.memory.type = false
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }


        binding.saveButton.setOnClickListener {
            if(viewModel.memory.name.isNotEmpty() && viewModel.memory.text.isNotEmpty() &&
                    viewModel.memory.place.isNotEmpty()){




//                if (spinner.onItemSelectedListener.toString() == "Positive"){
//                    viewModel.memory.type = true
//                }else{
//                    viewModel.memory.type = false
//                }

                lifecycleScope.launch {
                    viewModel.saveMemory()
                }.invokeOnCompletion {
                    //z basefragmentu - skryje klavesnicu a popbackne
                    finishCurrentFragment()
                }


            }else{
                if(nameField.text.isEmpty()){
                    nameField.setError("Cannot be empty")
                }

                if(textField.text.isEmpty()){
                    textField.setError("Cannot be empty")
                }

                if(placeField.text.isEmpty()){
                    placeField.setError("Cannot be empty")
                }


            }





        }




        setInteractionListeners()

    }

    override fun onActivityCreated() {
    }


    private fun setInteractionListeners(){

        binding.memoryName.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.memoryName.setError(null)
                viewModel.memory.name = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.memoryName.text.isEmpty()) {
                    binding.memoryName.setError("Cannot be empty")
                } else {
                    binding.memoryName.setError(null)
                    viewModel.memory.name = p0.toString()
                }
            }
        })


        binding.memoryText.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.memoryText.setError(null)
                viewModel.memory.text = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.memoryText.text.isEmpty()) {
                    binding.memoryText.setError("Cannot be empty")
                } else {
                    binding.memoryText.setError(null)
                    viewModel.memory.text = p0.toString()
                }
            }
        })

        binding.memoryPlace.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.memoryPlace.setError(null)
                viewModel.memory.place = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.memoryPlace.text.isEmpty()) {
                    binding.memoryPlace.setError("Cannot be empty")
                } else {
                    binding.memoryPlace.setError(null)
                    viewModel.memory.place = p0.toString()
                }
            }
        })




    }



}