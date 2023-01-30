package com.base.commonality.base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * @Author: jhosnjson
 * @Time: 2023/1/30
 * @Description 没有viewModel的情况
 */

public class EmptyViewModel extends AndroidViewModel {

    public EmptyViewModel(@NonNull Application application) {
        super(application);
    }
}
