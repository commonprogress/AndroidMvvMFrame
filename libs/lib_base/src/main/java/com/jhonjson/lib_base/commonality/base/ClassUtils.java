package com.jhonjson.lib_base.commonality.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.lifecycle.AndroidViewModel;

import com.jhonjson.lib_base.commonality.base.viewmodel.EmptyViewModel;

/**
 * 获取泛型ViewModel的class对象
 */
public class ClassUtils {
    /**
     * 获取泛型ViewModel的class对象
     */
    public static <T> Class<T> getViewModel(Object obj) {
        Class<?> currentClass = obj.getClass();
        Class<T> tClass = getGenericClass(currentClass);
        if (tClass == null || tClass == AndroidViewModel.class || tClass == EmptyViewModel.class) {
            return null;
        }
        return tClass;
    }

    private static <T> Class<T> getGenericClass(Class<?> mClass) {
        Type type = mClass.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        // 获取范型集合
        Type[] types = parameterizedType.getActualTypeArguments();
        for (Type t : types) {
            Class<T> tClass = (Class<T>) t;
            if (AndroidViewModel.class.isAssignableFrom(tClass)) {
                return tClass;
            }
        }
        return null;
    }
}
