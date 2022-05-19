package cz.mendelu.tododolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.mendelu.tododolist.architecture.BaseFragment
import cz.mendelu.tododolist.databinding.FragmentTaskListBinding
import cz.mendelu.tododolist.databinding.RowTaskListBinding
import cz.mendelu.tododolist.model.Task
import cz.mendelu.tododolist.viewmodels.TaskListViewModel
import kotlinx.coroutines.launch

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
            return (oldList[oldItemPosition].text == newlist[newItemPosition].text) &&
                    (oldList[oldItemPosition].description == newlist[newItemPosition].description)
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

            if(!task.description.isNullOrEmpty()){
                holder.binding.taskDescription.text = task.description
                holder.binding.taskDescription.visibility = View.VISIBLE
            }else{
                holder.binding.taskDescription.visibility = View.GONE
            }

            holder.binding.checkbox.isChecked = task.done

            holder.binding.checkbox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                    lifecycleScope.launch{
                        viewModel.setCheckBoxState(tasksList.get(holder.adapterPosition).id!!, p1)
                    }

                }

            })

            holder.binding.root.setOnClickListener{
                val actions = TaskListFragmentDirections.actionListToAddTask()
                // pouzivat holder.adapterPosition ten vzdy vrati spravny riadok,
                // naopak od get(position) tu nemusi fungovat
                actions.id = tasksList.get(holder.adapterPosition).id!!

                findNavController().navigate(actions)
            }

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