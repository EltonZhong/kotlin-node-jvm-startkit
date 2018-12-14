package coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope
import ktor.get

suspend fun getInt(): Int {
    val d = GlobalScope.async {
        return@async 1
    }
    return d.await()
}

fun getDeferred(): Deferred<Int> {
    val d = GlobalScope.async {
        return@async 1
    }
    return d
}

@ExperimentalCoroutinesApi
expect suspend fun main(args: Array<String>)

suspend expect fun platform(): String
