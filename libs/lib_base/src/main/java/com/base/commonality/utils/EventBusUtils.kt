package com.base.commonality.utils

import org.greenrobot.eventbus.EventBus
import com.base.commonality.interfaces.IEvent


/**
 * @Author: jhosnjson
 * @Time: 2021/7/18
 * @Class: EventBusUtil
 * @Remark: EventBus工具类
 */
object EventBusUtils {

    /**
     * 订阅
     * @param subscriber 订阅者
     */
    @JvmStatic
    fun register(subscriber: Any) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber)
        }
    }

    /**
     * 解除注册
     * @param subscriber 订阅者
     */
    @JvmStatic
    fun unRegister(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    /**
     * 发送普通事件
     * @param event 事件
     */
    @JvmStatic
    fun postEvent(event: IEvent) = EventBus.getDefault().post(event)

    /**
     * 发送粘性事件
     * @param stickyEvent 粘性事件
     */
    fun postStickyEvent(stickyEvent: Any) = EventBus.getDefault().postSticky(stickyEvent)

    /**
     * 手动获取粘性事件
     * @param stickyEventType 粘性事件
     * @param <T>             事件泛型
     * @return 返回给定事件类型的最近粘性事件
     */
    fun <T> getStickyEvent(stickyEventType: Class<T>): T =
        EventBus.getDefault().getStickyEvent(stickyEventType)

    /**
     * 手动删除粘性事件
     * @param stickyEventType 粘性事件
     * @param <T>             事件泛型
     * @return 返回给定事件类型的最近粘性事件
     */
    fun <T> removeStickyEvent(stickyEventType: Class<T>): T =
        EventBus.getDefault().removeStickyEvent(stickyEventType)
}