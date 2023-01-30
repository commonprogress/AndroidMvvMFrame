package com.base.commonality.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.text.TextUtils
import java.io.*

/**
 * 文件管理类 封装
 * @Author: jhosnjson
 * @Time: 2023/1/30
 */
class FileUtils {

    companion object {
        val singleton = Instance.instance
    }

    private object Instance {
        val instance = FileUtils()
    }

    private val TAG = "FileUtils"
    val FILE_SCHEME_LENGTH = 7

    fun FileUtils() {}


    fun getNameFromUrl(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

    fun getFileInputStream(path: String?): InputStream? {
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = FileInputStream(File(path))
        } catch (var3: FileNotFoundException) {
//            RLog.e(TAG, "getFileInputStream", var3)
        }
        return fileInputStream
    }

    fun getByteFromUri(uri: Uri?): ByteArray? {
        return if (uri == null) {
//            RLog.e(TAG, "getByteFromUri uri should not be null!")
            ByteArray(0)
        } else {
            val input = getFileInputStream(uri.path)
            var bytes: ByteArray
            try {
                var count = 0
                while (true) {
                    if (count == 0) {
                        count = input!!.available()
                        if (count != 0) {
                            continue
                        }
                    }
                    bytes = ByteArray(count)
                    val byteCount = input!!.read(bytes)
                    return bytes
                }
            } catch (var15: Exception) {
                bytes = ByteArray(0)
            } finally {
                if (input != null) {
                    try {
                        input.close()
                    } catch (var14: IOException) {
//                        RLog.e(TAG, "getByteFromUri", var14)
                    }
                }
            }
            bytes
        }
    }

    fun writeByte(uri: Uri?, data: ByteArray?) {
        if (uri != null && uri.path != null && uri.path!!.lastIndexOf("/") != -1) {
            val fileFolder = File(uri.path!!.substring(0, uri.path!!.lastIndexOf("/")))
            val successMkdir = fileFolder.mkdirs()
            if (!successMkdir) {
//                RLog.e(TAG, "Created folders unSuccessfully")
            }
            val file = File(uri.path)
            var os: BufferedOutputStream? = null
            try {
                os = BufferedOutputStream(FileOutputStream(file))
                os.write(data)
            } catch (var15: IOException) {
//                RLog.e(TAG, "writeByte", var15)
            } finally {
                if (os != null) {
                    try {
                        os.close()
                    } catch (var14: IOException) {
//                        RLog.e(TAG, "os close", var14)
                    }
                }
            }
        }
    }

    fun convertBitmap2File(bm: Bitmap?, dir: String?, name: String): File? {
        return if (bm != null && !TextUtils.isEmpty(dir)) {
            val dirFile = File(dir)
            if (!dirFile.exists()) {
//                RLog.e(TAG, "convertBitmap2File: dir does not exist! -" + dirFile.absolutePath)
                val successMkdir = dirFile.mkdirs()
                if (!successMkdir) {
//                    RLog.e(TAG, "Created folders unSuccessfully")
                }
            }
            var targetFile = File(dirFile.path + File.separator + name)
            if (targetFile.exists()) {
                val isDelete = targetFile.delete()
//                RLog.e(TAG, "convertBitmap2File targetFile isDelete:$isDelete")
            }
            val tmpFile = File(dirFile.path + File.separator + name + ".tmp")
            try {
                val bos = BufferedOutputStream(FileOutputStream(tmpFile))
                bm.compress(CompressFormat.PNG, 100, bos)
                bos.flush()
                bos.close()
            } catch (var7: IOException) {
//                RLog.e(TAG, "convertBitmap2File: Exception!", var7)
            }
            targetFile = File(dirFile.path + File.separator + name)
            if (tmpFile.renameTo(targetFile)) targetFile else tmpFile
        } else {
//            RLog.e(TAG, "convertBitmap2File bm or dir should not be null!")
            null
        }
    }

    fun copyFile(src: File?, path: String, name: String): File? {
        return if (src == null) {
//            RLog.e(TAG, "copyFile src should not be null!")
            null
        } else if (!src.exists()) {
//            RLog.e(TAG, "copyFile: src file does not exist! -" + src.absolutePath)
            null
        } else {
            var dest = File(path)
            if (!dest.exists()) {
//                RLog.d(TAG, "copyFile: dir does not exist!")
                val successMkdir = dest.mkdirs()
                if (!successMkdir) {
//                    RLog.e(TAG, "Created folders unSuccessfully")
                }
            }
            dest = File(path + name)
            var fis: FileInputStream? = null
            var fos: FileOutputStream? = null
            val var7: File
            try {
                fis = FileInputStream(src)
                fos = FileOutputStream(dest)
                val buffer = ByteArray(1024)
                var length: Int
                while (fis.read(buffer).also { length = it } != -1) {
                    fos.write(buffer, 0, length)
                }
                fos.flush()
                return dest
            } catch (var21: IOException) {
//                RLog.e(TAG, "copyFile: Exception!", var21)
                var7 = dest
            } finally {
                if (fis != null) {
                    try {
                        fis.close()
                    } catch (var20: IOException) {
//                        RLog.e(TAG, "copyFile: fis close!", var20)
                    }
                }
                if (fos != null) {
                    try {
                        fos.close()
                    } catch (var19: IOException) {
//                        RLog.e(TAG, "copyFile: fos close!", var19)
                    }
                }
            }
            var7
        }
    }

    fun copyFile(srcPath: String?, path: String?, name: String?): Boolean {
        return if (TextUtils.isEmpty(srcPath)) {
//            RLog.e(TAG, "copyFile src should not be null!")
            false
        } else {
            val src = File(srcPath)
            if (!src.exists()) {
//                RLog.e(TAG, "copyFile: src file does not exist! -" + src.absolutePath)
                false
            } else {
                var dest = File(path)
                if (!dest.exists()) {
//                    RLog.d(TAG, "copyFile: dir does not exist!")
                    val successMkdir = dest.mkdirs()
                    if (!successMkdir) {
//                        RLog.e(TAG, "Created folders unSuccessfully")
                    }
                }
                dest = File(path, name)
                var fis: FileInputStream? = null
                var fos: FileOutputStream? = null
                val var8: Boolean
                try {
                    fis = FileInputStream(src)
                    fos = FileOutputStream(dest)
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (fis.read(buffer).also { length = it } != -1) {
                        fos.write(buffer, 0, length)
                    }
                    fos.flush()
                    return true
                } catch (var22: IOException) {
//                    RLog.e(TAG, "copyFile: Exception!", var22)
                    var8 = false
                } finally {
                    try {
                        fos?.close()
                    } catch (var21: IOException) {
//                        RLog.e(TAG, "copyFile fos close", var21)
                    }
                    try {
                        fis?.close()
                    } catch (var20: IOException) {
//                        RLog.e(TAG, "copyFile fis close", var20)
                    }
                }
                var8
            }
        }
    }


    fun getFileNameWithPath(path: String): String? {
        return if (TextUtils.isEmpty(path)) {
//            RLog.e(TAG, "getFileNameWithPath path should not be null!")
            null
        } else {
            val start = path.lastIndexOf("/")
            if (start != -1) path.substring(start + 1) else null
        }
    }

    fun file2byte(file: File?): ByteArray? {
        return if (file == null) {
//            RLog.e(TAG, "file2byte file should not be null!")
            null
        } else if (!file.exists()) {
//            RLog.e(TAG, "file2byte: src file does not exist! -" + file.absolutePath)
            null
        } else {
            var buffer: ByteArray? = null
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(file)
                val bos = ByteArrayOutputStream()
                val b = ByteArray(1024)
                var n: Int
                while (fis.read(b).also { n = it } != -1) {
                    bos.write(b, 0, n)
                }
                bos.close()
                buffer = bos.toByteArray()
            } catch (var14: Exception) {
//                RLog.e(TAG, "file2byte: Exception!", var14)
            } finally {
                if (fis != null) {
                    try {
                        fis.close()
                    } catch (var13: IOException) {
//                        RLog.e(TAG, "file2byte: fis close!", var13)
                    }
                }
            }
            buffer
        }
    }


    fun byte2File(buf: ByteArray?, filePath: String?, fileName: String): File? {
        var bos: BufferedOutputStream? = null
        var fos: FileOutputStream? = null
        var file: File? = null
        try {
            val dir = File(filePath)
            if (!dir.exists()) {
//                RLog.d(TAG, "byte2File: dir does not exist!")
                val successMkdir = dir.mkdirs()
                if (!successMkdir) {
//                    RLog.e(TAG, "Created folders unSuccessfully")
                }
            }
            file = File(dir.path + File.separator + fileName)
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)
            bos.write(buf)
        } catch (var20: Exception) {
//            RLog.e(TAG, "byte2File: Exception!", var20)
        } finally {
            if (bos != null) {
                try {
                    bos.close()
                } catch (var19: IOException) {
//                    RLog.e(TAG, "byte2File: IOException!", var19)
                }
            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (var18: IOException) {
//                    RLog.e(TAG, "byte2File: IOException!", var18)
                }
            }
        }
        return file
    }

    fun isFileExists(filePath: String?): Boolean {

        return if (isEmpty(filePath)) {
            false
        } else File(filePath).exists()
    }

    fun copyFileIfNeed(context: Context, fileName: String, className: String): Boolean {
        val path = getFilePath(context, className + File.separator + fileName)
        if (path != null) {
            val file = File(path)
            if (!file.exists()) {
                /*
                 * 模型文件不存在
                 * The model file does not exist
                 * */
                try {
                    var folderpath: String? = null
                    var dataDir = context.applicationContext.getExternalFilesDir(null)
                    if (VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        dataDir = context.applicationContext.filesDir
                    }
                    if (dataDir != null) {
                        folderpath = dataDir.absolutePath + File.separator + className
                    }
                    val folder = File(folderpath)
                    if (!folder.exists()) {
                        folder.mkdirs()
                    }
                    if (file.exists()) file.delete()
                    file.createNewFile()
                    val `in` = context.assets.open(className + File.separator + fileName) ?: return false
                    val out: OutputStream = FileOutputStream(file)
                    val buffer = ByteArray(4096)
                    var n: Int
                    while (`in`.read(buffer).also { n = it } > 0) {
                        out.write(buffer, 0, n)
                    }
                    `in`.close()
                    out.close()
                } catch (e: IOException) {
                    file.delete()
                    return false
                }
            }
        }
        return true
    }

    fun getFilePath(context: Context, fileName: String): String? {
        var path: String? = null
        var dataDir = context.applicationContext.getExternalFilesDir(null)
        if (VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dataDir = context.applicationContext.filesDir
        }
        if (dataDir != null) {
            path = dataDir.absolutePath + File.separator + fileName
        }
        return path
    }


    /**
     * if string is empty
     *
     * @param input
     * @return boolean
     */
    fun isEmpty(input: String?): Boolean {
        if (input == null || "" == input.trim { it <= ' ' }) return true
        for (i in 0 until input.length) {
            val c = input[i]
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false
            }
        }
        return true
    }

    fun deleteFile(file: File) {
        //判断文件不为null或文件目录存在
        if (!file.exists()) {
//            Log.e("FileUtils", "文件删除失败,请检查文件路径是否正确")
            return
        }
        //取得这个目录下的所有子文件对象
        val files = file.listFiles()
        //遍历该目录下的文件对象
        files.forEach {
            if (it.isDirectory) {
                deleteFile(it)
            } else {
                it.delete()
            }
        }
    }

}
