package com.base.commonality.ktx

import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.base.commonality.BaseAppliction
import com.base.commonality.interfaces.IEvent
import com.base.commonality.utils.EventBusUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Interceptor
import org.json.JSONObject
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * 使用 Flow 做的简单的轮询
 * 请使用单独的协程来进行管理该 Flow
 * Flow 仍有一些操作符是实验性的 使用时需添加 @InternalCoroutinesApi 注解
 * @param intervals 轮询间隔时间/毫秒
 * @param block 需要执行的代码块
 */
suspend fun startPolling(intervals: Long, block: () -> Unit) {
    flow {
        while (true) {
            delay(intervals)
            emit(0)
        }
    }
        .catch {
//            Log.e("flow", "startPolling: $it")
        }
        .flowOn(Dispatchers.Main)
        .collect { block.invoke() }
}
/**************************************************************************************************/

/**
 * 发送普通EventBus事件
 *
 */
fun sendEvent(event: IEvent) = EventBusUtils.postEvent(event)

/**************************************************************************************************/
/**
 * 阿里路由不带参数跳转
 * @param routerUrl String 路由地址
 */
fun aRouterJump(routerUrl: String) {
    ARouter.getInstance().build(routerUrl).navigation()
}

/**************************************************************************************************/
/**
 * 获取App版本号
 * @return Long App版本号
 */
private var versionCode = 0L
fun getVersionCode(): Long {
    Log.d("utils", "versionCode:$versionCode")
    if (versionCode != 0L) return versionCode
    val packageInfo = BaseAppliction.mContext
        .packageManager
        .getPackageInfo(BaseAppliction.mContext.packageName, 0)
    versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        packageInfo.versionCode.toLong()
    }
    return versionCode
}

private var androidId = ""
fun getAndroidId(context: Context): String {
    Log.d("utils", "androidId:$androidId")
    if (androidId.isNotEmpty()) return androidId
    try {
        val contentResolver: ContentResolver = context.contentResolver
        androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        return androidId
    } catch (e: java.lang.Exception) {
    }
    return ""
}

/**
 * 获得设备硬件标识
 *
 * @param context 上下文
 * @return 设备硬件标识
 */
private var deviceIdString = ""
fun getDeviceId(context: Context): String {
    Log.d("utils", "deviceIdString:$deviceIdString")
    if (deviceIdString.isNotEmpty()) return deviceIdString
    val sbDeviceId = StringBuilder()

    //获得设备默认IMEI（>=6.0 需要ReadPhoneState权限）
    var imei: String? = ""

    //获得AndroidId（无需权限）
    val androidid: String = getAndroidId(context)
    //获得设备序列号（无需权限）
    val serial: String = getSERIAL()
    //获得硬件uuid（根据硬件相关属性，生成uuid）（无需权限）
    val uuid: String = getDeviceUUID()

    //追加androidid
    if (androidid != null && androidid.isNotEmpty()) {
        sbDeviceId.append(androidid)
        sbDeviceId.append("|")
    }
    //追加serial
    if (serial != null && serial.isNotEmpty()) {
        sbDeviceId.append(serial)
        sbDeviceId.append("|")
    }
    //追加硬件uuid
    if (uuid != null && uuid.isNotEmpty()) {
        sbDeviceId.append(uuid)
    }

    //生成SHA1，统一DeviceId长度
    if (sbDeviceId.isNotEmpty()) {
        try {
            val hash: ByteArray = getHashByString(sbDeviceId.toString())
            val sha1: String = bytesToHex(hash)
            if (sha1 != null && sha1.isNotEmpty()) {
                //返回最终的DeviceId
                deviceIdString = sha1
                return deviceIdString
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    //如果以上硬件标识数据均无法获得，
    //则DeviceId默认使用系统随机数，这样保证DeviceId不为空
    deviceIdString = UUID.randomUUID().toString().replace("-", "")
    return deviceIdString
}


/**
 * 获得设备硬件uuid
 * 使用硬件信息，计算出一个随机数
 *
 * @return 设备硬件uuid
 */
private var deviceUUID = ""
private fun getDeviceUUID(): String {
    Log.d("utils", "deviceUUID:$deviceUUID")
    if (deviceUUID.isNotEmpty()) return deviceUUID
    return try {
        val dev =
            "3883756" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.DEVICE.length % 10 + Build.HARDWARE.length % 10 + Build.ID.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10 + Build.SERIAL.length % 10
        deviceUUID = UUID(
            dev.hashCode().toLong(),
            Build.SERIAL.hashCode().toLong()
        ).toString()
        deviceUUID
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
        ""
    }
}

/**
 * 获得设备序列号（如：WTK7N16923005607）, 个别设备无法获取
 *
 * @return 设备序列号
 */
private var sERIAL = ""
private fun getSERIAL(): String {
    Log.d("utils", "sERIAL:$sERIAL")
    if (sERIAL.isNotEmpty()) return sERIAL
    try {
        sERIAL = Build.SERIAL
        return sERIAL
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
    return ""
}

/**
 * 转16进制字符串
 *
 * @param data 数据
 * @return 16进制字符串
 */
private fun bytesToHex(data: ByteArray): String {
    val sb = StringBuilder()
    var stmp: String
    for (n in data.indices) {
        stmp = Integer.toHexString(data[n].toInt() and 0xFF)
        if (stmp.length == 1) sb.append("0")
        sb.append(stmp)
    }
    return sb.toString().toUpperCase(Locale.CHINA)
}

/**
 * 取SHA1
 *
 * @param data 数据
 * @return 对应的hash值
 */
private fun getHashByString(data: String): ByteArray {
    return try {
        val messageDigest: MessageDigest = MessageDigest.getInstance("SHA1")
        messageDigest.reset()
        messageDigest.update(data.toByteArray(charset("UTF-8")))
        messageDigest.digest()
    } catch (e: java.lang.Exception) {
        "".toByteArray()
    }
}

/**
 * 获取渠道全路径
 * @return
 */
private var channelFullString: String? = null
fun getChannelFull(): String? {
    Log.d("utils", "channelFullString:$channelFullString")
    if (channelFullString?.isNotEmpty() == true) return channelFullString
    try {
        val metaData: Bundle = BaseAppliction.mContext.packageManager
            .getApplicationInfo(
                BaseAppliction.mContext.packageName,
                PackageManager.GET_META_DATA
            ).metaData
        val channel = metaData["UMENG_CHANNEL"] as String
        channelFullString = channel
        return channelFullString
    } catch (ignore: java.lang.Exception) {
    }
    return "dev"
}

/**
 * 获取渠道名
 * @return
 */
private var channelString: String? = null
fun getChannel(): String? {
    Log.d("utils", "channelString:$channelString")
    if (channelString?.isNotEmpty() == true) return channelString
    try {
        channelString = splitNotNumber(getChannelFull())
        return channelString
    } catch (ignore: java.lang.Exception) {
    }
    return "dev"
}

/**
 * 获取渠道号
 * @return
 */
private var channelNo: String? = null
fun getChannelNo(): String? {
    Log.d("utils", "channelNo:$channelNo")
    if (channelNo?.isNotEmpty() == true) return channelNo
    try {
        channelNo = getNumbers(getChannelFull())
        return channelNo
    } catch (ignore: java.lang.Exception) {
    }
    return "0"
}

//截取数字
fun getNumbers(content: String?): String? {
    val pattern: Pattern = Pattern.compile("\\d+")
    val matcher: Matcher = pattern.matcher(content)
    while (matcher.find()) {
        return matcher.group(0)
    }
    return ""
}

// 截取非数字
fun splitNotNumber(content: String?): String? {
    val pattern = Pattern.compile("\\D+")
    val matcher = pattern.matcher(content)
    while (matcher.find()) {
        return matcher.group(0)
    }
    return ""
}


/**************************************************************************************************/
/**
 * 获取App版本名
 * @return String App版本名
 */
private var versionName = ""
fun getVersionName(): String {
    Log.d("utils", "versionName:$versionName")
    if (versionName.isNotEmpty()) return versionName
    versionName = BaseAppliction.mContext
        .packageManager
        .getPackageInfo(BaseAppliction.mContext.packageName, 0)
        .versionName
    return versionName
}

/**************************************************************************************************/
/**
 * 获取App包名
 * @return String App包名
 */
fun getPackageName(): String {
    return try {
        BaseAppliction.mContext
            .packageManager
            .getPackageInfo(BaseAppliction.mContext.packageName, 0)
            .packageName
    } catch (e: Exception) {
        ""
    }

}

val lock = Object()

fun getSign(chain: Interceptor.Chain): String = synchronized(lock) {

    var charString: String = ""
    array.clear()
    map.clear()
    if (chain.request().method.equals("POST")) {

        chain.request().body?.let {
            val buffer = okio.Buffer()
            it.writeTo(buffer)

            val contentType = it.contentType()
            val charset: Charset =
                contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

            charString = buffer.readString(charset)
            Log.e("TAG GETSIGN", "charString:" + charString)
        }

        if (charString.isNullOrEmpty()) {
            var nonce = getNonce()
            var timestamp = "" + System.currentTimeMillis()

            array.add("nonce")
            array.add("timestamp")
            map.put("nonce", nonce)
            map.put("timestamp", timestamp)
        } else {
            try {
                var jo: JSONObject = JSONObject(charString)
                var iterator: MutableIterator<String> = jo.keys()
                while (iterator.hasNext()) {

                    var key: String = iterator.next()
                    try {
                        var value: String = jo.getString(key)
                        Log.e("TAG GETSIGN", "key=" + key + ":  value=" + value)
                        Log.e("TAG GETSIGN", "array:  value=  " + value.contains("["))
                        if (key != "password") {
                            if (value.contains("[") || value.contains("{")) {
                                value = ""
                            } else if (value == " ") {
                                value = ""
                            }
                        }
                        if (value.isNotEmpty()) {
                            array.add(key)
                            map.put(key, value)
                        }
                    } catch (e: Exception) {
                        Log.e("TAG GETSIGN", "charString 1111 :" + e.message)
                    }
                }

                if (array.size == 0) {
                    var nonce = getNonce()
                    var timestamp = "" + System.currentTimeMillis()

                    array.add("nonce")
                    array.add("timestamp")
                    map.put("nonce", nonce)
                    map.put("timestamp", timestamp)
                }
            } catch (e: java.lang.Exception) {
                Log.e("TAG GETSIGN", "charString 2222 :" + e.message)
                return ""
            }
        }
    } else {
        var nonce = getNonce()
        var timestamp = "" + System.currentTimeMillis()

        array.add("nonce")
        array.add("timestamp")
        map.put("nonce", nonce)
        map.put("timestamp", timestamp)
    }

    if (array.size == 0) {
        return ""
    }

    array.sort()
    var sTr: String = ""
    for (str: String in array) {
        sTr += str + "=" + map.get(str) + "&"
    }

    sTr += "privateKey=i07QG4yNmyz7Z9wVtc8LJB6oArHm2cuT"
//    sTr = sTr.replace(" ", "")
    Log.e("TAG GETSIGN", "-->" + sTr)
    lock.notifyAll()
    return md5(sTr)

}


fun getParams(params: Map<String, Any?>?): Map<String, Any?> {
    var hs = HashMap<String, Any?>()
    if (params != null)
        hs.putAll(params)

    hs.put("nonce", getNonce())
    hs.put("timestamp", "" + System.currentTimeMillis())
    return hs
}

fun getParams2(params: Map<String, String?>?): Map<String, String?> {
    var hs = HashMap<String, String?>()
    if (params != null)
        hs.putAll(params)
    hs.put("nonce", getNonce())
    hs.put("timestamp", "" + System.currentTimeMillis())
    return hs
}

fun getNonce(): String {
    return md5("" + System.currentTimeMillis() + getIpAddress() + getDeviceUUID())
}

var array = mutableListOf<String>()
var map: HashMap<String, String> = HashMap<String, String>()

/**彭冲让加到请求头部*/
fun getDeviceNumber(): String {
    return md5("" + System.currentTimeMillis() + getDeviceUUID())
}

/** md5加密 */
fun md5(content: String): String {
    val hash = MessageDigest.getInstance("MD5").digest(content.toByteArray())
    val hex = StringBuilder(hash.size * 2)
    for (b in hash) {
        var str = Integer.toHexString(b.toInt())
        if (b < 0x10) {
            str = "0$str"
        }
        hex.append(str.substring(str.length - 2))
    }
    return hex.toString().toUpperCase()
}

private var ipAddress = ""
fun getIpAddress(): String {
    Log.d("utils", "ipAddress:$ipAddress")
    if (ipAddress.isNotEmpty()) return ipAddress
    var ip = ""
    val conMann =
        BaseAppliction.mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val mobileNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
    val wifiNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    if (mobileNetworkInfo!!.isConnected) {
        ip = getLocalIpAddress()
        System.out.println("本地ip-----$ip")
    } else if (wifiNetworkInfo!!.isConnected) {
        val wifiManager = BaseAppliction.mContext.getApplicationContext()
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        ip = intToIp(ipAddress)
        System.out.println("wifi_ip地址为------$ip")
    }
    ipAddress = ip
    Log.e("TAG", "ip:" + ip)
    return ip
}

//如果连接的是移动网络，第二步，获取本地ip地址：getLocalIpAddress();这样获取的是ipv4格式的ip地址。
private var localIpAddress = ""
fun getLocalIpAddress(): String {
    Log.d("utils", "localIpAddress:$localIpAddress")
    if (localIpAddress.isNotEmpty()) return localIpAddress
    var ipv4: String = ""
    try {
        val nilist: ArrayList<NetworkInterface> =
            Collections.list(NetworkInterface.getNetworkInterfaces())
        for (ni in nilist) {
            val ialist: ArrayList<InetAddress> = Collections.list(ni.getInetAddresses())
//            for (address in ialist) {
//                if (!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(
//                        address.getHostAddress().also {
//                            ipv4 = it
//                        })
//                ) {
//                    return ipv4
//                }
//            }
        }
    } catch (ex: SocketException) {
        Log.e("localip", ex.toString())
    }
    localIpAddress = ipv4
    return ipv4
}

//如果连接的是WI-FI网络，第三步，获取WI-FI ip地址：intToIp(ipAddress);
fun intToIp(ipInt: Int): String {
    val sb = StringBuilder()
    sb.append(ipInt and 0xFF).append(".")
    sb.append(ipInt shr 8 and 0xFF).append(".")
    sb.append(ipInt shr 16 and 0xFF).append(".")
    sb.append(ipInt shr 24 and 0xFF)
    return sb.toString()
}

/**
 * Check domain
 * 校验H5白名单
 * @param url
 * @return true在白名单中   false不在白名单中
 */
fun checkDomain(url: String): Boolean {
    val whitelist = arrayOf(
        "wondertemple.com",
        "xinlangcentury.com",
        "wh-djz.com"
    )

    var isHave = false
    run braking@{
        whitelist.forEach {
            Log.d("checkDomain", "checkDomain: it:${it}")
            isHave = url.indexOf(it) != -1
            if (isHave)
                return@braking
        }
    }

    return isHave
}


/**
 * Get web sign
 * H5请求接口时参数加签
 * @param string 参数字符串（JSON格式）
 * @return 加签后的字符串
 */
fun getWebSign(string: String): String = synchronized(lock) {
    var charString = string
    array.clear()
    map.clear()
    if (charString.isNullOrEmpty()) {
        var nonce = getNonce()
        var timestamp = "" + System.currentTimeMillis()

        array.add("nonce")
        array.add("timestamp")
        map.put("nonce", nonce)
        map.put("timestamp", timestamp)
    } else {
        try {
            var jo: JSONObject = JSONObject(charString)
            var iterator: MutableIterator<String> = jo.keys()
            while (iterator.hasNext()) {

                var key: String = iterator.next()
                try {
                    var value: String = jo.getString(key)
                    Log.e("TAG WEBSIGN", "key=" + key + ":  value=" + value)
                    Log.e("TAG WEBSIGN", "array:  value=  " + value.contains("["))
                    if (value.contains("[") || value.contains("{")) {
                        value = ""
                    } else if (value == " ") {
                        value = ""
                    }
                    if (value.isNotEmpty()) {
                        array.add(key)
                        map.put(key, value)
                    }
                } catch (e: Exception) {
                    Log.e("TAG WEBSIGN", "charString 1111 :" + e.message)
                }
            }

            if (array.size == 0) {
                var nonce = getNonce()
                var timestamp = "" + System.currentTimeMillis()

                array.add("nonce")
                array.add("timestamp")
                map.put("nonce", nonce)
                map.put("timestamp", timestamp)
            }
        } catch (e: java.lang.Exception) {
            Log.e("TAG WEBSIGN", "charString 2222 :" + e.message)
            return ""
        }
    }

    if (array.size == 0) {
        return ""
    }

    array.sort()
    var sTr: String = ""
    for (str: String in array) {
        sTr += str + "=" + map.get(str) + "&"
    }

    sTr += "privateKey=i07QG4yNmyz7Z9wVtc8LJB6oArHm2cuT"
//    sTr = sTr.replace(" ", "")
    Log.e("TAG WEBSIGN", "-->" + sTr)
    lock.notifyAll()
    return md5(sTr)

}
