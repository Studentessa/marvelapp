package com.mmartinez.data

import okhttp3.internal.and
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class HashGenerator {
    fun buildMD5Digest(message: String): String {
        return run {
            val messageDigest = MessageDigest.getInstance("MD5")
            val bytes = messageDigest.digest(message.toByteArray())
            val bigInteger = BigInteger(1, bytes)
            var md5 = bigInteger.toString(16)
            while (md5.length < 32) {
                md5 = "0$md5"
            }
            md5
        }
    }

}