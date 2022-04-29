package cz.mendelu.tododolist

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import cz.mendelu.tododolist.architecture.BaseFragment
import cz.mendelu.tododolist.database.TasksDatabase
import cz.mendelu.tododolist.databinding.FragmentAddTaskBinding
import cz.mendelu.tododolist.model.Task
import cz.mendelu.tododolist.utils.DateUtils

class AddTaskFragment : BaseFragment<FragmentAddTaskBinding, AddTaskViewModel>(AddTaskViewModel::class) {
    private val arguments: AddTaskFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_add_task, container, false)




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


                //todo opravit
            setInteractionListeners()
        }

        return view
    }


    override val bindingInflater: (LayoutInflater) -> FragmentAddTaskBinding
        get() = FragmentAddTaskBinding::inflate

    override fun initViews() {
        viewModel.id = if (arguments.id != -1L) arguments.id else null

        if (viewModel.id == null){
            //pridam task
        }else{
            //uprav task
        }

        setInteractionListeners()
    }

    override fun onActivityCreated() {

    }

    private fun fillLayout(){
        if(viewModel.task.text.isNotEmpty()){
            binding.taskName.text = viewModel.task.text
        }
        if(viewModel.task.description.isNullOrEmpty()){
            binding.taskDescription.text = viewModel.task.description!!
        }

        setDate()

    }

    private fun setDate(){
        viewModel.task.date?.let {
            binding.taskDate.text = DateUtils.getDateString(it) //it je z let
        }?: kotlin.run {
            binding.taskDate.text = "Not set"
        }
    }

    private fun setInteractionListeners(){
        binding.taskDate.setOnClickListener{
            openDatePickerDialog()
        }

        binding.taskName.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.task.text = p0.toString()
            }
        })
    }


    private fun openDatePickerDialog(){
        val calendar = Calendar.getInstance()
        viewModel.task.date?.let {
            calendar.timeInMillis = it
        }

        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(requireContext(),

                object : DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(
                        p0: DatePicker?,
                        year: Int,
                        month: Int,
                        day: Int
                    ) {
                        viewModel.task.date = DateUtils.getUnixTime(year, month, day)
                        setDate()
                    }
                }
            , y,m,d)



        dialog.show()
    }

}