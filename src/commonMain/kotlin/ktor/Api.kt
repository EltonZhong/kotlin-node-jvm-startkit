package ktor

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.Url
import kotlinx.coroutines.*

internal expect val ApplicationDispatcher: CoroutineDispatcher
internal expect fun getClient(): HttpClient

class Api {
    private val client = getClient()

    var address = Url("https://www.baidu.com/")

    fun get(): Deferred<String> {
        GlobalScope.apply {
            return async(ApplicationDispatcher) {
                val result: String = client.get {
                    url(this@Api.address.toString())
                }
                return@async result
            }
        }
    }
}

suspend fun get() {
    val a = Api()
    println(a.get().await())
    println("finish")
}