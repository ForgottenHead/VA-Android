package com.example.skuska

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.skuska.databinding.FragmentEditMemoryBinding
import com.example.skuska.viewModels.EditMemoryViewModel
import cz.mendelu.tododolist.architecture.BaseFragment
import kotlinx.coroutines.launch


class EditMemoryFragment : BaseFragment<FragmentEditMemoryBinding, EditMemoryViewModel>(EditMemoryViewModel::class) {
    private val arguments: EditMemoryFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentEditMemoryBinding
        get() = FragmentEditMemoryBinding::inflate

    override fun initViews() {
        viewModel.id = if (arguments.id != -1L) arguments.id else null

        if (viewModel.id == null){
            binding.memoryNameShow.text = "Error"
            binding.memoryTextShow.text = "error"
            binding.memoryPlaceShow.text = "error"
            binding.memoryTypeShow.text = "error"
        }else{
            setHasOptionsMenu(true)

            lifecycleScope.launch{
                viewModel.memory = viewModel.findMemoryById()
            }.invokeOnCompletion {
                binding.memoryNameShow.text = viewModel.memory.name
                binding.memoryTextShow.text = viewModel.memory.text
                binding.memoryPlaceShow.text = viewModel.memory.place
                if (viewModel.memory.type == true){
                    binding.memoryTypeShow.text = "Positive"
                }else{
                    binding.memoryTypeShow.text = "Negative"
                }
            }
        }



    }

    override fun onActivityCreated() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_delete ->{
                lifecycleScope.launch{
                    viewModel.deleteMemory()
                }.invokeOnCompletion {
                    finishCurrentFragment()
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}