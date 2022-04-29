package cz.mendelu.tododolist

import androidx.lifecycle.ViewModel
import cz.mendelu.tododolist.model.Task

class AddTaskViewModel : ViewModel() {

    var id: Long? = null
    var task: Task = Task("")
}