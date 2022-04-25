package cz.mendelu.tododolist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import cz.mendelu.tododolist.database.TasksDatabase
import cz.mendelu.tododolist.model.Task

class AddTaskFragment : Fragment() {
    private val arguments: AddTaskFragmentArgs by navArgs()

    companion object {
        fun newInstance() = AddTaskFragment()
    }



    private lateinit var viewModel: AddTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_add_task, container, false)
        val id = arguments.id

        val textInputView: TextInputView = view.findViewById(R.id.taskName)
        val saveButton: MaterialButton = view.findViewById(R.id.saveButton)

        saveButton.setOnClickListener{
            if(textInputView.text.isNotEmpty()){
                textInputView.setError(null)
                //ulozenie do DB
                TasksDatabase.getDatabase(requireContext()).tasksDao().insertTask(Task(textInputView.text))

                //navrat spat na list
                findNavController().popBackStack()
            }else{
                textInputView.setError("Cannot be empty")
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddTaskViewModel::class.java)
        // TODO: Use the ViewModel
    }

}