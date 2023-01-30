package com.common.utils

import java.lang.StringBuilder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object MD5Utils {

    fun getMD5(content: String): String? {
        return try {
            val var1: MessageDigest = MessageDigest.getInstance("MD5")
            var1.update(content.toByteArray())
            getHashString(var1)
        } catch (var2: NoSuchAlgorithmException) {
            var2.printStackTrace()
            null
        }
    }

    private fun getHashString(digest: MessageDigest): String? {
        val var1 = StringBuilder()
        val var2: ByteArray = digest.digest()
        val var3 = var2.size
        for (var4 in 0 until var3) {
            val var5 = var2[var4]

            Byte.to(4)
            var1.append(Integer.toHexString(var5.toInt() shr 4 and 15))
            var1.append(Integer.toHexString(var5.toInt() and 15))
        }
        return var1.toString()
    }
}

