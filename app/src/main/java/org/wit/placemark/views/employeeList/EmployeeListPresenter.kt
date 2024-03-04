package org.wit.placemark.views.employeeList

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.placemark.views.employee.EmployeeView
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.EmployeeModel
import org.wit.placemark.views.map.EmployeeMapView

class EmployeeListPresenter(val view: EmployeeListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getEmployees() = app.employees.findAll()

    fun doAddEmployee() {
        val launcherIntent = Intent(view, EmployeeView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditEmployee(employee: EmployeeModel, pos: Int) {
        val launcherIntent = Intent(view, EmployeeView::class.java)
        launcherIntent.putExtra("employee_edit", employee)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowEmployeesMap() {
        val launcherIntent = Intent(view, EmployeeMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}