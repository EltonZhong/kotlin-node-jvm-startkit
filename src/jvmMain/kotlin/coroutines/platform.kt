package coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

actual suspend fun platform(): String {
    delay(100)
    return "jvm"
}

@ExperimentalCoroutinesApi
actual suspend fun main(args: Array<String>) {
}