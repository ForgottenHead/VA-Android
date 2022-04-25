package cz.mendelu.tododolist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class TaskListFragment : Fragment() {

    companion object {
        fun newInstance() = TaskListFragment()
    }

    private lateinit var viewModel: TaskListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_task_list, container, false)
        val button: MaterialButton = view.findViewById(R.id.tlacitko)
        button.setOnClickListener{

//         ---BAD PRACTICE!---
//            val bundle = Bundle()
//            bundle.putInt("id", 10)
//            findNavController().navigate(R.id.AddTaskFragment, bundle)

            val directions = TaskListFragmentDirections.actionListToAddTask()
            findNavController().navigate(directions)
        }
        return view


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskListViewModel::class.java)
        // TODO: Use the ViewModel
    }



}