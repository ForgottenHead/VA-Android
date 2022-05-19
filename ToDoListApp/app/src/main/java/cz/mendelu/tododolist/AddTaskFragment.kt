package cz.mendelu.tododolist

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.tododolist.architecture.BaseFragment
import cz.mendelu.tododolist.database.TasksDatabase
import cz.mendelu.tododolist.databinding.FragmentAddTaskBinding
import cz.mendelu.tododolist.model.Task
import cz.mendelu.tododolist.utils.DateUtils
import kotlinx.coroutines.launch

class AddTaskFragment : BaseFragment<FragmentAddTaskBinding, AddTaskViewModel>(AddTaskViewModel::class) {
    private val arguments: AddTaskFragmentArgs by navArgs()


    override val bindingInflater: (LayoutInflater) -> FragmentAddTaskBinding
        get() = FragmentAddTaskBinding::inflate

    override fun initViews() {
        viewModel.id = if (arguments.id != -1L) arguments.id else null

        if (viewModel.id == null){
            fillLayout()
        }else{

            lifecycleScope.launch{
                viewModel.task = viewModel.findTaskById()
            }.invokeOnCompletion {
                fillLayout()
            }
        }

        setInteractionListeners()

    }

    override fun onActivityCreated() {

    }

    private fun fillLayout(){
        if(viewModel.task.text.isNotEmpty()){
            binding.taskName.text = viewModel.task.text
        }
        if(!viewModel.task.description.isNullOrEmpty()){
            binding.taskDescription.text = viewModel.task.description!!
        }

        setDate()

    }

    private fun setDate(){
        viewModel.task.date?.let {
            binding.taskDate.text = DateUtils.getDateString(it) //it je z let
            binding.taskDate.showClearButton()
        }?: kotlin.run {
            binding.taskDate.text = "Not set"
            binding.taskDate.hideClearButton()
        }
    }

    private fun setInteractionListeners(){
        binding.saveButton.setOnClickListener{
            if(binding.taskName.text.isNotEmpty()){
                binding.taskName.setError(null)
                //ulozenie do DB cez viewModel (treba cez ine vlakno nez hlavne)
                lifecycleScope.launch {
                    viewModel.saveTask()
                }.invokeOnCompletion {
                    //z basefragmentu - skryje klavesnicu a popbackne
                    finishCurrentFragment()
                }

            }else{
                binding.taskName.setError("Cannot be empty")
            }
        }

        binding.taskDate.setOnClickListener{
            openDatePickerDialog()
        }

        binding.taskName.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.task.text = p0.toString()
            }
        })

        binding.taskDescription.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.task.description = p0.toString()
            }
        })

        binding.taskDate.setOnClearClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                viewModel.task.date = null
                setDate()
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