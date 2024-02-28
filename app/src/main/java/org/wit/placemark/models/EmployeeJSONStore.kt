package org.wit.placemark.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.placemark.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "placemarks.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<EmployeeModel>>() {}.type


fun generateRandomId(): Long {
    return Random().nextLong()
}

class EmployeeJSONStore(private val context: Context) : EmployeeStore {

    var employees = mutableListOf<EmployeeModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<EmployeeModel> {
        logAll()
        return employees
    }

    override fun findById(id:Long) : EmployeeModel? {
        val findEmployee: EmployeeModel? = employees.find { it.id == id }
        return findEmployee
    }

    override fun create(employee: EmployeeModel) {
        employee.id = generateRandomId()
        employees.add(employee)
        serialize()
    }

    override fun update(employee: EmployeeModel) {
        val employeeList = findAll() as ArrayList<EmployeeModel>
        var findEmployee: EmployeeModel? = employeeList.find { p -> p.id == employee.id }
        if (findEmployee != null) {
            findEmployee.name = employee.name
            findEmployee.bio = employee.bio
            findEmployee.email = employee.email
            findEmployee.phone = employee.phone
            findEmployee.image = employee.image
            findEmployee.lat = employee.lat
            findEmployee.lng = employee.lng
            findEmployee.zoom = employee.zoom
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(employees, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        employees = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(employee: EmployeeModel) {
        employees.remove(employee)
        serialize()
    }

    private fun logAll() {
        employees.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}