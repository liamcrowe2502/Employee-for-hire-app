package org.wit.placemark.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import org.wit.placemark.databinding.ActivityEmployeeMapsBinding
import org.wit.placemark.databinding.ContentEmployeeMapsBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.EmployeeModel

class EmployeeMapView : AppCompatActivity() , GoogleMap.OnMarkerClickListener{

    private lateinit var binding: ActivityEmployeeMapsBinding
    private lateinit var contentBinding: ContentEmployeeMapsBinding
    lateinit var app: MainApp
    lateinit var presenter: EmployeeMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityEmployeeMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = EmployeeMapPresenter(this)

        contentBinding = ContentEmployeeMapsBinding.bind(binding.root)

        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync{
            presenter.doPopulateMap(it)
        }
    }
    fun showPlacemark(placemark: EmployeeModel) {
        contentBinding.currentTitle.text = placemark.name
        contentBinding.currentDescription.text = placemark.bio
        Picasso.get()
            .load(placemark.image)
            .into(contentBinding.currentImage)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }
}