package com.example.skuska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.skuska.databinding.FragmentListBinding
import com.example.skuska.databinding.RowMemoryListBinding
import com.example.skuska.model.Memory
import com.example.skuska.viewModels.MemoryListViewModel
import cz.mendelu.tododolist.architecture.BaseFragment

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MemoryListFragment : BaseFragment<FragmentListBinding, MemoryListViewModel>(MemoryListViewModel::class) {

    private val memoryList: MutableList<Memory> = mutableListOf()
    private lateinit var adapter: MemoryAdapter
    private lateinit var layoutManager : LinearLayoutManager


    inner class MemoryDiffUtils(private val oldList: MutableList<Memory>,
                                private val newList: MutableList<Memory>): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].name == newList[newItemPosition].name) &&
                    (oldList[oldItemPosition].text == newList[newItemPosition].text) &&
                    (oldList[oldItemPosition].place == newList[newItemPosition].place)
        }

    }



    inner class MemoryAdapter:RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder>(){

        inner class MemoryViewHolder(val bindig:RowMemoryListBinding):
            RecyclerView.ViewHolder(bindig.root){}


        override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): MemoryAdapter.MemoryViewHolder {
            return MemoryViewHolder(
                RowMemoryListBinding.inflate(LayoutInflater.from(parent.context), parent,false))
        }

        override fun onBindViewHolder(holder: MemoryAdapter.MemoryViewHolder, position: Int) {
            val memory = memoryList.get(position)
            holder.bindig.memoryName.text = memory.name
            holder.bindig.memoryPlace.text = memory.place

            holder.bindig.root.setOnClickListener {
                val actions = MemoryListFragmentDirections.actionFirstToThird()
                actions.id = memoryList.get(holder.adapterPosition).id!!
                findNavController().navigate(actions)

            }
            

        }

        override fun getItemCount(): Int {
            return memoryList.size
        }

    }






    override val bindingInflater: (LayoutInflater) -> FragmentListBinding
        get() = FragmentListBinding::inflate

    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = MemoryAdapter()
        binding.memoriesList.layoutManager = layoutManager
        binding.memoriesList.adapter = adapter

        binding.fab.setOnClickListener{
            findNavController().navigate(MemoryListFragmentDirections.actionFirstFragmentToSecondFragment())
        }

        viewModel.getAll().observe(viewLifecycleOwner, object : Observer<MutableList<Memory>> {
            override fun onChanged(t: MutableList<Memory>?) {
                val diffCallback = MemoryDiffUtils(memoryList, t!!)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                diffResult.dispatchUpdatesTo(adapter)
                memoryList.clear()
                memoryList.addAll(t)
            }

        })
    }

    override fun onActivityCreated() {

    }

}