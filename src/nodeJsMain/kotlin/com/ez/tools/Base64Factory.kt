package com.ez.tools

actual object Base64Factory {
    actual fun createEncoder(): Base64Encoder = JsBase64Encoder
}

object JsBase64Encoder : Base64Encoder {
    override fun encode(src: ByteArray): ByteArray {
        val buffer = js("Buffer").from(src)
        val str = buffer.toString("base64") as String
        return ByteArray(size = str.length) { str[it].toByte() }
    }

    override fun helloWorld(): Array<String> {
        console.log("Hello World")
        return arrayOf("Hello", "JS")
    }

}