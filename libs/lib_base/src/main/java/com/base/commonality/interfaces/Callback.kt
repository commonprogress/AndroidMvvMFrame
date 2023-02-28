/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.base.commonality.interfaces

interface Callback<T> {
    fun call(callEntity: T)
}
