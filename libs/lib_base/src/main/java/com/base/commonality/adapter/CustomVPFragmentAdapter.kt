package com.base.commonality.adapter


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * @Author: jhosnjson
 * @Time: 2023/1/30
 * @Remark: 通用viewpage fragment 使用
 */
class CustomVPFragmentAdapter(fragment: Fragment, var fragments: List<Fragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
