package pl.meksu.rentcar.common

import com.google.gson.Gson

object ErrorParser {

    fun parseErrorResponse(jsonBody: Any?): String {
        return if (jsonBody != null) {
            try {
                val backendResponse = Gson().fromJson(jsonBody.toString(), BackendResponse::class.java)
                backendResponse.message
            } catch (ex: Exception) {
                "Nieoczekiwany błąd serwera!"
            }
        } else {
            "Nieoczekiwany błąd serwera!"
        }
    }
}

