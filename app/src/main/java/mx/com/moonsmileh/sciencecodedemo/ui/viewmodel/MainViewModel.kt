package mx.com.moonsmileh.sciencecodedemo.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.com.moonsmileh.sciencecodedemo.domain.GetDriversUseCase
import mx.com.moonsmileh.sciencecodedemo.domain.GetShipmentsUseCase

class MainViewModel : ViewModel() {

    val driversLiveData = MutableLiveData<List<String>>()
    val bestSuitableShipment = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    private lateinit var getDriversUseCase: GetDriversUseCase
    private lateinit var getShipmentsUseCase: GetShipmentsUseCase

    private var drivers = mutableListOf<String>()
    private var shipments = mutableListOf<String>()


    fun onCreate(context: Context) {
        getDriversUseCase = GetDriversUseCase(context)
        getShipmentsUseCase = GetShipmentsUseCase(context)

        viewModelScope.launch {
            isLoading.value = true
            drivers = getDriversUseCase() as MutableList<String>
            shipments = getShipmentsUseCase() as MutableList<String>
            if (!drivers.isNullOrEmpty()) {
                driversLiveData.postValue(drivers)
                isLoading.value = false
            }
        }

    }

    fun getBestSuitableShipment(driver: String) {
        val driverToShipment = mutableMapOf<String, String>()

        val localDrivers = drivers.toMutableList()

        for (shipment in shipments) {
            var lastIndex = 0
            var lastMajorSS = 0.0

            for (index in localDrivers.indices) {
                var suitabilityScore: Double =
                    if (isOddNumber(shipment.length)) getConsonantsNumber(localDrivers[index]).times(
                        1.5
                    ) else
                        getVowelsNumber(localDrivers[index]).toDouble()

                if (suitabilityScore > lastMajorSS) {
                    lastMajorSS = suitabilityScore
                    lastIndex = index
                }

                if (shipment.length == localDrivers[index].length) {
                    suitabilityScore += suitabilityScore.times(0.5)
                }

            }
            driverToShipment[localDrivers[lastIndex]] = shipment
            localDrivers.removeAt(lastIndex)
        }
        val shipment = driverToShipment.get(driver)

        bestSuitableShipment.value =
            "Driver $driver has the shipment $shipment assigned"
    }

    private fun isOddNumber(number: Int): Boolean = number % 2 == 0


    private fun getVowelsNumber(word: String): Int {

        val regex = Regex("[aeiou]", RegexOption.IGNORE_CASE)
        var count = 0

        for (letter in word) {
            count += if (regex.containsMatchIn(letter.toString())) 1 else 0
        }

        return count
    }

    private fun getConsonantsNumber(word: String): Int {
        val regex = Regex("[bcdfghjklmnpqrstvwxyz]", RegexOption.IGNORE_CASE)
        var count = 0

        for (letter in word) {
            count += if (regex.containsMatchIn(letter.toString())) 1 else 0
        }

        return count
    }
}