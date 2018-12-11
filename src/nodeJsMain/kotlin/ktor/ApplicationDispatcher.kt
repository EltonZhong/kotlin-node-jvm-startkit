package ktor

import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers

internal actual val ApplicationDispatcher = Dispatchers.Default
internal actual fun getClient(): HttpClient {
    // TODO: impl node engine
    return HttpClient()
}