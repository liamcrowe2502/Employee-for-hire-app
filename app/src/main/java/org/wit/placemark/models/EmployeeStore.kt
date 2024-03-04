package org.wit.placemark.models

interface EmployeeStore {
    fun findAll(): List<EmployeeModel>
    fun findById(id:Long) : EmployeeModel?
    fun create(employee: EmployeeModel)
    fun update(employee: EmployeeModel)
    fun delete(employee: EmployeeModel)
}