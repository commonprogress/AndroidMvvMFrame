package com.base.commonality.adapter


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @Author: jhosnjson
 * @Time: 2023/1/30
 * @Remark: 通用viewpage activity 使用
 */
class CustomVPAdapter(activity: AppCompatActivity, var fragments: List<Fragment>) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
