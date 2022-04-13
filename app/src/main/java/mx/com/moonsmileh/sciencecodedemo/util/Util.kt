package mx.com.moonsmileh.sciencecodedemo.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mx.com.moonsmileh.sciencecodedemo.data.model.ShipmentDriver
import java.io.IOException

fun getShipmentValues(context: Context): ShipmentDriver {
    lateinit var jsonString: String
    try {
        jsonString = context.assets.open("data.json")
            .bufferedReader()
            .use { it.readText() }
    } catch (ioException: IOException) {
    }

    val listCountryType = object : TypeToken<ShipmentDriver>() {}.type
    return Gson().fromJson(jsonString, listCountryType)
}