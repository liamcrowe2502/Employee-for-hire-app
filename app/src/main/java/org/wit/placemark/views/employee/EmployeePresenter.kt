package org.wit.placemark.views.employee

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.wit.placemark.databinding.EmployeeInfoBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.EmployeeModel
import org.wit.placemark.showImagePicker
import org.wit.placemark.views.editlocation.EditLocationView
import timber.log.Timber

class EmployeePresenter(private val view: EmployeeView) {

    var employee = EmployeeModel()
    var app: MainApp = view.application as MainApp
    var binding: EmployeeInfoBinding = EmployeeInfoBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("employee_edit")) {
            edit = true
            employee = view.intent.extras?.getParcelable("employee_edit")!!
            view.showEmployee(employee)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, bio: String, email: String, phoneNum: String) {
        employee.name = title
        employee.bio = bio
        employee.email = email
        employee.phone = phoneNum
        if (edit) {
            app.employees.update(employee)
        } else {
            app.employees.create(employee)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.employees.delete(employee)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (employee.zoom != 0f) {
            location.lat =  employee.lat
            location.lng = employee.lng
            location.zoom = employee.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun casheEmployee (title: String, description: String, email: String, phoneNum: String) {
        employee.name = title;
        employee.bio = description;
        employee.email = email;
        employee.phone = phoneNum;
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            employee.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(employee.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            view.updateImage(employee.image)
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            employee.lat = location.lat
                            employee.lng = location.lng
                            employee.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}