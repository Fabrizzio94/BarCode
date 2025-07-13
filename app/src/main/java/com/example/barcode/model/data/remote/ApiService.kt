package com.example.barcode.model.data.remote

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable

@Serializable
data class Activo(
    val activoFijo: String,
    val nombreActivo: String,
    val nombreCC: String,
    val fechaAlta: String?
    // Añade más propiedades según tu API
)

class ApiService {
    private val client = KtorClient.client

    suspend fun getActivo(codigo: String): Activo {
        return client.get("activos/$codigo").body()
    }

    suspend fun createActivo(activo: Activo): HttpResponse {
        return client.post("activos") {
            setBody(activo)
        }
    }

    // Agrega más métodos según tus endpoints
}