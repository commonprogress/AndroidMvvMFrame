package com.jhonjson.lib_base.commonality.constant

/**
 * Created by huangyayong
 * on 2021/9/10.
 * description：
 */
object BaseConstants {
    var isBackground : Boolean = false//是否处于后台

    /**布局中有ListPlayerView时，是否有下拉刷新的操作，
     * 因为下拉刷新时onInitComplete只执行一次*/
    var isRefresh : Boolean = false
}