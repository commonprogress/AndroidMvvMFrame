package com.common.route

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter



/**
 * @author jhonjson
 * @date 2021/7/27.
 * description：路由跳转公共类
 */
object GoRouteUtils {

    /**
     * 获取fragment
     */
    fun getFragment(tag: String): Fragment {
        return ARouter.getInstance().build(tag).navigation() as Fragment
    }


    /**
     * 跳转发布朋友圈动态
     */
//    fun goTakeDynamicActivity() {
//        ARouter.getInstance()
//            .build(RouteLiveUtils.Live_TakeDynamicActivity)
//            .navigation()
//    }


}