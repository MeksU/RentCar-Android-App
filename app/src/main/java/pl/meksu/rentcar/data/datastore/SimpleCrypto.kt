package pl.meksu.rentcar.data.datastore

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest

object SimpleCrypto {
    private const val ALGORITHM = "AES/ECB/PKCS5Padding"

    fun createKeyFromPassword(password: String): SecretKey {
        val keyBytes = MessageDigest.getInstance("SHA-256")
            .digest(password.toByteArray())
        return SecretKeySpec(keyBytes, ALGORITHM)
    }

    fun encrypt(data: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    fun decrypt(data: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decodedBytes = Base64.decode(data, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }
}
