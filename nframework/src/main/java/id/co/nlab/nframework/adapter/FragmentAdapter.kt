package id.co.nlab.nframework.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/** fragment adapter for viewpager*/
class FragmentAdapter constructor(fm:FragmentManager,private val isShowTitle:Boolean):FragmentPagerAdapter(fm) {
    private var mFragment = ArrayList<Fragment>()
    private var mFragmentTitle = ArrayList<String>()

    override fun getItem(position: Int): Fragment { return  mFragment[position] }

    override fun getCount(): Int { return  mFragment.size}

    override fun getPageTitle(position: Int): CharSequence? {
        return if(isShowTitle) mFragmentTitle[position] else " "
    }

    fun addFragment(fragment: Fragment,title:String){
        mFragment.add(fragment)
        mFragmentTitle.add(title)
    }
}