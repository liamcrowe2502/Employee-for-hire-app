package org.wit.placemark.models

import timber.log.Timber.Forest.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class EmployeeMemStore : EmployeeStore {

    val employees = ArrayList<EmployeeModel>()

    override fun findAll(): List<EmployeeModel> {
        return employees
    }

    override fun findById(id:Long) : EmployeeModel? {
        val findEmployee: EmployeeModel? = employees.find { it.id == id }
        return findEmployee
    }

    override fun create(employee: EmployeeModel) {
        employee.id = getId()
        employees.add(employee)
        logAll()
    }

    override fun update(employee: EmployeeModel) {
        val findEmployee: EmployeeModel? = employees.find { p -> p.id == employee.id }
        if (findEmployee != null) {
            findEmployee.name = employee.name
            findEmployee.bio = employee.bio
            findEmployee.email = employee.email
            findEmployee.phone = employee.phone
            findEmployee.work = employee.work
            findEmployee.image = employee.image
            findEmployee.lat = employee.lat
            findEmployee.lng = employee.lng
            findEmployee.zoom = employee.zoom
            logAll()
        }
    }

    private fun logAll() {
        employees.forEach { i("$it") }
    }

    override fun delete(employee: EmployeeModel) {
        employees.remove(employee)
    }
}