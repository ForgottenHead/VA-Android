package cz.mendelu.tododolist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import cz.mendelu.tododolist.architecture.BaseFragment
import cz.mendelu.tododolist.database.TasksDatabase
import cz.mendelu.tododolist.databinding.FragmentTaskListBinding
import cz.mendelu.tododolist.databinding.RowTaskListBinding
import cz.mendelu.tododolist.model.Task

class TaskListFragment : BaseFragment<FragmentTaskListBinding, TaskListViewModel>(TaskListViewModel::class) {

    private val tasksList: MutableList<Task> = mutableListOf()
    private lateinit var adapter: TasksAdapter
    private lateinit var layoutManager : LinearLayoutManager


    inner class TaskDiffUtils(private val oldList: MutableList<Task>,
                              private val newlist: MutableList<Task>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newlist.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newlist[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].text == newlist[newItemPosition].text
        }
    }

    inner class TasksAdapter: RecyclerView.Adapter<TasksAdapter.TaskViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            return TaskViewHolder(
                RowTaskListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = tasksList.get(position)
            holder.binding.taskName.text = task.text
            holder.binding.taskDescription.text = task.description

//            if(position % 2 == 0){
//                holder.binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
//                holder.binding.taskName.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
//
//            }else{
//                holder.binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
//            }

        }
        //override fun getItemCount(): Int = tasksList.size
        override fun getItemCount(): Int {
                return  tasksList.size
        }

        inner class TaskViewHolder(val binding:RowTaskListBinding):
            RecyclerView.ViewHolder(binding.root){
        }
    }

    override val bindingInflater: (LayoutInflater) -> FragmentTaskListBinding
        get() = FragmentTaskListBinding::inflate

    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = TasksAdapter()
        binding.taskList.layoutManager = layoutManager
        binding.taskList.adapter = adapter


        binding.fab.setOnClickListener{
            findNavController().navigate(TaskListFragmentDirections.actionListToAddTask())
        }
        viewModel.getAll().observe(viewLifecycleOwner, object : Observer<MutableList<Task>>{
            override fun onChanged(t: MutableList<Task>?) {
                val diffCallback = TaskDiffUtils(tasksList, t!!)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                diffResult.dispatchUpdatesTo(adapter)
                tasksList.clear()
                tasksList.addAll(t)
            }

        })
    }

    override fun onActivityCreated() {

    }


}