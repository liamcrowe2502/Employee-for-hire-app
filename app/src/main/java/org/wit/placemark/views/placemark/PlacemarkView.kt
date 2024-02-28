package org.wit.placemark.views.placemark

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.placemark.R
import org.wit.placemark.databinding.EmployeeInfoBinding
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber.Forest.i

class PlacemarkView : AppCompatActivity() {

    private lateinit var binding: EmployeeInfoBinding
    private lateinit var presenter: PlacemarkPresenter
    var placemark = PlacemarkModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = EmployeeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = PlacemarkPresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cachePlacemark(binding.employeeName.text.toString(), binding.employeeBio.text.toString())
            presenter.doSelectImage()
        }

        binding.placemarkLocation.setOnClickListener {
            presenter.cachePlacemark(binding.employeeName.text.toString(), binding.employeeBio.text.toString())
            presenter.doSetLocation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                if (binding.employeeName.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_placemark_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    // presenter.cachePlacemark(binding.placemarkTitle.text.toString(), binding.description.text.toString())
                    presenter.doAddOrSave(binding.employeeName.text.toString(), binding.employeeBio.text.toString())
                }
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showPlacemark(placemark: PlacemarkModel) {
        binding.employeeName.setText(placemark.title)
        binding.employeeBio.setText(placemark.description)
        Picasso.get()
            .load(placemark.image)
            .into(binding.placemarkImage)
        if (placemark.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_placemark_image)
        }

    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.placemarkImage)
        binding.chooseImage.setText(R.string.change_placemark_image)
    }

}