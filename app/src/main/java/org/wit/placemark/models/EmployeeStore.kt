package org.wit.placemark.models

interface EmployeeStore {
    fun findAll(): List<EmployeeModel>
    fun findById(id:Long) : EmployeeModel?
    fun create(placemark: EmployeeModel)
    fun update(placemark: EmployeeModel)
    fun delete(placemark: EmployeeModel)
}