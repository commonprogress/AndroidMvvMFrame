package com.base.commonality.ktx

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.base.commonality.BaseAppliction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


inline fun <T> Flow<T>.flowWithLifecycle2(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.(T) -> Unit,
) = lifecycleOwner.lifecycleScope.launch {
    //前后台切换时可以重复订阅数据。如：Lifecycle.State是STARTED，那么在生命周期进入 STARTED 状态时开始任务，在 STOPED 状态时停止订阅
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { block(it) }
    //另外一种写法：
    //    lifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
    //        collect {
    //            action(it)
    //        }
    //    }
}

/**
 * Toast扩展函数
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(BaseAppliction.application, message, duration).show()
}

fun Context.log(message: String) {
    Log.e("TTT", message)
}

fun log(message: String) {
    Log.e("TTT", message)
}

/**
 * Fragment中Toast扩展函数
 */
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(activity, message, duration).show()
}






