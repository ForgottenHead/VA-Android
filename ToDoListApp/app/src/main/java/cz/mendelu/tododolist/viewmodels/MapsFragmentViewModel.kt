package cz.mendelu.tododolist.viewmodels

import androidx.lifecycle.ViewModel

class MapsFragmentViewModel: ViewModel() {
    var latitude: Double? = null
    var longitude: Double? = null

    var locationChanged: Boolean = false
}