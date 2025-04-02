package com.hector.nativewechat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import androidx.annotation.NonNull
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

class NativeWechatUtils {
    companion object {
        private val client = OkHttpClient()

        fun downloadFileAsBitmap(url: String, callback: DownloadBitmapCallback) {
            val request = Request.Builder().url(url).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(@NonNull call: Call, @NonNull e: IOException) {
                    callback.onFailure(call, e)
                }

                override fun onResponse(@NonNull call: Call, @NonNull response: Response) {
                    response.body?.let { responseBody ->
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        val bytes = responseBody.bytes()
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            ?: throw IOException("Failed to decode bitmap")
                        callback.onResponse(bitmap)
                    }
                }
            })
        }

        fun bmpToByteArray(bmp: Bitmap, needRecycle: Boolean): ByteArray {
            val i: Int
            val j: Int
            if (bmp.height > bmp.width) {
                i = bmp.width
                j = bmp.width
            } else {
                i = bmp.height
                j = bmp.height
            }

            val localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565)
            val localCanvas = Canvas(localBitmap)

            localCanvas.drawBitmap(bmp, Rect(0, 0, i, j), Rect(0, 0, i, j), null)
            if (needRecycle) {
                bmp.recycle()
            }
            
            val localByteArrayOutputStream = ByteArrayOutputStream()
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream)
            localBitmap.recycle()
            
            val arrayOfByte = localByteArrayOutputStream.toByteArray()
            try {
                localByteArrayOutputStream.close()
                return arrayOfByte
            } catch (e: Exception) {
                // 处理异常
            }
            
            // 注意：原Java代码有一个无限循环，这里修正了设计
            return arrayOfByte
        }

        fun compressImage(image: Bitmap, size: Int): Bitmap {
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var options = 100

            while (baos.toByteArray().size / 1024 > size) {
                // 重置baos即清空baos
                baos.reset()
                if (options > 10) {
                    options -= 8
                } else {
                    val scaledBitmap = Bitmap.createScaledBitmap(
                        image, 
                        280, 
                        image.height / image.width * 280, 
                        true
                    )
                    return compressImage(scaledBitmap, size)
                }
                // 这里压缩options%，把压缩后的数据存放到baos中
                image.compress(Bitmap.CompressFormat.JPEG, options, baos)
            }

            val isBm = ByteArrayInputStream(baos.toByteArray())
            // 确保返回非空的Bitmap
            return BitmapFactory.decodeStream(isBm, null, null) 
                ?: image.copy(image.config, true)
        }
    }

    interface DownloadBitmapCallback {
        fun onFailure(@NonNull call: Call, @NonNull e: IOException)
        fun onResponse(@NonNull bitmap: Bitmap)
    }
} 