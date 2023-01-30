package com.common.extend

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


/**
 * 类描述:activity扩展函数，只能用于单个组件内跳转
 * 创建人: lz
 * 创建时间: 2021/7/23
 * 修改备注:
 */
inline fun <reified T : Activity> Context.startActivity() {
    startActivity<T>(null)
}

inline fun <reified T : Activity> Context.startActivity(bundle: Bundle?) {
    val intent = Intent(this, T::class.java)
    bundle?.let { intent.putExtras(bundle) }
    startActivity(intent)
}

/**
 * 老的方式，需要重写onActivityResult方法接受参数
 */
inline fun <reified T : Activity> Activity.startActivityForResult(bundle: Bundle?, requestCode: Int) {
    val intent = Intent(this, T::class.java)
    bundle?.let { intent.putExtras(bundle) }
    startActivityForResult(intent, requestCode)
}

/**
 * 新的方式，直接回掉
 *
 * demo:
 * startActivityForResult<LiveReadyActivity>(Bundle().apply {
 *    putString("test", "test")
 * }){// 返回参数
 *   it.data?.extras?.getString("test1")
 *   }
 */
inline fun <reified T : ComponentActivity> ComponentActivity.startActivityForResult(
    bundle: Bundle?,
    callback: ActivityResultCallback<ActivityResult>,
) {
    val intent = Intent(this, T::class.java)
    bundle?.let { intent.putExtras(bundle) }
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback).launch(intent)
}


/**
 * 新的方式，直接回掉
 *
 * demo:
 * startActivityForResult<LiveReadyActivity>(Bundle().apply {
 *    putString("test", "test")
 * }){// 返回参数
 *   it.data?.extras?.getString("test1")
 *   }
 */
inline fun <reified T : ComponentActivity> Fragment.startActivityForResult(
    bundle: Bundle?,
    callback: ActivityResultCallback<ActivityResult>,
) {
    val intent = Intent(context, T::class.java)
    bundle?.let { intent.putExtras(bundle) }
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback).launch(intent)
}




