package cz.mendelu.tododolist

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cz.mendelu.tododolist.architecture.BaseFragment
import cz.mendelu.tododolist.databinding.FragmentAddTaskBinding
import cz.mendelu.tododolist.extensions.roundTwoDecimal
import cz.mendelu.tododolist.utils.DateUtils
import cz.mendelu.tododolist.viewmodels.AddTaskViewModel
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

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Double>("latitude")
            ?.observe(viewLifecycleOwner, {
                viewModel.task.latitude = it

                findNavController().currentBackStackEntry?.savedStateHandle?.remove<Double>("latitude")
                setLocation()
            })

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Double>("longitude")
            ?.observe(viewLifecycleOwner, {
                viewModel.task.longitude = it

                findNavController().currentBackStackEntry?.savedStateHandle?.remove<Double>("longitude")
                setLocation()
            })

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
        setLocation()

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

    private fun setLocation(){
        if(viewModel.task.latitude != null && viewModel.task.longitude  !=null){
            binding.location.text = "${viewModel.task.latitude!!.roundTwoDecimal()}; ${viewModel.task.longitude!!.roundTwoDecimal()}"
            binding.location.showClearButton()
        }else{
            binding.location.text = "Not set"
            binding.location.hideClearButton()
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

        binding.location.setOnClickListener{
            val directions = AddTaskFragmentDirections.actionAddtaskToMap()
            if(viewModel.task.latitude != null && viewModel.task.longitude != null){
                directions.latitude = viewModel.task.latitude!!.toFloat()
                directions.longitude = viewModel.task.longitude!!.toFloat()
            }

            findNavController().navigate(directions)
        }

        binding.location.setOnClearClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                viewModel.task.latitude = null
                viewModel.task.longitude = null
                setLocation()
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