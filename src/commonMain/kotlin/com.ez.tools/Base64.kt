package com.ez.tools

interface Base64Encoder {
    fun encode(src: ByteArray): ByteArray
    fun helloWorld(): Array<String>
}

expect object Base64Factory {
    fun createEncoder(): Base64Encoder
}
