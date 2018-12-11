package coroutines

import kotlinx.coroutines.delay

actual suspend fun platform(): String {
    delay(100)
    return "jvm"
}