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

        fun loadImage(url: String, callback: DownloadBitmapCallback) {
            // 检查是否是本地文件路径
            if (url.startsWith("file://") || (!url.startsWith("http://") && !url.startsWith("https://"))) {
                try {
                    // 处理本地文件
                    val filePath = if (url.startsWith("file://")) {
                        url.substring(7) // 移除 "file://" 前缀
                    } else {
                        url
                    }
                    
                    // 读取本地文件
                    val bitmap = BitmapFactory.decodeFile(filePath)
                    if (bitmap != null) {
                        callback.onResponse(bitmap)
                    } else {
                        callback.onFailure(
                            client.newCall(Request.Builder().url("http://dummy").build()),
                            IOException("无法读取图片文件: $filePath")
                        )
                    }
                } catch (e: Exception) {
                    callback.onFailure(
                        client.newCall(Request.Builder().url("http://dummy").build()),
                        IOException("读取本地文件失败: ${e.message}")
                    )
                }
            } else {
                // 处理网络图片
                downloadFileAsBitmap(url, callback)
            }
        }

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
            // 创建100x100的缩略图，保持比例
            val thumbSize = 100
            val scaledBitmap = Bitmap.createScaledBitmap(bmp, thumbSize, thumbSize, true)
            
            val localByteArrayOutputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, localByteArrayOutputStream)
            scaledBitmap.recycle()
            
            if (needRecycle) {
                bmp.recycle()
            }
            
            val arrayOfByte = localByteArrayOutputStream.toByteArray()
            try {
                localByteArrayOutputStream.close()
            } catch (e: Exception) {
                // 处理异常
            }
            
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
                    options -= 10
                } else {
                    // 如果压缩质量已经很低了，就缩小图片尺寸到100x100
                    val scaledBitmap = Bitmap.createScaledBitmap(
                        image, 
                        100, 
                        100, 
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
                ?: image.copy(image.config ?: Bitmap.Config.ARGB_8888, true)
        }
    }

    interface DownloadBitmapCallback {
        fun onFailure(@NonNull call: Call, @NonNull e: IOException)
        fun onResponse(@NonNull bitmap: Bitmap)
    }
} 