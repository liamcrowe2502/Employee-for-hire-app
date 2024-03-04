package org.wit.placemark.views.employee

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.placemark.R
import org.wit.placemark.databinding.EmployeeInfoBinding
import org.wit.placemark.models.EmployeeModel
import timber.log.Timber.Forest.i

class EmployeeView : AppCompatActivity() {

    private lateinit var binding: EmployeeInfoBinding
    private lateinit var presenter: EmployeePresenter
    var placemark = EmployeeModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = EmployeeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = EmployeePresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cachePlacemark(binding.employeeInfoName.text.toString(), binding.employeeInfoBio.text.toString(),
                binding.employeeInfoEmail.text.toString(), binding.employeeInfoPhoneNum.text.toString())
            presenter.doSelectImage()
        }

        binding.placemarkLocation.setOnClickListener {
            presenter.cachePlacemark(binding.employeeInfoName.text.toString(), binding.employeeInfoBio.text.toString(),
                binding.employeeInfoEmail.text.toString(), binding.employeeInfoPhoneNum.text.toString())
            presenter.doSetLocation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_employee, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                if (binding.employeeInfoName.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_placemark_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    // presenter.cachePlacemark(binding.placemarkTitle.text.toString(), binding.description.text.toString())
                    presenter.doAddOrSave(binding.employeeInfoName.text.toString(), binding.employeeInfoBio.text.toString(),
                        binding.employeeInfoEmail.text.toString(), binding.employeeInfoPhoneNum.text.toString())
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

    fun showPlacemark(placemark: EmployeeModel) {
        binding.employeeInfoName.setText(placemark.name)
        binding.employeeInfoBio.setText(placemark.bio)
        binding.employeeInfoEmail.setText(placemark.email)
        binding.employeeInfoPhoneNum.setText(placemark.phone)
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