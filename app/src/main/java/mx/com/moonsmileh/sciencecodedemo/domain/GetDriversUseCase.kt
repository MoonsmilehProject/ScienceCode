package mx.com.moonsmileh.sciencecodedemo.domain

import android.content.Context
import mx.com.moonsmileh.sciencecodedemo.data.model.Repository

class GetDriversUseCase(context: Context) {
    private val repository = Repository(context)

    operator fun invoke(): List<String> {
        return repository.getAllDrivers()
    }

}