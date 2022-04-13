package mx.com.moonsmileh.sciencecodedemo.data.model

import android.content.Context
import mx.com.moonsmileh.sciencecodedemo.util.getShipmentValues

class Repository(context: Context) {

    private val response = getShipmentValues(context)

    fun getAllDrivers(): List<String> {
        return response.drivers
    }

    fun getAllShipments(): List<String> {
        return response.shipments
    }
}