package cz.mendelu.tododolist

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import cz.mendelu.tododolist.architecture.BaseFragment
import cz.mendelu.tododolist.constants.BundleConstants
import cz.mendelu.tododolist.databinding.FragmentMapsBinding
import cz.mendelu.tododolist.viewmodels.MapsFragmentViewModel

class MapsFragment : BaseFragment<FragmentMapsBinding, MapsFragmentViewModel>(MapsFragmentViewModel::class) {

    private val arguments: MapsFragmentArgs by navArgs()

    private val callback = OnMapReadyCallback { googleMap ->
//
//        //marker ma vlastnost .tag , tomu mozes dat cokolvek napr ID. !!!!!!!!!!!!!!!
//        val marker = googleMap.addMarker(MarkerOptions().position(mendelu).title("Mendelu marker"))
//        //marker.tag = ID
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(mendelu))


        var position: LatLng
        if (viewModel.latitude != null && viewModel.longitude != null){
            position = LatLng(viewModel.latitude!!, viewModel.longitude!!)
        }else{
            position = LatLng(49.21006714845514, 16.616193346967748)
        }

        val markerOptions = MarkerOptions().position(position).draggable(true)
        val marker = googleMap.addMarker(markerOptions)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0f))

        //draging
        googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {}

            override fun onMarkerDragEnd(p0: Marker) {
                viewModel.latitude = p0.position.latitude
                viewModel.longitude = p0.position.longitude
                viewModel.locationChanged = true
            }

            override fun onMarkerDragStart(p0: Marker) {}

        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override val bindingInflater: (LayoutInflater) -> FragmentMapsBinding
        get() = FragmentMapsBinding::inflate

    override fun initViews() {
        //pokial chces pouzit vo fragmente menu tak treba prebit aktivitu
        setHasOptionsMenu(true)
        if (arguments.latitude != 0.0f && arguments.longitude != 0.0f)
            viewModel.latitude = arguments.latitude.toDouble()
            viewModel.longitude = arguments.longitude.toDouble()

    }

    override fun onActivityCreated() {
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_map, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_save ->{
                findNavController().previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(BundleConstants.LATITUDE, viewModel.latitude)


                findNavController().previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(BundleConstants.LONGITUDE, viewModel.longitude)
                finishCurrentFragment()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}