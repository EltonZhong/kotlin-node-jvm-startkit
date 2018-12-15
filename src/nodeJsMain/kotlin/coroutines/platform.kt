package coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

actual suspend fun platform(): String {
    println("js")
    return GlobalScope.async {
        delay(11)
        return@async "js"
    }.await()
}

actual suspend fun main(args: Array<String>) {
}