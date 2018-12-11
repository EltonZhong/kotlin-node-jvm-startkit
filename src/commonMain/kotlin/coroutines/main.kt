package coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope

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
suspend fun main(args: Array<String>) {
    GlobalScope.launch {
        println("something after0 ")
        delay(1000)
        println("something after1 ")
        delay(1000)
        println("something after2 ")
    }

    delay(1000)
    println("A")
    delay(3000)
    println("B")
    println(platform())
    println(getInt())
    println(getDeferred().await())
}

suspend expect fun platform(): String
