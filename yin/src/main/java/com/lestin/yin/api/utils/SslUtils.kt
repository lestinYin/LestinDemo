package com.lestin.yin.api.utils

import com.lestin.yin.MyApplication
import com.lestin.yin.utils.LogUtil
import java.security.SecureRandom
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.interfaces.RSAPublicKey
import javax.net.ssl.*
import javax.security.cert.CertificateException


/**
 *
 * @ProjectName:    Taurus
 * @Package:        com.lestin.yin.api.utils
 * @ClassName:      SslUtils
 * @Description:     验证证书
 * @Author:         lestin.yin
 * @CreateDate:     2019-08-02 18:35
 * @Version:        1.0
 */
class SslUtils {
    companion object {

        private fun getKeyStore(fileName: String): KeyStore? {
            var keyStore: KeyStore? = null
            try {

                val assetManager = MyApplication.context.assets
                val cf = CertificateFactory.getInstance("X.509")
                val caInput = assetManager.open(fileName)
                val ca: Certificate
                try {
                    ca = cf.generateCertificate(caInput)
                } finally {
                    caInput.close()
                }

                val keyStoreType = KeyStore.getDefaultType()
                keyStore = KeyStore.getInstance(keyStoreType)
                keyStore!!.load(null, null)
                keyStore!!.setCertificateEntry("ca", ca)
            } catch (e: Exception) {
            }

            return keyStore
        }

        fun getSslContextForCertificateFile(fileName: String): SSLContext {
            try {
                val keyStore = getKeyStore(fileName)
                val sslContext = SSLContext.getInstance("SSL")
                val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(keyStore)
                sslContext.init(null, trustManagerFactory.getTrustManagers(), SecureRandom())
                return sslContext
            } catch (e: Exception) {
                val msg = "Error during creating SslContext for certificate from assets"
                LogUtil.e("SslUtils", msg, e)
                throw RuntimeException(msg)
            }

        }


        /**
         * 单项认证
         */
        fun getSSLSocketFactoryForOneWay(vararg certificates: InputStream): SSLSocketFactory? {
            try {

                val certificateFactory = CertificateFactory.getInstance("X.509", "BC")
                val keyStore = KeyStore.getInstance("BKS")
                keyStore.load(null)
                var index = 0
                for (certificate in certificates) {
                    val certificateAlias = Integer.toString(index++)
                    keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate))
                    try {
                        if (certificate != null)
                            certificate!!.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                val sslContext = SSLContext.getInstance("TLS")

                val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())

                trustManagerFactory.init(keyStore)
                sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
                return sslContext.socketFactory
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }
    }


}
