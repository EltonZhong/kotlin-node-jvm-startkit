package sample

import kotlin.reflect.KClass

expect class Sample() {
    fun checkMe(): Int
}

expect object Platform {
    val name: String
}

annotation class ForTest(vararg val arg: KClass<out Throwable>)

@ForTest(Exception::class, Throwable::class)
fun hello(): String = "Hello from ${Platform.name}"