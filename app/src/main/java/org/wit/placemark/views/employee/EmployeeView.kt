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
    var employee = EmployeeModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EmployeeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = EmployeePresenter(this)

        binding.employeeInfoCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.employeeInfoCheckbox.text = getString(R.string.open_to_work)
            } else {
                binding.employeeInfoCheckbox.text = getString(R.string.not_available)
            }
        }

        binding.chooseImage.setOnClickListener {
            presenter.casheEmployee(
                binding.employeeInfoName.text.toString(),
                binding.employeeInfoBio.text.toString(),
                binding.employeeInfoEmail.text.toString(),
                binding.employeeInfoPhoneNum.text.toString().toLongOrNull() ?: 0L,
                binding.employeeInfoCheckbox.isChecked
            )
            presenter.doSelectImage()
        }

        binding.employeeLocation.setOnClickListener {
            presenter.casheEmployee(
                binding.employeeInfoName.text.toString(),
                binding.employeeInfoBio.text.toString(),
                binding.employeeInfoEmail.text.toString(),
                binding.employeeInfoPhoneNum.text.toString().toLongOrNull() ?: 0L,
                binding.employeeInfoCheckbox.isChecked
            )
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
                    Snackbar.make(binding.root, R.string.enter_employee_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    presenter.doAddOrSave(
                        binding.employeeInfoName.text.toString(),
                        binding.employeeInfoBio.text.toString(),
                        binding.employeeInfoEmail.text.toString(),
                        binding.employeeInfoPhoneNum.text.toString().toLongOrNull() ?: 0L,
                        binding.employeeInfoCheckbox.isChecked
                    )
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

    fun showEmployee(employee: EmployeeModel) {
        binding.employeeInfoName.setText(employee.name)
        binding.employeeInfoBio.setText(employee.bio)
        binding.employeeInfoEmail.setText(employee.email)
        binding.employeeInfoPhoneNum.setText(employee.phone.toString())
        Picasso.get()
            .load(employee.image)
            .into(binding.employeeImage)

        // Set the text for the hire notice based on work status
        if (employee.work) {
            binding.employeeInfoCheckbox.text = getString(R.string.open_to_work)
        } else {
            binding.employeeInfoCheckbox.text = getString(R.string.not_available)
        }

        if (employee.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_employee_image)
        }
    }


    fun updateImage(image: Uri) {
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.employeeImage)
        binding.chooseImage.setText(R.string.change_employee_image)
    }
}
