package org.wit.placemark.views.employeeList

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.placemark.R
import org.wit.placemark.adapters.EmployeeAdapter
import org.wit.placemark.adapters.PlacemarkListener
import org.wit.placemark.databinding.ActivityEmployeeListBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.main.SignUpActivity
import org.wit.placemark.models.EmployeeModel
import android.content.Intent

class EmployeeListView : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityEmployeeListBinding
    lateinit var presenter: EmployeeListPresenter
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = EmployeeListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadPlacemarks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddEmployee() }
            R.id.item_map -> { presenter.doShowEmployeesMap() }
            R.id.item_logout -> {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEmployeeClick(placemark: EmployeeModel, position: Int) {
        this.position = position
        presenter.doEditEmployee(placemark, this.position)
    }

    private fun loadPlacemarks() {
        binding.recyclerView.adapter = EmployeeAdapter(presenter.getEmployees(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getEmployees().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}